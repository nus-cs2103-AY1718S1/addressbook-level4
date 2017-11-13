package seedu.address.logic.commands;

import java.util.List;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.LessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.LocationContainsKeywordsPredicate;
import seedu.address.model.module.predicates.MarkedLessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.ModuleContainsKeywordsPredicate;


/**
 * Finds and lists items in address book which module or location contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all module or location whose names contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " LT25";
    public static final String MESSAGE_SUCCESS = "find command executed";

    private List<String> keywords;

    public FindCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    //@@author angtianlannus
    @Override
    public CommandResult execute() {
        Predicate<ReadOnlyLesson> predicate;

        switch (ListingUnit.getCurrentListingUnit()) {
        case LOCATION:
            predicate = new LocationContainsKeywordsPredicate(keywords);
            ListingUnit.setCurrentPredicate(new LocationContainsKeywordsPredicate(keywords));
            break;
        case LESSON:
            if (model.getCurrentViewingAttribute().equals("marked")) {
                predicate = new MarkedLessonContainsKeywordsPredicate(keywords);
                ListingUnit.setCurrentPredicate(new MarkedLessonContainsKeywordsPredicate(keywords));
                EventsCenter.getInstance().post(new ViewedLessonEvent());
                break;
            }
            predicate = new LessonContainsKeywordsPredicate(keywords, model.getCurrentViewingLesson(),
                    model.getCurrentViewingAttribute());
            ListingUnit.setCurrentPredicate(
                    new LessonContainsKeywordsPredicate(keywords, model.getCurrentViewingLesson(),
                    model.getCurrentViewingAttribute()));
            EventsCenter.getInstance().post(new ViewedLessonEvent());
            break;
        default:
            predicate = new ModuleContainsKeywordsPredicate(keywords);
            ListingUnit.setCurrentPredicate(new ModuleContainsKeywordsPredicate(keywords));
            break;
        }
        model.updateFilteredLessonList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.keywords.equals(((FindCommand) other).keywords)); // state check
    }
}
