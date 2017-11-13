package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.person.NameContainsKeywordsPredicate;

//@@author awarenessxz
/**
 * Finds and lists all persons in address book whose name or tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or tag contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[ " + PREFIX_NAME + "KEYWORD [MORE_KEYWORDS]... ] [ " + PREFIX_TAG + "KEYWORD [MORE_KEYWORDS]... ] "
            + "[ " + PREFIX_SORT + "<name|tag|email|address> ]\n"
            + "Examples:\n"
            + "1) " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie\n"
            + "2) " + COMMAND_WORD + " " + PREFIX_TAG + "tag1 tag2 tag3\n"
            + "3) " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie " + PREFIX_TAG + "tag1 tag2 tag3\n"
            + "4) " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie " + PREFIX_SORT + "name\n"
            + "5) " + COMMAND_WORD + " " + PREFIX_TAG + "tag1 tag2 tag3 " + PREFIX_SORT + "tag";

    private final NameContainsKeywordsPredicate predicate;
    private int sortOrder = 0;

    public FindCommand(NameContainsKeywordsPredicate predicate, int sortOrder) {
        this.predicate = predicate;
        this.sortOrder = sortOrder;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        model.sortFilteredPersons(sortOrder);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
