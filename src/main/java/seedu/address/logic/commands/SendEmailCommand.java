//@author srishag
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import seedu.address.commons.GetGmailService;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sends an email with optional subject and body to the person of the specified index
 */
public class SendEmailCommand extends Command {
    public static final String COMMAND_WORD = "send";
    public static final String COMMAND_ALIAS = "sd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sends an email to a person. "
            + "Use '//' for going to the next line "
            + "identified by the index number in the the address book. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_EMAIL_SUBJECT + "EMAIL SUBJECT "
            + PREFIX_EMAIL_BODY + "EMAIL BODY "
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_EMAIL_SUBJECT + "Reminder of meeting "
            + PREFIX_EMAIL_BODY + "Meeting on 1st October 2017, 10 am.//Thanks.";

    public static final String MESSAGE_SUCCESS = "Email sent successfully";
    public static final String EMAIL_SENDER = "me"; //unique userId as recognized by Google

    private final Index targetIndex;
    private final String emailSubject;
    private final String emailBody;
    private Message message = new Message();

    /**
     * @param targetIndex of the person in the filtered person list to send email to
     * @param emailSubject subject of the email to be sent
     * @param emailBody body of the email to be sent
     */
    public SendEmailCommand(Index targetIndex, String emailSubject, String emailBody) {
        this.targetIndex = targetIndex;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
    }

    @Override
    public CommandResult execute() throws CommandException, GoogleAuthException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        String personToSendEmail = lastShownList.get(targetIndex.getZeroBased()).getEmail().toString();

        try {
            MimeMessage emailToBeSent = ModelManager.createEmail(personToSendEmail,
                    EMAIL_SENDER, emailSubject, emailBody);
            Gmail gmailService = new GetGmailService().getGmailService();
            message = ModelManager.sendMessage(gmailService, EMAIL_SENDER, emailToBeSent);
        } catch (MessagingException | IOException E) {
            assert false;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
