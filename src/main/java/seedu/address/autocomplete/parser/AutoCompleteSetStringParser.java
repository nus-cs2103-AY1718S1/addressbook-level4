package seedu.address.autocomplete.parser;

import java.util.LinkedList;
import java.util.List;

import seedu.address.autocomplete.AutoCompleteUtils;

//@@author john19950730
/** Represents autocomplete parser that matches a fixed set of strings, defined in the constructor. */
public class AutoCompleteSetStringParser implements AutoCompleteParser {

    private final List<String> allPossibleMatches;

    public AutoCompleteSetStringParser(List<String> allPossibleMatches) {
        this.allPossibleMatches = allPossibleMatches;
    }

    @Override
    public List<String> parseForPossibilities(String stub) {
        final LinkedList<String> possibleMatches = new LinkedList<String>();

        possibleMatches.addAll(AutoCompleteUtils.generateListOfMatches(allPossibleMatches,
                AutoCompleteUtils.getStaticSection(stub),
                AutoCompleteUtils.getAutoCompleteSection(stub)));
        possibleMatches.add(stub);

        return possibleMatches;
    }
}
