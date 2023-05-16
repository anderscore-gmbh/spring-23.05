package setup.postproc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import setup.GreetingService;
import setup.GreetingServiceJsr250;

public class PostProcessorAndEventsApp {
    // tag::logdecl[]
    private static final Logger log =
            LoggerFactory.getLogger(PostProcessorAndEventsApp.class);
    // end::logdecl[]

    public static void main(String[] args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.registerBean("trainingService",
                GreetingService.class, () -> new GreetingService());
        ctx.registerBean("trainingServiceJsr250",
                GreetingServiceJsr250.class, () -> new GreetingServiceJsr250());
        ctx.registerBean(DemoBeanPostProcessor.class);
        ctx.registerBean(DemoEventListener.class);
        ctx.refresh();

        // Aufgabe 4: Der ShutdownHook ist notwendig, damit der ApplicationContext sauber heruntergefahren wird.
        ctx.registerShutdownHook();

        // tag::log[]
        log.info("Lade GreetingService");
        GreetingService service = ctx
                .getBean("trainingService", GreetingService.class);
        String greeting = service.getGreeting();
        log.info("Message von TraniningService: '{}'", greeting);
        // end::log[]
        System.out.println("Message: " + greeting);

        GreetingServiceJsr250 serviceJsr250 = ctx
                .getBean("trainingServiceJsr250", GreetingServiceJsr250.class);
    }
}
