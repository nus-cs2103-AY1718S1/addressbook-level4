//@@author quangtdn
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} matches any of the phones given.
 */

public class NameContainsPhonePredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> numbers;

    public NameContainsPhonePredicate(List<String> numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return numbers.stream().anyMatch(number
            -> StringUtil.containsWordIgnoreCase(person.getPhone().toString(), number)) || numbers.stream()
            .anyMatch(number -> person.getPhone().toString().contains(number));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsPhonePredicate // instanceof handles nulls
                && this.numbers.containsAll((((NameContainsPhonePredicate) other).numbers))
                && ((NameContainsPhonePredicate) other).numbers.containsAll(this.numbers)); // state check
    }

}
//@@author
