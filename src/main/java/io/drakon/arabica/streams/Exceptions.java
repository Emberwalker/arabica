package io.drakon.arabica.streams;

import java.util.Optional;

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
        return new MaybeThrows<R>(callable);
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

    }

}
