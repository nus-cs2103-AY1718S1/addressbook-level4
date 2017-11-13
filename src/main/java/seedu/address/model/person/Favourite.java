package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author justintkj
/**
 * Represents a person's Favourite/color on in the addressBook
 * Guarantees: immutable; is valid as declared in {@link #isValidInput(String)}
 */
public class Favourite implements Comparable {

    public static final String MESSAGE_FAVOURITE_CONSTRAINTS =
            "Person favourite should only be true or false, and it should not be blank";
    public static final String COLOR_SWITCH = "true";
    public static final String COLOR_OFF = "false";
    private Boolean isColorOn = false;

    /**
     * Validates given Favourite.
     *
     * @throws IllegalValueException if given favourite string is invalid.
     */
    public Favourite(String input) throws IllegalValueException {
        input = processNoInput(input);
        String trimmedinput = input.trim();
        isValidInput(trimmedinput);
        //Confirms the input is legal, COLOR_SWITCH or COLOR_OFF
        assert input.equals(COLOR_SWITCH) || input.equals(COLOR_OFF);
        updateColor(trimmedinput);
    }

    /**
     * Generates Color to be OFF if not input given
     * @param input input given, true or false
     * @return color to be on or off
     */
    private String processNoInput(String input) {
        if (input == null) {
            input = COLOR_OFF;
        }
        return input;
    }

    /**
     * Changes the isColorOn state if input is true
     * @param trimmedinput input given as true or false
     */
    private void updateColor(String trimmedinput) {
        if (trimmedinput.equals(COLOR_SWITCH) && !isColorOn) {
            this.isColorOn = true;
        } else {
            this.isColorOn = false;
        }
    }

    /**
     * Inverses the current state of Favourite
     */
    public void inverse() {
        isColorOn = false;
    }

    /**
     * Returns true if a given string is a favourite type
     * Throws Exception if invalid input.
     */
    public void isValidInput(String input) throws IllegalValueException {
        if (input.toLowerCase().equals(COLOR_SWITCH) || input.toLowerCase().equals(COLOR_OFF)) {
            return;
        } else {
            throw new IllegalValueException(MESSAGE_FAVOURITE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        if (isColorOn) {
            return COLOR_SWITCH;
        } else {
            return COLOR_OFF;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favourite // instanceof handles nulls
                && this.isColorOn.equals(((Favourite) other).isColorOn)); // state check
    }

    @Override
    public int compareTo(Object o) {
        Favourite comparedFavourite = (Favourite) o;
        return (comparedFavourite.toString()).compareTo(this.toString());
    }
}
