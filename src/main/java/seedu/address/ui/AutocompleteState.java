package seedu.address.ui;

import seedu.address.logic.parser.Prefix;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Represents the states of the autcompleter
 */
public enum AutocompleteState {
    COMMAND,
    EMPTY,
    MULTIPLE_COMMAND,
    NO_RESULT,
    ADD,
    EDIT,
    FIND;

    private static final Prefix[] addCommandPrefixes = {PREFIX_TRACKING_NUMBER, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS, PREFIX_DELIVERY_DATE, PREFIX_STATUS, PREFIX_TAG};

    private static final Prefix[] noPrefixes = { };

    public static Prefix[] getPrefixes(AutocompleteState state) {
        switch (state) {
            case ADD:
                return addCommandPrefixes;
        }
        return noPrefixes;
    }

}
