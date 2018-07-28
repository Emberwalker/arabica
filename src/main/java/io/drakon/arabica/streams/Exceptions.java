package io.drakon.arabica.streams;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import io.drakon.arabica.functional.Producer;
import io.drakon.arabica.functional.ThrowableCallable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Utility methods for handling exceptions in streams-based code.
 */
@UtilityClass
public class Exceptions {

    /**
     * Wrap a function call in a {@link MaybeThrows}, which can suppress exceptions. Useful for streams with sections
     * that might throw (which Java prohibits).
     *
     * @param callable The function to call.
     * @param <R> The return type of the function.
     * @return A {@link MaybeThrows} wrapper around the function.
     */
    public <R> MaybeThrows<R> maybeThrows(@NonNull ThrowableCallable<R> callable) {
        return new MaybeThrows<>(callable);
    }

    /**
     * Function call wrapper which provides optional exception safety, for use in {@link java.util.stream.Stream}
     * pipelines.
     *
     * @param <R> The return type of the wrapped function.
     */
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MaybeThrows<R> {

        @NonNull
        @Getter
        private final ThrowableCallable<R> callable;

        /**
         * Runs the wrapped function, ignoring any thrown exceptions. This version returns null on failure, for
         * returning an {@link Optional} see {@link MaybeThrows#ignoringExceptionsOptional()}.
         *
         * @return The return value of the wrapped function, or null if an exception occurred.
         */
        public R ignoringExceptions() {
            try {
                return callable.apply();
            } catch (Throwable throwable) {
                return null;
            }
        }

        /**
         * Runs the wrapped function, ignoring any thrown exceptions. This version returns an empty {@link Optional} on
         * failure, for returning {@literal null} see {@link MaybeThrows#ignoringExceptions()}.
         *
         * Note that null returns from the wrapped function will also be converted to {@link Optional#empty()}.
         *
         * @return The return value of the wrapped function, {@link Optional#empty()} if the return was {@literal null},
         *         or {@link Optional#empty()} if an exception occurred.
         */
        public Optional<R> ignoringExceptionsOptional() {
            try {
                return Optional.ofNullable(callable.apply());
            } catch (Throwable throwable) {
                return Optional.empty();
            }
        }

        /**
         * Runs the wrapped function, rethrowing any exceptions as a {@link StreamException}.
         *
         * @throws StreamException Wrapped exception thrown by the function.
         * @return The return value of the wrapped function.
         */
        public R throwUnchecked() {
            try {
                return callable.apply();
            } catch (Throwable t) {
                throw new StreamException(t);
            }
        }

        /**
         * Works identically to {@link MaybeThrows#ignoringExceptions()} but calls a given consumer with any exceptions
         * thrown.
         *
         * @param exceptionHandler A consumer which accepts {@link Throwable} instances.
         * @return The return value of the wrapped function, or null if an exception occurred.
         */
        public R exceptionally(Consumer<Throwable> exceptionHandler) {
            try {
                return callable.apply();
            } catch (Throwable t) {
                exceptionHandler.accept(t);
                return null;
            }
        }

        /**
         * Works identically to {@link MaybeThrows#ignoringExceptionsOptional()} but calls a given consumer with any
         * exceptions thrown.
         *
         * @param exceptionHandler A consumer which accepts {@link Throwable} instances.
         * @return The return value of the wrapped function, or null if an exception occurred.
         */
        public Optional<R> exceptionallyOptional(Consumer<Throwable> exceptionHandler) {
            try {
                return Optional.ofNullable(callable.apply());
            } catch (Throwable t) {
                exceptionHandler.accept(t);
                return Optional.empty();
            }
        }

        /**
         * Runs the wrapped function, but returns the fallback value specified instead on an exception.
         *
         * @param fallback The value to return on error.
         * @return The return value of the wrapped function, or the fallback if an exception occurred.
         */
        public R orElse(R fallback) {
            try {
                return callable.apply();
            } catch (Throwable t) {
                return fallback;
            }
        }

        /**
         * Runs the wrapped function, but returns a fallback generated from a producer instead on an exception.
         *
         * @param fallbackProducer The producer for a value to return on error.
         * @return The return value of the wrapped function, or the fallback produced by the producer if an exception
         *         occurred.
         */
        public R orElse(Producer<R> fallbackProducer) {
            try {
                return callable.apply();
            } catch (Throwable t) {
                return fallbackProducer.produce();
            }
        }

    }

    /**
     * Exception type thrown by {@link MaybeThrows#throwUnchecked()} when trying to rethrow an exception as unchecked.
     */
    public static class StreamException extends RuntimeException {
        private StreamException(Throwable cause) {
            super(cause);
        }
    }

}
