package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

/**
 * Sends an Email to all contacts with the specified tag.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the default browser on the desktop with the Gmail "
            + "compose box open and specific details pre-filled.\n"
            + "Parameters: " + PREFIX_TAG + "OnTAG (must match tag) \n"
            + PREFIX_SUBJECT + "Subject of the email."
            + "Example: " + COMMAND_WORD + " t/friends s/birthday.";

    public static final String MESSAGE_EMAIL_SUCCESS = "Email has been sent!";
    public static final String MESSAGE_NOT_EXISTING_TAGS = "The tag provided is invalid. Please check again.";

    private final String tag;
    private final String subject;
    private String modifiedSubject;

    /**
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

        boolean isExistingTagName = checkIfExistingTagName(tag);

        if (!isExistingTagName) {
            throw new CommandException(String.format(MESSAGE_NOT_EXISTING_TAGS));
        }
        else {
            modifiedSubject = getSubjectForBrowser(subject);
            model.sendMailToContacts(tag, modifiedSubject, model.getFilteredPersonList());
            return new CommandResult(MESSAGE_EMAIL_SUCCESS);
        }


    }
    /**
     * Get subject with '+' appended
     */
    private String getSubjectForBrowser(String subject) {

        String modifiedSubject = "";
        int loopVariable;
        for (loopVariable = 0; loopVariable < subject.length(); loopVariable++) {
            if (subject.charAt(loopVariable) == ' ') {
                modifiedSubject = modifiedSubject + '+';
            } else {
                modifiedSubject = modifiedSubject + subject.charAt(loopVariable);
            }
        }
        return modifiedSubject;
    }

    /**
     * Check whether a given tag exists in address book.
     *
     * @param tagName tag that is to be checked
     */
    private boolean checkIfExistingTagName(String tagName) {

        for (Tag tag : model.getAddressBook().getTagList()) {
            if (tag.tagName.equalsIgnoreCase(tagName)) {
                return true;
            }
        }
        return false;
    }

}
