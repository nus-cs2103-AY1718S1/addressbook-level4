package seedu.address.logic.commands;

import static seedu.address.model.ListingUnit.LESSON;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RefreshPanelEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.DuplicateLessonException;

//@@author junming403
/**
 * Bookmark a lesson identified using it's last displayed index from the address book into the favourite list.
 */
public class MarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds the lesson into marked list identified by the index number used in the last lesson listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_BOOKMARK_LESSON_SUCCESS = "Marked Lesson:  %1$s";
    public static final String MESSAGE_WRONG_LISTING_UNIT_FAILURE = "You can only add lesson into marked list";

    private final Index targetIndex;

    public MarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyLesson lessonToCollect = lastShownList.get(targetIndex.getZeroBased());

        if (ListingUnit.getCurrentListingUnit().equals(LESSON)) {
            try {
                model.bookmarkLesson(lessonToCollect);
                EventsCenter.getInstance().post(new RefreshPanelEvent());
            } catch (DuplicateLessonException pnfe) {
                throw new CommandException(pnfe.getMessage());
            }
            return new CommandResult(String.format(MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToCollect));
        } else {
            throw new CommandException(MESSAGE_WRONG_LISTING_UNIT_FAILURE);
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((MarkCommand) other).targetIndex)); // state check
    }
}
