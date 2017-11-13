//@@author inGall
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sort names of contacts by alphabetical order
 */
public class SortAgeCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sortAge";
    public static final String COMMAND_ALIAS = "sa";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted by age. (Oldest To Youngest)";
    public static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyPerson> contactList;

    public SortAgeCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean isEmpty = model.checkIfPersonListEmpty(contactList);
        if (!isEmpty) {
            model.sortListByAge(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}
