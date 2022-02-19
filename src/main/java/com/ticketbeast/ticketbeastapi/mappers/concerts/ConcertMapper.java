package com.ticketbeast.ticketbeastapi.mappers.concerts;

import com.montealegreluis.ticketbeast.concerts.Concert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConcertMapper {
  ConcertMapper INSTANCE = Mappers.getMapper(ConcertMapper.class);

  @Mapping(source = "date", target = "concertDate")
  ConcertResponse map(Concert concert);
}
