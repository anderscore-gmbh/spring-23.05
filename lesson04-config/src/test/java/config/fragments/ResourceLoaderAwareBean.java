package config.fragments;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.Properties;

// tag::main[]
public class ResourceLoaderAwareBean implements ResourceLoaderAware, InitializingBean {
    private ResourceLoader resourceLoader;
    private Properties config;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:config.properties");
        config = new Properties();
        config.load(resource.getInputStream());
    }
}
// end::main[]