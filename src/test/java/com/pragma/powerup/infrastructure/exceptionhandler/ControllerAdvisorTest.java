package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.mockito.Mockito.mock;

class ControllerAdvisorTest {

    ControllerAdvisor controllerAdvisor = new ControllerAdvisor();

    @Test
    void testHandleNoDataFoundException() {
        NoDataFoundException exception = new NoDataFoundException();
        ResponseEntity<Map<String, String>> result = controllerAdvisor.handleNoDataFoundException(exception);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode(),
                "The status code must be NOT_FOUND");
        Assertions.assertEquals(ExceptionResponse.NO_DATA_FOUND.getMessage(),
                result.getBody().get("message"),
                "The message in the body does not match the expected one");
    }

    @Test
    void testHandleException() {
        Exception exception = new Exception("General exception", new Throwable("General exception"));
        ResponseEntity<Map<String, String>> result = controllerAdvisor.handleException(exception);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode(),
                "The status code must be INTERNAL_SERVER_ERROR");
        Assertions.assertEquals(ExceptionResponse.INTERNAL_SERVER_ERROR.getMessage(),
                result.getBody().get("message"),
                "The message in the body does not match the expected one");
    }

    @Test
    void testDomainException() {
        DomainException exception = new DomainException("General exception");
        ResponseEntity<Map<String, String>> result = controllerAdvisor.handleDomainException(exception);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(),
                "The status code must be INTERNAL_SERVER_ERROR");
        Assertions.assertEquals(ExceptionResponse.INTERNAL_SERVER_ERROR.getMessage(),
                result.getBody().get("message"),
                "The message in the body does not match the expected one");
    }

    @Test
    void testHandleValidationExceptions() {
        Object target = new Object();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, "target");
        bindingResult.addError(new FieldError("target", "field1", "error message"));

        MethodParameter methodParameter = mock(MethodParameter.class);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        Map<String, String> errors = controllerAdvisor.handleValidationExceptions(ex);

        Assertions.assertEquals(1, errors.size(), "Se esperaba un único error");
        Assertions.assertEquals("error message", errors.get("field1"), "El mensaje de error no coincide");
    }
}
