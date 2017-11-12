package seedu.address.model.person;
//@@author Henning
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a persons score in the address book.
 * Guarantees: immutable; is valid.
 */
public class Score {

    public static final String MESSAGE_SCORE_CONSTRAINTS =
            "The score should be a number between 0 and 9, with 9 being the best score and 0 the worst.";

    public static final String SCORE_VALIDATION_REGEX = "\\d";
    public final String value;

    public Score(String score) throws IllegalValueException {
        requireNonNull(score);
        if (score.equals("")) {
            this.value = score;
        } else {
            String filteredScore = score.replaceAll("[^\\d]", "");
            if (!isValidScore(filteredScore)) {
                throw new IllegalValueException(MESSAGE_SCORE_CONSTRAINTS);
            }
            this.value = "Group score: " + filteredScore;
        }
    }

    /**
     *Returns true if given string is a valid score.
     */
    public static boolean isValidScore(String value) {
        String copy = value.replaceAll("[^\\d]", ""); //For testing purposes
        return copy.matches(SCORE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }


    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Score && this.value.equals(((Score) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
//@@author
