package com.ticketbeast.ticketbeastapi.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@NoArgsConstructor
@PropertySource({"classpath:stripe.properties"})
public class StripeConfiguration {
  @Value("${stripe.apiKey}")
  private String apiKey;
}
