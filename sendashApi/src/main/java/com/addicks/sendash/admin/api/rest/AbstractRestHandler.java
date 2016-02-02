package com.addicks.sendash.admin.api.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.addicks.sendash.admin.domain.RestErrorInfo;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.exception.DataFormatException;
import com.addicks.sendash.admin.exception.ResourceNotFoundException;

/**
 * This class is meant to be extended by all REST resource "controllers". It
 * contains exception mapping and other common REST API functionality
 */
// @ControllerAdvice?
public abstract class AbstractRestHandler implements ApplicationEventPublisherAware {

  protected final Logger log = LoggerFactory.getLogger(this.getClass());

  protected ApplicationEventPublisher eventPublisher;

  protected static final String DEFAULT_PAGE_SIZE = "100";

  protected static final String DEFAULT_PAGE_NUM = "0";

  protected static final String DEFAULT_SORT = "DESC";

  protected static final String DEFAULT_SORT_FIELD = "id";

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(DataFormatException.class)
  public @ResponseBody RestErrorInfo handleDataStoreException(DataFormatException ex,
      WebRequest request, HttpServletResponse response) {
    log.info("Converting Data Store exception to RestResponse : " + ex.getMessage());

    return new RestErrorInfo(ex, "You messed up.");
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({ ResourceNotFoundException.class })
  public @ResponseBody RestErrorInfo handleResourceNotFoundException(ResourceNotFoundException ex,
      WebRequest request, HttpServletResponse response) {
    log.info("ResourceNotFoundException handler:" + ex.getMessage());

    return new RestErrorInfo(ex, "Sorry I couldn't find it.");
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  RestErrorInfo handleException(MethodArgumentNotValidException ex) {
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
    List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
    String error;
    for (FieldError fieldError : fieldErrors) {
      error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
      errors.add(error);
    }
    for (ObjectError objectError : globalErrors) {
      error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
      errors.add(error);
    }
    return new RestErrorInfo(ex, StringUtils.join(errors, ","));
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.eventPublisher = applicationEventPublisher;
  }

  // todo: replace with exception mapping
  public static <T> T checkResourceFound(final T resource) {
    if (resource == null) {
      throw new ResourceNotFoundException("resource not found");
    }
    return resource;
  }

  protected User getUserFromAuthentication(OAuth2Authentication oauthUser) {
    return (User) oauthUser.getPrincipal();
  }

}