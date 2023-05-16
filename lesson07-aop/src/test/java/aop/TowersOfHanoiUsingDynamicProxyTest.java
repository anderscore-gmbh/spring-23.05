package aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class TowersOfHanoiUsingDynamicProxyTest {

    private Deque<Integer> towers[] = new Deque[3];

    class StackInvocationHandler implements InvocationHandler {
        private Deque<Integer> tower;
        private int index;

        public StackInvocationHandler(Deque<Integer> tower, int index) {
            this.tower = tower;
            this.index = index;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.print("tower " + index + ": " + tower + " "
                    + method.getName() + (args == null ? "" : " " + Arrays.toString(args)));
            Object result = method.invoke(tower, args);
            System.out.println(" -> " + tower
                    + (result == null ? "" : ", " + result));
            return result;
        }
    }

    @BeforeEach
    void init() {
        for (int i = 0; i < towers.length; i++) {
            towers[i] = wrap(new LinkedList<>(), i);
        }
        IntStream.rangeClosed(1, 5).forEach(towers[0]::add);
    }

    private Deque<Integer> wrap(Deque<Integer> stack, int index) {
        // TODO: Verpacken Sie jeden Stack mit einem Dynamic Proxy, der die Methodenaufrufe protokolliert.
        // Verwenden Sie dazu den StackInvocationHandler.
        return stack;
    }

    @Test
    public void test() {
        dump();
        solve();
        dump();
    }

    private void solve() {
        move(towers[0].size(), towers[0], towers[2], towers[1]);
    }

    private void move(int count, Deque<Integer> source, Deque<Integer> target, Deque<Integer> temp) {
        if (count == 1) {
            target.push(source.pop());
        } else {
            temp.push(source.pop());
            move(count - 1, source, target, temp);
            target.push(temp.pop());
        }
    }

    private void dump() {
        StringBuilder sb = new StringBuilder(" ->");
        for (int i = 0; i < towers.length; i++) {
            sb.append(" tower " + i + ": " + towers[i] + " ");
        }
        System.out.println(sb);
    }
}
