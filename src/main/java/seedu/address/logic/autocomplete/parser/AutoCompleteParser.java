package seedu.address.logic.autocomplete.parser;

import java.util.List;

//@@author john19950730
/**
 * Represents a parser used for autocomplete, different logics can be used for different occassions
 */
public interface AutoCompleteParser {

    /** Returns a list of possibilities based on the incomplete user input provided. */
    List<String> parseForPossibilities(String stub);

}
