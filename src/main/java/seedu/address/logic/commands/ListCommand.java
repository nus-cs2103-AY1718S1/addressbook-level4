

package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

//@@author hengyu95
/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the contacts and if specified, sort by tags "
            + "or by the names in alphabetical order\n"
            + "Parameters: "
            + "[ " + PREFIX_SORT + "<name|tag|email|address> ]\n"
            + "Examples:\n"
            + "1) " + COMMAND_WORD + "\n"
            + "2) " + COMMAND_WORD + " " + PREFIX_SORT + "tag";

    private int sortOrder = 0;

    public ListCommand() {
        new ListCommand(0);
    }

    public ListCommand(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.sortFilteredPersons(sortOrder);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
