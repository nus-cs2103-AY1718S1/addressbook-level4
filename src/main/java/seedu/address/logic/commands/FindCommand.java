package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.FindLessonRequestEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;

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

    private final Predicate<ReadOnlyLesson> predicate;

    public FindCommand(Predicate<ReadOnlyLesson> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new FindLessonRequestEvent(this.predicate));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
