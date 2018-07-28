package io.drakon.arabica.functional;

/**
 * Functional interface which defines a non-throwing producer.
 *
 * @param <R> The type produced by this producer.
 */
@FunctionalInterface
public interface Producer<R> {

    R produce();

}
