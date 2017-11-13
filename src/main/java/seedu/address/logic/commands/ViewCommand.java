package seedu.address.logic.commands;

import static seedu.address.model.ListingUnit.LESSON;
import static seedu.address.model.ListingUnit.getCurrentListingUnit;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RefreshPanelEvent;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.FixedCodePredicate;
import seedu.address.model.module.predicates.FixedLocationPredicate;

//@@author junming403
/**
 * Views all persons with the selected listing unit from the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views all lessons with the selected listing attribute from the address book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_LOCATION_SUCCESS = "Lesson(s) founded with location %1$s.";
    public static final String MESSAGE_VIEW_MODULE_SUCCESS = "Lesson(s) founded with module code %1$s.";
    public static final String MESSAGE_VIEW_LESSON_FAILURE = "You can only view module or location.";
    public static final String VIEWING_ATTRIBUTE_MODULE = "module";
    public static final String VIEWING_ATTRIBUTE_DEFAULT = "default";
    public static final String VIEWING_ATTRIBUTE_LOCATION = "location";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyLesson toView = lastShownList.get(targetIndex.getZeroBased());

        setViewingPanelAttribute();
        model.setCurrentViewingLesson(toView);

        String resultMessage = updateFilterList(toView);

        EventsCenter.getInstance().post(new RefreshPanelEvent());
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return new CommandResult(resultMessage);
    }

    /***
     * Update the filterList that only returns lesson with the same location or module name
     * base in the current listing unit
     */
    private String updateFilterList(ReadOnlyLesson toView) throws CommandException {

        Predicate predicate;
        String result;

        switch (getCurrentListingUnit()) {

        case LOCATION:
            logger.info("---[View success]Viewing lessons with location " + toView.getLocation());
            predicate = new FixedLocationPredicate(toView.getLocation());
            result = String.format(MESSAGE_VIEW_LOCATION_SUCCESS, toView.getLocation());
            break;

        case MODULE:
            predicate = new FixedCodePredicate(toView.getCode());
            logger.info("---[View success]Viewing lessons with module code " + toView.getCode());
            result = String.format(MESSAGE_VIEW_MODULE_SUCCESS, toView.getCode());
            break;

        default:
            logger.info("---[View success]Viewing failed, invalid listing unit: LESSON");
            throw new CommandException(MESSAGE_VIEW_LESSON_FAILURE);
        }

        ListingUnit.setCurrentPredicate(predicate);
        ListingUnit.setCurrentListingUnit(LESSON);
        model.updateFilteredLessonList(predicate);
        return result;
    }

    public void setViewingPanelAttribute() {
        switch (getCurrentListingUnit()) {
        case MODULE:
            model.setViewingPanelAttribute(VIEWING_ATTRIBUTE_MODULE);
            break;
        case LOCATION:
            model.setViewingPanelAttribute(VIEWING_ATTRIBUTE_LOCATION);
            break;
        default:
            model.setViewingPanelAttribute(VIEWING_ATTRIBUTE_DEFAULT);
            break;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.targetIndex.equals(((ViewCommand) other).targetIndex)); // state check
    }
}
