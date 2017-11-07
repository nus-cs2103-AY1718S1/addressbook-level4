package seedu.address.logic.autocomplete.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.autocomplete.AutoCompleteUtils;
import seedu.address.model.Model;

/** Represents a parser that specifically parses only words in names based on last word of incomplete user input. */
public class AutoCompleteWordInNameParser extends AutoCompleteByPrefixModelParser {

    public AutoCompleteWordInNameParser(Model model) {
        super(model);
    }

    @Override
    public List<String> parseForPossibilities(String stub) {
        final LinkedList<String> possibleMatches = new LinkedList<String>();
        setPrefix(PREFIX_NAME);

        String staticSection = AutoCompleteUtils.getStaticSection(stub);
        String autoCompleteSection = AutoCompleteUtils.getAutoCompleteSection(stub);

        possibleMatches.addAll(allPossibleMatches.stream()
                .filter(possibleMatch -> AutoCompleteUtils.startWithSameLetters(autoCompleteSection, possibleMatch))
                .map(filteredMatch -> staticSection + filteredMatch)
                .collect(Collectors.toList()));
        possibleMatches.add(stub);

        return possibleMatches;
    }

    //@@author john19950730
    /**
     * Updates the possible matches list by flattening out the list of all names in the address book
     */
    private void flattenPossibleMatches() {
        final LinkedList<String> allWordsInNames = new LinkedList<String>();
        for (String name : allPossibleMatches) {
            addAllDistinctWordsInName(allWordsInNames, name);
        }
        allPossibleMatches = allWordsInNames;
    }

    /**
     * Checks for duplicate before adding each word in the name provided to the list.
     * @param listOfWordsInNames list containing words in names
     * @param name name to be split and added to the list
     */
    private void addAllDistinctWordsInName(List<String> listOfWordsInNames, String name) {
        for (String wordInName : getAllWordsInName(name)) {
            if (listOfWordsInNames.indexOf(wordInName) == -1) {
                listOfWordsInNames.add(wordInName);
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
