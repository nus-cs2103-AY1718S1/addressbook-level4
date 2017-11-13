//@@author qihao27
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons.\n"
            + "Parameters: [OPTION] (must be one of the available field and a String)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_NAME + " (sort by name)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_PHONE + " (sort by phone number)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_EMAIL + " (sort by email address)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_ADDRESS + " (sort by address)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORT_BY_TAG + " (sort by tag)\n";

    public static final String MESSAGE_SUCCESS_BY_NAME = "Persons sorted by name.";
    public static final String MESSAGE_SUCCESS_BY_PHONE = "Persons sorted by phone.";
    public static final String MESSAGE_SUCCESS_BY_EMAIL = "Persons sorted by email.";
    public static final String MESSAGE_SUCCESS_BY_ADDRESS = "Persons sorted by address.";
    public static final String MESSAGE_SUCCESS_BY_TAG = "Persons sorted by tag.";
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
        if (option.contains("-n")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_NAME);
        } else if (option.contains("-p")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_PHONE);
        } else if (option.contains("-e")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_EMAIL);
        } else if (option.contains("-a")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_ADDRESS);
        } else if (option.contains("-t")) {
            return new CommandResult(MESSAGE_SUCCESS_BY_TAG);
        }

        return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.option.equals(((SortCommand) other).option)); // state check
    }
}
