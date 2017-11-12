//@@author siri99
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FAVOURITE_PERSONS;

/**
 * Displays the Favourite list.
 */

public class FavouriteListCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favlist";
    public static final String COMMAND_WORD_ALIAS = "fl";

    public static final String MESSAGE_SUCCESS = "Listed all favourite contacts";

    @Override
    public CommandResult executeUndoableCommand() {
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredFavouritePersonList(PREDICATE_SHOW_ALL_FAVOURITE_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author siri99
