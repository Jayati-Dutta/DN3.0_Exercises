package com.example.bookstoreapi.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public CustomMetrics customMetrics(MeterRegistry meterRegistry) {
        return new CustomMetrics(meterRegistry);
    }

    public static class CustomMetrics {
        private final MeterRegistry meterRegistry;

        public CustomMetrics(MeterRegistry meterRegistry) {
            this.meterRegistry = meterRegistry;
            // Register a custom counter metric
            meterRegistry.counter("custom_metric_counter", "type", "example");
        }
    }
}
