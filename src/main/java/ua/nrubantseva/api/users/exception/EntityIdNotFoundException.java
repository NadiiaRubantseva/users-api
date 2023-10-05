package ua.nrubantseva.api.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom runtime exception indicating that an entity with a specific ID is not found.
 * It is annotated with @ResponseStatus to automatically set the HTTP response status code to 404 (NOT_FOUND).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityIdNotFoundException extends RuntimeException {

    /**
     * Constructs a new EntityIdNotFoundException with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public EntityIdNotFoundException(String message) {
        super(message);
    }
}
