package ua.nrubantseva.api.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.nrubantseva.api.users.dto.UserModificationRequest;
import ua.nrubantseva.api.users.exception.EntityIdNotFoundException;
import ua.nrubantseva.api.users.exception.UserAgeRestrictionException;
import ua.nrubantseva.api.users.model.User;
import ua.nrubantseva.api.users.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service class providing business logic for managing User entities.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Repository for User entities.
     */
    public final UserRepository userRepository;

    /**
     * Age restriction for user registration.
     */
    @Value("${user.age.restriction}")
    private int userAgeRestriction;

    /**
     * Creates a new user based on the provided UserModificationRequest.
     *
     * @param userModificationRequest The request containing user information.
     * @return The created User entity.
     * @throws UserAgeRestrictionException If the user does not meet the age restriction.
     */
    public User createUser(UserModificationRequest userModificationRequest) {
        isNotAgeRestriction(userModificationRequest.getBirthDate());

        return userRepository.save(mapUserModificationRequestToUser(userModificationRequest));
    }

    /**
     * Updates an existing user based on the provided UserModificationRequest.
     *
     * @param userId                   The ID of the user to be updated.
     * @param userModificationRequest The request containing updated user information.
     * @throws UserAgeRestrictionException If the user does not meet the age restriction.
     * @throws EntityIdNotFoundException   If the user with the specified ID is not found.
     */
    public void updateUser(UUID userId, UserModificationRequest userModificationRequest) {
        isNotAgeRestriction(userModificationRequest.getBirthDate());
        isExistById(userId);

        User user = mapUserModificationRequestToUser(userModificationRequest);
        user.setId(userId);
        userRepository.save(user);
    }

    /**
     * Maps a UserModificationRequest to a User entity.
     *
     * @param userModificationRequest The request containing user information.
     * @return The mapped User entity.
     */
    public User mapUserModificationRequestToUser(UserModificationRequest userModificationRequest) {
        User user = new User();
        user.setEmail(userModificationRequest.getEmail());
        user.setFirstName(userModificationRequest.getFirstName());
        user.setLastName(userModificationRequest.getLastName());
        user.setBirthDate(userModificationRequest.getBirthDate());
        user.setAddress(userModificationRequest.getAddress());
        user.setPhone(userModificationRequest.getPhone());

        return user;
    }

    /**
     * Updates the email of an existing user.
     *
     * @param userId The ID of the user to be updated.
     * @param email  The new email address.
     * @throws EntityIdNotFoundException If the user with the specified ID is not found.
     */
    public void updateUserEmail(UUID userId, String email) {
        User userInDB = isExistById(userId);
        userInDB.setEmail(email);
        userRepository.save(userInDB);
    }

    /**
     * Checks if a user's birth date meets the age restriction.
     *
     * @param birthDate The birth date of the user.
     * @throws UserAgeRestrictionException If the user does not meet the age restriction.
     */
    private void isNotAgeRestriction(LocalDate birthDate) {
        if (birthDate.plusYears(userAgeRestriction).isAfter(LocalDate.now())) {
            throw new UserAgeRestrictionException("User must be more than " + userAgeRestriction + " age");
        }
    }

    /**
     * Checks if a user with the specified ID exists in the repository.
     *
     * @param userId The ID of the user to check.
     * @return The User entity if found.
     * @throws EntityIdNotFoundException If the user with the specified ID is not found.
     */
    private User isExistById(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityIdNotFoundException("user with id " + userId + " is not found"));
    }

    /**
     * Deletes a user based on the specified ID.
     *
     * @param userId The ID of the user to be deleted.
     * @throws EntityIdNotFoundException If the user with the specified ID is not found.
     */
    public void deleteUserById(UUID userId) {
        isExistById(userId);
        userRepository.deleteById(userId);
    }

    /**
     * Retrieves a list of users with birth dates within the specified range.
     *
     * @param fromDate The start date of the birth date range.
     * @param toDate   The end date of the birth date range.
     * @return A list of users whose birth dates fall within the specified range.
     */
    public List<User> findByBirthDateRange(LocalDate fromDate, LocalDate toDate) {
        return userRepository.findByBirthDateGreaterThanEqualAndBirthDateLessThanEqual(fromDate, toDate);
    }
}
