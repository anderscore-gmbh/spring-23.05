package wator.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wator.config.WatorFxConfig;
import wator.core.WorldEngine;
import wator.fx.WritableImageTorus;

/**
 * Wator mit Java Fx
 *
 * <p>Aufruf mit Java 11: <code>--module-path C:\Tools\javafx-sdk-11.0.1\lib --add-modules=javafx.controls</code>.
 * Dafuer ist <a href="https://gluonhq.com/products/javafx/">JavaFX runtime</a> notwendig!</p>
 */
public class WatorAppFxAutowire extends Application {
    @Autowired
    private WritableImageTorus torus;
    @Autowired
    private WorldEngine worldEngine;
    private AnimationTimer timer;

    public void init() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WatorFxConfig.class);
        ctx.getAutowireCapableBeanFactory().autowireBean(this);

        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                worldEngine.tick();
            }
        };
    }

    @Override
    public void start(Stage stage) throws Exception {
        ImageView iv = new ImageView();
        iv.setImage(torus.getImage());
        StackPane root = new StackPane();
        root.getChildren().add(iv);

        Scene scene = new Scene(root, torus.getWidth(), torus.getHeight());
        scene.setFill(Color.BLACK);
        stage.setTitle("Wator");
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
