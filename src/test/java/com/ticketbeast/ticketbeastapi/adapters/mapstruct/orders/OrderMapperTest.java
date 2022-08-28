package com.ticketbeast.ticketbeastapi.adapters.mapstruct.orders;

import static org.junit.jupiter.api.Assertions.*;

import com.montealegreluis.tickebeast.fakes.concerts.InMemoryConcerts;
import com.montealegreluis.tickebeast.fixtures.OrdersFixture;
import com.montealegreluis.ticketbeast.concerts.NotEnoughTickets;
import com.montealegreluis.ticketbeast.concerts.Ticket;
import java.util.List;
import org.junit.jupiter.api.Test;

final class OrderMapperTest {
  @Test
  void it_maps_a_concert_to_a_response() throws NotEnoughTickets {
    var scenario = new OrdersFixture(new InMemoryConcerts());
    var order = scenario.withAnOrderWithTickets(1);

    var response = mapper.map(order);

    assertEquals(response.confirmationNumber, order.getConfirmationNumber());
    assertEquals(response.cardLast4Digits, order.getCardLast4Digits());
    assertEquals(response.email, order.getEmail());
    assertEquals(response.total.amount, order.getTotal().getAmount());
    assertEquals(response.total.currency, order.getTotal().getCurrency());
    assertEquals(response.tickets.size(), order.getTickets().size());
    Ticket ticket = (Ticket) List.of(order.getTickets().toArray()).get(0);
    assertEquals(response.tickets.get(0).code, ticket.getCode());
    assertEquals(response.tickets.get(0).concert.title, ticket.getConcert().getTitle());
    assertEquals(response.tickets.get(0).concert.subtitle, ticket.getConcert().getSubtitle());
    assertEquals(response.tickets.get(0).concert.concertDate, ticket.getConcert().getDate());
    assertEquals(
        response.tickets.get(0).concert.venue.address.streetAndNumber,
        ticket.getConcert().getVenue().getAddress().getStreetAndNumber());
    assertEquals(
        response.tickets.get(0).concert.venue.address.state,
        ticket.getConcert().getVenue().getAddress().getState());
    assertEquals(
        response.tickets.get(0).concert.venue.address.city,
        ticket.getConcert().getVenue().getAddress().getCity());
    assertEquals(
        response.tickets.get(0).concert.venue.address.zipCode,
        ticket.getConcert().getVenue().getAddress().getZipCode());
    assertEquals(
        response.tickets.get(0).concert.venue.name, ticket.getConcert().getVenue().getName());
  }

  @Test
  void it_maps_null() {
    var response = mapper.map(null);

    assertNull(response);
  }

  private final OrderMapper mapper = OrderMapper.INSTANCE;
}
