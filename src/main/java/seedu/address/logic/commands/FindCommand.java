package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Finds and lists all persons in address book whose fields contain any of the argument keywords.
 * Any number of arguments for each field is supported.
 * Keyword matching is non-case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    //@@author sofarsophie
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose name, phone, email, "
            + "address, tag or group contain at least one of the keywords specified by parameter "
            + "and displays them as a list with index numbers. "
            + "Each parameter can be specified any number of times.\n"
            + "Parameters: [n/NAME]... [p/PHONE]... [e/EMAIL]... [a/ADDRESS]... [g/GROUP]... "
            + "[c/CUSTOMFIELD]... [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " n/alice p/910 a/bukit e/pp@pp.com c/meeting";

    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
    //@@author

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
