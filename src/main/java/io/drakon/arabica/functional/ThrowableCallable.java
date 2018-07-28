package io.drakon.arabica.functional;

/**
 * Functional interface defining a callable which may produce a value but may throw exceptions.
 *
 * @param <R> The type returned from the callable.
 */
@FunctionalInterface
public interface ThrowableCallable<R> {

    R apply() throws Throwable;

}
