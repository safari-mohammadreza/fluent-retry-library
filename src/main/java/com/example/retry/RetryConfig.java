package com.example.retry;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RetryConfig {
    private int maxAttempts;
    private Duration initialDelay;
    private double backoffMultiplier;
    private final Set<Class<? extends Exception>> retryOn;

    private RetryConfig() {
        this.maxAttempts = 3;
        this.initialDelay = Duration.ofMillis(500);
        this.backoffMultiplier = 2.0;
        this.retryOn = new HashSet<>();
    }

    public static RetryConfig custom() {
        return new RetryConfig();
    }

    public RetryConfig maxAttempts(int attempts) {
        this.maxAttempts = attempts;
        return this;
    }

    public RetryConfig initialDelay(Duration delay) {
        this.initialDelay = delay;
        return this;
    }

    public RetryConfig backoffMultiplier(double multiplier) {
        this.backoffMultiplier = multiplier;
        return this;
    }

    @SafeVarargs
    public final RetryConfig retryOn(Class<? extends Exception>... exceptions) {
        Collections.addAll(retryOn, exceptions);
        return this;
    }

    // Getters
    int getMaxAttempts() { return maxAttempts; }
    Duration getInitialDelay() { return initialDelay; }
    double getBackoffMultiplier() { return backoffMultiplier; }
    Set<Class<? extends Exception>> getRetryOn() { return retryOn; }
}