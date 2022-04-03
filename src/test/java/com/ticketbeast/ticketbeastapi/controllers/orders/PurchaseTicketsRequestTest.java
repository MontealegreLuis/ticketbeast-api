package com.ticketbeast.ticketbeastapi.controllers.orders;

import static com.ticketbeast.ticketbeastapi.builders.orders.PurchaseTicketsRequestBuilder.aPurchaseTicketsRequest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class PurchaseTicketsRequestTest {
  @Test
  void it_prevents_null_values() {
    var request = aPurchaseTicketsRequest().withNullValues().build();

    var violations = validator.validate(request);

    var paths =
        violations.stream()
            .map(violation -> violation.getPropertyPath().toString())
            .collect(Collectors.toList());
    assertEquals(3, paths.size());
    assertTrue(paths.contains("concertId"));
    assertTrue(paths.contains("email"));
    assertTrue(paths.contains("paymentToken"));
  }

  @Test
  void it_prevents_empty_values() {
    var request = aPurchaseTicketsRequest().withEmptyValues().build();

    var violations = validator.validate(request);

    var paths =
        violations.stream()
            .map(violation -> violation.getPropertyPath().toString())
            .collect(Collectors.toList());
    assertEquals(3, paths.size());
    assertTrue(paths.contains("concertId"));
    assertTrue(paths.contains("email"));
    assertTrue(paths.contains("paymentToken"));
  }

  @Test
  void it_prevents_buying_less_than_one_ticket() {
    var request = aPurchaseTicketsRequest().withQuantity(0).build();

    var violations = validator.validate(request);

    var paths =
        violations.stream()
            .map(violation -> violation.getPropertyPath().toString())
            .collect(Collectors.toList());
    assertEquals(1, paths.size());
    assertTrue(paths.contains("ticketsQuantity"));
  }

  @BeforeEach
  void let() {
    var factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  private Validator validator;
}
