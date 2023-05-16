package reactor.fragments;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class ScheduleTest {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

    @Test
    public void test() throws InterruptedException {
        Runnable beeper = () -> System.out.println("beep");
        ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 2, 1, TimeUnit.SECONDS);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        scheduler.schedule(countDownLatch::countDown, 10, TimeUnit.SECONDS);
        countDownLatch.await();
    }
}
