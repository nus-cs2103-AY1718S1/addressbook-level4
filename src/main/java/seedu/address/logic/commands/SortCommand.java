package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.ReadOnlyPerson;

//@@author sofarsophie
/**
 * Sorts and displays the most recent persons listing based on a field identified by the prefix given.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts and lists the Persons in the last person listing based on the single given field. "
            + "Sorts Persons by name by default if no parameter is given.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "] "
            + "[" + PREFIX_PHONE + "] "
            + "[" + PREFIX_EMAIL + "] "
            + "[" + PREFIX_GROUP + "] "
            + "[" + PREFIX_ADDRESS + "]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PHONE;

    public static final String MESSAGE_SORT_SUCCESS = "Sorted list!";

    private Prefix prefixToSortBy;

    public SortCommand() {
    }

    public SortCommand(Prefix prefixToSortBy) {
        this.prefixToSortBy = prefixToSortBy;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        ObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_PERSON_LIST);
        }
        model.sortFilteredPersonList(lastShownList, prefixToSortBy);

        return new CommandResult(MESSAGE_SORT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return true;
    }
}
//@@author
