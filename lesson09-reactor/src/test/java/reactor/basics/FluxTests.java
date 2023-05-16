package reactor.basics;

import org.junit.jupiter.api.Test;

public class FluxTests {

    @Test
    public void testCreateFlux() {
        // TODO: Verwenden Sie die Methode just, um einen Flux zu erstellen. Subscriben Sie mit System.out::println
        // TODO: Wann werden die Methoden doOnRequest, doNoNext und doOnComplete aufgerufen?
    }

    @Test
    public void testCreateFluxFromCollection() {
        // TODO: Erstellen Sie ein Flux aus einer Collection.
    }

    @Test
    public void testCreateFluxFromRange() {
        //TODO: Erstellen Sie ein Flux mit der Methode range.
    }

    @Test
    public void testCreateFluxFromStream() {
        //TODO: Erstellen ein Flux aus einem Stream.
    }

    @Test
    public void testZipFlux() {
        //TODO: Erstellen Sie ein Flux mit range und eines mit interval und zippen Sie die beiden.
        //TODO: Nutzen Sie ein CountDownLatch, um alle Elemente auszugeben.
    }

    @Test
    public void testGenerate() {
        //TODO: Nutzen Sie Flux.generate, um einen Flux zu erstellen das von 10 bis 0 zählt.
    }
}
