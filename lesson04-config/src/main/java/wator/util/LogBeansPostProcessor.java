package wator.util;

import jakarta.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Named
public class LogBeansPostProcessor implements BeanPostProcessor {
    private Logger log = LogManager.getLogger(LogBeansPostProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info(() -> beanName + ": " + bean);
        return bean;
    }

}
