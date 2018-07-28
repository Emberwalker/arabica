package io.drakon.arabica.internal.streams;

import java.util.Optional;
import java.util.function.Consumer;

import io.drakon.arabica.functional.Producer;
import io.drakon.arabica.functional.ThrowableCallable;
import io.drakon.arabica.streams.MaybeThrowsFunction;
import io.drakon.arabica.streams.MaybeThrowsProducer;
import lombok.Getter;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL, consumers = {"io.drakon.arabica.streams.*"})
public class MaybeThrowsProducerImpl<R> implements MaybeThrowsProducer<R> {

    @Getter
    private final ThrowableCallable<R> callable;
    private final MaybeThrowsFunction<Void, R> wrappedMaybeThrows;

    public MaybeThrowsProducerImpl(ThrowableCallable<R> callable) {
        this.callable = callable;
        wrappedMaybeThrows = new MaybeThrowsFunctionImpl<>(ignored -> callable.apply());
    }

    @Override
    public R ignoringExceptions() {
        return wrappedMaybeThrows.ignoringExceptions(null);
    }

    @Override
    public Optional<R> ignoringExceptionsOptional() {
        return wrappedMaybeThrows.ignoringExceptionsOptional(null);
    }

    @Override
    public R throwUnchecked() {
        return wrappedMaybeThrows.throwUnchecked(null);
    }

    @Override
    public R exceptionally(Consumer<Throwable> exceptionHandler) {
        return wrappedMaybeThrows.exceptionally(null, exceptionHandler);
    }

    @Override
    public Optional<R> exceptionallyOptional(Consumer<Throwable> exceptionHandler) {
        return wrappedMaybeThrows.exceptionallyOptional(null, exceptionHandler);
    }

    @Override
    public R orElse(R fallback) {
        return wrappedMaybeThrows.orElse(null, fallback);
    }

    @Override
    public R orElse(Producer<R> fallbackProducer) {
        return wrappedMaybeThrows.orElse(null, fallbackProducer);
    }

}
