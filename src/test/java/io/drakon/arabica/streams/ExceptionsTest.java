package io.drakon.arabica.streams;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

// Tests for Exceptions, MaybeThrowsFunction and MaybeThrowsProducer
class ExceptionsTest {

    @Test
    void smokeTestProducer() {
        assertThatCode(() -> Exceptions.maybeThrows(() -> "smoke")).doesNotThrowAnyException();
    }

    @Test
    void smokeTestFunction() {
        assertThatCode(() -> Exceptions.<String, String>maybeThrows(it -> "smoke")).doesNotThrowAnyException();
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

    @Test
    void testExceptionRethrown() {
        Exception exception = new Exception();
        assertThatCode(Exceptions.maybeThrows(() -> { throw exception; })::throwUnchecked).hasCause(exception);
    }

    @Test
    void testNoExceptionRethrown() {
        assertThat(Exceptions.maybeThrows(() -> "test").throwUnchecked()).isEqualTo("test");
    }

    @Test
    void testExceptionallyException() {
        TestBooleanState state = new TestBooleanState(false);
        assertThat(Exceptions.maybeThrows(ExceptionsTest::throwException).exceptionally(it -> state.setPass(true))).isNull();
        assertThat(state.isPass()).isTrue();
    }

    @Test
    void testExceptionallyNoException() {
        TestBooleanState state = new TestBooleanState(true);
        assertThat(Exceptions.maybeThrows(() -> "test").exceptionally(it -> state.setPass(false))).isEqualTo("test");
        assertThat(state.isPass()).isTrue();
    }

    @Test
    void testExceptionallyOptionalException() {
        TestBooleanState state = new TestBooleanState(false);
        assertThat(Exceptions.maybeThrows(ExceptionsTest::throwException).exceptionallyOptional(it -> state.setPass(true))).isEmpty();
        assertThat(state.isPass()).isTrue();
    }

    @Test
    void testExceptionallyOptionalNoException() {
        TestBooleanState state = new TestBooleanState(true);
        assertThat(Exceptions.maybeThrows(() -> "test").exceptionallyOptional(it -> state.setPass(false))).hasValue("test");
        assertThat(state.isPass()).isTrue();
    }

    @Test
    void testOrElseValueNoException() {
        assertThat(Exceptions.maybeThrows(() -> "pass").orElse("fail")).isEqualTo("pass");
    }

    @Test
    void testOrElseValueException() {
        assertThat(Exceptions.maybeThrows(ExceptionsTest::throwException).orElse(true)).isTrue();
    }

    @Test
    void testOrElseProducerNoException() {
        assertThat(Exceptions.maybeThrows(() -> "pass").orElse(() -> "fail")).isEqualTo("pass");
    }

    @Test
    void testOrElseProducerException() {
        assertThat(Exceptions.maybeThrows(ExceptionsTest::throwException).orElse(() -> true)).isTrue();
    }

    @Test
    void testFnOrElseValueNoException() {
        assertThat(Exceptions.maybeThrows(it -> "pass").orElse("fail").apply(null)).isEqualTo("pass");
    }

    @Test
    void testFnOrElseValueException() {
        assertThat(Exceptions.maybeThrows(it -> throwException()).orElse(true).apply(null)).isTrue();
    }

    @Test
    void testFnOrElseProducerNoException() {
        assertThat(Exceptions.maybeThrows(it -> "pass").orElse(() -> "fail").apply(null)).isEqualTo("pass");
    }

    @Test
    void testFnOrElseProducerException() {
        assertThat(Exceptions.maybeThrows(it -> throwException()).orElse(() -> true).apply(null)).isTrue();
    }

    @Data
    @AllArgsConstructor
    private static class TestBooleanState {
        private boolean pass;
    }

    private static boolean throwException() throws Exception {
        throw new Exception();
    }

}
