package com.ticketbeast.ticketbeastapi.mappers.concerts;

import com.montealegreluis.ticketbeast.values.StringValueObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class MoneyResponse {
  @Schema(description = "Amount in cents", type = "number", example = "18050")
  public final long amount;

  @Schema(description = "Currency code (ISO 4217)", type = "string", example = "USD")
  public final StringValueObject currency;
}
