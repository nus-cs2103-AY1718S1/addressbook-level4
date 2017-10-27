package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.FindLessonRequestEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.LessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.LocationContainsKeywordsPredicate;
import seedu.address.model.module.predicates.MarkedLessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.ModuleContainsKeywordsPredicate;

import java.util.List;
import java.util.function.Predicate;

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

    private Predicate<ReadOnlyLesson> predicate;
    private List<String> keywords;

    public FindCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {

        switch (ListingUnit.getCurrentListingUnit()) {
        case LOCATION:
            this.predicate = new LocationContainsKeywordsPredicate(keywords);
            ListingUnit.setCurrentPredicate(this.predicate);
            break;
        case LESSON:
            if (model.getCurrentViewingAttribute().equals("marked")) {
                this.predicate = new MarkedLessonContainsKeywordsPredicate(keywords);
                ListingUnit.setCurrentPredicate(this.predicate);
                break;
            }
            this.predicate = new LessonContainsKeywordsPredicate(keywords, model.getCurrentViewingLesson(),
                    model.getCurrentViewingAttribute());
            ListingUnit.setCurrentPredicate(this.predicate);
            break;
        default:
            this.predicate = new ModuleContainsKeywordsPredicate(keywords);
            ListingUnit.setCurrentPredicate(this.predicate);
            break;
        }
        model.updateFilteredLessonList(predicate);
        EventsCenter.getInstance().post(new FindLessonRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.keywords.equals(((FindCommand) other).keywords)); // state check
    }
}
