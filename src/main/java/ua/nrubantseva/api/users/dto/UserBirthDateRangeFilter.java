package ua.nrubantseva.api.users.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a filter for retrieving users within a specified birth date range.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserBirthDateRangeFilter {

    /**
     * The start date of the birth date range. It must be in the past.
     */
    @Past
    private LocalDate fromDate;

    /**
     * The end date of the birth date range. It must be in the past.
     */
    @Past
    private LocalDate toDate;

    /**
     * Checks if the date range is valid. The 'fromDate' must be before the 'toDate'.
     *
     * @return True if the date range is valid, false otherwise.
     */
    @AssertTrue(message = "Date range is not valid")
    private boolean isValidDateRange() {
        return fromDate.isBefore(toDate);
    }
}
