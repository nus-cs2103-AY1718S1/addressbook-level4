package seedu.address.logic.commands;

import seedu.address.model.person.FindFunctionPredicate;


/**
 * Finds and lists all persons in address book whose name / address / tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    //@@author yilun-zhu
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names / address / tags contain"
            + " any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob family Serangoon";
    //@@author

    private final FindFunctionPredicate predicate;

    public FindCommand(FindFunctionPredicate predicate) {
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
