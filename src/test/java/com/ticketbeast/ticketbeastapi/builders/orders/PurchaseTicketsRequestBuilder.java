package com.ticketbeast.ticketbeastapi.builders.orders;

import com.montealegreluis.tickebeast.builders.Random;
import com.montealegreluis.ticketbeast.shared.Uuid;
import com.ticketbeast.ticketbeastapi.controllers.purchasetickets.PurchaseTicketsRequest;

public final class PurchaseTicketsRequestBuilder {
  private String concertId = Random.uuid();
  private Integer ticketsQuantity = Random.ticketsQuantity();
  private String email = Random.email();
  private String paymentToken = Random.uuid();

  public static PurchaseTicketsRequestBuilder aPurchaseTicketsRequest() {
    return new PurchaseTicketsRequestBuilder();
  }

  public PurchaseTicketsRequestBuilder withConcertId(Uuid concertId) {
    this.concertId = concertId.value();
    return this;
  }

  public PurchaseTicketsRequestBuilder withQuantity(int ticketsQuantity) {
    this.ticketsQuantity = ticketsQuantity;
    return this;
  }

  public PurchaseTicketsRequestBuilder withInvalidPaymentToken() {
    this.paymentToken = "a98bf663-d172-495a-b07e-ea5301057444";
    return this;
  }

  public PurchaseTicketsRequestBuilder withNullValues() {
    concertId = null;
    email = null;
    paymentToken = null;
    return this;
  }

  public PurchaseTicketsRequestBuilder withEmptyValues() {
    concertId = " ";
    email = " ";
    paymentToken = " ";
    return this;
  }

  public PurchaseTicketsRequest build() {
    var request = new PurchaseTicketsRequest();
    request.setConcertId(concertId);
    request.setTicketsQuantity(ticketsQuantity);
    request.setEmail(email);
    request.setPaymentToken(paymentToken);
    return request;
  }
}
