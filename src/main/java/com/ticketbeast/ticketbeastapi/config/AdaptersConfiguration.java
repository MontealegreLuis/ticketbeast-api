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
@PropertySource({"classpath:adapters.properties"})
public class AdaptersConfiguration {
  @Value("${stripe.apiKey}")
  private String stripeApiKey;

  @Value("${hashids.salt}")
  private String hashIdsSalt;
}
