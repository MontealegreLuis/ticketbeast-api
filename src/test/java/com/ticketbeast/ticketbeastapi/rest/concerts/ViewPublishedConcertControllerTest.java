package com.ticketbeast.ticketbeastapi.rest.concerts;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static com.montealegreluis.tickebeast.builders.concerts.ConcertBuilder.aConcert;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.montealegreluis.ticketbeast.adapters.jpa.repostories.concerts.ConcertsRepository;
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
final class ViewPublishedConcertControllerTest {
  @Test
  void it_finds_an_upcoming_published_concert() throws Exception {
    var publishedConcert = aConcert().published().build();
    concerts.save(publishedConcert);

    mvc.perform(get("/v1/concerts/" + publishedConcert.id()).accept(MediaType.APPLICATION_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.id").value(publishedConcert.id().value()));
  }

  @Test
  void it_fails_to_find_an_unknown_concert() throws Exception {
    mvc.perform(
            get("/v1/concerts/9d2e574e-d393-4b9e-817b-127b76c003fb")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.code").value("view-published-concert-unknown-concert"));
  }

  @Test
  void it_fails_to_find_a_concert_with_invalid_id() throws Exception {
    mvc.perform(get("/v1/concerts/invalid").accept(MediaType.APPLICATION_JSON))
        .andExpect(openApi().isValid(validator))
        .andExpect(jsonPath("$.code").value("view-published-concert-application-error"));
  }

  @BeforeEach
  void let() {
    validator = OpenApiInteractionValidator.createForSpecificationUrl("/api/api.yml").build();
  }

  private OpenApiInteractionValidator validator;
  @Autowired private MockMvc mvc;
  @Autowired private ConcertsRepository concerts;
}
