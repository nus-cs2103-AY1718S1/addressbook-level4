package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.tag.Tag;

//@@author RonakLakhotia
/**
 * Sends an Email to all contacts with the specified tag.
 */
public class EmailCommand extends Command {


    public static final String characterToAppendAfterEachWordInSubjectLine = "+";
    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the default browser on the desktop with the Gmail "
            + "compose box open and specific details pre-filled.\n"
            + "Parameters: " + PREFIX_TAG + "One Tag (must match tag) \n"
            + PREFIX_SUBJECT + "Subject of the email."
            + "Example: " + COMMAND_WORD + " t/friends s/birthday.";

    public static final String MESSAGE_EMAIL_SUCCESS = "Email has been sent!";
    public static final String MESSAGE_NOT_EXISTING_TAGS = "The tag provided is invalid. Please check again.";

    private static final Logger logger = LogsCenter.getLogger(EmailCommand.class);
    private final String tag;
    private final String subject;
    private String modifiedSubject;


    /**
     * Emails a group of person with the same tag description and a given subject body.
     * @param tag     of the persons to whom the email has to be sent
     * @param subject the subject line of th email
     */

    public EmailCommand(String tag, String subject) {
        requireNonNull(tag);
        requireNonNull(subject);

        this.tag = tag;
        this.subject = subject;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            boolean isExistingTagName = checkIfExistingTagName(tag);

            if (!isExistingTagName) {
                logger.warning("Incorrect tags entered");
                throw new CommandException(String.format(MESSAGE_NOT_EXISTING_TAGS));
            }
            else {
                logger.info("Processing subject line and executing");
                modifiedSubject = getSubjectForBrowser(subject);
                model.sendMailToContacts(tag, modifiedSubject, model.getFilteredPersonList());
                return new CommandResult(MESSAGE_EMAIL_SUCCESS);
            }
        } catch (IOException io) {
            logger.severe(StringUtil.getDetails(io));
            throw new AssertionError("Invalid Input");
        } catch (URISyntaxException ur) {
            throw new AssertionError("urisyntax erro");
        } catch (IllegalValueException ie) {
            throw new AssertionError("Illegal values");
        }


    }
    /**
     * Gets subject with the '+' character appended after each word to match the URL requirements.
     */
    public String getSubjectForBrowser(String subject) {

        String modifiedSubject = "";

        for (int loopVariable = 0; loopVariable < subject.length(); loopVariable++) {
            if (subject.charAt(loopVariable) == ' ') {
                modifiedSubject = modifiedSubject + characterToAppendAfterEachWordInSubjectLine;
            } else {
                modifiedSubject = modifiedSubject + subject.charAt(loopVariable);
            }
        }
        return modifiedSubject;
    }

    /**
     * Checks whether a given tag exists in address book.
     * @param tagName tag that is to be checked
     */
    public boolean checkIfExistingTagName(String tagName) {

        for (Tag tag : model.getAddressBook().getTagList()) {
            if (tag.tagName.equalsIgnoreCase(tagName)) {
                return true;
            }
        }
        return false;
    }

}
