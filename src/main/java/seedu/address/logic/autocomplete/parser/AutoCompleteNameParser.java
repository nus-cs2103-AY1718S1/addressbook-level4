package seedu.address.logic.autocomplete.parser;

import java.util.LinkedList;
import java.util.List;

import seedu.address.model.Model;

/** Parses the possible names that the user might have been trying to type,
 *  based on the names currently present in the address book. */
public class AutoCompleteNameParser implements AutoCompleteParser {

    private final Model model;

    public AutoCompleteNameParser(Model model) {
        this.model = model;
    }

    @Override
    public List<String> parseForPossibilities(String stub) {
        final LinkedList<String> possibleNames = new LinkedList<String>();
        final List<String> allNames = model.getAllNamesInAddressBook();

        for (String name : allNames) {
            if (AutoCompleteUtils.startWithSameLetters(stub, name)) {
                possibleNames.add(name);
            }
        }

        return possibleNames;
    }

}
