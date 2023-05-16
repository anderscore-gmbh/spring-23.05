package wator.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import wator.core.WorldEngine;
import wator.simple.SimpleTorus;

import jakarta.inject.Inject;
import java.io.IOException;

public class WatorAppXmlConfig {
    @Inject
    private SimpleTorus torus;
    @Inject
    private WorldEngine worldEngine;

    public static void main(String[] args) throws IOException {
        WatorAppXmlConfig app = new WatorAppXmlConfig();
        app.plumb();
        app.run();
    }

    private void plumb() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
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
