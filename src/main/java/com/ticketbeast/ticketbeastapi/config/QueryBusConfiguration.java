package com.ticketbeast.ticketbeastapi.config;

import com.montealegreluis.servicebuses.querybus.QueryBus;
import com.montealegreluis.servicebusesmiddleware.querybus.MiddlewareQueryBus;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.QueryMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.error.QueryErrorHandlerMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.handler.QueryHandlerMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.logger.QueryLoggerMiddleware;
import com.montealegreluis.servicebusesspringboot.springbootfactories.WithQueryBus;
import com.ticketbeast.ticketbeastapi.TicketBeastApiApplication;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class QueryBusConfiguration implements WithQueryBus {
  @Bean
  @RequestScope
  public QueryBus queryBus(
      QueryHandlerMiddleware queryHandler,
      QueryLoggerMiddleware logger,
      QueryErrorHandlerMiddleware errorHandler) {

    List<QueryMiddleware> middleware = List.of(errorHandler, logger, queryHandler);

    return new MiddlewareQueryBus(middleware);
  }

  @Override
  public String queryHandlersPackageName() {
    return "com.montealegreluis.ticketbeast";
  }

  @Override
  public Class<?> applicationClass() {
    return TicketBeastApiApplication.class;
  }
}
