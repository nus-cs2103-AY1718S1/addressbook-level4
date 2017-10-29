package seedu.address.logic.autocomplete.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.autocomplete.AutoCompleteUtils;
import seedu.address.model.Model;

/** Represents a parser that specifically parses only tags based on last word of incomplete user input. */
public class AutoCompleteTagParser implements AutoCompleteParser {

    private final Model model;
    private List<String> allPossibleMatches = Collections.emptyList();

    public AutoCompleteTagParser(Model model) {
        this.model = model;
    }

    @Override
    public List<String> parseForPossibilities(String stub) {
        final LinkedList<String> possibleMatches = new LinkedList<String>();
        updateAllPossibleMatches();

        String[] splitStub = stub.split(" ");
        String staticSection = getStaticSection(splitStub);
        String autoCompleteSection = getAutoCompleteSection(splitStub);

        possibleMatches.addAll(allPossibleMatches.stream()
                .filter(possibleMatch -> AutoCompleteUtils.startWithSameLetters(autoCompleteSection, possibleMatch))
                .map(filteredMatch -> staticSection + filteredMatch)
                .collect(Collectors.toList()));
        possibleMatches.add(stub);

        return possibleMatches;
    }

    /**
     * * Returns the section of stub that is not to be modified by autocomplete.
     * @param splitStub Stub that has already been split by whitespace
     * @return Section of the stub that will not be modified
     */
    private String getStaticSection(String[] splitStub) {
        String staticSection = "";
        for (int index = 0; index < splitStub.length - 1; ++index) {
            staticSection = staticSection + splitStub[index] + " ";
        }
        return staticSection;
    }

    /**
     * Returns the section of stub that is to be completed by autocomplete
     * @param splitStub Stub that has been split by whitespace
     * @return Section of the stub that will be modified
     */
    private String getAutoCompleteSection(String[] splitStub) {
        return splitStub[splitStub.length - 1];
    }

    /**
     * Updates the possible matches list by obtaining list of all tags in the address book
     */
    private void updateAllPossibleMatches() {
        allPossibleMatches = model.getAllTagsInAddressBook();
    }

}
