package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_TO;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
//@@author sidhmads
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MESSAGE_EMAIL_APP = "Opened Email Application";
    public static final String MESSAGE_NOT_SENT = "Please enter a valid name/tag with a valid Email ID.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  people in the Address Book.\n"
            + "The 'to' field is compulsory\n"
            + "The 'to' field can take both names and tags and it also supports more than one parameter.\n"
            + "Parameters: "
            + PREFIX_EMAIL_TO + "NAMES or TAGS "
            + PREFIX_EMAIL_SUBJECT + "PHONE "
            + PREFIX_EMAIL_BODY + "EMAIL \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL_TO + "John Doe Friends Family "
            + PREFIX_EMAIL_SUBJECT + " TEST SUBJECT "
            + PREFIX_EMAIL_BODY + " TEST BODY ";

    private final NameContainsKeywordsPredicate predicate;
    private final String subject;
    private final String body;

    public EmailCommand(NameContainsKeywordsPredicate predicate, String subject, String body) {
        this.predicate = predicate;
        this.subject = subject;
        this.body = body;
    }

    /**
     * Compiles the recipients, subject and body together.
     * Opens the desktop mail app and fill in those compiled details.
     */
    private void sendEmail(String emailTo) throws ParseException {
        if (emailTo.equalsIgnoreCase("")) {
            throw new ParseException("Invalid recipient email.");
        }
        Desktop desktop;
        String url = "";
        URI mailTo;
        if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            try {
                url = "mailTo:" + emailTo + "?subject=" + this.subject
                        + "&body=" + this.body;
                mailTo = new URI(url);
                desktop.mail(mailTo);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("desktop doesn't support this feature.");
        }
    }

    @Override
    public CommandResult execute() {
        String emailTo = model.updateEmailRecipient(predicate);
        try {
            sendEmail(emailTo);
        } catch (ParseException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_NOT_SENT);
        } catch (RuntimeException re) {
            return new CommandResult("desktop doesn't support this feature.");
        }
        return new CommandResult(MESSAGE_EMAIL_APP);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.predicate.equals(((EmailCommand) other).predicate)); // state check
    }
}
