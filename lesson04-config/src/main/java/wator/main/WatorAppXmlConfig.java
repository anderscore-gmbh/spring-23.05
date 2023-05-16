package wator.main;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WatorAppXmlConfig {

    public static void main(String[] args) throws IOException {
        WatorAppXmlConfig app = new WatorAppXmlConfig();
        app.plumb();
    }

    // tag::plumb[]
    private void plumb() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
    }
    // end::plumb[]
}
