package seedu.address.logic.commands;

import seedu.address.email.Email;
import seedu.address.email.exceptions.EmailSendFailedException;
import seedu.address.email.exceptions.NotAnEmailException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.AddressBookStorage;

/**
 * Sends an email with a logged in email
 */
public class EmailSendCommand extends Command {
    public static final String COMMAND_WORD = "email_send";
    public static final String MESSAGE_SUCCESS = "Successfully sent";
    public static final String MESSAGE_FAILED = "Failed to send: ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Send an email to one or more recipients\n"
            + "Requires an logged in email using email_login\n"
            + "Parameters: email_send \"[RECIPIENTS]\" \"[TITLE]\" \"[BODY]\" \n"
            + "Example: email_send \"example@gmail.com;example2@yahoo.com\" \"Test\" \"Test Body\"";

    private String[] recipients;
    private String body;
    private String title;

    public EmailSendCommand(String[] recipients, String title, String body) {
        this.recipients = recipients;
        this.body = body;
        this.title = title;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!emailManager.isLoggedIn()) {
            return new CommandResult("No email logged in");
        }

        try {
            emailManager.sendEmail(recipients, title, body);
        } catch (NotAnEmailException e) {
            return new CommandResult(e.getMessage());
        } catch (EmailSendFailedException e) {
            return new CommandResult(MESSAGE_FAILED + e.getMessage());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Overridden as access to email manager is needed
     */
    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Email emailManager,
                        AddressBookStorage addressBookStorage) {
        this.emailManager = emailManager;
    }
}
