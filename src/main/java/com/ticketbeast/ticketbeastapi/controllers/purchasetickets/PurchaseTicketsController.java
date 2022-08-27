package com.ticketbeast.ticketbeastapi.controllers.purchasetickets;

import com.montealegreluis.apiproblem.ApiProblem;
import com.montealegreluis.servicebuses.ActionException;
import com.montealegreluis.servicebuses.commandbus.CommandBus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class PurchaseTicketsController {
  private final CommandBus commandBus;

  public PurchaseTicketsController(CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @Operation(
      summary = "Purchase tickets for an upcoming concert",
      description = "Purchase tickets for an upcoming concert")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Concert tickets have been ordered and paid",
        content = @Content(mediaType = "application/json")),
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
                          "{\"title\": \"Not Found\",\"status\": 404,\"type\": \"https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5\",\"detail\": \"Cannot purchase tickets. Concert with ID 740d893b-6a94-4749-980b-2b18a3499498 cannot be found\",\"code\": \"purchase-tickets-unknown-concert\"}")
                })),
    @ApiResponse(
        responseCode = "409",
        description = "Cannot complete tickets purchase",
        content =
            @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ApiProblem.class),
                examples = {
                  @ExampleObject(
                      name = "Not Enough Tickets Available",
                      value =
                          "{\"title\": \"Conflict\",\"status\": 409,\"type\": \"https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10\",\"detail\": \"Cannot purchase tickets. Cannot place order for von.mraz@gmail.com, trying to purchase 4 tickets, but only 3 are available\",\"code\": \"purchase-tickets-not-enough-tickets\"}"),
                  @ExampleObject(
                      name = "Payment Failed",
                      value =
                          "{\"title\": \"Conflict\",\"status\": 409,\"type\": \"https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10\",\"detail\": \"Cannot purchase tickets. Payment with token 'a98bf663-d172-495a-b07e-ea5301057444' cannot be completed\",\"code\": \"purchase-tickets-payment-failed\"}")
                })),
    @ApiResponse(
        responseCode = "422",
        description = "Invalid input provided",
        content =
            @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ApiProblem.class),
                examples = {
                  @ExampleObject(
                      name = "Invalid Tickets Quantity",
                      value =
                          "{\"title\": \"Unprocessable Entity\",\"status\": 422,\"type\": \"https://tools.ietf.org/html/rfc4918#section-11.2\",\"detail\": \"Cannot purchase tickets. Invalid input provided\",\"code\": \"purchase-tickets-invalid-input\",\"errors\": {\"ticketsQuantity\": \"must be greater than or equal to 1\"}}")
                })),
    @ApiResponse(
        responseCode = "500",
        description = "Internal Server Error",
        content =
            @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ApiProblem.class),
                examples = {
                  @ExampleObject(
                      name = "Internal Server Error",
                      value =
                          "{\"title\": \"Internal Server Error\",\"status\": 500,\"type\": \"https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1\",\"detail\": \"Cannot purchase tickets. JSON parse error: Cannot deserialize value\",\"code\": \"purchase-tickets-application-error\",\"exception\": {\"message\": \"JSON parse error: Cannot deserialize value of type `int` from String \\\"blur\\\": not a valid `int` value\",\"class\": \"org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter\",\"line\": 389,\"file\": \"AbstractJackson2HttpMessageConverter.java\",\"trace\": [\"org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:389)\"]}}")
                }))
  })
  @PostMapping("/v1/orders")
  public ResponseEntity<Object> purchaseTickets(@RequestBody @Valid PurchaseTicketsRequest request)
      throws ActionException {
    commandBus.dispatch(request.command());
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
