package wator.main;

import wator.core.FishStrategy;
import wator.core.SharkStrategy;
import wator.core.WorldEngine;
import wator.simple.SimpleTorus;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WatorAppNoSpring {
    private SimpleTorus torus;
    private WorldEngine worldEngine;

    public static void main(String[] args) throws IOException {
        WatorAppNoSpring app = new WatorAppNoSpring();
        app.plumb();
        app.run();
    }

    private void plumb() {
        Random rnd = ThreadLocalRandom.current();

        torus = new SimpleTorus(60, 10);

        FishStrategy fishStrategy = new FishStrategy();
        fishStrategy.setBreedTime(3);
        fishStrategy.setRnd(rnd);
        fishStrategy.setTorus(torus);

        SharkStrategy sharkStrategy = new SharkStrategy();
        sharkStrategy.setBreedTime(5);
        sharkStrategy.setRnd(rnd);
        sharkStrategy.setStarveTime(4);
        sharkStrategy.setTorus(torus);

        worldEngine = WorldEngine.createPopulatedWorld(0.3, // initial rate of fish per cell
                0.1, // initial rate of shark per cell
                rnd, // random base
                torus, // the torus the creatures live in
                new FishStrategy[] { fishStrategy, sharkStrategy });
    }

    private void run() throws IOException {
        torus.dump(0);
        for (int i = 1; i <= 20; i++) {
            worldEngine.tick();
            torus.dump(i);
        }
    }
}
