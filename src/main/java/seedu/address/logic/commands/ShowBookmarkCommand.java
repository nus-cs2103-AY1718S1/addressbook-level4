package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.place.TagContainsKeywordsPredicate;

//@@author Chng-Zhi-Xuan
/**
 * Clears all bookmark tags from all places
 */
public class ShowBookmarkCommand extends Command {

    public static final String COMMAND_WORD = "show_bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": show all bookmarks in address book\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SHOW_BOOKMARK_SUCCESS = "Showing all bookmarks";
    private final String[] bookmarkString = {"Bookmarked"};

    public ShowBookmarkCommand() {

    }

    //@@author
    //@@author Chng-Zhi-Xuan-reused
    @Override
    public CommandResult execute() {

        List<String> keyword = Arrays.asList(bookmarkString);

        model.updateFilteredPlaceList(new TagContainsKeywordsPredicate(keyword));

        return new CommandResult(MESSAGE_SHOW_BOOKMARK_SUCCESS);
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowBookmarkCommand); // instanceof handles nulls
    }
}

