package com.ticketbeast.ticketbeastapi.config;

import com.montealegreluis.tickebeast.fakes.payments.FakePaymentGateway;
import com.montealegreluis.ticketbeast.payments.PaymentGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentGatewayConfiguration {
  @Bean
  public PaymentGateway paymentGateway() {
    return new FakePaymentGateway();
  }
}
