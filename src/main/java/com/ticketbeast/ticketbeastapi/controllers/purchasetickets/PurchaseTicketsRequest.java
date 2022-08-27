package com.ticketbeast.ticketbeastapi.controllers.purchasetickets;

import com.montealegreluis.ticketbeast.orders.purchasetickets.PurchaseTicketsInput;
import com.ticketbeast.ticketbeastapi.adapters.validation.Email;
import com.ticketbeast.ticketbeastapi.adapters.validation.Uuid;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class PurchaseTicketsRequest {
  @Uuid
  @Schema(
      description = "Unique identifier of the concert",
      format = "uuid",
      example = "6eff8716-8a40-45c8-bc6a-d6315ad5ee11")
  private String concertId;

  @Min(1)
  @Schema(description = "Amount of tickets to buy", example = "2")
  private int ticketsQuantity;

  @Email
  @Schema(
      description = "Email address of the buyer",
      format = "email",
      example = "john@example.com")
  private String email;

  @NotBlank
  @Schema(
      description = "Secure authorization token to pay for the tickets",
      example = "3eaec31c-ac6b-4d20-9be4-d53ff685dd7f")
  private String paymentToken;

  public PurchaseTicketsInput command() {
    return new PurchaseTicketsInput(concertId, ticketsQuantity, email, paymentToken);
  }
}
