package wator.main;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WatorAppXmlScanConfig {

    public static void main(String[] args) throws IOException {
        WatorAppXmlScanConfig app = new WatorAppXmlScanConfig();
        app.plumb();
    }

    private void plumb() {
        new ClassPathXmlApplicationContext("scan-context.xml");
    }
}
