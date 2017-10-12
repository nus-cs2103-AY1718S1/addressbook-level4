package seedu.address.logic.commands;

import java.util.function.Predicate;


/**
 * Finds and lists all persons in address book who contain any of the specified argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindSpecificCommand extends Command {

    public static final String COMMAND_WORD = "finds";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who contain any of "
            + "the specified keywords (non case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/Dodo Bob \n"
            + "Example: " + COMMAND_WORD + " p/91234567 12345678\n"
            + "Example: " + COMMAND_WORD + " t/[Friends] [Colleagues]";

    private final Predicate predicate;

    public FindSpecificCommand(Predicate predicate) {
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
                || (other instanceof FindSpecificCommand // instanceof handles nulls
                && this.predicate.equals(((FindSpecificCommand) other).predicate)); // state check
    }
}
