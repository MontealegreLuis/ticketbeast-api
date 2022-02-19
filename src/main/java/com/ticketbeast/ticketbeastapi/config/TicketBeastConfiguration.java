package com.ticketbeast.ticketbeastapi.config;

import com.montealegreluis.servicebuses.Query;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.montealegreluis.ticketbeast")
@EnableJpaRepositories("com.montealegreluis.ticketbeast.adapters.jpa")
@ComponentScan(
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Query.class),
    value = {"com.montealegreluis.ticketbeast"})
public class TicketBeastConfiguration {}
