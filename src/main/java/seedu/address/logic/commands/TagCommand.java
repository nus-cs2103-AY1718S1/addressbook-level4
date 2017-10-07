package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Lists all persons in the address book to the user.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_SUCCESS = "Listed all persons with tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters and shows list of persons with the specified tag.\n"
            + "Parameters: Existing tag\n"
            + "Example: " + COMMAND_WORD + " friends ";


    private final TagContainsKeywordsPredicate predicate;

    public TagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCommand // instanceof handles nulls
                && this.predicate.equals(((TagCommand) other).predicate)); // state check
    }
}
