package wator.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import wator.config.WatorSimpleConfig;

public class WatorAppSimple {

    public static void main(String[] args) throws IOException {
        WatorAppSimple app = new WatorAppSimple();
        app.plumb();
    }

    private void plumb() {
        new AnnotationConfigApplicationContext(WatorSimpleConfig.class);
    }

    // tag::dumpProperties[]
    private void dumpProperties(InputStream is) throws IOException {
        Properties props = new Properties();
        props.load(is);
        props.list(System.out);
    }
    // end::dumpProperties[]
}
