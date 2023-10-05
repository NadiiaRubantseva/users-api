package ua.nrubantseva.api.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nrubantseva.api.users.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing User entities. Extends JpaRepository for basic CRUD operations.
 * The interface is annotated with @Repository to indicate it as a Spring repository bean.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Retrieves a list of users with birth dates within the specified range.
     *
     * @param fromDate The start date of the birth date range.
     * @param toDate   The end date of the birth date range.
     * @return A list of users whose birth dates fall within the specified range.
     */
    List<User> findByBirthDateGreaterThanEqualAndBirthDateLessThanEqual(LocalDate fromDate, LocalDate toDate);

}
