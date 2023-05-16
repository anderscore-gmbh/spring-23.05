package setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class HelloWorldApp {
    // tag::logdecl[]
    private static final Logger log =
            LoggerFactory.getLogger(HelloWorldApp.class);
    // end::logdecl[]

    public static void main(String[] args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.registerBean("greetingService",
                GreetingService.class, () -> new GreetingService());
        ctx.registerBean("greetingServiceJsr250",
                GreetingServiceJsr250.class, () -> new GreetingServiceJsr250());
        ctx.refresh();

        // Aufgabe 4: Der ShutdownHook ist notwendig, damit der ApplicationContext sauber heruntergefahren wird.
        ctx.registerShutdownHook();

        log.info("Lade GreetingService");
        GreetingService service = ctx
                .getBean("greetingService", GreetingService.class);
        String greeting = service.getGreeting();
        log.info("Message von TraniningService: '{}'", greeting);
        System.out.println("Message: " + greeting);

        GreetingServiceJsr250 serviceJsr250 = ctx
                .getBean("greetingServiceJsr250", GreetingServiceJsr250.class);
    }
}
