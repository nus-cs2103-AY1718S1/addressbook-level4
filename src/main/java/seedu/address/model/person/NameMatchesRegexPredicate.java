// @@author donjar

package seedu.address.model.person;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameMatchesRegexPredicate implements Predicate<ReadOnlyPerson> {
    private final Pattern pattern;

    public NameMatchesRegexPredicate(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return pattern.matcher(person.getName().fullName).find();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof NameMatchesRegexPredicate) {
            String thisPattern = pattern.toString();
            String otherPattern = ((NameMatchesRegexPredicate) other).pattern.toString();
            return thisPattern.equals(otherPattern);
        }
        return false;
    }

}
