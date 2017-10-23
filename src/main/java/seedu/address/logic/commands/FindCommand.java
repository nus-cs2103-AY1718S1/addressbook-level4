package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose particulars contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_ALIAS + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: PREFIX_PERSON_ATTRIBUTE/KEYWORD [MORE_KEYWORDS]... [MORE_PARAMETERS]...\n"
            + "Examples: " + System.lineSeparator()
            + "1) " + COMMAND_WORD + " " + PREFIX_NAME.toString() + "alice bob charlie" + System.lineSeparator()
            + "2) " + COMMAND_WORD + " " + PREFIX_TAG.toString() + "family friends" + System.lineSeparator()
            + "3) " + COMMAND_WORD + " " + PREFIX_NAME.toString() + "alice bob charlie"
                                   + " " + PREFIX_TAG.toString() + "family friends";

    private final PersonContainsKeywordsPredicate predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
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
