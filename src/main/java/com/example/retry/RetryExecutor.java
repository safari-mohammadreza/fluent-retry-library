package com.example.retry;

import java.time.Duration;
import java.util.concurrent.Callable;

public class RetryExecutor {
    private final RetryConfig config;

    public RetryExecutor(RetryConfig config) {
        this.config = config;
    }

    public <T> T execute(Callable<T> task) {
        int attempt = 0;
        Duration delay = config.getInitialDelay();

        while (true) {
            try {
                return task.call();
            } catch (Exception e) {
                attempt++;
                boolean shouldRetry = attempt < config.getMaxAttempts()
                        && config.getRetryOn().stream().anyMatch(c -> c.isAssignableFrom(e.getClass()));

                if (!shouldRetry) {
                    throw new RetryException("Operation failed after " + attempt + " retries", e);
                }

                try {
                    Thread.sleep(delay.toMillis());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RetryException("Retry interrupted", ie);
                }

                delay = delay.multipliedBy((long) config.getBackoffMultiplier());
            }
        }
    }
}