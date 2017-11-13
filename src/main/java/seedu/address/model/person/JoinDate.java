package seedu.address.model.person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents when Person was added to addressbook.
 * Guarantees: Every Person will have a Join Date.
 */

//@@author ReneeSeet

public class JoinDate {

    public static final String JOINDATE_VALIDATION_REGEX =
            "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";
    private String joinDate;

    /**
     * Default constructor
     * only used for testing
     */
    public JoinDate(String date) {
        joinDate = date;
    }

    public JoinDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //e.g 16/10/2017
        LocalDate localDate = LocalDate.now();
        joinDate = dtf.format(localDate);
    }

    /**
     * Returns true if a given string is a valid Join Date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(JOINDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return joinDate;
    }

}
