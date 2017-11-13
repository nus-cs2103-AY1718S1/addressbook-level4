package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHARE;

import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.InternetConnectionCheck;
import seedu.address.logic.TextToSpeech;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.threads.SendEmail;
import seedu.address.model.person.ReadOnlyPerson;



//@@author hanselblack
/**
 * Emails the list of contact details to the input email address
 */
public class ShareCommand extends Command {

    public static final String COMMAND_WORD = "share";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Emails the person's contact details identified by the index number used in the listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SHARE + "EMAIL ADDRESS";

    public static final String MESSAGE_SUCCESS = "Email Sent!";
    public static final String MESSAGE_EMAIL_NOT_VALID = "Email address is not valid!";
    public static final String MESSAGE_NO_INTERNET = "Not Connected to the Internet";

    public static final String MESSAGE_FAILURE = "Email was not sent!";

    private static SendEmail sendEmail;

    private Index targetIndex;
    private String[] shareEmailArray;

    public ShareCommand(Index targetIndex, String[] shareEmailArray) {
        this.targetIndex = targetIndex;
        this.shareEmailArray = shareEmailArray;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireNonNull(targetIndex);

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (InternetConnectionCheck.isConnectedToInternet()) {
            ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
            String to;

            for (int index = 0; index < shareEmailArray.length; index++) {
                to = shareEmailArray[index];
                if (isNumeric(to)) {
                    try {
                        Index recipientIndex = ParserUtil.parseIndex(to);
                        if (recipientIndex.getZeroBased() >= lastShownList.size()) {
                            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                        }
                        ReadOnlyPerson personRecipient = lastShownList.get(recipientIndex.getZeroBased());
                        to = personRecipient.getEmail().toString();

                    } catch (IllegalValueException ive) {
                        //Text to Speech
                        new TextToSpeech(MESSAGE_FAILURE).speak();
                        return new CommandResult(MESSAGE_FAILURE);
                    }
                }
                if (isValidEmailAddress(to)) {
                    sendEmail = new SendEmail(to, person);
                    sendEmail.start();
                } else {
                    //Text to Speech
                    new TextToSpeech(MESSAGE_EMAIL_NOT_VALID).speak();;
                    return new CommandResult(MESSAGE_EMAIL_NOT_VALID);
                }
            }
            //Text to Speech
            new TextToSpeech(MESSAGE_SUCCESS).speak();;
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_NO_INTERNET);
        }
    }

    /**
     * Returns true if string is numeric number. This method is to identify which are
     * index or email address in the s/ parameter.
     */
    public boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
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
