package wator.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import wator.core.WorldEngine;
import wator.simple.SimpleTorus;

import java.io.IOException;

public class WatorAppXmlAutoConfig {
    private SimpleTorus torus;
    private WorldEngine worldEngine;

    public static void main(String[] args) throws IOException {
        WatorAppXmlAutoConfig app = new WatorAppXmlAutoConfig();
        app.plumb();
        app.run();
    }

    // tag::plumb[]
    private void plumb() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("auto-context.xml");

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
