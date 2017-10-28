package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sorts the displayed person list.
 */
public abstract class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "sr";

    private static final String MESSAGE_SORTED_BY = "Persons sorted!\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the displayed person list.\n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Options: \n"
            + "\tdefault - Sorts first based on whether contact is a favorite, then by name in alphabetical order.\n"
            + "\t" + SortByNameCommand.COMMAND_OPTION + " - Sorts by name in alphabetical order\n"
            + "Parameters: [OPTION]\n"
            + "Example: \n"
            + COMMAND_WORD + "\n";

    @Override
    public CommandResult execute() {
        Comparator<ReadOnlyPerson> comparator = getComparator();
        model.sortPersons(comparator);
        return new CommandResult(MESSAGE_SORTED_BY);
    }

    /**
     * Gets the comparator used to order the person list
     */
    public abstract Comparator<ReadOnlyPerson> getComparator();
}
