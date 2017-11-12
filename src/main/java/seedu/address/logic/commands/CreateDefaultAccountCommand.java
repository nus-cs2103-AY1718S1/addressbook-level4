package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author derickjw
/**
 * Creates a account with username "admin" and password "admin"
 */
public class CreateDefaultAccountCommand extends Command {
    public static final String COMMAND_WORD = "createDefaultAcc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a default account where username and "
            + "password is admin.\n" + "Example:" + " " + COMMAND_WORD;

    public static final String MESSAGE_CREATE_SUCCESS = "Default account created successfully!";
    public static final String MESSAGE_ACCOUNT_EXISTS = "Account already exists!";

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().checkUsername("") && model.getUserPrefs().checkPassword("")) {
            model.getUserPrefs().setDefaultUsername("admin");
            model.getUserPrefs().setDefaultPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
            return new CommandResult(MESSAGE_CREATE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_ACCOUNT_EXISTS);
        }
    }
}
