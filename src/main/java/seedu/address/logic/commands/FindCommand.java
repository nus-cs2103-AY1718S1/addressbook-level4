package seedu.address.logic.commands;

<<<<<<< HEAD
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.FindLessonRequestEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;

import java.util.List;
import java.util.function.Predicate;

/**
 * Finds and lists items in address book which module or location contains any of the argument keywords.
 * Keyword matching is case insensitive.
=======
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;



/**
 * Finds and lists all persons in address book whose name, address, email and phone number
 * contains any of the argument keywords.
 * Keyword matching is case sensitive.
>>>>>>> master
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

<<<<<<< HEAD
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all module or location whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
=======
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose name, address,\n"
            + "phone number, email contain any of the specified keywords (case-sensitive) and displays \n"
            + "them as a list with index numbers. If list command with specific attribute was called \n"
            + "previously, only that specific attribute will be taken into consideration of finding"
>>>>>>> master
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " LT25";
    public static final String MESSAGE_SUCCESS = "find command executed";

<<<<<<< HEAD
    private final Predicate<ReadOnlyLesson> predicate;

    public FindCommand(Predicate<ReadOnlyLesson> predicate) {
=======
    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
>>>>>>> master
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
