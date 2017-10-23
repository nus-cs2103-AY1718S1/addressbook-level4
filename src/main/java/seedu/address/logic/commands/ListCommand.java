package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.model.person.PersonContainsTagsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all persons whose tags "
            + "contain any of the specified keywords\n"
            + "or list all persons with or without tags"
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [TAG]\n"
            + "Example: " + COMMAND_WORD + " friends\n"
            + COMMAND_WORD + " all";;

    private Predicate<ReadOnlyPerson> predicate = PREDICATE_SHOW_ALL_PERSONS;

    public ListCommand(PersonContainsTagsPredicate predicate) {
        this.predicate = predicate;
    }

    public ListCommand() {
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && this.predicate.equals(((ListCommand) other).predicate)); // state check
    }
}
