package seedu.address.logic.commands;

import seedu.address.model.person.BloodtypeContainsKeywordPredicate;

//@@author Ernest
/**
 * Finds and lists all persons in address book whose blood type matches the keyword.
 * Keyword matching is case insensitive.
 */
public class ListByBloodtypeCommand extends Command {

    public static final String COMMAND_WORD = "bloodtype";
    public static final String COMMAND_ALIAS = "bt"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons of a blood type "
            + " and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + " AB+\n"
            + "Example: " + COMMAND_ALIAS + " O";

    private final BloodtypeContainsKeywordPredicate predicate;

    public ListByBloodtypeCommand(BloodtypeContainsKeywordPredicate predicate) {
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
                || (other instanceof ListByBloodtypeCommand // instanceof handles nulls
                && this.predicate.equals(((ListByBloodtypeCommand) other).predicate)); // state check
    }
}
