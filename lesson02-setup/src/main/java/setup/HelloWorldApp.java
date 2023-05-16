package setup;

import org.springframework.context.support.GenericApplicationContext;

public class HelloWorldApp {

    // tag::context[]
    public static void main(String[] args) {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean("greetingService", GreetingService.class,
                () -> new GreetingService());
        ctx.refresh();

        GreetingService service = ctx
                .getBean("greetingService", GreetingService.class);
        System.out.println("Message: " + service.getGreeting());
    }
    // end::context[]
}
