package com.ticketbeast.ticketbeastapi.mappers.concerts;

import com.montealegreluis.ticketbeast.values.StringValueObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ConcertResponse {
  @Schema(
      description = "Concert unique identifier",
      type = "string",
      example = "984a3447-f7a5-4e2a-81f7-f1c330662b5b")
  public final StringValueObject id;

  @Schema(description = "Concert title", type = "string", example = "The red chord")
  public final StringValueObject title;

  @Schema(
      description = "Concert subtitle",
      type = "string",
      example = "with Animosity and the Lethargy")
  public final StringValueObject subtitle;

  @Schema(description = "Concert date", type = "number", example = "1645573411000")
  public final Date concertDate;

  @Schema(description = "Ticket price")
  public final MoneyResponse ticketPrice;

  @Schema(
      description = "Additional information",
      type = "string",
      example = "For tickets call (555) 222-2222.")
  public final StringValueObject additionalInformation;

  @Schema(description = "Venue information where the concert will be held")
  public final VenueResponse venue;
}
