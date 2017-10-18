package seedu.address.logic.commands;

import static seedu.address.logic.parser.optionparser.CommandOptionUtil.PREFIX_OPTION_INDICATOR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.NoPersonFoundException;

/**
 * Format full help instructions for every command for display.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String PREFIX_SORT_BY_NAME = PREFIX_OPTION_INDICATOR + "n";
    public static final String PREFIX_SORT_BY_PHONE = PREFIX_OPTION_INDICATOR + "p";
    public static final String PREFIX_SORT_BY_EMAIL = PREFIX_OPTION_INDICATOR + "e";
    public static final String PREFIX_SORT_BY_ADDRESS = PREFIX_OPTION_INDICATOR + "a";
    public static final String PREFIX_SORT_BY_TAG = PREFIX_OPTION_INDICATOR + "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons."
            + "Parameters: [OPTION] (must be one of the available field and a String)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_NAME;

    public static final String MESSAGE_SUCCESS = "Persons sorted.";
    public static final String NO_PERSON_FOUND = "Empty list.";

    private final String option;

    public SortCommand(String option) {
        this.option = option;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.sortPerson(option);
        } catch (NoPersonFoundException npf) {
            throw new CommandException(NO_PERSON_FOUND);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.option.equals(((SortCommand) other).option)); // state check
    }
}
