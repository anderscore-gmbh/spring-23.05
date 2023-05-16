package reactor.basics;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class FluxTests {

    @Test
    public void testCreateFlux() {
        // TODO: Verwenden Sie die Methode just, um einen Flux zu erstellen. Subscriben Sie mit System.out::println
        // TODO: Wann werden die Methoden doOnRequest, doNoNext und doOnComplete aufgerufen?
        Flux<String> flux = Flux.just("a", "b", "c")
                .log()
                .doOnRequest(l -> System.out.println("request: " + l))
                .doOnNext(s -> System.out.println("next: " + s))
                .doOnComplete(() -> System.out.println("complete"));
        flux.subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxFromCollection() {
        // TODO: Erstellen Sie ein Flux aus einer Collection
        Flux<String> flux = Flux.fromIterable(Arrays.asList("a", "b", "c"));
        flux.subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxFromRange() {
        //TODO: Erstellen Sie ein Flux mit range
        Flux<Integer> flux = Flux.range(2, 5).log();
        flux.subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxFromStream() {
        //TODO: Erstellen ein Flux aus einem Stream
        Flux<Integer> flux = Flux.fromStream(IntStream.rangeClosed(1, 7).boxed());
        flux.subscribe(System.out::println);
    }

    @Test
    public void testZipFlux() throws InterruptedException {
        //TODO: Erstellen Sie ein Flux mit range und eines mit interval und zippen Sie die beiden.
        //TODO: Nutzen Sie ein CountDownLatch, um alle Elemente auszugeben
        Flux<Integer> rangeFlux = Flux.range(1, 7);
        Flux<Long> intervalflux = Flux.interval(Duration.ofSeconds(1));

        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux<Tuple2<Integer, Long>> combinedFlux = rangeFlux.zipWith(intervalflux)
                .doOnComplete(countDownLatch::countDown);
        combinedFlux.subscribe(System.out::println);
        countDownLatch.await();
    }

    @Test
    public void testGenerate() {
        //TODO: Nutzen Sie Flux.generate, um einen Flux zu erstellen der von 10 bis 0 zählt.
        Flux<Integer> countDownFlux = Flux.generate(() -> 10, (value, sink) -> {
            value--;
            if (value >= 0) {
                sink.next(value);
            } else {
                sink.complete();
            }
            return value;
        });
        countDownFlux.subscribe(System.out::println);
    }
}
