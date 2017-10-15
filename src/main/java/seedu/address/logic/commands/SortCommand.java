package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import java.util.ArrayList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
  * Sort names of contacts by alphabetical order
  */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted.";
    public static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyPerson> contactList;

    public SortCommand(){
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Boolean isEmpty = model.checkIfListEmpty(contactList);
        if (!isEmpty) {
            model.sortListByName(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}
