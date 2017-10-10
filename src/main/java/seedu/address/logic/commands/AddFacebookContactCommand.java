package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class AddFacebookContactCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addfacebookcontact";
    public static final String COMMAND_ALIAS = "afbc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a facebook friend to the address book.\n"
            + "Alias: " + COMMAND_ALIAS + "\n";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "addfacebookcontact command not implemented yet";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}