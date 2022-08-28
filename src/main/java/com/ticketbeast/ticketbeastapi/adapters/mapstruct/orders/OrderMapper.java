package com.ticketbeast.ticketbeastapi.adapters.mapstruct.orders;

import com.montealegreluis.ticketbeast.orders.Order;
import com.ticketbeast.ticketbeastapi.adapters.mapstruct.concerts.ConcertMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ConcertMapper.class})
public interface OrderMapper {
  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  OrderResponse map(Order order);
}
