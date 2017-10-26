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

    private static final Prefix[] addCommandPrefixes = {PREFIX_TRACKING_NUMBER, PREFIX_NAME, PREFIX_ADDRESS,
            PREFIX_DELIVERY_DATE};

    private static final Prefix[] noPrefixes = { };

    public static Prefix[] getNeededPrefixes(AutocompleteState state) {
        switch (state) {
            case ADD:
                return addCommandPrefixes;
        }
        return noPrefixes;
    }

}
