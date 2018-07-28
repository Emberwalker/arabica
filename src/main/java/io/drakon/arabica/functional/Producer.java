package io.drakon.arabica.functional;

import org.apiguardian.api.API;

/**
 * Functional interface which defines a non-throwing producer.
 *
 * @param <R> The type produced by this producer.
 */
@FunctionalInterface
@API(status = API.Status.STABLE)
public interface Producer<R> {

    R produce();

}
