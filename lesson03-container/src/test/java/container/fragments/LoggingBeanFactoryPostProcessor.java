package container.fragments;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Arrays;

// tag::factory-post-proc[]
public class LoggingBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Hier könnte man den Container ändern, bevor die ersten Beans erzeugt werden.
        System.out.println("bean-factory: " + Arrays.toString(beanFactory.getBeanDefinitionNames()));
    }
}
// end::factory-post-proc[]