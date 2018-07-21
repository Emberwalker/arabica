package io.drakon.arabica.streams;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ExceptionsTest {

    @Test
    void smokeTest() {
        assertThatCode(() -> Exceptions.maybeThrows(() -> "smoke")).doesNotThrowAnyException();
    }

    @Test
    void testIgnoresException() {
        assertThat(Exceptions.maybeThrows(() -> { throw new Exception(); }).ignoringExceptions()).isNull();
    }

    @Test
    void testIgnoresExceptionOptional() {
        assertThat(Exceptions.maybeThrows(() -> { throw new Exception(); }).ignoringExceptionsOptional()).isEmpty();
    }

    @Test
    void testNoException() {
        assertThat(Exceptions.maybeThrows(() -> "test").ignoringExceptions()).isEqualTo("test");
    }

    @Test
    void testNoExceptionOptional() {
        assertThat(Exceptions.maybeThrows(() -> "test").ignoringExceptionsOptional()).hasValue("test");
    }

    @Test
    void testNoExceptionNullOptional() {
        assertThat(Exceptions.maybeThrows(() -> null).ignoringExceptionsOptional()).isEmpty();
    }

}
