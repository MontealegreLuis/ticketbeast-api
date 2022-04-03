package com.ticketbeast.ticketbeastapi.adapters.mapstruct.concerts;

import com.montealegreluis.ticketbeast.shared.StringValueObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class VenueResponse {
  @Schema(description = "Venue name", type = "string", example = "The Mosh Pit")
  public final StringValueObject name;

  @Schema(description = "Venue address")
  public final AddressResponse address;
}
