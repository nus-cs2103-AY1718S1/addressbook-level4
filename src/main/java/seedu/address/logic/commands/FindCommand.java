package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.FindLessonRequestEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

import java.util.function.Predicate;

/**
 * Finds and lists items in address book which module or location contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Predicate<ReadOnlyLesson> predicate;

    public FindCommand(Predicate<ReadOnlyLesson> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredLessonList(predicate);
        EventsCenter.getInstance().post(new FindLessonRequestEvent());
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredLessonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
