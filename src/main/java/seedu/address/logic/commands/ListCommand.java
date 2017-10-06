package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniqueAddressPredicate;

import java.util.HashSet;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all persons or all attributes(if specified) and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: ATTRIBUTE NAME\n"
            + "Example: " + COMMAND_WORD + " address";

    public static final String MESSAGE_SUCCESS = "Listed all %1$s";

    public static final String DEFAULT_LISTING_ELEMENT = "Persons";

    private final String attName;

    public ListCommand(String attributeName) { this.attName = attributeName; }

    public ListCommand() { this.attName = DEFAULT_LISTING_ELEMENT; };

    private boolean hasAttribute() {
        return !attName.equals(DEFAULT_LISTING_ELEMENT);
    }

    @Override
    public CommandResult execute() {
        if (hasAttribute()) {
            UniqueAddressPredicate predicate = new UniqueAddressPredicate(getUniqueAdPersonSet());
            model.updateFilteredPersonList(predicate);
            return new CommandResult(String.format(MESSAGE_SUCCESS, attName));
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, attName));
        }
    }

    private HashSet<Address> getUniqueAdPersonSet() {
        HashSet<Address> set = new HashSet<>();

        ObservableList<ReadOnlyPerson> personLst = model.getFilteredPersonList();
        for(ReadOnlyPerson p : personLst) {
            if (!set.contains(p.getAddress())) {
                set.add(p.getAddress());
            }
        }
        return set;
    }
}
