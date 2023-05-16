package aop.fragments;

import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Tag;
import org.springframework.stereotype.Service;

public class PointcutSamples {

    // tag::signature[]
    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {
        // jede öffentliche Methode
    }

    @Pointcut("execution(ReturnType aop.fragments.SomeClass.someMethod(..))")
    public void someMethod() {
        // jede Methode 'someMethod' in 'SomeClass', die 'ReturnType' zurückgibt
    }

    @Pointcut("execution(* aop.fragments.*Class.*Method(..))")
    public void someMethodUsingPatterns() {
        // jede Methode, die auf 'Method' endet in einer Klasse, die auf 'Class' endet
    }
    // end::signature[]

    // tag::annotation[]
    @Pointcut("within(aop.fragments.SomeClass))")
    public void allMethodsWithinSomeClass() {
        // alle Methoden in der Klasse 'SomeClass'
    }

    @Pointcut("@annotation(tag)")
    public void methodAnnotatedByTag(Tag tag) {
        // alle Methoden, die mit '@Tag' annotiert sind
    }

    @Pointcut("@within(service)")
    public void allMethodsWithinClassesAnnotedByService(Service service) {
        // alle Methoden von Klassen, die mit '@Service' annotiert sind
    }
    // end::annotation[]

    // tag::combined[]
    @Pointcut("execution(public * *(..)) && @annotation(tag)")
    public void anyPublicMethodAnnotedByTag(Tag tag) {
    }

    @Pointcut("anyPublicMethod() && @annotation(tag)")
    public void anyPublicMethodAnnotedByTagVariant(Tag tag) {
    }

    @Pointcut("anyPublicMethod() && methodAnnotatedByTag(tag)")
    public void anyPublicMethodAnnotedByTagVariant2(Tag tag) {
    }
    // end::combined[]
}
