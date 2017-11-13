package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OVERDUE_PERSONS;

import seedu.address.commons.core.ListObserver;

//@@author lawwman
/**
 * Lists all persons in the address book with overdue debt to the user.
 */
public class OverdueListCommand extends Command {

    public static final String COMMAND_WORD = "overduelist";
    public static final String COMMAND_WORD_ALIAS = "ol";

    public static final String MESSAGE_SUCCESS = "Listed all debtors with overdue debt";

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.deselectPerson();
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS);
        String currentList = ListObserver.getCurrentListName();
        return new CommandResult(currentList + MESSAGE_SUCCESS);
    }

}
