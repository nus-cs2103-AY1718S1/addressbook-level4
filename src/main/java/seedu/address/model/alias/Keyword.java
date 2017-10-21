package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the alias keyword in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidKeyword(String)}
 */
public class Keyword {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Alias keywords should only contain one word with at least 2 letters and it should not be blank";

    public static final String KEYWORD_INVALIDATION_REGEX = ".*\\s+.*";

    public final String keyword;

    /**
     * Validates given keyword.
     *
     * @throws IllegalValueException if given keyword string is invalid.
     */
    public Keyword(String keyword) throws IllegalValueException {
        requireNonNull(keyword);
        if (!isValidKeyword(keyword)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.keyword = keyword.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidKeyword(String test) {
        String trimmedKeyword = test.trim();
        return ((!test.matches(KEYWORD_INVALIDATION_REGEX)) && (trimmedKeyword.length() > 1));
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Keyword // instanceof handles nulls
                && this.keyword.equals(((Keyword) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
