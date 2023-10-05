package ua.nrubantseva.api.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a request to modify user information.
 * This class is used for creating or updating user details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModificationRequest {

    /**
     * The email address of the user. It must be a valid email format and cannot be null.
     */
    @Email
    @NotNull
    private String email;

    /**
     * The first name of the user. It must not be blank.
     */
    @NotBlank
    private String firstName;

    /**
     * The last name of the user. It must not be blank.
     */
    @NotBlank
    private String lastName;

    /**
     * The birth date of the user. It must be in the past.
     */
    @Past
    private LocalDate birthDate;

    /**
     * The address of the user. It can be null or an empty string.
     */
    private String address;

    /**
     * The phone number of the user. It can be null or an empty string.
     */
    private String phone;
}

