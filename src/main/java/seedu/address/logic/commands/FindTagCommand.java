package seedu.address.logic.commands;

//@@author Minghui94
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_HELP = "findtag [TAG]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Find all persons whose tags contain any of "
            + "the specific keyword (case-sensitive) and display them as a list with index numbers.\n"
            + "Parameters: [MORE KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends colleague family";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindTagCommand
                && this.predicate.equals(((FindTagCommand) other).predicate));
    }
}
