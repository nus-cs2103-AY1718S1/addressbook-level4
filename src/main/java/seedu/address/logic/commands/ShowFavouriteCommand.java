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

    //public ShowFavouriteCommand() { }

    @Override
    public CommandResult execute() {
        final IsFavouritePredicate predicate = new IsFavouritePredicate();
        model.updateFilteredPersonList(predicate);
        return new CommandResult((getMessageForPersonListShownSummary(model.getFilteredPersonList().size())));
    }
}
