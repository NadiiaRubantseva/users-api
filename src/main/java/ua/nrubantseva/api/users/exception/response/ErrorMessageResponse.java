package ua.nrubantseva.api.users.exception.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * A record representing a standardized error response for API exceptions.
 * It includes information such as the error message, timestamp, request path, and HTTP method.
 */
public record ErrorMessageResponse(String error, String path, String method) {

    /**
     * Converts the ErrorMessageResponse to a formatted string.
     *
     * @return A formatted string containing the error message, timestamp, request path, and HTTP method.
     */
    @Override
    public String toString() {
        return new StringJoiner(".", "API error: ", ".")
                .add(error)
                .add(" At " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME))
                .add(" Path: " + path)
                .add(" Method: " + method)
                .toString();
    }
}

