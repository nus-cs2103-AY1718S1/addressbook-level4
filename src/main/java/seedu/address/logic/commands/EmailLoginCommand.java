package seedu.address.logic.commands;

import seedu.address.email.exceptions.LoginFailedException;
import seedu.address.email.Email;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.AddressBookStorage;

public class EmailLoginCommand extends Command{
    public static final String COMMAND_WORD = "email_login";
    public static final String MESSAGE_SUCCESS = "Successfully logged in as ";
    public static final String MESSAGE_FAILED = "Log in failed: ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": log in with an email address\n"
                                            + "Parameters: email_login \"[EMAIL]\" \"[PASSWORD]\"\n"
                                            + "Example: email_login \"example@gmail.com\"\" example password\"";
    public static final String MESSAGE_INVALID_EMAIL = "The given email is not valid";

    private String email;
    private String password;

    public EmailLoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            emailManager.login(email, password);
        } catch (LoginFailedException e){
            return new CommandResult(MESSAGE_FAILED + e.getMessage());
        }

        return new CommandResult(MESSAGE_SUCCESS + email);
    }

    /**
     * Overridden as access to email manager is needed
     */
    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Email emailManager, AddressBookStorage addressBookStorage) {
        this.emailManager = emailManager;
    }
}

