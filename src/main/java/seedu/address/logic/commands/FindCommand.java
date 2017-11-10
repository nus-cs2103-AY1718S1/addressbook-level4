package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MRT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose fields contains any of the argument keywords.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose fields contain any of "
            + "the specified keywords (tags are case sensitive) and displays them as a list with index numbers.\n"
            + "Type refers to the kind of search: 'AND', 'OR'. \n"
            + "Parameters: TYPE [PREFIX/KEYWORD] [PREFIX/MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " AND "
            + PREFIX_PHONE + "91234567 "
            + "[" + PREFIX_EMAIL + "johndoe@example.com" + "]"
            + "[" + PREFIX_NAME + "alice bob charlie" + "]"
            + "[" + PREFIX_MRT + "Serangoon" + "]"
            + "[" + PREFIX_TAG + "owesMoney" + "]";

    private final PersonContainsKeywordsPredicate predicate;


    public FindCommand(boolean isInclusive, FindPersonDescriptor personDescriptor) {
        this.predicate = new PersonContainsKeywordsPredicate(isInclusive, personDescriptor);
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
