package wator.main;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import wator.config.WatorScanOnlyConfig;

public class WatorAppJavaScanOnlyConfig {

    public static void main(String[] args) throws IOException {
        WatorAppJavaScanOnlyConfig app = new WatorAppJavaScanOnlyConfig();
        app.plumb();
    }

    private void plumb() {
        new AnnotationConfigApplicationContext(WatorScanOnlyConfig.class);
    }
}
