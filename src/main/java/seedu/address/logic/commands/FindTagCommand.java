package seedu.address.logic.commands;

//@@author wangyiming1019
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;

import seedu.address.model.person.NameContainsTagsPredicate;

/**
 * Finds and lists all persons in address book who has a tag that contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends FindCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_TAG_FULL
            + ": Finds all persons whose tags contain any of "
            + "the specified tags (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG_FULL + " friends colleagues";

    private final NameContainsTagsPredicate predicate;

    public FindTagCommand(NameContainsTagsPredicate predicate) {
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
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}
