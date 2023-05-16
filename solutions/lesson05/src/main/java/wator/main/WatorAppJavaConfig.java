package wator.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wator.config.WatorConfig;
import wator.core.WorldEngine;
import wator.simple.SimpleTorus;

import jakarta.inject.Inject;
import java.io.IOException;

public class WatorAppJavaConfig {
    private static final Logger log = LoggerFactory.getLogger(WatorAppJavaConfig.class);

    @Inject
    private SimpleTorus torus;
    @Inject
    private WorldEngine worldEngine;

    public static void main(String[] args) throws IOException {
        WatorAppJavaConfig app = new WatorAppJavaConfig();
        app.plumb();
        app.run();
    }

    private void plumb() throws IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WatorConfig.class);
        ctx.getAutowireCapableBeanFactory().autowireBean(this);
    }

    private void run() throws IOException {
        torus.dump(0);
        for (int i = 1; i <= 20; i++) {
            worldEngine.tick();
            torus.dump(i);
        }
    }
}
