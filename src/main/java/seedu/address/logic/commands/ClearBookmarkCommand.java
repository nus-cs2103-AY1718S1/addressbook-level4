package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PLACE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PLACES;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.place.exceptions.DuplicatePlaceException;
import seedu.address.model.place.exceptions.PlaceNotFoundException;
import seedu.address.model.tag.Tag;

//@@author Chng-Zhi-Xuan
/**
 * Clears all bookmark tags from all places
 */
public class ClearBookmarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear_bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": clears all bookmarks in address book\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_CLEAR_BOOKMARK_SUCCESS = "Cleared bookmarks";
    private final String bookmarkString = "Bookmarked";

    public ClearBookmarkCommand() {

    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        Tag toRemove = null;

        model.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);


        try {
            toRemove = new Tag (bookmarkString);
            model.removeAllTags(toRemove);
        } catch (DuplicatePlaceException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PLACE);
        } catch (PlaceNotFoundException pnfe) {
            assert false : "The target place cannot be missing";
        } catch (IllegalValueException e) {
            assert false : "Tag cannot be invalid";
        }

        return new CommandResult(String.format(MESSAGE_CLEAR_BOOKMARK_SUCCESS, toRemove.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearBookmarkCommand); // instanceof handles nulls
    }
}

