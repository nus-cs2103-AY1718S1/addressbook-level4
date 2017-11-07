package seedu.address.logic.autocomplete.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.autocomplete.AutoCompleteUtils;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

//@@author john19950730
/** Parses the possible names that the user might have been trying to type,
 *  based on the names currently present in the address book. */
public class AutoCompleteByPrefixModelParser implements AutoCompleteParser {

    protected List<String> allPossibleMatches = Collections.emptyList();
    private Prefix currentPrefix;
    private final Model model;

    public AutoCompleteByPrefixModelParser(Model model) {
        this.model = model;
    }

    @Override
    public List<String> parseForPossibilities(String stub) {
        final LinkedList<String> possibleMatches = new LinkedList<String>();
        int prefixPosition = AutoCompleteUtils.findLastPrefixPosition(stub, currentPrefix.toString());
        String staticSection = stub.substring(0, prefixPosition);
        String autoCompleteSection = stub.substring(prefixPosition, stub.length());

        possibleMatches.addAll(generateListOfMatches(staticSection, autoCompleteSection));
        possibleMatches.add(stub);

        return possibleMatches;
    }

    /**
     * Changes which attribute to match in the Model component, based on the prefix specified,
     * and update possible matches accordingly.
     * @param newPrefix new prefix to parse for input
     */
    public void setPrefix(Prefix newPrefix) {
        currentPrefix = newPrefix;
        updateAllPossibleMatches();
    }

    /**
     * Generates list of matches based on static section (not to be considered in matching)
     * and autocomplete section (to be matched with all possible matches)
     * @param staticSection section of the stub to be left untouched
     * @param autoCompleteSection section of the stub to match for autocomplete
     * @return list of possible matches
     */
    protected List<String> generateListOfMatches(String staticSection, String autoCompleteSection) {
        return allPossibleMatches.stream()
                .filter(possibleMatch -> AutoCompleteUtils.startWithSameLetters(autoCompleteSection, possibleMatch))
                .map(filteredMatch -> staticSection + filteredMatch)
                .collect(Collectors.toList());
    }

    /**
     * Updates the possible matches list according to currently set prefix.
     */
    private void updateAllPossibleMatches() {
        if (currentPrefix.equals(PREFIX_NAME)) {
            allPossibleMatches = model.getAllNamesInAddressBook();
        } else if (currentPrefix.equals(PREFIX_PHONE)) {
            allPossibleMatches = model.getAllPhonesInAddressBook();
        } else if (currentPrefix.equals(PREFIX_EMAIL)) {
            allPossibleMatches = model.getAllEmailsInAddressBook();
        } else if (currentPrefix.equals(PREFIX_ADDRESS)) {
            allPossibleMatches = model.getAllAddressesInAddressBook();
        } else if (currentPrefix.equals(PREFIX_TAG)) {
            allPossibleMatches = model.getAllTagsInAddressBook();
        } else if (currentPrefix.equals(PREFIX_REMARK)) {
            allPossibleMatches = model.getAllRemarksInAddressBook();
        } else {
            allPossibleMatches = Collections.emptyList();
        }
    }

}
