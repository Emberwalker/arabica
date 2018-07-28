package io.drakon.arabica.streams;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import io.drakon.arabica.functional.Producer;
import io.drakon.arabica.functional.ThrowableFunction;
import org.apiguardian.api.API;

/**
 * Function call wrapper which provides optional exception safety, for use in {@link java.util.stream.Stream}
 * pipelines.
 *
 * @param <T> The input type of the wrapped function.
 * @param <R> The return type of the wrapped function.
 */
@API(status = API.Status.EXPERIMENTAL)
public interface MaybeThrowsFunction<T, R> {
    /**
     * Runs the wrapped function, ignoring any thrown exceptions. This version returns null on failure, for
     * returning an {@link Optional} see {@link MaybeThrowsFunction#ignoringExceptionsOptional(Object)}.
     *
     * @param input The input to the wrapped function.
     * @return The return value of the wrapped function, or null if an exception occurred.
     */
    R ignoringExceptions(T input);

    /**
     * Runs the wrapped function, ignoring any thrown exceptions. This version returns an empty {@link Optional} on
     * failure, for returning {@literal null} see {@link MaybeThrowsFunction#ignoringExceptions(Object)}.
     * <p>
     * Note that null returns from the wrapped function will also be converted to {@link Optional#empty()}.
     *
     * @param input The input to the wrapped function.
     * @return The return value of the wrapped function, {@link Optional#empty()} if the return was {@literal null},
     * or {@link Optional#empty()} if an exception occurred.
     */
    Optional<R> ignoringExceptionsOptional(T input);

    /**
     * Runs the wrapped function, rethrowing any exceptions as a {@link StreamException}.
     *
     * @param input The input to the wrapped function.
     * @return The return value of the wrapped function.
     * @throws StreamException Wrapped exception thrown by the function.
     */
    R throwUnchecked(T input);

    /**
     * Works identically to {@link MaybeThrowsFunction#ignoringExceptions(Object)} but calls a given consumer with any
     * exceptions thrown.
     *
     * @param input            The input to the wrapped function.
     * @param exceptionHandler A consumer which accepts {@link Throwable} instances.
     * @return The return value of the wrapped function, or null if an exception occurred.
     */
    R exceptionally(T input, Consumer<Throwable> exceptionHandler);

    /**
     * Version of {@link MaybeThrowsFunction#exceptionally(Object, Consumer)} which returns a function suitable for
     * using with {@link java.util.stream.Stream#map(Function)}.
     *
     * @param exceptionHandler A consumer which accepts {@link Throwable} instances.
     * @return A callable which returns the return value of the wrapped function, or null if an exception occurred.
     */
    default Function<T, R> exceptionally(Consumer<Throwable> exceptionHandler) {
        return (T input) -> this.exceptionally(input, exceptionHandler);
    }

    /**
     * Works identically to {@link MaybeThrowsFunction#ignoringExceptionsOptional(Object)} but calls a given consumer
     * with any exceptions thrown.
     *
     * @param input            The input to the wrapped function.
     * @param exceptionHandler A consumer which accepts {@link Throwable} instances.
     * @return The return value of the wrapped function, {@link Optional#empty()} if the return was {@literal null},
     * or {@link Optional#empty()} if an exception occurred.
     */
    Optional<R> exceptionallyOptional(T input, Consumer<Throwable> exceptionHandler);

    /**
     * Version of {@link MaybeThrowsFunction#exceptionallyOptional(Object, Consumer)} which returns a function suitable
     * for using with {@link java.util.stream.Stream#map(Function)}.
     *
     * @param exceptionHandler A consumer which accepts {@link Throwable} instances.
     * @return A callable which returns the return value of the wrapped function, {@link Optional#empty()} if the return
     * was {@literal null}, or {@link Optional#empty()} if an exception occurred.
     */
    default Function<T, Optional<R>> exceptionallyOptional(Consumer<Throwable> exceptionHandler) {
        return (T input) -> this.exceptionallyOptional(input, exceptionHandler);
    }

    /**
     * Runs the wrapped function, but returns the fallback value specified instead on an exception.
     *
     * @param input    The input to the wrapped function.
     * @param fallback The value to return on error.
     * @return The return value of the wrapped function, or the fallback if an exception occurred.
     */
    R orElse(T input, R fallback);

    /**
     * Version of {@link MaybeThrowsFunction#orElse(Object, Object)} which returns a function suitable for using with
     * {@link java.util.stream.Stream#map(Function)}.
     *
     * @param fallback The value to return on error.
     * @return A callable which returns the return value of the wrapped function, or the fallback if an exception
     * occurred.
     */
    default Function<T, R> orElse(R fallback) {
        return (T input) -> this.orElse(input, fallback);
    }

    /**
     * Runs the wrapped function, but returns a fallback generated from a producer instead on an exception.
     *
     * @param input            The input to the wrapped function.
     * @param fallbackProducer The producer for a value to return on error.
     * @return The return value of the wrapped function, or the fallback produced by the producer if an exception
     * occurred.
     */
    R orElse(T input, Producer<R> fallbackProducer);

    /**
     * Version of {@link MaybeThrowsFunction#orElse(Object, Producer)} which returns a function suitable for using with
     * {@link java.util.stream.Stream#map(Function)}.
     *
     * @param fallbackProducer The producer for a value to return on error.
     * @return A callable which returns the return value of the wrapped function, or the fallback produced by the
     * producer if an exception occurred.
     */
    default Function<T, R> orElse(Producer<R> fallbackProducer) {
        return (T input) -> this.orElse(input, fallbackProducer);
    }

    /**
     * Get the underlying callable represented by this instance.
     *
     * @return The underlying callable.
     */
    ThrowableFunction<T, R> getCallable();
}
