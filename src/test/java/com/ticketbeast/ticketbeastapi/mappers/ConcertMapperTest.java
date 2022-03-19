package com.ticketbeast.ticketbeastapi.mappers;

import static com.montealegreluis.tickebeast.builders.concerts.ConcertBuilder.aConcert;
import static org.junit.jupiter.api.Assertions.*;

import com.ticketbeast.ticketbeastapi.mappers.concerts.ConcertMapper;
import org.junit.jupiter.api.Test;

final class ConcertMapperTest {
  @Test
  void it_maps_a_concert_to_a_response() {
    var concert = aConcert().build();
    var response = mapper.map(concert);

    assertEquals(response.id, concert.getId());
    assertEquals(response.title, concert.getTitle());
    assertEquals(response.subtitle, concert.getSubtitle());
    assertEquals(response.concertDate, concert.getDate());
    assertEquals(response.additionalInformation, concert.getAdditionalInformation());
    assertEquals(response.venue.name, concert.getVenue().getName());
    assertEquals(
        response.venue.address.streetAndNumber,
        concert.getVenue().getAddress().getStreetAndNumber());
    assertEquals(response.venue.address.city, concert.getVenue().getAddress().getCity());
    assertEquals(response.venue.address.state, concert.getVenue().getAddress().getState());
    assertEquals(response.venue.address.zipCode, concert.getVenue().getAddress().getZipCode());
    assertEquals(response.ticketPrice.amount, concert.getTicketPrice().getAmount());
    assertEquals(response.ticketPrice.currency, concert.getTicketPrice().getCurrency());
  }

  @Test
  void it_maps_null() {
    var response = mapper.map(null);

    assertNull(response);
  }

  private final ConcertMapper mapper = ConcertMapper.INSTANCE;
}
