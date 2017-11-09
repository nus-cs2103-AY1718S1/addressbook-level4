package seedu.address.logic.commands;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.SendEmail;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author hanselblack
/**
 * Emails the list of contact details to the input email address
 */
public class ShareCommand extends Command {

    public static final String COMMAND_WORD = "share";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Emails the person's contact details identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Email Sent!";

    public static final String MESSAGE_EMAILNOTVALID= "Email address is not valid!";

    private static final String MESSAGE_FAILURE = "Email was not sent!";

    private static SendEmail sendEmail;

    private Index targetIndex;
    private Index recipientIndexEmail;
    private String recipientEmail;
    private String[] shareEmailArray;

    public ShareCommand(Index targetIndex, String[] shareEmailArray) {
        this.targetIndex = targetIndex;
        this.shareEmailArray = shareEmailArray;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());

        String name = person.getName().fullName;
        String phone = person.getPhone().toString();
        String address = person.getAddress().toString();
        String email = person.getEmail().toString();
        String remark  = person.getRemark().toString();
        String tags = "";
        for (Tag tag :  person.getTags()) {
            tags += tag.tagName + " ";
        }

        String to;
        // Sender's email ID needs to be mentioned
        String from = "unifycs2103@gmail.com";
        // For Gmail host
        String host = "smtp.gmail.com";
        // Get system properties
        Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected  PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "CS2103CS2103");
                    }
                }
        );

        for (int index = 0; index < shareEmailArray.length; index++) {
            to = shareEmailArray[index];
            if (isNumeric(to)) {
                try {
                    Index recepientIndex = ParserUtil.parseIndex(to);
                    if (recepientIndex.getZeroBased() >= lastShownList.size()) {
                        throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                    }
                    ReadOnlyPerson personRecipient = lastShownList.get(recepientIndex.getZeroBased());
                    to = personRecipient.getEmail().toString();

                } catch (IllegalValueException ive) {
                    return new CommandResult(MESSAGE_FAILURE);
                }
            }
            if (isValidEmailAddress(to)) {
                sendEmail = new SendEmail(to, person);
                sendEmail.start();
            }
            else {
                return new CommandResult(MESSAGE_EMAILNOTVALID);
            }

        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShareCommand // instanceof handles nulls
                && this.targetIndex.equals(((ShareCommand) other).targetIndex)); // state check
    }

    /**
     * returns true if string is a valid email address
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
