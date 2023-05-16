package wator.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wator.config.WatorConfig;
import wator.core.WorldEngine;
import wator.simple.SimpleTorus;

import java.io.IOException;

public class WatorAppJavaConfig {
    private SimpleTorus torus;
    private WorldEngine worldEngine;

    public static void main(String[] args) throws IOException {
        WatorAppJavaConfig app = new WatorAppJavaConfig();
        app.plumb();
        app.run();
    }

    // tag::plumb[]
    private void plumb() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WatorConfig.class);

        torus = ctx.getBean("torus", SimpleTorus.class);
        worldEngine = ctx.getBean("worldEngine", WorldEngine.class);
    }
    // end::plumb[]

    private void run() throws IOException {
        torus.dump(0);
        for (int i = 1; i <= 20; i++) {
            worldEngine.tick();
            torus.dump(i);
        }
    }
}
