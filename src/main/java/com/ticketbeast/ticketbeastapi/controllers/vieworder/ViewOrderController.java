package com.ticketbeast.ticketbeastapi.controllers.vieworder;

import com.montealegreluis.apiproblem.ApiProblem;
import com.montealegreluis.servicebuses.ActionException;
import com.montealegreluis.servicebuses.querybus.QueryBus;
import com.montealegreluis.ticketbeast.orders.Order;
import com.montealegreluis.ticketbeast.orders.vieworder.ViewOrderInput;
import com.ticketbeast.ticketbeastapi.adapters.mapstruct.orders.OrderMapper;
import com.ticketbeast.ticketbeastapi.adapters.mapstruct.orders.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class ViewOrderController {
  private final OrderMapper mapper = OrderMapper.INSTANCE;
  private final QueryBus queryBus;

  public ViewOrderController(QueryBus queryBus) {
    this.queryBus = queryBus;
  }

  @Operation(
      summary = "View an existing order",
      description =
          "View an existing order. It includes payment information and tickets information")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Order information",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OrderResponse.class))),
    @ApiResponse(
        responseCode = "422",
        description = "Provided concert information is invalid",
        content =
            @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ApiProblem.class),
                examples = {
                  @ExampleObject(
                      name = "Invalid confirmation number",
                      value =
                          "{\"title\": \"Unprocessable Entity\",\"status\": 422,\"type\": \"https://tools.ietf.org/html/rfc4918#section-11.2\",\"detail\": \"Cannot view order. Invalid input provided\",\"code\": \"view-order-invalid-input\",\"errors\": {\"viewOrder.confirmationNumber\": \"must be a valid confirmation number\"}}")
                })),
    @ApiResponse(
        responseCode = "404",
        description = "Order cannot be found",
        content =
            @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ApiProblem.class),
                examples = {
                  @ExampleObject(
                      name = "Order not found",
                      value =
                          "{\"title\": \"Not Found\",\"status\": 404,\"type\": \"https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5\",\"detail\": \"Cannot view order. Cannot find order with ID 86c34ee9-8eb7-402a-9940-95e762fb3e92\",\"code\": \"view-order-unknown-order\"}")
                })),
    @ApiResponse(
        responseCode = "500",
        description = "Something went wrong while trying to find an order",
        content =
            @Content(
                mediaType = "application/problem+json",
                schema = @Schema(implementation = ApiProblem.class),
                examples = {
                  @ExampleObject(
                      name = "Cannot establish a database connection",
                      value =
                          "{\"title\": \"Internal Server Error\",\"status\": 500,\"type\": \"https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1\",\"detail\": \"Cannot view order. Could not open JPA EntityManager for transaction; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to acquire JDBC Connection\",\"code\": \"view-order-application-error\",\"exception\": {\"message\": \"Could not open JPA EntityManager for transaction; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to acquire JDBC Connection\",\"class\": \"com.montealegreluis.servicebusesmiddleware.querybus.middleware.error.QueryErrorHandlerMiddleware\",\"line\": 46,\"file\": \"QueryErrorHandlerMiddleware.java\",\"trace\": [\"com.montealegreluis.servicebusesmiddleware.querybus.middleware.error.QueryErrorHandlerMiddleware.rethrowException(QueryErrorHandlerMiddleware.java:46)\",\"com.montealegreluis.servicebusesmiddleware.querybus.middleware.error.QueryErrorHandlerMiddleware.lambda$execute$1(QueryErrorHandlerMiddleware.java:30)\",\"io.vavr.control.Try.getOrElseThrow(Try.java:748)\",\"com.montealegreluis.servicebusesmiddleware.querybus.middleware.error.QueryErrorHandlerMiddleware.execute(QueryErrorHandlerMiddleware.java:30)\",\"com.montealegreluis.servicebusesmiddleware.querybus.MiddlewareQueryBus.execute(MiddlewareQueryBus.java:32)\",\"com.montealegreluis.servicebusesmiddleware.querybus.MiddlewareQueryBus.dispatch(MiddlewareQueryBus.java:26)\"]}}")
                }))
  })
  @GetMapping("/v1/orders/{confirmationNumber}")
  public ResponseEntity<Object> viewOrder(
      @PathVariable
          @Pattern(regexp = "[A-Z1-9]{24}", message = "must be a valid confirmation number")
          @Schema(description = "Order confirmation number", example = "35BF45447C454824384D4DE7")
          String confirmationNumber)
      throws ActionException {
    final Order order = queryBus.dispatch(new ViewOrderInput(confirmationNumber));
    return new ResponseEntity<>(mapper.map(order), HttpStatus.OK);
  }
}
