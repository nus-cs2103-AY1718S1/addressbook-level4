package seedu.address.logic.commands;

import seedu.address.model.person.ReadOnlyPerson;

import java.util.function.Predicate;

/**
 * Finds and lists all persons in address book whose name, address, email and phone number
 * contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose name, address,\n"
            + "phone number, email contain any of the specified keywords (case-sensitive) and displays \n"
            + "them as a list with index numbers. If list command with specific attribute was called \n"
            + "previously, only that specific attribute will be taken into consideration of finding"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
