package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.FindLessonRequestEvent;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.FindPredicate;
import seedu.address.model.module.predicates.LessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.LocationContainsKeywordsPredicate;
import seedu.address.model.module.predicates.ModuleContainsKeywordsPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/**
 * Finds and lists items in address book which module or location contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all module or location whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " LT25";
    public static final String MESSAGE_SUCCESS = "find command executed";

    private Predicate<ReadOnlyLesson> previousPredicate;
    private Predicate<ReadOnlyLesson> predicate;
    private ReadOnlyLesson currentViewingLesson;
    private final List<String> keywords;
    private List<String> oldKeywordList;
    private List<String> newKeywordList;

    public FindCommand(List<String> currentKeywords) {
        this.keywords = currentKeywords;
        this.newKeywordList = new ArrayList<>();
        this.oldKeywordList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() {

        previousPredicate = ListingUnit.getCurrentPredicate();

        newKeywordList.addAll(keywords);

        if (previousPredicate instanceof LessonContainsKeywordsPredicate) {
            oldKeywordList.addAll(((LessonContainsKeywordsPredicate) previousPredicate).getKeywords());
        } else if (previousPredicate instanceof FindPredicate) {
            newKeywordList.addAll(((FindPredicate) previousPredicate).getKeywords());
        }

        switch (ListingUnit.getCurrentListingUnit()) {
        case LOCATION:
            predicate = new LocationContainsKeywordsPredicate(newKeywordList);
            break;
        case LESSON:
            currentViewingLesson = model.getCurrentViewingLesson();
            predicate = new LessonContainsKeywordsPredicate
                    (keywords, oldKeywordList, currentViewingLesson, model.getCurrentViewingAttribute());
            break;
        default:
            predicate = new ModuleContainsKeywordsPredicate(newKeywordList);
            break;
        }

        model.updateFilteredLessonList(predicate);
        ListingUnit.setCurrentPredicate(predicate);
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
