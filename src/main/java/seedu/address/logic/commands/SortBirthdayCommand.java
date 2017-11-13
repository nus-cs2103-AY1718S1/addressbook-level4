//@@author inGall
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sort names of contacts by alphabetical order
 */
public class SortBirthdayCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sortBirthday";
    public static final String COMMAND_ALIAS = "sb";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted by birthday.";
    public static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyPerson> contactList;

    public SortBirthdayCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean isEmpty = model.checkIfPersonListEmpty(contactList);
        if (!isEmpty) {
            model.sortListByBirthday(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}
