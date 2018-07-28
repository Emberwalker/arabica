package io.drakon.arabica.internal.streams;

import java.util.Optional;
import java.util.function.Consumer;

import io.drakon.arabica.functional.Producer;
import io.drakon.arabica.functional.ThrowableFunction;
import io.drakon.arabica.streams.MaybeThrowsFunction;
import io.drakon.arabica.streams.StreamException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apiguardian.api.API;

@AllArgsConstructor
@API(status = API.Status.INTERNAL, consumers = {"io.drakon.arabica.streams.*"})
public class MaybeThrowsFunctionImpl<T, R> implements MaybeThrowsFunction<T, R> {

    @NonNull
    @Getter
    private final ThrowableFunction<T, R> callable;

    @Override
    public R ignoringExceptions(T input) {
        try {
            return callable.apply(input);
        } catch (Throwable throwable) {
            return null;
        }
    }

    @Override
    public Optional<R> ignoringExceptionsOptional(T input) {
        try {
            return Optional.ofNullable(callable.apply(input));
        } catch (Throwable throwable) {
            return Optional.empty();
        }
    }

    @Override
    public R throwUnchecked(T input) {
        try {
            return callable.apply(input);
        } catch (Throwable t) {
            throw new StreamException(t);
        }
    }

    @Override
    public R exceptionally(T input, Consumer<Throwable> exceptionHandler) {
        try {
            return callable.apply(input);
        } catch (Throwable t) {
            exceptionHandler.accept(t);
            return null;
        }
    }

    @Override
    public Optional<R> exceptionallyOptional(T input, Consumer<Throwable> exceptionHandler) {
        try {
            return Optional.ofNullable(callable.apply(input));
        } catch (Throwable t) {
            exceptionHandler.accept(t);
            return Optional.empty();
        }
    }

    @Override
    public R orElse(T input, R fallback) {
        try {
            return callable.apply(input);
        } catch (Throwable t) {
            return fallback;
        }
    }

    @Override
    public R orElse(T input, Producer<R> fallbackProducer) {
        try {
            return callable.apply(input);
        } catch (Throwable t) {
            return fallbackProducer.produce();
        }
    }

}
