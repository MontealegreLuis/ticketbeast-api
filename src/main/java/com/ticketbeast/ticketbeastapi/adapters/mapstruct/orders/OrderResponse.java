package com.ticketbeast.ticketbeastapi.adapters.mapstruct.orders;

import com.montealegreluis.ticketbeast.shared.StringValueObject;
import com.ticketbeast.ticketbeastapi.adapters.mapstruct.concerts.MoneyResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class OrderResponse {
  @Schema(
      description = "Order confirmation number",
      type = "string",
      example = "984A3447F7A54E2A8F7FC33P")
  public final StringValueObject confirmationNumber;

  @Schema(
      description = "Customer's email address",
      type = "string",
      example = "example@example.com")
  public final StringValueObject email;

  @Schema(description = "Order's total amount")
  public final MoneyResponse total;

  @Schema(description = "Card's last 4 digits", type = "string", example = "2406")
  public final StringValueObject cardLast4Digits;

  @Schema(description = "Tickets from this order")
  public final List<TicketResponse> tickets;
}
