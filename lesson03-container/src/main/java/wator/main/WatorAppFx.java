package wator.main;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import wator.core.FishStrategy;
import wator.core.SharkStrategy;
import wator.core.WorldEngine;
import wator.fx.WritableImageTorus;

/**
 * Wator mit Java Fx
 *
 * <p>Aufruf mit Java 11: <code>--module-path C:\Tools\javafx-sdk-11.0.1\lib --add-modules=javafx.controls</code>.
 * Dafuer ist <a href="https://gluonhq.com/products/javafx/">JavaFX runtime</a> notwendig!</p>
 */
public class WatorAppFx extends Application {
    private WritableImageTorus torus;
    private WorldEngine worldEngine;
    private AnimationTimer timer;

    public void init() {
        plumb();
        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                worldEngine.tick();
            }
        };
    }

    private void plumb() {
        Random rnd = ThreadLocalRandom.current();

        torus = new WritableImageTorus(800, 600);

        FishStrategy fishStrategy = new FishStrategy();
        fishStrategy.setBreedTime(3);
        fishStrategy.setRnd(rnd);
        fishStrategy.setTorus(torus);

        SharkStrategy sharkStrategy = new SharkStrategy();
        sharkStrategy.setBreedTime(5);
        sharkStrategy.setRnd(rnd);
        sharkStrategy.setStarveTime(4);
        sharkStrategy.setTorus(torus);

        worldEngine = WorldEngine.createPopulatedWorld(
                0.3, // initial rate of fish per cell
                0.1, // initial rate of shark per cell
                rnd, // random base
                torus, // the torus the creatures live in
                new FishStrategy[]{fishStrategy, sharkStrategy}
        );
    }

    @Override
    public void start(Stage stage) throws Exception {
        ImageView iv = new ImageView();
        iv.setImage(torus.getImage());
        StackPane root = new StackPane();
        root.getChildren().add(iv);

        Scene scene = new Scene(root, torus.getWidth(), torus.getHeight());
        scene.setFill(Color.BLACK);
        stage.setTitle(getClass().getSimpleName());
        stage.setScene(scene);
        stage.show();

        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
