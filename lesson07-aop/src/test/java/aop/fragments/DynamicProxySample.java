package aop.fragments;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

// tag::proxy[]
public class DynamicProxySample implements InvocationHandler {

    interface Greeter {
        String say(String greeting);
    }

    @Test
    void testDynamicProxy() {
        Greeter greeterProxy = (Greeter) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[] { Greeter.class }, this);
        System.out.println(greeterProxy.say("World"));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return method.getName() + ": Hello " + args[0] + "!";
    }
}
// end::proxy[]