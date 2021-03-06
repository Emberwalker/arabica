package io.drakon.arabica.functional;

import org.apiguardian.api.API;

/**
 * Functional interface defining a callable which may produce a value but may throw exceptions.
 *
 * @param <R> The type returned from the callable.
 */
@FunctionalInterface
@API(status = API.Status.STABLE)
public interface ThrowableCallable<R> {

    R apply() throws Throwable;

}
