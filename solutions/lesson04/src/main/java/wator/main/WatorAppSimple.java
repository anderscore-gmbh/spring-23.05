package wator.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import wator.config.WatorSimpleConfig;

public class WatorAppSimple {

    public static void main(String[] args) throws IOException {
        WatorAppSimple app = new WatorAppSimple();
        app.plumb();
    }

    private void plumb() throws IOException {
        var ctx = new AnnotationConfigApplicationContext(WatorSimpleConfig.class);

        // Lösung Aufgabe 1
        Resource resource = ctx.getResource("wator-prod.properties");
        dumpProperties(resource.getInputStream());
    }

    // tag::dumpProperties[]
    private void dumpProperties(InputStream is) throws IOException {
        Properties props = new Properties();
        props.load(is);
        props.list(System.out);
    }
    // end::dumpProperties[]
}
