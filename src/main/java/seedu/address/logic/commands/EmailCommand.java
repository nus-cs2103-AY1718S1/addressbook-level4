package seedu.address.logic.commands;

import java.io.IOException;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.OpenEmailClient;

/**
 * The UI component that is responsible for emailing the selected person.
 */
public class EmailCommand extends Command {
    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";
    public static final String MESSAGE_SUCCESS = "Email opened!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private int emailIndex;
    public EmailCommand(String emailIndex) {
        this.emailIndex = Integer.parseInt(emailIndex.trim());
    }
    public void openEmail() {}
    @Override
    public CommandResult execute() throws CommandException, IOException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson personToEmail = lastShownList.get(emailIndex - 1);

        OpenEmailClient emailClient = new OpenEmailClient(personToEmail.getEmail().toString());
        emailClient.sendMail();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
