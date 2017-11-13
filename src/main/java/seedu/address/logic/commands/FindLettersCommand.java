//@@author PhuaJunJie
package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsAlphabetsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the characters.\
 * This function works without having the user to hit the "Enter" key
 * Keyword matching is case insensitive.
 */
public class FindLettersCommand extends Command {

    public static final String COMMAND_WORD = "Sfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the sequential alphabets\n"
            + "Parameters: Sequence of characters [MORE_KEYWORDS]...\n";

    private final NameContainsAlphabetsPredicate predicate;

    public FindLettersCommand(NameContainsAlphabetsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForAlphabetListSummary(model.getFilteredPersonList().size()));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindLettersCommand // instanceof handles nulls
                && this.predicate.equals(((FindLettersCommand) other).predicate)); // state check
    }
}
