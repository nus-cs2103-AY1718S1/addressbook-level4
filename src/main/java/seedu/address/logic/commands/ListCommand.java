package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_FAV_PERSONS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    //@@author keithsoc
    public static final String COMMAND_OPTION_FAV = PREFIX_OPTION + FavoriteCommand.COMMAND_WORD;

    public static final String MESSAGE_SUCCESS_LIST_ALL = "Listed all persons";
    public static final String MESSAGE_SUCCESS_LIST_FAV = "Listed all favorite persons";

    private boolean hasOptionFav = false;

    public ListCommand(String args) {
        if (args.trim().equals(COMMAND_OPTION_FAV)) {
            hasOptionFav = true;
        }
    }

    @Override
    public CommandResult execute() {
        if (hasOptionFav) {
            model.updateFilteredPersonList(PREDICATE_SHOW_FAV_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_LIST_FAV);
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_LIST_ALL);
        }
    }
    //@@author
}
