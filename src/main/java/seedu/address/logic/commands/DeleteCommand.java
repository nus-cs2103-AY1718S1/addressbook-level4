package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.Code;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;




/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson(s) by module(if current listing element is module).\n"
            + ": Deletes the lesson(s) by location(if current listing element is location).\n"
            + ": Deletes the lesson(s) identified by the index (if current listing element is lesson).\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";
    public static final String MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS = "Deleted location: %1$s";
    public static final String MESSAGE_DELETE_PERSON_WITH_MODULE_SUCCESS = "Deleted Module: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        switch (ListingUnit.getCurrentListingUnit()) {

        case MODULE:
            Code codeToDelete = lastShownList.get(targetIndex.getZeroBased()).getCode();
            return deleteLessonWithModuleCode(codeToDelete);

        case LOCATION:
            Location locationToDelete = lastShownList.get(targetIndex.getZeroBased()).getLocation();
            return deleteLessonWithLocation(locationToDelete);

        default:
            ReadOnlyLesson lessonToDelete = lastShownList.get(targetIndex.getZeroBased());
            try {
                model.deleteLesson(lessonToDelete);
            } catch (LessonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete));
        }
    }

    /**
     * Delete all lessons with specified location
     */
    private CommandResult deleteLessonWithLocation(Location location) {

        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();

        for (ReadOnlyLesson l : lessonList) {
            if (l.getLocation().equals(location)) {
                lessonList.remove(l);
            }
        }
        model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));


        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS, location));
    }

    /**
     * Delete all lessons with specified Module Code.
     */
    private CommandResult deleteLessonWithModuleCode(Code code) {

        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        for (ReadOnlyLesson l : lessonList) {
            if (l.getCode().equals(code)) {
                lessonList.remove(l);
            }
        }
        model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_WITH_MODULE_SUCCESS, code));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
