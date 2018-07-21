package io.drakon.arabica.functional;

@FunctionalInterface
public interface ThrowableCallable<R> {

    R apply() throws Throwable;

}
