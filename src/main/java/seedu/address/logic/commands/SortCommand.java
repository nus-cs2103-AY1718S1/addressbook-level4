package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;

import seedu.address.model.person.ReadOnlyPerson;

//@@author JavynThun
/**
 * Sorts all persons in the address book by name to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";
    public static final String MESSAGE_SUCCESS = "List is sorted!";
    public static final String MESSAGE_EMPTY_LIST = "List is empty!";

    private ArrayList<ReadOnlyPerson> personList;

    public SortCommand() {
        personList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean listSize = model.sortPersonList(personList);
        if (listSize == null) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
