package seedu.address.logic.commands;

//@author Minghui94
import seedu.address.model.person.EmailContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose email contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindEmailCommand extends Command {

    public static final String COMMAND_WORD = "findemail";
    public static final String COMMAND_HELP = "findemail EMAIL";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " bob@gmail.com ";

    private final EmailContainsKeywordsPredicate emailPredicate;

    public FindEmailCommand(EmailContainsKeywordsPredicate predicate) {
        this.emailPredicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(emailPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEmailCommand // instanceof handles nulls
                && this.emailPredicate.equals(((FindEmailCommand) other).emailPredicate)); // state check
    }
}
