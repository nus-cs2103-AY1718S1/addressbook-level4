package seedu.address.logic.commands;

import java.util.function.Predicate;


/**
 * Finds and lists all places in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_WORD_ALIAS = "fd";
    //@@author huyuanrong
    public static final String COMMAND_WORD_NAME_PREFIX = "n/";
    public static final String COMMAND_WORD_TAG_PREFIX = "t/";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all places whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + COMMAND_WORD_NAME_PREFIX + "alice bob charlie"
            + "Example: " + COMMAND_WORD + COMMAND_WORD_TAG_PREFIX + "[bookmark] [attractions]";
    //@@author
    private final Predicate predicate;

    public FindCommand(Predicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute() {
        model.updateFilteredPlaceList(predicate);

        return new CommandResult(getMessageForPlaceListShownSummary(model.getFilteredPlaceList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
