package seedu.address.logic.commands;

import seedu.address.model.person.TagContainsKeywordsPredicate;

//@@author Jeremy
/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class ListByTagCommand extends Command {

    public static final String COMMAND_WORD = "list tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [AND/OR] [KEYWORD]...\n"
            + "Example: " + COMMAND_WORD + " colleague and family";

    public static final String MESSAGE_SUCCESS = "Listed all persons with specified tags";

    private final TagContainsKeywordsPredicate predicate;

    public ListByTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Returns a success message and updates the filtered person list.
     *
     * @return new CommandResult(MESSAGE_SUCCESS).
     */
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Checks if 'other' is the same object or an instance of this object.
     *
     * @param other Another object type for comparison.
     * @return True if 'other' is the same object or an instance of this object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListByTagCommand // instanceof handles nulls
                && this.predicate.equals(((ListByTagCommand) other).predicate)); // state check
    }
}
