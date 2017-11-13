// @@author tanchc
package seedu.address.logic.commands;

import seedu.address.model.person.ModuleContainsKeywordPredicate;

/**
 * Finds and lists all persons in address book whose modules contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindModuleCommand extends Command {
    public static final String COMMAND_WORD = "findmodule";
    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose modules contain any of "
            + "the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS2101" + "\n"
            + "Example: " + COMMAND_ALIAS + " CS2103T";


    private final ModuleContainsKeywordPredicate predicate;

    public FindModuleCommand(ModuleContainsKeywordPredicate predicate) {
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
                || (other instanceof FindModuleCommand // instanceof handles nulls
                && this.predicate.equals(((FindModuleCommand) other).predicate)); // state check
    }
}
