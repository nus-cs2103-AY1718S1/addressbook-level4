package seedu.address.logic.commands;

import static seedu.address.model.ListingUnit.LESSON;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeListingUnitEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.FixedCodePredicate;
import seedu.address.model.module.predicates.FixedLocationPredicate;
import seedu.address.model.module.predicates.ShowSpecifiedLessonPredicate;


/**
 * Views all persons with the selected listing unit from the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views all lessons with the selected listing attribute from the address book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_LESSON_SUCCESS = "lesson founded with %1$s";
    public static final String MESSAGE_VIEW_LOCATION_SUCCESS = "lessons(s) founded with location %1$s";
    public static final String MESSAGE_VIEW_MODULE_SUCCESS = "lessons(s) founded with module code %1$s";
    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();
        ListingUnit currentUnit = ListingUnit.getCurrentListingUnit();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyLesson toView = lastShownList.get(targetIndex.getZeroBased());

        Predicate predicate;
        String resultMessage;

        switch (currentUnit) {

        case LOCATION:
            predicate = new FixedLocationPredicate(toView.getLocation());
            resultMessage = String.format(MESSAGE_VIEW_LOCATION_SUCCESS, toView.getLocation());
            break;

        case MODULE:
            predicate = new FixedCodePredicate(toView.getCode());
            resultMessage = String.format(MESSAGE_VIEW_MODULE_SUCCESS, toView.getCode());
            break;

        default:
            predicate = new ShowSpecifiedLessonPredicate(toView);
            resultMessage = String.format(MESSAGE_VIEW_LESSON_SUCCESS, toView);
        }

        model.updateFilteredLessonList(predicate);
        ListingUnit.setCurrentListingUnit(LESSON);
        EventsCenter.getInstance().post(new ChangeListingUnitEvent());
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.targetIndex.equals(((ViewCommand) other).targetIndex)); // state check
    }
}
