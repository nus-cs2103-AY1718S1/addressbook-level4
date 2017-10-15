package seedu.address.logic.autocomplete;

import java.util.LinkedList;
import java.util.List;

import seedu.address.logic.parser.AutoCompleteCommandParser;

/**
 * Stores the possible autocomplete options.
 */
public class AutoCompletePossibilities {
    private final String stub;
    private final List<String> possibilities;
    private final AutoCompleteCommandParser commandParser = new AutoCompleteCommandParser();

    /** Default constructor */
    public AutoCompletePossibilities(String stub) {
        this.stub = stub;
        possibilities = commandParser.parseForCommands(stub);
    }

    public List<String> getPossibilities() {
        return possibilities;
    }

    public String getStub() {
        return stub;
    }

}
