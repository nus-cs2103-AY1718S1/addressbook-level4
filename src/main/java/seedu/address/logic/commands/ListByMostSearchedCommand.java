package seedu.address.logic.commands;

/***
 * Lists all users in the addressbook based on how frequently they are searched
 * Sorts by search frequency
 * @author Sri-vatsa
 */

public class ListByMostSearchedCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "listMostSearched";
    public static final String COMMAND_ALIAS = "lms";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all persons sorted by frequency of search";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersonListBySearchCount();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
