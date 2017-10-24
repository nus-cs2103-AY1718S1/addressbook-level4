package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Lists all persons in the address book to the user.
 */
public class TaskByEndCommand extends Command {

    public static final String COMMAND_WORD = "taskbyend";
    public static final String COMMAND_ALIAS = "byend";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Taskbyend command not implemented yet";


    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
