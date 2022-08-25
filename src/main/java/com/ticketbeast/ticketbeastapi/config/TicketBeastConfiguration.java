package com.ticketbeast.ticketbeastapi.config;

import com.montealegreluis.servicebuses.Command;
import com.montealegreluis.servicebuses.Query;
import com.montealegreluis.ticketbeast.adapters.hashids.HashIdsCodesGenerator;
import com.montealegreluis.ticketbeast.adapters.stripe.StripePaymentGateway;
import com.montealegreluis.ticketbeast.concerts.CodesGenerator;
import com.montealegreluis.ticketbeast.payments.PaymentGateway;
import com.stripe.net.RequestOptions;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.montealegreluis.ticketbeast")
@EnableJpaRepositories("com.montealegreluis.ticketbeast.adapters.jpa")
@ComponentScan(
    includeFilters = {
      @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Query.class),
      @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Command.class)
    },
    value = {"com.montealegreluis.ticketbeast"})
public class TicketBeastConfiguration {
  private final AdaptersConfiguration adaptersConfiguration;

  public TicketBeastConfiguration(final AdaptersConfiguration adaptersConfiguration) {
    this.adaptersConfiguration = adaptersConfiguration;
  }

  @Bean
  @Profile("!test")
  public PaymentGateway paymentGateway() {
    return new StripePaymentGateway(
        RequestOptions.builder().setApiKey(adaptersConfiguration.getStripeApiKey()).build());
  }

  @Bean
  public CodesGenerator codesGenerator() {
    return new HashIdsCodesGenerator(adaptersConfiguration.getHashIdsSalt());
  }
}
