package reactor.fragments;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class FluxAndMonoApi {

    @Test
    void monoConstructorMethods() {
        String mayBeNull = null;
        Optional<String> someOptional = Optional.empty();

        // tag::monoConstructorMethods[]
        Mono.empty();
        Mono.just("blub");
        Mono.justOrEmpty(mayBeNull);
        Mono.justOrEmpty(someOptional);
        Mono.fromCallable(this::toString);
        Mono.error(new RuntimeException("sample"));
        Mono.delay(Duration.ofSeconds(3));
        // end::monoConstructorMethods[]
    }

    @Test
    void fluxConstructorMethods() {
        // tag::fluxConstructorMethods[]
        Flux.empty();
        Flux.just(1, 2 ,3);
        Flux.range(1, 3);
        Flux.fromIterable(Arrays.asList(1, 2, 3));
        Flux.fromStream(IntStream.range(1, 3).boxed());

        Flux.error(new RuntimeException("sample"));
        Flux.interval(Duration.ofSeconds(3));
        // end::fluxConstructorMethods[]
    }

    @Test
    void operators() {
        Mono mono = Mono.empty();

        // tag::operators[]
        mono.log();
        mono.subscribe(System.out::println);
        mono.block();
        // end::operators[]
    }

    @Test
    void callbacks() {
        Flux<Integer> flux = Flux.range(1, 3);
        // tag::callbacks[]
        flux.doOnRequest(n -> System.out.println(n + "requested."));
        flux.doOnNext(x -> System.out.print("next: " + x));
        flux.doOnComplete(() -> System.out.println("complete"));
        flux.doOnError(th -> th.printStackTrace());
        // end::callbacks[]
    }

    @Test
    void converter() {
        Mono<Integer> mono = Mono.just(3);
        // tag::converter[]
        mono.map(x -> x * 2);
        mono.filter(x -> x > 3);
        mono.flatMap(x -> Mono.justOrEmpty(x * 2));
        mono.flux();
        // end::converter[]
    }

    @Test
    void combinators() {
        Mono mono1 = Mono.just(3);
        Mono mono2 = Mono.just("bla");

        // tag::combinators[]
        mono1.mergeWith(mono2);
        mono1.zipWith(mono2);
        // end::combinators[]
    }

    @Test
    void usingCountDownLatch() throws InterruptedException {
        // tag::usingCountDownLatch[]
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono<Long> delay = Mono.delay(Duration.ofMillis(500));
        delay.doOnTerminate(countDownLatch::countDown).subscribe();
        countDownLatch.await();
        // end::usingCountDownLatch[]
    }
}
