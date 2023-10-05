package ua.nrubantseva.api.users.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import ua.nrubantseva.api.users.exception.UserAgeRestrictionException;

/**
 * Global exception handler for handling specific exceptions thrown in the application.
 * It provides standardized error responses for different types of exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions of type EntityNotFoundException.
     *
     * @param e       The EntityNotFoundException instance.
     * @param request The ServletWebRequest containing information about the request.
     * @return A ResponseEntity containing an ErrorMessageResponse for the not found exception.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessageResponse> handleNotFoundExceptions(Exception e, ServletWebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(logAndGetErrorMessage(request, e.getLocalizedMessage(), e));
    }

    /**
     * Handles exceptions of type UserAgeRestrictionException.
     *
     * @param e       The UserAgeRestrictionException instance.
     * @param request The ServletWebRequest containing information about the request.
     * @return An ErrorMessageResponse for the bad request exception.
     */
    @ExceptionHandler(UserAgeRestrictionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageResponse handleBadRequestException(RuntimeException e, ServletWebRequest request) {
        return logAndGetErrorMessage(request, e.getLocalizedMessage(), e);
    }

    /**
     * Generates an ErrorMessageResponse based on the exception, request path, and HTTP method.
     *
     * @param request The ServletWebRequest containing information about the request.
     * @param message The error message from the exception.
     * @param e       The exception instance.
     * @return An ErrorMessageResponse containing error details.
     */
    private ErrorMessageResponse logAndGetErrorMessage(ServletWebRequest request, String message, Exception e) {
        var errorMessage = new ErrorMessageResponse(message, request.getRequest().getRequestURI(), request.getHttpMethod().name());
        return errorMessage;
    }
}

