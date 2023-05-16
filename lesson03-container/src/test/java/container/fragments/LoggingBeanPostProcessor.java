package container.fragments;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// tag::post-proc[]
public class LoggingBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Hier könnte man ein Stellvertreterobjekt zurückgeben, z. B. Proxy- oder
        // Decorator
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.printf("after-init %12s: %s%n", beanName, bean);
        return bean;
    }
}
// end::post-proc[]