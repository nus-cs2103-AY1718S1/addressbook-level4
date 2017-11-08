package seedu.address.autocomplete.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seedu.address.autocomplete.AutoCompleteUtils;
import seedu.address.model.Model;

//@@author john19950730
/** Represents a parser that specifically parses only words in names based on last word of incomplete user input. */
public class AutoCompleteWordInNameParser extends AutoCompleteByPrefixModelParser {

    public AutoCompleteWordInNameParser(Model model) {
        super(model);
    }

    @Override
    public List<String> parseForPossibilities(String stub) {
        final LinkedList<String> possibleMatches = new LinkedList<String>();
        setPrefix(PREFIX_NAME);
        flattenPossibleMatches();

        possibleMatches.addAll(AutoCompleteUtils.generateListOfMatches(allPossibleMatches,
                AutoCompleteUtils.getStaticSection(stub),
                AutoCompleteUtils.getAutoCompleteSection(stub)));
        possibleMatches.add(stub);

        return possibleMatches;
    }

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
