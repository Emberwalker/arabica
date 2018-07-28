package io.drakon.arabica.functional;

import org.apiguardian.api.API;

/**
 * Functional interface defining a callable which may produce a value but may throw exceptions.
 *
 * @param <T> The type of the callable input.
 * @param <R> The type returned from the callable.
 */
@FunctionalInterface
@API(status = API.Status.STABLE)
public interface ThrowableFunction<T, R> {

    R apply(T input) throws Throwable;

}
