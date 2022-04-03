package com.ticketbeast.ticketbeastapi.adapters.mapstruct.concerts;

import com.montealegreluis.ticketbeast.shared.StringValueObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class AddressResponse {
  @Schema(description = "Address street and number", type = "string", example = "Main St. 123")
  public final StringValueObject streetAndNumber;

  @Schema(description = "Address city", type = "string", example = "Austin")
  public final StringValueObject city;

  @Schema(description = "Address state", type = "string", example = "TX")
  public final StringValueObject state;

  @Schema(description = "Address zip code", type = "string", example = "12345")
  public final StringValueObject zipCode;
}
