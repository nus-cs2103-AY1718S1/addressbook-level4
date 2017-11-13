package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import seedu.address.model.person.ReadOnlyPerson;

//@@author jacoblipech
/**
 * Sort the list of contacts by their names
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORDVAR_1 = "sort";
    public static final String COMMAND_WORDVAR_2 = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Sort all contacts by alphabetical order according to their name. "
            + "Command is case-insensitive.";

    public static final String MESSAGE_SUCCESS = "All contacts in AddressBook are sorted by name.";
    public static final String MESSAGE_ALREADY_SORTED = "The AddressBook is already sorted.";
    public static final String MESSAGE_EMPTY_LIST = "There is no contact to be sorted in AddressBook.";

    private ArrayList<ReadOnlyPerson> contactList;

    public SortCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean isNotEmpty = model.sortPersonByName(contactList);
        if (isNotEmpty == null) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        } else if (!isNotEmpty) {
            return new CommandResult(MESSAGE_ALREADY_SORTED);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}


