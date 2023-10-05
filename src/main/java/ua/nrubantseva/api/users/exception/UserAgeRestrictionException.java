package ua.nrubantseva.api.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom runtime exception indicating that a user's age restriction is not met.
 * It is annotated with @ResponseStatus to automatically set the HTTP response status code to 400 (BAD_REQUEST).
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAgeRestrictionException extends RuntimeException {

    /**
     * Constructs a new UserAgeRestrictionException with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public UserAgeRestrictionException(String message) {
        super(message);
    }
}
