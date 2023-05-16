package reactor.fragments;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NonBlockingIOTest {
    private static final Logger log = LoggerFactory.getLogger(NonBlockingIOTest.class);
    private static final int SERVER_PORT = 9987;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final int CLIENT_COUNT = 10;
    private static final int MESSAGE_DELAY = 100;
    private static final int NUM_MESSAGES = 10;

    private Selector selector;
    private ServerSocketChannel server;
    private final ByteBuffer serverBuffer = ByteBuffer.allocate(64);
    private CountDownLatch countDownLatch = new CountDownLatch(CLIENT_COUNT);

    @Test
    void testUsingBuffer() {
        ByteBuffer buf = ByteBuffer.allocate(16);
        buf.put(UTF8.encode("some message"));
        buf.put(UTF8.encode("more"));
        buf.flip();
        System.out.println("1: " + UTF8.decode(buf));
        buf.flip();
        buf.put(UTF8.encode("more"));
        buf.flip();
        System.out.println("2: " + UTF8.decode(buf));
    }

    @Test
    void testNonBlockingCommunication() throws InterruptedException, IOException {
        selector = Selector.open();
        server = startEchoServer();
        run("server", this::runEchoServerAndLogExceptions);
        for (int i = 1; i <= CLIENT_COUNT; i++) {
            run("client-" + i, new Client());
        }
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    private ServerSocketChannel startEchoServer() throws IOException {
        // tag::server[]
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(SERVER_PORT));
        server.configureBlocking(false); // <1>
        server.register(selector, SelectionKey.OP_ACCEPT); // <2>
        // end::server[]
        return server;
    }

    private void run(String threadName, Runnable runnable) {
        Thread thread = new Thread(runnable, threadName);
        thread.start();
    }

    private void runEchoServerAndLogExceptions() {
        try {
            runEchoServer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void runEchoServer() throws IOException {
        while (countDownLatch.getCount() > 0) {
            waitForNextIO();
        }
    }

    private void waitForNextIO() throws IOException {
        // tag::select[]
        int count = selector.select(); // <1>
        Set<SelectionKey> selectedKeys = selector.selectedKeys();

        Iterator<SelectionKey> iter = selectedKeys.iterator();
        while (iter.hasNext()) {
            SelectionKey selectedKey = iter.next();
            if (selectedKey.isAcceptable()) {
                handleNewClientConnection(selectedKey);
            } else if (selectedKey.isReadable()) {
                handleDataReceivedFormClient(selectedKey);
            }
            iter.remove();
        }
        // end::select[]
    }

    private void handleNewClientConnection(SelectionKey selectedKey) throws IOException {
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }

    private void handleDataReceivedFormClient(SelectionKey selectedKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectedKey.channel();
        serverBuffer.clear();
        channel.read(serverBuffer);
        serverBuffer.flip();
        String received = UTF8.decode(serverBuffer).toString();
        log.info("received: " + received);

        serverBuffer.clear();
        serverBuffer.put(UTF8.encode("echo: " + received));
        serverBuffer.flip();
        channel.write(serverBuffer);

        if ("exit".equals(received.trim())) {
            countDownLatch.countDown();
            channel.close();
        }
    }

    static class Client implements Runnable {
        private final ByteBuffer sendBuffer = ByteBuffer.allocate(64);
        private final ByteBuffer receiveBuffer = ByteBuffer.allocate(64);
        private String name;
        private SocketChannel channel;

        @Override
        public void run() {
            name = Thread.currentThread().getName();
            try {
                connectAndSendMessages();
            } catch (IOException | InterruptedException ex) {
                log.error("client " + name + " failed", ex);
            }
        }

        private void connectAndSendMessages() throws IOException, InterruptedException {
            try (SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", SERVER_PORT))) {
                this.channel = channel;
                sendMessages();
            } finally {
                this.channel = null;
            }
        }

        private void sendMessages() throws IOException, InterruptedException {
            for (int i = 0; i < NUM_MESSAGES; i++) {
                transfer("message-" + i + " from " + name);
                Thread.sleep(MESSAGE_DELAY);
            }
            transfer("exit");
        }

        private String transfer(String message) throws IOException {
            log.info("sendig: " + message);
            sendBuffer.clear();
            sendBuffer.put(UTF8.encode(message));
            sendBuffer.flip();
            receiveBuffer.clear();
            do {
                channel.write(sendBuffer);
                channel.read(receiveBuffer);
            } while (sendBuffer.hasRemaining());
            receiveBuffer.flip();
            String response = UTF8.decode(receiveBuffer).toString();
            log.info("response: " + response);
            return response;
        }
    }
}
