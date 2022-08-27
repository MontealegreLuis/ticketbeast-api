package com.ticketbeast.ticketbeastapi.controllers.purchasetickets;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static com.montealegreluis.tickebeast.builders.concerts.ConcertBuilder.aConcert;
import static com.ticketbeast.ticketbeastapi.builders.orders.PurchaseTicketsRequestBuilder.aPurchaseTicketsRequest;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.montealegreluis.ticketbeast.adapters.jpa.repositories.concerts.ConcertsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
final class PurchaseTicketsControllerTest {
  @Test
  void it_purchases_tickets_successfully() throws Exception {
    var concert = aConcert().published().withTicketsCount(5).build();
    concerts.save(concert);
    var request = aPurchaseTicketsRequest().withConcertId(concert.id()).withQuantity(4).build();

    mvc.perform(
            post("/v1/orders/")
                .content(mapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON, APPLICATION_PROBLEM_JSON))
        .andExpect(openApi().isValid(validator));
  }

  @Test
  void it_fails_to_complete_a_purchase_if_concert_cannot_be_found() throws Exception {
    var request = aPurchaseTicketsRequest().build();

    mvc.perform(
            post("/v1/orders/")
                .content(mapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON, APPLICATION_PROBLEM_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.code").value("purchase-tickets-unknown-concert"));
  }

  @Test
  void it_fails_to_complete_a_purchase_if_there_are_not_enough_tickets_available()
      throws Exception {
    var concert = aConcert().published().withTicketsCount(3).build();
    concerts.save(concert);
    var request = aPurchaseTicketsRequest().withConcertId(concert.id()).withQuantity(4).build();

    mvc.perform(
            post("/v1/orders/")
                .content(mapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON, APPLICATION_PROBLEM_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.code").value("purchase-tickets-not-enough-tickets"));
  }

  @Test
  void it_fails_to_complete_a_purchase_if_payment_fails() throws Exception {
    var concert = aConcert().published().withTicketsCount(3).build();
    concerts.save(concert);
    var request =
        aPurchaseTicketsRequest()
            .withConcertId(concert.id())
            .withQuantity(3)
            .withInvalidPaymentToken()
            .build();

    mvc.perform(
            post("/v1/orders/")
                .content(mapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON, APPLICATION_PROBLEM_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.code").value("purchase-tickets-payment-failed"));
  }

  @Test
  void it_fails_to_complete_a_purchase_if_invalid_input_is_provided() throws Exception {
    var concert = aConcert().published().withTicketsCount(1).build();
    concerts.save(concert);
    var request = aPurchaseTicketsRequest().withQuantity(0).build();

    mvc.perform(
            post("/v1/orders/")
                .content(mapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON, APPLICATION_PROBLEM_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.code").value("purchase-tickets-invalid-input"));
  }

  @BeforeEach
  void let() {
    validator = OpenApiInteractionValidator.createForSpecificationUrl("/docs/api.yml").build();
  }

  private OpenApiInteractionValidator validator;
  @Autowired private MockMvc mvc;
  @Autowired private ConcertsRepository concerts;
  private static final ObjectMapper mapper = new ObjectMapper();
}
