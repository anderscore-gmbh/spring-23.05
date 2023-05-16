package aop.fragments;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;

public class ProxyFactoryTest {
    private List<Integer> list = IntStream.range(1, 7).boxed().collect(Collectors.toList());

    @Test
    void testTraceShuffle() {
        list = wrap(list);
        Collections.shuffle(list);
        System.out.println(list);
    }

    // tag::wrap[]
    private <T> List<T> wrap(List<T> list) {
        ProxyFactory proxyFactory = new ProxyFactory(list);
        proxyFactory.addAdvisor(new RegexpMethodPointcutAdvisor(".*\\.[gs]et", new MethodInterceptor() {

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                String call = invocation.getMethod().getName() + "(" + Arrays.toString(invocation.getArguments()) + ")";
                Object result = invocation.proceed();
                System.out.println(call + ": " + result);
                return result;
            }
        }));
        Object wrapped = proxyFactory.getProxy();
        return (List<T>) wrapped;
    }
    // end::wrap[]
}
