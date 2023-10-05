package ua.nrubantseva.api.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.nrubantseva.api.users.dto.UserModificationRequest;
import ua.nrubantseva.api.users.exception.EntityIdNotFoundException;
import ua.nrubantseva.api.users.exception.UserAgeRestrictionException;
import ua.nrubantseva.api.users.model.User;
import ua.nrubantseva.api.users.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User userFromDb;
    private UserModificationRequest userModificationRequest;

    @BeforeEach
    void setUp() {
        userFromDb = new User(UUID.randomUUID(), "e@gmail.com",
                "n", "r", LocalDate.of(2003, 7, 28), null, null);
        userModificationRequest = new UserModificationRequest("e@gmail.com", "n",
                "r", LocalDate.of(2003, 7, 28), null, null);
    }

    /**
     * Tests that the createUser method creates a user when user data is correct.
     */
    @Test
    void create_user_should_create_user_when_user_data_is_correct() {
        when(userRepository.save(any(User.class))).thenReturn(userFromDb);

        userService.createUser(userModificationRequest);

        verify(userRepository).save(any(User.class));
    }

    /**
     * Tests that createUser method throws an exception when the user has age restriction.
     */
    @Test
    void create_user_should_throw_exception_when_user_has_age_restriction() {
        userModificationRequest.setBirthDate(LocalDate.of(2009, 10, 10));

        assertThrows(UserAgeRestrictionException.class, () -> userService.createUser(userModificationRequest));

        verifyNoInteractions(userRepository);
    }

    /**
     * Tests that updateUser method updates a user when user data is correct.
     */
    @Test
    void update_user_should_update_user_when_user_data_is_correct() {
        when(userRepository.save(any(User.class))).thenReturn(userFromDb);
        when(userRepository.findById(userFromDb.getId())).thenReturn(Optional.of(userFromDb));

        userService.updateUser(userFromDb.getId(), userModificationRequest);

        verify(userRepository).save(any(User.class));
        verify(userRepository).findById(any(UUID.class));
    }

    /**
     * Tests that updateUser method throws an exception when the user has age restriction.
     */
    @Test
    void update_user_should_throw_exception_when_user_has_age_restriction() {
        userModificationRequest.setBirthDate(LocalDate.of(2009, 10, 10));

        assertThrows(UserAgeRestrictionException.class, () -> userService.updateUser(userFromDb.getId(), userModificationRequest));

        verifyNoInteractions(userRepository);
    }

    /**
     * Tests that updateUser method throws an exception when the user ID does not exist.
     */
    @Test
    void update_user_should_throw_exception_when_user_id_does_not_exist() {
        when(userRepository.findById(userFromDb.getId())).thenReturn(Optional.empty());

        assertThrows(EntityIdNotFoundException.class, () -> userService.updateUser(userFromDb.getId(), userModificationRequest));

        verify(userRepository).findById(userFromDb.getId());
    }

    /**
     * Tests that updateUserEmail method updates a user's email when user data is correct.
     */
    @Test
    void update_user_email_should_update_user_email_when_user_data_is_correct() {
        when(userRepository.save(any(User.class))).thenReturn(userFromDb);
        when(userRepository.findById(userFromDb.getId())).thenReturn(Optional.of(userFromDb));

        userService.updateUserEmail(userFromDb.getId(), "e@e.e");

        verify(userRepository).save(any(User.class));
        verify(userRepository).findById(any(UUID.class));
    }

    /**
     * Tests that updateUserEmail method throws an exception when the user ID does not exist.
     */
    @Test
    void update_user_email_should_throw_exception_when_user_id_does_not_exist() {
        when(userRepository.findById(userFromDb.getId())).thenReturn(Optional.empty());

        assertThrows(EntityIdNotFoundException.class, () -> userService.updateUserEmail(userFromDb.getId(), "e@e.e"));

        verify(userRepository).findById(userFromDb.getId());
    }

    /**
     * Tests that deleteUserById method deletes a user when the user ID exists.
     */
    @Test
    void delete_user_should_delete_user_when_user_id_exist() {
        when(userRepository.findById(userFromDb.getId())).thenReturn(Optional.of(userFromDb));

        userService.deleteUserById(userFromDb.getId());

        verify(userRepository).deleteById(any(UUID.class));
        verify(userRepository).findById(any(UUID.class));
    }

    /**
     * Tests that deleteUserById method throws an exception when the user ID does not exist.
     */
    @Test
    void delete_user_should_throw_exception_when_user_id_does_not_exist() {
        when(userRepository.findById(userFromDb.getId())).thenReturn(Optional.empty());

        assertThrows(EntityIdNotFoundException.class, () -> userService.deleteUserById(userFromDb.getId()));

        verify(userRepository).findById(userFromDb.getId());
    }
}