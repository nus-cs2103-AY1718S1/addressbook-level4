package seedu.address.commons.core.enablingkeyword;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author CT15
/**
 * Contains two possible keywords, either "enable" or "disable".
 * Guarantees: parameter is present and not null, parameter is validated.
 */
public class EnablingKeyword {
    public static final String MESSAGE_ENABLING_KEYWORD_CONSTRAINTS = "Enabling keyword should only be either"
            + "enable or disable";

    private String keyword;

    /**
     * Validates given enabling keyword.
     *
     * @throws IllegalValueException if the given keyword string is invalid.
     */
    public EnablingKeyword(String keyword) throws IllegalValueException {
        requireNonNull(keyword);

        if (!keyword.equals("enable") && !keyword.equals("disable")) {
            throw new IllegalValueException(MESSAGE_ENABLING_KEYWORD_CONSTRAINTS);
        }

        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EnablingKeyword // instanceof handles nulls
                && this.keyword.equals(((EnablingKeyword) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
