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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof AutoCompletePossibilities)) {
            return false;
        }

        AutoCompletePossibilities otherObject = (AutoCompletePossibilities)o;
        return stub.equals(otherObject.getStub());
    }
}
