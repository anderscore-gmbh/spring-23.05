package reactor.basics;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

class MonoTests {

    @Test
    void testCreateVoidMono() {
        //TODO: Erstellen Sie einen Mono Mono.empty(). Was bewirken die Methoden log() und subscribe()?
        Mono<Void> empty = Mono.empty();
        empty.log().subscribe();
    }

    @Test
    void testCreateHelloWorldMono() {
        //TODO: Erstellen Sie einen Mono mit dem Wert "Hello World" und geben Sie ihn über System.out aus.
        Mono<String> mono = Mono.just("Hello World!");
        mono.subscribe(System.out::println);
    }

    @Test
    void testFlatMapMono() {
        //TODO: Verwenden Sie flatMap um den "Hello World" Mono in Großbuchstaben auszugeben
        Mono<String> mono = Mono.just("Hello World!");
        Mono<String> mappedMono = mono.flatMap(s -> Mono.just(s.toUpperCase()));
        mappedMono.subscribe(System.out::println);
    }

    @Test
    void testCreateFluxFromMono() {
        //TODO: Erstellen Sie ein Mono aus einer Liste und konvertieren Sie es danach in in Flux.
        Mono<List<Integer>> mono = Mono.just(Arrays.asList(1, 2, 3, 4));

        Flux<Integer> flux = mono.flatMapIterable(Function.identity());
        flux.subscribe(System.out::println);
    }

    @Test
    void testCreateMonoFromCallable() {
        //TODO: Erstellen Sie ein Mono mit fromCallable. Wann wird das Callable aufgerufen?
        Mono<String> mono = Mono.fromCallable(this::createString);
        System.out.println("subscribing:");
        mono.subscribe(System.out::println);
    }

    private String createString() {
        System.out.println("createString called");
        return "bla bla";
    }

    @Test
    void testHandleError() {
        //TODO: Erstellen Sie mit Mono.error einen Mono zu einer Exception. Was passiert bei subscribe?
        //TODO: Verwenden Sie dann onErrorResume, um die Exception-Message auszugeben. Was ist der Unterschied zu doOnError?
        Mono<Object> mono = Mono
                .error(new RuntimeException("Some error"))
                .doOnError(th -> th.printStackTrace())
                .onErrorResume(th -> Mono.just("Error: " + th.getMessage()));
        mono.subscribe(System.out::println);
    }

    @Test
    void testMergeMonos() {
        //TODO: Erstellen Sie zwei Monos und verknüfen Sie sie mit mergeWith. Was erhalten Sie?
        Mono<String> bla = Mono.just("bla");
        Mono<String> blub = Mono.just("blub");
        Flux<String> flux = bla.mergeWith(blub);
        flux.subscribe(System.out::println);
    }

    @Test
    void testZipMonos() {
        //TODO: Erstellen Sie zwei Monos und verknüfen Sie sie mit zip. Was erhalten Sie?
        Mono<String> bla = Mono.just("bla");
        Mono<String> blub = Mono.just("blub");

        Mono<Tuple2<String, String>> tuple = bla.zipWith(blub);
        tuple.subscribe(System.out::println);
    }

    @Test
    void testZipDelay() {
        //TODO: Erstellen Sie einen Mono mit delay und zippen sie ihn mit einem anderen Mono. Was passiert?
        //TODO: Was bewirkt die Methode block?
        Mono<Long> delay = Mono.delay(Duration.ofSeconds(3));
        Mono<String> mono = Mono.just("bla");

        Mono<Tuple2<Long, String>> tupleMono = delay.zipWith(mono);
        Tuple2<Long, String> tuple = tupleMono.block();
        System.out.println(tuple);
    }
}
