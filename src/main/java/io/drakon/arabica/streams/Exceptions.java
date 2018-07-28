package io.drakon.arabica.streams;

import java.util.function.Function;

import io.drakon.arabica.functional.ThrowableCallable;
import io.drakon.arabica.functional.ThrowableFunction;
import io.drakon.arabica.internal.streams.MaybeThrowsFunctionImpl;
import io.drakon.arabica.internal.streams.MaybeThrowsProducerImpl;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apiguardian.api.API;

/**
 * Utility methods for handling exceptions in streams-based code.
 */
@UtilityClass
@API(status = API.Status.EXPERIMENTAL)
public class Exceptions {

    /**
     * Wrap a function call in a {@link MaybeThrowsProducer}, which can suppress exceptions. Useful for streams with
     * sections that might throw (which Java prohibits).
     *
     * @param callable The function to call.
     * @param <R>      The return type of the function.
     * @return A {@link MaybeThrowsProducer} wrapper around the function.
     */
    public <R> MaybeThrowsProducer<R> maybeThrows(@NonNull ThrowableCallable<R> callable) {
        return new MaybeThrowsProducerImpl<>(callable);
    }

    /**
     * Wrap a function call in a {@link MaybeThrowsFunction}, which can suppress exceptions. Useful for streams with sections
     * that might throw (which Java prohibits). This version is usable for {@link java.util.stream.Stream#map(Function)}
     * invocations.
     *
     * @param callable The function to call.
     * @param <T>      The input type of the function.
     * @param <R>      The return type of the function.
     * @return A {@link MaybeThrowsFunction} wrapper around the function.
     */
    public <T, R> MaybeThrowsFunction<T, R> maybeThrows(@NonNull ThrowableFunction<T, R> callable) {
        return new MaybeThrowsFunctionImpl<>(callable);
    }

}
