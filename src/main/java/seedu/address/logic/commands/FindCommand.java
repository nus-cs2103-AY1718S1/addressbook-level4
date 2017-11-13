package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

//@@author marvinchin
/**
 * Finds and lists all {@code Person}s in address book who meet the specified criteria.
 */
public abstract class FindCommand extends Command {

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

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(getPredicate());
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }


    /**
     * Returns the predicate used to determine which {@code Person}s should be shown.
     */
    protected abstract Predicate<ReadOnlyPerson> getPredicate();
}
