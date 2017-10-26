package seedu.address.logic.commands;

/**
 * Finds and lists persons in address book with possible duplicate entries (by name).
 * Keyword matching is case insensitive.
 */
public class DuplicatesCommand extends Command {

    public static final String COMMAND_WORD = "duplicates";
    public static final String COMMAND_ALIAS = "dups"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are possible duplicates "
            + " and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_ALIAS;

    @Override
    public CommandResult execute() {
        model.updateDuplicatePersonList();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DuplicatesCommand); // instanceof handles nulls
    }
}
