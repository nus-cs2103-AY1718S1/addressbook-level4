//@@author hxy0229
package seedu.address.logic.commands;

import seedu.address.model.person.FavoritePredicate;

/**
 * Finds and lists all persons in address book who are favorited.
 */

public class ListFavoriteCommand extends Command {
    public static final String COMMAND_WORD = "listfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the persons who are favorited\n"
            + "Example: " + COMMAND_WORD;

    private final FavoritePredicate predicate;

    public ListFavoriteCommand(FavoritePredicate predicate) {
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
                || (other instanceof ListFavoriteCommand // instanceof handles nulls
                && this.predicate.equals(((ListFavoriteCommand) other).predicate)); // state check
    }

}

//@@author
