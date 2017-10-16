package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import seedu.address.model.person.ReadOnlyPerson;

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

    public static final String MESSAGE_SUCCESS = "All contacts are sorted by name.";
    public static final String MESSAGE_EMPTY_LIST = "There is no contact to be sorted in AddressBook.";

    private ArrayList<ReadOnlyPerson> contactList;


    public SortCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean isNotEmpty = model.sortPersonByName(contactList);
        if (isNotEmpty == false) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}


