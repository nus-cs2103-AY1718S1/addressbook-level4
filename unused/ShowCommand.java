package seedu.address.logic.commands;

import seedu.address.model.person.TagsContainKeywordPredicate;

//@@author thehelpfulbees
/**
 * Finds and lists all persons in address book whose tags include the argument keyword.
 * Keyword matching is case sensitive.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String COMMAND_ALIAS = "sh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons in a given tag "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends";

    private final TagsContainKeywordPredicate predicate;

    public ShowCommand(TagsContainKeywordPredicate predicate) {
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
                || (other instanceof ShowCommand // instanceof handles nulls
                && this.predicate.equals(((ShowCommand) other).predicate)); // state check
    }
}
