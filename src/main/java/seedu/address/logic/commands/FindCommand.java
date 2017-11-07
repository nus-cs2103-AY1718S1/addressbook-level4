package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Finds and lists all persons in address book who meet the specified criteria.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who meet the specified criteria"
            + "and displays them as a list with index numbers.\n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Options: \n"
            + "\tdefault - Find contacts whose names contain any of the specified keywords (case-sensitive)\n"
            + "\t" + FindByTagsCommand.COMMAND_OPTION
            + " - Find contacts whose tags contain any of the specified keywords (case-sensitive)\n"
            + "Parameters: [OPTION] KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: \n"
            + COMMAND_WORD + " bob alice\n"
            + COMMAND_WORD + " -" + FindByTagsCommand.COMMAND_OPTION + " friends colleagues";

    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
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
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
