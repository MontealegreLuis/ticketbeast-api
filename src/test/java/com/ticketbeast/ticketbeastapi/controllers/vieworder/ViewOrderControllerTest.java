package com.ticketbeast.ticketbeastapi.controllers.vieworder;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.montealegreluis.tickebeast.fixtures.OrdersFixture;
import com.montealegreluis.ticketbeast.adapters.jpa.repositories.concerts.ConcertsRepository;
import com.montealegreluis.ticketbeast.adapters.jpa.repositories.orders.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
final class ViewOrderControllerTest {
  @Test
  void it_finds_an_existing_order() throws Exception {
    var order = scenario.withAnOrderWithTickets(1);
    orders.save(order);

    mvc.perform(
            get("/v1/orders/" + order.confirmationNumber().value())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.confirmationNumber").value(order.getConfirmationNumber().value()));
  }

  @Test
  void it_fails_to_find_an_unknown_order() throws Exception {
    mvc.perform(get("/v1/orders/AC8A65422E46DA46975235BD").accept(MediaType.APPLICATION_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.code").value("view-order-unknown-order"));
  }

  @Test
  void it_fails_to_find_an_order_with_invalid_id() throws Exception {
    mvc.perform(get("/v1/orders/invalid").accept(MediaType.APPLICATION_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.code").value("view-order-invalid-input"));
  }

  @BeforeEach
  void let() {
    scenario = new OrdersFixture(concerts);
    validator = OpenApiInteractionValidator.createForSpecificationUrl("/docs/api.yml").build();
  }

  @Autowired private MockMvc mvc;
  @Autowired private ConcertsRepository concerts;
  @Autowired private OrdersRepository orders;
  private OpenApiInteractionValidator validator;
  private OrdersFixture scenario;
}
