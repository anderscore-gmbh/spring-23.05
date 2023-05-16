package reactor.basics;

import org.junit.jupiter.api.Test;

class MonoTests {

    @Test
    void testCreateVoidMono() throws InterruptedException {
        //TODO: Erstellen Sie ein Mono mit Mono.empty(). Was bewirken die Methoden log() und subscribe()?
    }

    @Test
    void testCreateHelloWorldMono() {
        //TODO: Erstellen Sie ein Mono mit dem Wert "Hello World!" und geben Sie es über System.out aus.
    }

    @Test
    void testFlatMapMono() {
        //TODO: Verwenden Sie flatMap um das "Hello World" Mono in Großbuchstaben auszugeben
    }

    @Test
    void testCreateFluxFromMono() {
        //TODO: Erstellen Sie ein Mono aus einer Liste und konvertieren Sie es danach mit flatMapIterable in in Flux.
    }

    @Test
    void testCreateMonoFromCallable() {
        //TODO: Erstellen Sie ein Mono mit fromCallable. Wann wird das Callable aufgerufen?
    }

    // Diese Methode kann als Callable verwendet werden.
    private String createString() {
        System.out.println("createString called");
        return "bla bla";
    }

    @Test
    void testHandleError() {
        //TODO: Erstellen Sie mit Mono.error einen Mono zu einer Exception. Was passiert bei subscribe?
        //TODO: Verwenden Sie dann onErrorResume, um die Exception-Message auszugeben. Was ist der Unterschied zu doOnError?
    }

    @Test
    void testMergeMonos() {
        //TODO: Erstellen Sie zwei Monos und verknüfen Sie sie mit mergeWith. Was erhalten Sie?
    }

    @Test
    void testZipMonos() {
        //TODO: Erstellen Sie zwei Monos und verknüfen Sie sie mit zip. Was erhalten Sie?
    }

    @Test
    void testZipDelay() {
        //TODO: Erstellen Sie einen Mono mit delay und zippen sie es mit einem anderen Mono. Was passiert?
        //TODO: Was bewirkt die Methode block?
    }
}
