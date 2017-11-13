package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author thehelpfulbees
/**
 * Counts number of times a person has been searched for
 * Guarantees: immutable;
 */
public class NumTimesSearched implements Comparable {

    public static final String MESSAGE_NUM_TIMES_SEARCHED_CONSTRAINTS =
            "Initial value of NumTimesSearched should be >= 0";

    private static final int STARTING_VALUE = 0;

    private int value = STARTING_VALUE; //num times searched

    /**
     * Validates given Favourite.
     *
     * @throws IllegalValueException if given favourite string is invalid.
     */
    public NumTimesSearched(int initialValue) throws IllegalValueException {
        if (!isValidValue(initialValue)) {
            throw new IllegalValueException(MESSAGE_NUM_TIMES_SEARCHED_CONSTRAINTS);
        }
        this.value = initialValue;
    }

    public NumTimesSearched() {
        this.value = STARTING_VALUE;
    }

    public void incrementValue() {
        value++;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidValue(int value) {
        return (value >= 0);
    }

    public int getValue() {
        return value;
    }

    public void setValue (int newValue) throws IllegalValueException {
        if (!isValidValue(newValue)) {
            throw new IllegalValueException(MESSAGE_NUM_TIMES_SEARCHED_CONSTRAINTS);
        }
        this.value = newValue;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NumTimesSearched // instanceof handles nulls
                && this.value == ((NumTimesSearched) other).value); // state check
    }

    //@@author justintkj
    @Override
    public int compareTo(Object o) {
        NumTimesSearched comparedSearched = (NumTimesSearched) o;
        return comparedSearched.getValue() - this.value;
    }
}
