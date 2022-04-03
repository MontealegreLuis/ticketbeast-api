package com.ticketbeast.ticketbeastapi.config;

import com.montealegreluis.servicebuses.commandbus.CommandBus;
import com.montealegreluis.servicebuses.querybus.QueryBus;
import com.montealegreluis.servicebusesmiddleware.commandbus.MiddlewareCommandBus;
import com.montealegreluis.servicebusesmiddleware.commandbus.middleware.CommandMiddleware;
import com.montealegreluis.servicebusesmiddleware.commandbus.middleware.error.CommandErrorHandlerMiddleware;
import com.montealegreluis.servicebusesmiddleware.commandbus.middleware.events.EventsLoggerMiddleware;
import com.montealegreluis.servicebusesmiddleware.commandbus.middleware.handler.CommandHandlerMiddleware;
import com.montealegreluis.servicebusesmiddleware.commandbus.middleware.logger.CommandLoggerMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.MiddlewareQueryBus;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.QueryMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.error.QueryErrorHandlerMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.handler.QueryHandlerMiddleware;
import com.montealegreluis.servicebusesmiddleware.querybus.middleware.logger.QueryLoggerMiddleware;
import com.montealegreluis.servicebusesspringboot.commandbus.middleware.transaction.TransactionMiddleware;
import com.montealegreluis.servicebusesspringboot.springbootfactories.WithServiceBuses;
import com.ticketbeast.ticketbeastapi.TicketBeastApiApplication;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@EnableTransactionManagement
@ComponentScan(
    includeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = TransactionMiddleware.class)
    },
    value = {"com.montealegreluis.servicebusesspringboot.commandbus.middleware.transaction"})
public class ServiceBusesConfiguration implements WithServiceBuses {
  @Bean
  @RequestScope
  public QueryBus queryBus(
      QueryHandlerMiddleware queryHandler,
      QueryLoggerMiddleware logger,
      QueryErrorHandlerMiddleware errorHandler) {

    final List<QueryMiddleware> middleware = List.of(errorHandler, logger, queryHandler);

    return new MiddlewareQueryBus(middleware);
  }

  @Bean
  @RequestScope
  public CommandBus commandBus(
      CommandHandlerMiddleware commandHandler,
      CommandLoggerMiddleware logger,
      EventsLoggerMiddleware eventsLogger,
      CommandErrorHandlerMiddleware errorHandler,
      TransactionMiddleware transaction) {

    final List<CommandMiddleware> middleware =
        List.of(errorHandler, transaction, logger, eventsLogger, commandHandler);

    return new MiddlewareCommandBus(middleware);
  }

  @Override
  public String queryHandlersPackageName() {
    return "com.montealegreluis.ticketbeast";
  }

  @Override
  public String commandHandlersPackageName() {
    return "com.montealegreluis.ticketbeast";
  }

  @Override
  public Class<?> applicationClass() {
    return TicketBeastApiApplication.class;
  }
}
