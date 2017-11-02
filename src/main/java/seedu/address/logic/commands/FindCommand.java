package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";
    public static final int NO_RESULTS = 0;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD
            + " [PERSON 1] "
            + " [PERSON 2]";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /***
     * @author Sri-vatsa
     */
    @Override
    public CommandResult execute() throws CommandException {

        model.updateFilteredPersonList(predicate);
        int searchResultsCount = model.getFilteredPersonList().size();

        if (searchResultsCount != NO_RESULTS) {
            model.recordSearchHistory();
        }
        return new CommandResult(getMessageForPersonListShownSummary(searchResultsCount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
