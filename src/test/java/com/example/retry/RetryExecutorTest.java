package com.example.retry;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RetryExecutorTest {

    @Test
    void testSucceedsWithoutRetry() {
        RetryExecutor exec = new RetryExecutor(
                RetryConfig.custom()
                        .retryOn(RuntimeException.class)
        );
        String result = exec.execute(() -> "ok");
        assertEquals("ok", result);
    }

    @Test
    void testRetriesAndSucceeds() {
        AtomicInteger counter = new AtomicInteger();
        RetryExecutor exec = new RetryExecutor(
                RetryConfig.custom()
                        .maxAttempts(3)
                        .initialDelay(Duration.ofMillis(10))
                        .backoffMultiplier(1.5)
                        .retryOn(RuntimeException.class)
        );

        String result = exec.execute(() -> {
            if (counter.getAndIncrement() < 2) {
                throw new RuntimeException("fail");
            }
            return "finally!";
        });

        assertEquals("finally!", result);
        assertEquals(3, counter.get());
    }

    @Test
    void testFailsAfterMaxRetries() {
        RetryExecutor exec = new RetryExecutor(
                RetryConfig.custom()
                        .maxAttempts(2)
                        .initialDelay(Duration.ofMillis(5))
                        .retryOn(IllegalStateException.class)
        );

        RetryException ex = assertThrows(RetryException.class, () ->
                exec.execute(() -> {
                    throw new IllegalStateException("always fails");
                })
        );

        assertTrue(ex.getMessage().contains("after 2 retries"));
    }
}