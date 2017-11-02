//@@author majunting
package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Test that a {@code ReadOnlyPerson}'s {@code Name} starts with a certain alphabet
 */
public class NameStartsWithAlphabetPredicate implements Predicate<ReadOnlyPerson> {
    private final String alphabet;

    public NameStartsWithAlphabetPredicate(String alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return alphabet.charAt(0) == person.getName().fullName.charAt(0);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NameStartsWithAlphabetPredicate
                && this.alphabet.equals(((NameStartsWithAlphabetPredicate) other).alphabet));
    }
}
