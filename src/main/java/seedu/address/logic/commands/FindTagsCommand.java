package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.FilteredListChangedEvent;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

//@@author lincredibleJC
/**
 * Finds and lists all persons in address book whose tags contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagsCommand extends Command {

    public static final String COMMAND_WORD = "findtags";
    public static final String COMMAND_ALIAS = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends owesMoney";

    private final TagsContainsKeywordsPredicate predicate;

    public FindTagsCommand(TagsContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new FilteredListChangedEvent(model.getFilteredPersonList()));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagsCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagsCommand) other).predicate)); // state check
    }

}
