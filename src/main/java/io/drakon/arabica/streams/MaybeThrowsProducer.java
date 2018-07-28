package io.drakon.arabica.streams;

import java.util.Optional;
import java.util.function.Consumer;

import io.drakon.arabica.functional.Producer;
import io.drakon.arabica.functional.ThrowableCallable;
import org.apiguardian.api.API;

/**
 * Producer wrapper which provides optional exception safety, for use in {@link java.util.stream.Stream} pipelines.
 *
 * @param <R> The return type of the wrapped function.
 */
@API(status = API.Status.EXPERIMENTAL)
public interface MaybeThrowsProducer<R> {

    /**
     * Runs the wrapped function, ignoring any thrown exceptions. This version returns null on failure, for
     * returning an {@link Optional} see {@link MaybeThrowsProducer#ignoringExceptionsOptional()}.
     *
     * @return The return value of the wrapped function, or null if an exception occurred.
     */
    R ignoringExceptions();

    /**
     * Runs the wrapped function, ignoring any thrown exceptions. This version returns an empty {@link Optional} on
     * failure, for returning {@literal null} see {@link MaybeThrowsProducer#ignoringExceptions()}.
     * <p>
     * Note that null returns from the wrapped function will also be converted to {@link Optional#empty()}.
     *
     * @return The return value of the wrapped function, {@link Optional#empty()} if the return was {@literal null},
     * or {@link Optional#empty()} if an exception occurred.
     */
    Optional<R> ignoringExceptionsOptional();

    /**
     * Runs the wrapped function, rethrowing any exceptions as a {@link StreamException}.
     *
     * @return The return value of the wrapped function.
     * @throws StreamException Wrapped exception thrown by the function.
     */
    R throwUnchecked();

    /**
     * Works identically to {@link MaybeThrowsProducer#ignoringExceptions()} but calls a given consumer with any
     * exceptions thrown.
     *
     * @param exceptionHandler A consumer which accepts {@link Throwable} instances.
     * @return The return value of the wrapped function, or null if an exception occurred.
     */
    R exceptionally(Consumer<Throwable> exceptionHandler);

    /**
     * Works identically to {@link MaybeThrowsProducer#ignoringExceptionsOptional()} but calls a given consumer with any
     * exceptions thrown.
     *
     * @param exceptionHandler A consumer which accepts {@link Throwable} instances.
     * @return The return value of the wrapped function, or null if an exception occurred.
     */
    Optional<R> exceptionallyOptional(Consumer<Throwable> exceptionHandler);

    /**
     * Runs the wrapped function, but returns the fallback value specified instead on an exception.
     *
     * @param fallback The value to return on error.
     * @return The return value of the wrapped function, or the fallback if an exception occurred.
     */
    R orElse(R fallback);

    /**
     * Runs the wrapped function, but returns a fallback generated from a producer instead on an exception.
     *
     * @param fallbackProducer The producer for a value to return on error.
     * @return The return value of the wrapped function, or the fallback produced by the producer if an exception
     * occurred.
     */
    R orElse(Producer<R> fallbackProducer);

    /**
     * Get the underlying callable represented by this instance.
     *
     * @return The underlying callable.
     */
    ThrowableCallable<R> getCallable();

}
