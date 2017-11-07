package seedu.address.logic.autocomplete;

import java.util.List;

import seedu.address.logic.autocomplete.parser.AutoCompleteParser;

//@@author john19950730
/**
 * Stores the possible autocomplete options.
 */
public class AutoCompletePossibilities {
    private final String stub;
    private final List<String> possibilities;
    private final AutoCompleteParser parser;

    /** Default constructor */
    public AutoCompletePossibilities(String stub, AutoCompleteParser parser) {
        this.stub = stub;
        this.parser = parser;
        possibilities = parser.parseForPossibilities(stub);
    }

    public List<String> getPossibilities() {
        return possibilities;
    }

    public String getStub() {
        return stub;
    }

}
