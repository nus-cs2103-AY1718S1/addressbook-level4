//@@author conantteo
package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday} matches any of the birthday month given
 */
public class PersonContainsBirthdayPredicate implements Predicate<ReadOnlyPerson> {
    private final String birthdayMonth;

    public PersonContainsBirthdayPredicate(String birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return birthdayMonth.equals(person.getBirthday().getBirthdayMonth());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsBirthdayPredicate// instanceof handles nulls
                && this.birthdayMonth.equals(((PersonContainsBirthdayPredicate) other)
                .birthdayMonth)); // state check
    }

}
