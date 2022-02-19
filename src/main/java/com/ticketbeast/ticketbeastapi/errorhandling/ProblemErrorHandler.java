package com.ticketbeast.ticketbeastapi.errorhandling;

import static com.montealegreluis.apiproblem.ApiProblemBuilder.aProblem;

import com.montealegreluis.activityfeed.ActivityFeed;
import com.montealegreluis.apiproblem.ApiProblem;
import com.montealegreluis.apiproblem.ApiProblemBuilder;
import com.montealegreluis.apiproblem.Status;
import com.montealegreluis.servicebusesproblem.ActionApiProblemHandler;
import com.montealegreluis.servicebusesproblem.ActionThrowableAdvice;
import com.montealegreluis.servicebusesproblem.validation.ActionValidationAdvice;
import com.montealegreluis.ticketbeast.concerts.UnknownConcert;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;

@ControllerAdvice
public final class ProblemErrorHandler extends ActionApiProblemHandler
    implements ActionThrowableAdvice, ActionValidationAdvice {
  private final ActivityFeed feed;

  public ProblemErrorHandler(
      @Qualifier("requestMappingHandlerMapping") HandlerMapping mapping, ActivityFeed feed) {
    super(mapping);
    this.feed = feed;
  }

  @ExceptionHandler
  public ResponseEntity<ApiProblem> handle(UnknownConcert exception) {
    final ApiProblemBuilder builder = aProblem().from(Status.NOT_FOUND);

    exception
        .action()
        .ifPresent(
            action ->
                builder
                    .withDetail("Cannot " + action.toWords() + ". " + exception.getMessage())
                    .with("code", action.toSlug() + "-" + exception.code()));

    return problemResponse(builder.build(), HttpStatus.NOT_FOUND);
  }

  @Override
  public boolean includeStackTrace() {
    return true;
  }

  @Override
  public ActivityFeed feed() {
    return this.feed;
  }
}
