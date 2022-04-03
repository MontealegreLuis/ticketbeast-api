package com.ticketbeast.ticketbeastapi.config;

import com.montealegreluis.servicebuses.Command;
import com.montealegreluis.servicebuses.Query;
import com.montealegreluis.ticketbeast.concerts.Money;
import com.montealegreluis.ticketbeast.payments.PaymentFailed;
import com.montealegreluis.ticketbeast.payments.PaymentGateway;
import com.montealegreluis.ticketbeast.payments.PaymentToken;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
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
  @Bean
  public PaymentGateway paymentGateway() {
    return new TempPaymentGateway();
  }

  public static class TempPaymentGateway implements PaymentGateway {
    public static final String VALID_TOKEN = "0e917e2b-c950-4673-a417-bc3ba585b269";

    @Override
    public void charge(Money money, PaymentToken paymentToken) throws PaymentFailed {
      if (!VALID_TOKEN.equals(paymentToken.value())) {
        throw PaymentFailed.forTransactionWith(paymentToken);
      }
    }
  }
}
