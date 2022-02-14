package com.ticketbeast.ticketbeastapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.montealegreluis.activityfeed.ActivityFeed;
import com.montealegreluis.activityfeed.ContextSerializer;
import com.montealegreluis.servicebuses.querybus.QueryBus;
import com.montealegreluis.servicebuses.querybus.locator.ReflectionsQueryHandlerLocator;
import com.montealegreluis.servicebusesmiddleware.querybus.MiddlewareQueryBus;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.QueryMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.error.QueryErrorHandlerMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.handler.QueryHandlerMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.logger.QueryLoggerMiddleware;
import com.montealegreluis.servicebusesspringboot.querybus.factory.ApplicationContextQueryHandlerFactory;
import com.ticketbeast.ticketbeastapi.TicketBeastApiApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.List;

@Configuration
public class QueryBusConfiguration {
  @Bean
  public QueryBus queryBus(
      QueryHandlerMiddleware queryHandler,
      QueryLoggerMiddleware logger,
      QueryErrorHandlerMiddleware errorHandler) {

    List<QueryMiddleware> middleware = List.of(errorHandler, logger, queryHandler);

    return new MiddlewareQueryBus(middleware);
  }

  @Bean
  public QueryHandlerMiddleware queryHandlerMiddleware(ApplicationContext context) {
    var factory = new ApplicationContextQueryHandlerFactory(context);
    var locator = new ReflectionsQueryHandlerLocator("com.ticketbeast.ticketbeastapi");
    return new QueryHandlerMiddleware(locator, factory);
  }

  @Bean
  public QueryLoggerMiddleware queryLoggerMiddleware(ActivityFeed feed, Clock clock) {
    return new QueryLoggerMiddleware(feed, clock);
  }

  @Bean
  public QueryErrorHandlerMiddleware queryErrorHandlerMiddleware(
      ActivityFeed feed, ObjectMapper mapper) {
    return new QueryErrorHandlerMiddleware(feed, new ContextSerializer(mapper));
  }

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  public Logger logger() {
    return LoggerFactory.getLogger(TicketBeastApiApplication.class);
  }

  @Bean
  public ActivityFeed activityFeed(Logger logger) {
    return new ActivityFeed(logger);
  }
}
