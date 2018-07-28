package io.drakon.arabica.streams;

import org.apiguardian.api.API;

/**
 * Exception type thrown by {@link MaybeThrowsFunction#throwUnchecked(Object)} or
 * {@link MaybeThrowsProducer#throwUnchecked()}when trying to rethrow an exception as unchecked.
 */
@API(status = API.Status.STABLE)
public class StreamException extends RuntimeException {

    @API(status = API.Status.INTERNAL, consumers = {"io.drakon.arabica.streams.*"})
    public StreamException(Throwable cause) {
        super(cause);
    }

}
