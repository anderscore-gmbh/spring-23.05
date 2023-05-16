package config.fragments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class ResourceTest {

    @Configuration
    static class Config {
        @Bean
        ResourceLoaderAwareBean resourceLoaderAwareBean() {
            return new ResourceLoaderAwareBean();
        }
    }

    @Value("classpath:config.properties")
    private Resource propertiesResource;

    @Autowired
    private ResourceLoaderAwareBean resourceLoaderAwareBean;

    @Test
    void dumpResource() throws IOException {
        dumpPropertiesResource(propertiesResource);
    }

    @Test
    void displayResourceProperties() throws IOException {
        DefaultResourceLoader loader = new DefaultResourceLoader();

        Resource resource = loader.getResource("ftp:/config/fragments/ResourceTest.class");
        System.out.println(resource.getDescription());
        System.out.println(resource.getURI());
    }

    @Test
    void useResourceAbstractionOnly() throws IOException {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:config.properties");
        dumpPropertiesResource(resource);
    }

    @Test
    void testResourceLoaderAwareBean() {
        Properties config = (Properties) ReflectionTestUtils.getField(resourceLoaderAwareBean, "config");
        assertThat(config.get("server.port")).isEqualTo("8081");
    }

    private void dumpPropertiesResource(Resource resource) throws IOException {
        Properties props = new Properties();
        props.load(resource.getInputStream());
        System.out.println(props);
    }
}