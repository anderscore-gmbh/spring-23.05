package config.fragments;

import org.springframework.core.env.PropertySource;

import java.util.Map;

// tag::src[]
public class SimpleMapPropertySource extends PropertySource<Map<String, Object>> {
    public SimpleMapPropertySource(String name, Map<String, Object> source) {
        super(name, source); // <1>
    }

    @Override
    public Object getProperty(String name) { // <2>
        return source.get(name);
    }
}
// end::src[]
