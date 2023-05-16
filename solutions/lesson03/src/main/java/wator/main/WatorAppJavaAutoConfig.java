package wator.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wator.config.WatorAutoConfig;
import wator.core.WorldEngine;
import wator.simple.SimpleTorus;

import java.io.IOException;

public class WatorAppJavaAutoConfig {
    private SimpleTorus torus;
    private WorldEngine worldEngine;

    public static void main(String[] args) throws IOException {
        WatorAppJavaAutoConfig app = new WatorAppJavaAutoConfig();
        app.plumb();
        app.run();
    }

    // tag::plumb[]
    private void plumb() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WatorAutoConfig.class);

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
