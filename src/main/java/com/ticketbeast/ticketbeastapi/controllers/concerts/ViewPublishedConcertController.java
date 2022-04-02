package com.ticketbeast.ticketbeastapi.controllers.concerts;

import com.montealegreluis.apiproblem.ApiProblem;
import com.montealegreluis.servicebuses.ActionException;
import com.montealegreluis.servicebuses.querybus.QueryBus;
import com.montealegreluis.ticketbeast.concerts.Concert;
import com.montealegreluis.ticketbeast.concerts.actions.ViewPublishedConcertInput;
import com.ticketbeast.ticketbeastapi.mappers.concerts.ConcertMapper;
import com.ticketbeast.ticketbeastapi.mappers.concerts.ConcertResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class ViewPublishedConcertController {
  private final ConcertMapper mapper = ConcertMapper.INSTANCE;
  private final QueryBus queryBus;

  public ViewPublishedConcertController(QueryBus queryBus) {
    this.queryBus = queryBus;
  }

  @Operation(
      summary = "View an upcoming concert information",
      description = "View an upcoming published concert information")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Upcoming concert information",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ConcertResponse.class))),
    @ApiResponse(
        responseCode = "422",
        description = "Provided concert information is invalid",
        content =
            @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ApiProblem.class),
                examples = {
                  @ExampleObject(
                      name = "Invalid Concert Information",
                      value =
                          "{\"title\": \"Unprocessable Entity\", \"status\": 422, \"type\": \"https://tools.ietf.org/html/rfc4918#section-11.2\", \"detail\": \"Cannot view published concert. Invalid input provided\", \"code\": \"view-published-concert-invalid-input\", \"errors\": {   \"viewPublishedConcert.concertId\": \"must not be blank\"}}")
                })),
    @ApiResponse(
        responseCode = "404",
        description = "Concert cannot be found",
        content =
            @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ApiProblem.class),
                examples = {
                  @ExampleObject(
                      name = "Concert not found",
                      value =
                          "{\"title\": \"Not Found\", \"status\": 404, \"type\": \"https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5\", \"detail\": \"Cannot view published concert. Concert with ID 35b0f454-470c-4514-8243-84d4de7ca033 cannot be found\", \"code\": \"view-published-concert-unknown-concert\"}")
                })),
    @ApiResponse(
        responseCode = "500",
        description = "Something went wrong while trying to find concerts",
        content =
            @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ApiProblem.class),
                examples = {
                  @ExampleObject(
                      name = "Invalid UUID",
                      value =
                          "{\"title\": \"Internal Server Error\",\"status\": 500,\"type\": \"https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1\",\"detail\": \"Cannot view published concert. '35b0f454-470c-4514-8243-84d4de7ca03g' is not a valid UUID\",\"code\": \"view-published-concert-application-error\",\"exception\": {\"message\": \"'35b0f454-470c-4514-8243-84d4de7ca03g' is not a valid UUID\",\"class\": \"com.montealegreluis.assertions.Assert\",\"line\": 88,\"file\": \"Assert.java\",\"trace\": [\"com.montealegreluis.assertions.Assert.reportIllegalArgument(Assert.java:88)\",\"com.montealegreluis.assertions.Assert.lambda$uuid$1(Assert.java:63)\",\"io.vavr.control.Try.onFailure(Try.java:659)\",\"com.montealegreluis.assertions.Assert.uuid(Assert.java:63)\",\"com.montealegreluis.assertions.Assert.uuid(Assert.java:59)\",\"com.montealegreluis.ticketbeast.values.Uuid.<init>(Uuid.java:26)\",\"com.montealegreluis.ticketbeast.values.Uuid.withValue(Uuid.java:22)\",\"com.montealegreluis.ticketbeast.concerts.actions.ViewPublishedConcertInput.<init>(ViewPublishedConcertInput.java:10)\",\"com.ticketbeast.ticketbeastapi.rest.concerts.ViewPublishedConcertController.viewPublishedConcert(ViewPublishedConcertController.java:83)\"]}}")
                })),
  })
  @GetMapping("/v1/concerts/{concertId}")
  public ResponseEntity<Object> viewPublishedConcert(
      @PathVariable
          @NotBlank
          @Schema(
              description = "Concert identifier",
              example = "35b0f454-470c-4514-8243-84d4de7ca033")
          String concertId)
      throws ActionException {
    final Concert publishedConcert = queryBus.dispatch(new ViewPublishedConcertInput(concertId));
    return new ResponseEntity<>(mapper.map(publishedConcert), HttpStatus.OK);
  }
}
