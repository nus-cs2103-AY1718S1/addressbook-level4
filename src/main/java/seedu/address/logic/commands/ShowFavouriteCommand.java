package seedu.address.logic.commands;

import seedu.address.model.person.IsFavouritePredicate;

/**
 * List all favourite persons
 */
public class ShowFavouriteCommand extends Command{

    public static final String COMMAND_WORD_1 = "showFavourite";
    public static final String COMMAND_WORD_2 = "sf";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1
            + " OR "
            + COMMAND_WORD_2;

    private final IsFavouritePredicate predicate;

    public ShowFavouriteCommand(IsFavouritePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult((getMessageForPersonListShownSummary(model.getFilteredPersonList().size())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowFavouriteCommand // instanceof handles nulls
                && this.predicate.equals(((ShowFavouriteCommand) other).predicate)); // state check
    }
}
