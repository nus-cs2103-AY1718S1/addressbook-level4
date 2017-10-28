package seedu.address.logic.commands;

import seedu.address.model.person.ModContainsKeywordPredicate;

/**
 * Finds and lists all persons in address book whose mods contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindModCommand extends Command {
    public static final String COMMAND_WORD = "findmod";
    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose mods contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS2101" + "\n"
            + "Example: " + COMMAND_ALIAS + " CS2103T";


    private final ModContainsKeywordPredicate predicate;

    public FindModCommand(ModContainsKeywordPredicate predicate) {
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
                || (other instanceof FindModCommand // instanceof handles nulls
                && this.predicate.equals(((FindModCommand) other).predicate)); // state check
    }
}
