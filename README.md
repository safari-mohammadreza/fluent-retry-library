# Fluent Retry Library

A lightweight, lambda-friendly Java retry utility for wrapping any `Callable` or lambda in retry logic with exponential backoff.

## Features

- Configurable max attempts, initial delay, backoff multiplier
- Filter which exception types should trigger a retry
- Throws a clear `RetryException` on ultimate failure
- Zero dependencies (pure JDK)

## Quick Start

### Gradle Dependency (after publishing to Maven Local or remote):
```groovy
dependencies {
  implementation 'com.example:fluent-retry:1.0.0'
}
