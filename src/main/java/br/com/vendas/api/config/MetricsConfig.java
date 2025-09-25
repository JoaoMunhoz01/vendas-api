package br.com.vendas.api.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class MetricsConfig {

  private MeterRegistry meterRegistry;

  @Bean
  public Timer vendaCreationTimer() {
    return Timer.builder("vendas.creation.time")
        .description("Time taken to create a sale")
        .register(meterRegistry);
  }

  @Bean
  public Timer vendaUpdateTimer() {
    return Timer.builder("vendas.update.time")
        .description("Time taken to update a sale")
        .register(meterRegistry);
  }
}

