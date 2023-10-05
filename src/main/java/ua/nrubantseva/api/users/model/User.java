package ua.nrubantseva.api.users.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity class representing a user with attributes such as email, name, birth date, address, and phone.
 * The class is annotated with @Entity to indicate it as a JPA entity and is mapped to a database table.
 * The Lombok annotations @Data, @NoArgsConstructor, and @AllArgsConstructor provide
 * automatic generation of getter, setter, equals, hashCode, and toString methods.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Email address of the user. Cannot be null.
     */
    @Column(nullable = false)
    private String email;

    /**
     * First name of the user. Cannot be null.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Last name of the user. Cannot be null.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Birth date of the user. Cannot be null.
     */
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    /**
     * Address of the user. Can be null.
     */
    @Column
    private String address;

    /**
     * Phone number of the user. Can be null.
     */
    @Column
    private String phone;
}
