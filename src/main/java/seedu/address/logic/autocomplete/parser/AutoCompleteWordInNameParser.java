package seedu.address.logic.autocomplete.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.autocomplete.AutoCompleteUtils;
import seedu.address.model.Model;

/** Represents a parser that specifically parses only words in names based on last word of incomplete user input. */
public class AutoCompleteWordInNameParser implements AutoCompleteParser {

    private final Model model;
    private List<String> allPossibleMatches = new LinkedList<String>();

    public AutoCompleteWordInNameParser(Model model) {
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

    //@@author john19950730
    /**
     * Updates the possible matches list by flattening out the list of all names in the address book
     */
    private void updateAllPossibleMatches() {
        final LinkedList<String> allWordsInNames = new LinkedList<String>();
        allPossibleMatches = new LinkedList<String>();
        for (String name : model.getAllNamesInAddressBook()) {
            allWordsInNames.addAll(getAllWordsInName(name));
        }
        for (String wordInName : allWordsInNames) {
            if (allPossibleMatches.indexOf(wordInName) == -1) {
                allPossibleMatches.add(wordInName);
            }
        }
    }

    /**
     * Returns a list of words that is split by whitespace in the name.
     * @param name Name to be split by whitespace
     * @return List of words in the name
     */
    private List<String> getAllWordsInName(String name) {
        return Arrays.asList(name.split(" "));
    }

}
