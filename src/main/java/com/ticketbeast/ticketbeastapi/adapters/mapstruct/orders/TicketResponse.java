package com.ticketbeast.ticketbeastapi.adapters.mapstruct.orders;

import com.montealegreluis.ticketbeast.shared.StringValueObject;
import com.ticketbeast.ticketbeastapi.adapters.mapstruct.concerts.ConcertResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class TicketResponse {
  @Schema(description = "Ticket's code", type = "string", example = "DEAFLB")
  public final StringValueObject code;

  @Schema(description = "Concert information")
  public final ConcertResponse concert;
}
