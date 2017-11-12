package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMARKS;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RemarkChangedEvent;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.BookedSlot;
import seedu.address.model.module.Code;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.exceptions.RemarkNotFoundException;

//@@author junming403
/**
 * Deletes a lesson/location/module identified using it's last displayed index from ModU.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "\n: Deletes the lesson(s) by module(if current listing element is module).\n"
            + ": Deletes the lesson(s) by location(if current listing element is location).\n"
            + ": Deletes the lesson(s) identified by the index (if current listing element is lesson).\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";
    public static final String MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS = "Deleted location: %1$s";
    public static final String MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS = "Deleted Module: %1$s";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


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
            return deleteLessonWithModuleCode(targetIndex);

        case LOCATION:
            return deleteLessonWithLocation(targetIndex);

        default:
            return deleteSpecifiedLesson(targetIndex);
        }
    }

    /**
     * Delete all lessons with specified location
     */
    private CommandResult deleteLessonWithLocation(Index targetIndex) {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();
        Location locationToDelete = lastShownList.get(targetIndex.getZeroBased()).getLocation();
        try {
            deleteLessonsWithLocation(locationToDelete);
            logger.info("---[Delete success]Deleted lessons with location:" + locationToDelete);
        } catch (LessonNotFoundException e) {
            assert false : "The target lesson cannot be missing";
        }

        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS, locationToDelete));
    }

    /**
     * Deletes all lessons with the given location.
     */
    private void deleteLessonsWithLocation(Location locationToDelete) throws LessonNotFoundException {
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        for (int i = 0; i < lessonList.size(); i++) {
            ReadOnlyLesson l = lessonList.get(i);
            if (l.getLocation().equals(locationToDelete)) {
                model.unbookBookedSlot(new BookedSlot(l.getLocation(), l.getTimeSlot()));
                model.deleteLesson(l);
                i--;
            }
        }
        model.updateLocationList();
    }

    /**
     * Delete all lessons with specified Module Code and all relevant remarks.
     */
    private CommandResult deleteLessonWithModuleCode(Index targetIndex) {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();
        Code moduleToDelete = lastShownList.get(targetIndex.getZeroBased()).getCode();
        try {

            deleteLessonsWithCode(moduleToDelete);
            deleteRemarkWithCode(moduleToDelete);
            logger.info("---[Delete success]Deleted lessons with module code:" + moduleToDelete);

        } catch (LessonNotFoundException e) {
            assert false : "The target lesson cannot be missing";
        } catch (RemarkNotFoundException e) {
            assert false : "The target remark cannot be missing";
        }

        EventsCenter.getInstance().post(new ViewedLessonEvent());
        EventsCenter.getInstance().post(new RemarkChangedEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, moduleToDelete));
    }

    /**
     * Deletes all lessons with the given module code.
     */
    private void deleteLessonsWithCode(Code moduleToDelete) throws LessonNotFoundException {
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        for (int i = 0; i < lessonList.size(); i++) {
            ReadOnlyLesson lesson = lessonList.get(i);
            if (lesson.getCode().equals(moduleToDelete)) {
                model.unbookBookedSlot(new BookedSlot(lesson.getLocation(), lesson.getTimeSlot()));
                model.deleteLesson(lesson);
                i--;
            }
        }
        model.updateModuleList();
    }

    /**
     * Deletes all remarks with the given module code.
     */
    private void deleteRemarkWithCode(Code moduleToDelete) throws RemarkNotFoundException {
        model.updateFilteredRemarkList(PREDICATE_SHOW_ALL_REMARKS);
        ObservableList<Remark> remarkList = model.getFilteredRemarkList();
        for (int i = 0; i < remarkList.size(); i++) {
            Remark remark = remarkList.get(i);
            if (remark.moduleCode.equals(moduleToDelete)) {
                model.deleteRemark(remark);
                i--;
            }
        }
    }


    /**
     * Delete all lessons with specified Module Code.
     */
    private CommandResult deleteSpecifiedLesson(Index targetIndex) {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();
        ReadOnlyLesson lessonToDelete = lastShownList.get(targetIndex.getZeroBased());
        try {
            model.unbookBookedSlot(new BookedSlot(lessonToDelete.getLocation(), lessonToDelete.getTimeSlot()));
            model.deleteLesson(lessonToDelete);
            logger.info("---[Delete success]Deleted lesson:" + lessonToDelete);
        } catch (LessonNotFoundException pnfe) {
            assert false : "The target lesson cannot be missing";
        }
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
