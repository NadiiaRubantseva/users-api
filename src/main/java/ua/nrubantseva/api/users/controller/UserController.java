package ua.nrubantseva.api.users.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.nrubantseva.api.users.dto.UserBirthDateRangeFilter;
import ua.nrubantseva.api.users.dto.UserModificationRequest;
import ua.nrubantseva.api.users.model.User;
import ua.nrubantseva.api.users.service.UserService;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for managing user-related operations through RESTful API.
 * Handles endpoints for creating, updating, retrieving, and deleting user information.
 */
@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    /**
     * Service responsible for handling user-related business logic.
     */
    public final UserService userService;

    /**
     * Retrieves a list of users within the specified birth date range.
     *
     * @param filter The filter containing the 'fromDate' and 'toDate' for the birth date range.
     * @return A list of users matching the specified birth date range.
     */
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<User> findUserByRange(@Valid UserBirthDateRangeFilter filter) {
        return userService.findByBirthDateRange(filter.getFromDate(), filter.getToDate());
    }

    /**
     * Creates a new user based on the provided user modification request.
     *
     * @param userModificationRequest The request containing user information for creation.
     * @return The created user.
     */
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid UserModificationRequest userModificationRequest) {
        return userService.createUser(userModificationRequest);
    }

    /**
     * Updates an existing user with the specified user ID using the provided modification request.
     *
     * @param userId                  The unique identifier of the user to be updated.
     * @param userModificationRequest The request containing updated user information.
     */
    @PutMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable UUID userId, @Valid UserModificationRequest userModificationRequest) {
        userService.updateUser(userId, userModificationRequest);
    }

    /**
     * Updates the email address of an existing user with the specified user ID.
     *
     * @param userId The unique identifier of the user to update the email address.
     * @param email  The new email address for the user.
     */
    @PutMapping(value = "/users/{userId}/email", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateUserEmail(@PathVariable UUID userId, @Email String email) {
        userService.updateUserEmail(userId, email);
    }

    /**
     * Deletes an existing user with the specified user ID.
     *
     * @param userId The unique identifier of the user to be deleted.
     */
    @DeleteMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
    }

}
