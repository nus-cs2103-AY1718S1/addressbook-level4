package seedu.address.logic.commands;

import seedu.address.commons.exceptions.NotAnEmailException;
import seedu.address.email.Email;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class EmailLoginCommand extends Command{
    public static final String COMMAND_WORD = "email_login";

    public static final String MESSAGE_SUCCESS = "Successfully logged in as ";

    private String email;
    private String password;
    private Email emailManager;

    public EmailLoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            emailManager.login(email, password);
        } catch (NoSuchProviderException e) {

        } catch (NotAnEmailException e) {

        }

        return new CommandResult(MESSAGE_SUCCESS + email);
    }

    /**
     * Overridden as access to email manager is needed
     */
    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Email emailManager) {
        this.emailManager = emailManager;
    }
}

