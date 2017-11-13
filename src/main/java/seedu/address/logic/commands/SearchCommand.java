//@@author zenghou
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.DetailsContainKeyphrasePredicate;

/**
 * Searches and lists all persons in address book whose details (e.g. Name, Phone, Email, etc.)
 * contain the keyphrase.
 * Keyphrase matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_ALIAS = "sh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all persons whose details (i.e. name, "
            + "phone, email, address, remark or tags) exactly matches the "
            + "the specified keyword/phrase (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final DetailsContainKeyphrasePredicate predicate;

    public SearchCommand(DetailsContainKeyphrasePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
