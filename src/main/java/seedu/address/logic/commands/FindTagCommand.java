package seedu.address.logic.commands;

import seedu.address.model.person.TagsContainKeywordsPredicate;
//@@author kosyoz
/**
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified tags (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " friends family";

    private final TagsContainKeywordsPredicate predicate;

    public FindTagCommand(TagsContainKeywordsPredicate predicate) {
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
