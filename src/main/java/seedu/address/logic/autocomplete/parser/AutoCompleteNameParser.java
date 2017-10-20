package seedu.address.logic.autocomplete.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.autocomplete.AutoCompleteUtils;
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

        int prefixPosition = AutoCompleteUtils.findPrefixPosition(stub, PREFIX_NAME.toString());
        String staticStub = stub.substring(0, prefixPosition);
        String namePart = stub.substring(prefixPosition, stub.length());

        possibleNames.addAll(allNames.stream()
                .filter(name -> AutoCompleteUtils.startWithSameLetters(namePart, name))
                .map(filteredName -> staticStub + filteredName).collect(Collectors.toList()));
        possibleNames.add(stub);

        return possibleNames;
    }

}
