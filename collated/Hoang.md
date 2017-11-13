# Hoang
###### \main\java\seedu\address\email\Email.java
``` java
package seedu.address.email;

import seedu.address.email.exceptions.EmailSendFailedException;
import seedu.address.email.exceptions.LoginFailedException;
import seedu.address.email.exceptions.NotAnEmailException;

/**
 * API of Email component
 */

public interface Email {
    /**
     * Returns a log-in session object that can be used to send and receive email
     *
     * @param email    The email address that needs to be logged in
     * @param password Password
     * @throws LoginFailedException if login fails
     */
    void login(String email, String password) throws LoginFailedException;

    /**
     * Checks emails from the logged in email
     *
     * @return A String array in which each element is an email
     */
    String[] checkEmails();

    /**
     * @param recipients Recipients' emails
     * @param title      Title of the email
     * @param message    Message to be included in email
     * @throws NotAnEmailException      if the given emails is/are not valid
     * @throws EmailSendFailedException if the emails were failed to send
     */
    void sendEmail(String[] recipients, String title, String message) throws NotAnEmailException,
            EmailSendFailedException;

    /**
     * Return the current logged in email
     *
     * @return Email address
     */
    String getEmail();

    /**
     * Check if there is an email logged in or not
     *
     * @return whether there is an email logged in
     */
    boolean isLoggedIn();

    /**
     * Log out from any currently logged in email
     */
    void logout();
}
```
###### \main\java\seedu\address\email\Email.java
``` java

```
###### \main\java\seedu\address\email\EmailManager.java
``` java
package seedu.address.email;

import java.util.Hashtable;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import seedu.address.email.exceptions.EmailSendFailedException;
import seedu.address.email.exceptions.LoginFailedException;
import seedu.address.email.exceptions.NotAnEmailException;

/**
 * The main EmailManager of the application
 */
public class EmailManager implements Email {
    public static final String MESSAGE_LOGIN_FAILED = "It could be one of the following reasons: \n"
            + "1. Your Internet connection is not working\n"
            + "2. Your email and password combination is not correct\n"
            + "3. Allow less secure apps is not enable in your email";
    private String currentEmail;
    private String currentEmailProvider;
    private Authenticator currentAuthenticator;
    private Properties propertiesSmtp;
    private Properties propertiesImap;
    private Hashtable<String, String> hostHashTable;

    public EmailManager() {
        currentEmail = null;
        currentEmailProvider = null;
        currentAuthenticator = null;
        propertiesSmtp = new Properties();
        propertiesImap = System.getProperties();

        initHostHashTable();
    }


    @Override
    public void login(String email, String password) throws LoginFailedException {
        init(email);

        Authenticator newAuthenticator =
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };

        Session session = Session.getInstance(propertiesSmtp, newAuthenticator);

        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(hostHashTable.get(currentEmailProvider), email, password);
            transport.close();
            this.currentEmail = email;
            this.currentAuthenticator = newAuthenticator;
        } catch (NoSuchProviderException e) {
            throw new LoginFailedException("No such email provider");
        } catch (MessagingException e) {
            throw new LoginFailedException(MESSAGE_LOGIN_FAILED);
        }
    }

    @Override
    public String[] checkEmails() {
        return new String[0];
    }

    @Override
    public void sendEmail(String[] recipients, String subject, String body)
            throws NotAnEmailException, EmailSendFailedException {
        //Parse email string into internet addresses
        InternetAddress[] recipientsAddresses = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            try {
                recipientsAddresses[i] = new InternetAddress(recipients[i]);
            } catch (AddressException e) {
                throw new NotAnEmailException();
            }
        }

        //Create a new MIME message
        try {
            Session session = Session.getInstance(propertiesSmtp, this.currentAuthenticator);

            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(currentEmail));
            emailMessage.setRecipients(Message.RecipientType.TO, recipientsAddresses);
            emailMessage.setSubject(subject);
            emailMessage.setText(body);

            Transport.send(emailMessage);

        } catch (MessagingException e) {
            throw new EmailSendFailedException("It could be one of the following reasons: \n"
                    + "1. Your Internet connection is not working\n"
                    + "2. One or more of the recipients' emails does not exist");
        }

    }

    @Override
    public String getEmail() {
        return currentEmail;
    }

    @Override
    public boolean isLoggedIn() {
        return (currentAuthenticator != null);
    }

    @Override
    public void logout() {
        currentEmail = null;
        currentEmailProvider = null;
        currentAuthenticator = null;
        propertiesSmtp = new Properties();
        propertiesImap = System.getProperties();
    }


    /**
     * Initiate the host address hash table based on the given email
     *
     * @param email given email string
     * @throws LoginFailedException
     */
    private void init(String email) throws LoginFailedException {
        String domain = (((email.split("@"))[1]).split("\\."))[0];

        switch (domain) {
        case "gmail":
            this.currentEmailProvider = "gmail";
            this.propertiesImap.setProperty("mail.store.protocol", "imaps");
            this.propertiesSmtp.put("mail.smtp.auth", "true");
            this.propertiesSmtp.put("mail.smtp.starttls.enable", "true");
            this.propertiesSmtp.put("mail.smtp.host", "smtp.gmail.com");
            this.propertiesSmtp.put("mail.smtp.port", "587");
            break;
        case "yahoo":
            this.currentEmailProvider = "yahoo";
            this.propertiesImap.setProperty("mail.store.protocol", "imaps");
            this.propertiesSmtp.put("mail.smtp.auth", "true");
            this.propertiesSmtp.put("mail.smtp.starttls.enable", "true");
            this.propertiesSmtp.put("mail.smtp.host", "smtp.yahoo.com");
            this.propertiesSmtp.put("mail.smtp.port", "465");
            break;
        default:
            throw new LoginFailedException("The email domain is not supported");
        }
    }

    private void initHostHashTable() {
        this.hostHashTable = new Hashtable<String, String>();
        hostHashTable.put("gmail", "smtp.gmail.com");
        hostHashTable.put("yahoo", "smtp.mail.yahoo.com");
    }
}
```
###### \main\java\seedu\address\email\EmailManager.java
``` java

```
###### \main\java\seedu\address\email\exceptions\EmailSendFailedException.java
``` java
package seedu.address.email.exceptions;

/**
 * Represents an error when an email fails to send
 */
public class EmailSendFailedException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public EmailSendFailedException(String message) {
        super(message);
    }
}
```
###### \main\java\seedu\address\email\exceptions\EmailSendFailedException.java
``` java

```
###### \main\java\seedu\address\email\exceptions\LoginFailedException.java
``` java
package seedu.address.email.exceptions;

/**
 * Represents an error when an login fails
 */
public class LoginFailedException extends Exception {

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public LoginFailedException(String message) {
        super(message);
    }
}
```
###### \main\java\seedu\address\email\exceptions\LoginFailedException.java
``` java

```
###### \main\java\seedu\address\email\exceptions\NotAnEmailException.java
``` java
package seedu.address.email.exceptions;

/**
 * Exception when the given string is not a valid email
 */
public class NotAnEmailException extends Exception {
    public NotAnEmailException() {
        super("One or more of the given emails is not valid");
    }
}
```
###### \main\java\seedu\address\email\exceptions\NotAnEmailException.java
``` java

```
###### \main\java\seedu\address\logic\commands\EmailLoginCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.email.Email;
import seedu.address.email.exceptions.LoginFailedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Login with an email address
 */
public class EmailLoginCommand extends Command {
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
        } catch (LoginFailedException e) {
            return new CommandResult(MESSAGE_FAILED + e.getMessage());
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
```
###### \main\java\seedu\address\logic\commands\EmailLoginCommand.java
``` java

```
###### \main\java\seedu\address\logic\commands\EmailLogoutCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.email.Email;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Log out any email currently logged in
 */
public class EmailLogoutCommand extends Command {
    public static final String COMMAND_WORD = "email_logout";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Log out from any logged in email\n"
            + "Parameter: email_logout";
    public static final String MESSAGE_SUCCESS = "Logged out";

    public EmailLogoutCommand() {

    }

    @Override
    public CommandResult execute() throws CommandException {
        emailManager.logout();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Overridden as access to email manager is needed
     */
    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Email emailManager) {
        this.emailManager = emailManager;
    }
}
```
###### \main\java\seedu\address\logic\commands\EmailLogoutCommand.java
``` java

```
###### \main\java\seedu\address\logic\commands\EmailSendCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.email.Email;
import seedu.address.email.exceptions.EmailSendFailedException;
import seedu.address.email.exceptions.NotAnEmailException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Email emailManager) {
        this.emailManager = emailManager;
    }
}
```
###### \main\java\seedu\address\logic\commands\EmailSendCommand.java
``` java

```
###### \main\java\seedu\address\logic\commands\ExportCommand.java
``` java
package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * exports the current address book to a .txt file
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": export the address book to a chosen file type\n"
            + "Parameters: export .txt [PATH]\n"
            + "Example: export .txt C:/user/user/desktop";
    public static final String MESSAGE_SUCCESS = "Successfully exported";
    public static final String MESSAGE_FAILED = "Failed to export";
    public static final String MESSAGE_PATH_NOT_FOUND = "The specified path is not found";
    public static final String MESSAGE_ACCESS_DENIED = "Access denied";
    public static final String MESSAGE_ERROR_WRITING_FILE = "Error writing file";
    public static final String MESSAGE_ERROR_READING_FILE = "Error reading file";
    public static final String MESSAGE_FILE_TYPE_NOT_SUPPORTED = "File type not supported";

    private String exportPath;
    private String exportType;
    private ObservableList<ReadOnlyPerson> personList;
    private Object[] personListArray;

    public ExportCommand(String exportType, String exportPath) {
        this.exportType = exportType;
        this.exportPath = exportPath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        personList = model.getAddressBook().getPersonList();
        personListArray = personList.toArray();

        Charset charset = Charset.forName("US-ASCII");

        File file = new File(exportPath);
        try {
            file.mkdirs();
        } catch (SecurityException e) {
            throw new CommandException(MESSAGE_ACCESS_DENIED);
        }

        file = new File(exportPath + "/addressbook.txt");

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ACCESS_DENIED);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
            for (int i = 0; i < personListArray.length; i++) {
                ReadOnlyPerson currentPerson = (ReadOnlyPerson) personListArray[i];
                writer.write(currentPerson.getAsText(), 0, currentPerson.getAsText().length());
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_READING_FILE);
        }


        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \main\java\seedu\address\logic\commands\ExportCommand.java
``` java

```
###### \main\java\seedu\address\logic\commands\FindContainCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsLettersPredicate;

/**
 * Find a person whose name / phone / email / address / tag contains given string
 */
public class FindContainCommand extends Command {

    public static final String COMMAND_WORD = "find_contain";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons whose name / phone / address / email contains the given string\n"
            + "Parameters: " + COMMAND_WORD + " PREFIX_PERSON_ATTRIBUTE/STRING [MORE_PARAMETERS]..."
            + "Examples: \n"
            + "1) find_contain n/david li p/91\n"
            + "2) find_contain e/gmail a/jurong";

    private final PersonContainsLettersPredicate predicate;

    public FindContainCommand(PersonContainsLettersPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindContainCommand // instanceof handles nulls
                && this.predicate.equals(((FindContainCommand) other).predicate)); // state check
    }
}
```
###### \main\java\seedu\address\logic\commands\FindContainCommand.java
``` java

```
###### \main\java\seedu\address\logic\commands\GetEmailCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.email.Email;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Get the currently logged in email
 */
public class GetEmailCommand extends Command {
    public static final String COMMAND_WORD = "email_address";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Get the current logged in email\n"
            + "Parameter: email_address";
    public static final String MESSAGE_NOT_LOGGED_IN = "Not logged in";

    public GetEmailCommand() {

    }

    @Override
    public CommandResult execute() throws CommandException {
        String email = emailManager.getEmail();
        if (email == null) {
            return new CommandResult(MESSAGE_NOT_LOGGED_IN);
        } else {
            return new CommandResult(email);
        }
    }

    /**
     * * Overridden as access to email manager is needed
     */
    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Email emailManager) {
        this.emailManager = emailManager;
    }
}
```
###### \main\java\seedu\address\logic\commands\GetEmailCommand.java
``` java

```
###### \main\java\seedu\address\logic\parser\EmailLoginParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import seedu.address.logic.commands.EmailLoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EmailLoginCommand object
 */
public class EmailLoginParser implements Parser<EmailLoginCommand> {


    @Override
    public EmailLoginCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String[] arguments = userInput.split("\"");

        if (arguments.length != 4) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailLoginCommand.MESSAGE_USAGE));
        }

        String email = arguments[1];
        String password = arguments[3];

        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException e) {
            throw new ParseException(EmailLoginCommand.MESSAGE_INVALID_EMAIL);
        }

        return new EmailLoginCommand(email, password);
    }
}
```
###### \main\java\seedu\address\logic\parser\EmailLoginParser.java
``` java

```
###### \main\java\seedu\address\logic\parser\EmailSendParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.EmailSendCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EmailSendParser object
 */
public class EmailSendParser implements Parser<EmailSendCommand> {

    @Override
    public EmailSendCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String[] arguments = userInput.split("\"");

        if (arguments.length != 6) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailSendCommand.MESSAGE_USAGE));
        } else {
            String[] email = arguments[1].split(";");
            return new EmailSendCommand(email, arguments[3], arguments[5]);
        }
    }
}
```
###### \main\java\seedu\address\logic\parser\EmailSendParser.java
``` java

```
###### \main\java\seedu\address\logic\parser\ExportCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public ExportCommand parse(String args) throws ParseException {
        String[] arguments = args.split(" ");

        if (arguments.length < 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        switch (arguments[1]) {
        case ".txt":
            //need to check whether windows or posix
            //for now just do windows

            char[] tmp = args.toCharArray();
            if (tmp[tmp.length - 1] == '/') {
                args = new String(tmp, 6, args.length() - 7);
            } else {
                args = new String(tmp, 6, args.length() - 6);
            }

            Path exportPath = Paths.get(args);
            return new ExportCommand(".txt", args);

        default:
            throw new ParseException(ExportCommand.MESSAGE_FILE_TYPE_NOT_SUPPORTED);
        }
    }
}
```
###### \main\java\seedu\address\logic\parser\ExportCommandParser.java
``` java

```
###### \main\java\seedu\address\logic\parser\FindContainCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindContainCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsLettersPredicate;

/**
 * Parser for FindContainCommand
 */
public class FindContainCommandParser implements Parser<FindContainCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindContainCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindContainCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContainCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_TAG);

        HashMap<String, String> mapKeywords = new HashMap<>();

        try {
            if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
                String trimmedArgsName = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_NAME)).get().trim();
                if (trimmedArgsName.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_NAME.toString(), trimmedArgsName);
            }

            if (argumentMultimap.getValue(PREFIX_PHONE).isPresent()) {
                String trimmedArgsPhone = ParserUtil.parseKeywords(argumentMultimap
                        .getValue(PREFIX_PHONE)).get().trim();
                if (trimmedArgsPhone.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_PHONE.toString(), trimmedArgsPhone);
            }

            if (argumentMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                String trimmedArgsEmail = ParserUtil.parseKeywords(argumentMultimap
                        .getValue(PREFIX_EMAIL)).get().trim();
                if (trimmedArgsEmail.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_EMAIL.toString(), trimmedArgsEmail);
            }

            if (argumentMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                String trimmedArgsAddress = ParserUtil.parseKeywords(argumentMultimap
                        .getValue(PREFIX_ADDRESS)).get().trim();
                if (trimmedArgsAddress.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_ADDRESS.toString(), trimmedArgsAddress);
            }

            if (argumentMultimap.getValue(PREFIX_TAG).isPresent()) {
                String trimmedArgsTag = ParserUtil.parseKeywords(argumentMultimap.getValue(PREFIX_TAG)).get().trim();
                if (trimmedArgsTag.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FindContainCommand.MESSAGE_USAGE));
                }
                mapKeywords.put(PREFIX_TAG.toString(), trimmedArgsTag);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (mapKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContainCommand.MESSAGE_USAGE));
        }

        return new FindContainCommand(new PersonContainsLettersPredicate(mapKeywords));
    }
}
```
###### \main\java\seedu\address\logic\parser\FindContainCommandParser.java
``` java

```
###### \main\java\seedu\address\model\person\PersonContainsLettersPredicate.java
``` java
package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} or {@code Tag} or {@code Address} or {@code Phone}
 * or {@code Email} contains any string given
 */
public class PersonContainsLettersPredicate implements Predicate<ReadOnlyPerson> {
    private final HashMap<String, String> keywords;

    public PersonContainsLettersPredicate(HashMap<String, String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson readOnlyPerson) {
        boolean result = true;
        if (keywords.containsKey(PREFIX_NAME.toString())) {
            String keyword = keywords.get(PREFIX_NAME.toString());
            String name = readOnlyPerson.getName().fullName;
            result = name.toLowerCase().contains(keyword.toLowerCase());
        }

        if (keywords.containsKey(PREFIX_PHONE.toString())) {
            String keyword = keywords.get(PREFIX_PHONE.toString());
            String phone = readOnlyPerson.getPhone().toString();
            result = result && phone.toLowerCase().contains(keyword.toLowerCase());
        }

        if (keywords.containsKey(PREFIX_EMAIL.toString())) {
            String keyword = keywords.get(PREFIX_EMAIL.toString());
            String email = readOnlyPerson.getEmail().toString();
            result = result && email.toLowerCase().contains(keyword.toLowerCase());
        }

        if (keywords.containsKey(PREFIX_ADDRESS.toString())) {
            String keyword = keywords.get(PREFIX_ADDRESS.toString());
            String address = readOnlyPerson.getAddress().toString();
            result = result && address.toLowerCase().contains(keyword.toLowerCase());
        }

        if (keywords.containsKey(PREFIX_TAG.toString())) {
            String keyword = keywords.get(PREFIX_TAG.toString());
            Set<Tag> tagSet = readOnlyPerson.getTags();
            result = result && tagSet.stream().anyMatch(tag -> tag.toString().toLowerCase()
                    .contains(keyword.toLowerCase()));
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsLettersPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsLettersPredicate) other).keywords)); // state check
    }
}
```
###### \main\java\seedu\address\model\person\PersonContainsLettersPredicate.java
``` java

```
###### \main\java\seedu\address\ui\EmailLoginWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * Pop up window for email login
 */
public class EmailLoginWindow extends UiPart<Region> {
    private static final String FXML = "EmailLoginWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(EmailLoginWindow.class);
    private Stage primaryStage;
    private Logic logic;
    private FunctionButtons functionButtonsPanel;
    private UserPrefs prefs;

    @FXML
    private Text loginText;

    @FXML
    private Text passwordText;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button emailWindowLoginButton;

    @FXML
    private TextArea feedbackLabel;

    public EmailLoginWindow(Logic logic, Stage parentStage, FunctionButtons functionButtonsPanel, UserPrefs prefs) {
        super(FXML);

        this.primaryStage = new Stage();
        Scene scene = new Scene(getRoot());

        if (prefs.getCurrentUserTheme().equals("DarkTheme")) {
            scene.getStylesheets().add("view/CSS/EmailLoginWindowDark.css");
        } else {
            scene.getStylesheets().add("view/CSS/EmailLoginWindowLight.css");
        }

        this.primaryStage.setScene(scene);
        this.primaryStage.initOwner(parentStage);
        this.primaryStage.initModality(Modality.WINDOW_MODAL);
        this.primaryStage.setResizable(false);
        this.primaryStage.getIcons().add(new Image("/images/address_book_32.png"));
        this.primaryStage.setTitle("Login");
        this.logic = logic;
        this.functionButtonsPanel = functionButtonsPanel;

        setOnCloseEvent();
    }

    /**
     * When the login button is pressed
     */
    @FXML
    private void onLoginButtonPressed() {
        String emailString = emailField.getText();
        String passwordString = passwordField.getText();

        try {
            CommandResult commandResult = logic.execute("email_login "
                    + "\"" + emailString + "\"" + " "
                    + "\"" + passwordString + "\"");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            functionButtonsPanel.updateLoginStatus();
            feedbackLabel.setText(commandResult.feedbackToUser);

            if (commandResult.feedbackToUser.contains("Success")) {
                functionButtonsPanel.toggleLoginLogout();
            }
        } catch (CommandException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
            feedbackLabel.setText(e.getMessage());
        } catch (ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
            feedbackLabel.setText(e.getMessage());
        }
    }

    /**
     * Enable the login / logout button when this window closes
     */
    public void setOnCloseEvent() {

    }

    /**
     * Show the window
     */
    public void show() {
        primaryStage.show();
    }
}
```
###### \main\java\seedu\address\ui\EmailLoginWindow.java
``` java

```
###### \main\java\seedu\address\ui\EmailSendWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * A pop up Window for email sending
 */
public class EmailSendWindow extends UiPart<Region> {
    private static final String FXML = "EmailSendWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(EmailSendWindow.class);

    private Stage primaryStage;
    private Logic logic;
    private UserPrefs prefs;

    @FXML
    private Label recipientsLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label bodyLabel;

    @FXML
    private TextField recipientsField;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea bodyField;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea feedbackLabel;

    public EmailSendWindow(Logic logic, Stage parentStage, String recipients, String feedback, UserPrefs prefs) {
        super(FXML);

        this.primaryStage = new Stage();
        Scene scene = new Scene(getRoot());

        if (prefs.getCurrentUserTheme().equals("DarkTheme")) {
            scene.getStylesheets().add("view/CSS/EmailSendWindowDark.css");
        } else {
            scene.getStylesheets().add("view/CSS/EmailSendWindowLight.css");
        }

        this.primaryStage.setScene(scene);
        this.primaryStage.initOwner(parentStage);
        this.primaryStage.initModality(Modality.WINDOW_MODAL);
        this.primaryStage.setResizable(false);
        this.primaryStage.getIcons().add(new Image("/images/address_book_32.png"));
        this.primaryStage.setTitle("Send Emails");
        this.logic = logic;
        recipientsField.setText(recipients);
        feedbackLabel.setText(feedback);
        setOnCloseEvent();
    }

    /**
     * Enable the login / logout button when this window closes
     */
    public void setOnCloseEvent() {

    }

    /**
     * Show the window
     */
    public void show() {
        primaryStage.show();
    }

    /**
     * action for clicking send button
     */
    @FXML
    private void onSendButtonClicked() {
        String recipients = recipientsField.getText();
        String title = titleField.getText();
        String body = bodyField.getText();

        try {
            CommandResult commandResult = logic.execute("email_send "
                    + "\"" + recipients + "\"" + " "
                    + "\"" + title + "\"" + " "
                    + "\"" + body + "\"");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            feedbackLabel.setText(commandResult.feedbackToUser);
        } catch (ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
            feedbackLabel.setText(e.getMessage());
        } catch (CommandException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
            feedbackLabel.setText(e.getMessage());
        }
    }
}
```
###### \main\java\seedu\address\ui\EmailSendWindow.java
``` java

```
###### \main\java\seedu\address\ui\FunctionButtons.java
``` java
package seedu.address.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EmailLogoutCommand;
import seedu.address.logic.commands.GetEmailCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * A panel for function button on the main window
 */
public class FunctionButtons extends UiPart<Region> {
    private static final String FXML = "FunctionButtons.fxml";
    private Logic logic;
    private Stage stage;
    private MainWindow mainWindow;
    private UserPrefs prefs;

    @FXML
    private StackPane loginPane;
    @FXML
    private Button loginButton;
    @FXML
    private StackPane sendPane;
    @FXML
    private Button sendButton;
    @FXML
    private TextField loginStatus;

    public FunctionButtons(Logic logic, Stage stage, MainWindow mainWindow, UserPrefs prefs) {
        super(FXML);
        this.logic = logic;
        this.stage = stage;
        this.mainWindow = mainWindow;
        this.prefs = prefs;

        updateLoginStatus();
    }

    /**
     * Open the email login window
     */
    @FXML
    private void openEmailLoginWindow() {
        boolean isLoggedIn = false;
        try {
            if (!(logic.execute("email_address")).feedbackToUser.equals("Not logged in")) {
                isLoggedIn = true;
            }
        } catch (ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
        } catch (CommandException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
        }

        if (!isLoggedIn && loginButton.getText().equals("Login")) {
            EmailLoginWindow emailLoginWindow = new EmailLoginWindow(logic, stage, this, prefs);
            emailLoginWindow.show();
        } else if (isLoggedIn && loginButton.getText().equals("Login")) {
            loginButton.setText("Logout");
            updateLoginStatus();
        } else if (!isLoggedIn && loginButton.getText().equals("Logout")) {
            loginButton.setText("Login");
            updateLoginStatus();
        } else {
            try {
                CommandResult commandResult = logic.execute(EmailLogoutCommand.COMMAND_WORD);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
                toggleLoginLogout();
                updateLoginStatus();
            } catch (CommandException e) {
                raise(new NewResultAvailableEvent(e.getMessage()));
            } catch (ParseException e) {
                raise(new NewResultAvailableEvent(e.getMessage()));
            }
        }
    }

    /**
     * Open the email send window
     */
    @FXML
    private void openEmailSendWindow() {
        ArrayList<PersonCard> tickedPersons = mainWindow.getPersonListPanel().getTickedPersons();
        String recipients = new String();
        ArrayList<PersonCard> cardWithOutEmail = new ArrayList<PersonCard>();
        for (PersonCard card : tickedPersons) {
            if (card.isTicked()) {
                if (card.getEmail() != null) {
                    recipients += card.getEmail() + ";";
                } else {
                    cardWithOutEmail.add(card);
                }
            }
        }

        String feedbackPersonsWithoutEmail = "";

        if (cardWithOutEmail.size() != 0) {
            feedbackPersonsWithoutEmail = "The following person(s) do not have emails or have private emails:\n";

            for (PersonCard card : cardWithOutEmail) {
                feedbackPersonsWithoutEmail += card.getName() + "\n";
            }
        }

        EmailSendWindow emailSendWindow = new EmailSendWindow(logic, stage, recipients, feedbackPersonsWithoutEmail,
                prefs);
        emailSendWindow.show();
    }

    /**
     * update the current login status label
     */
    public void updateLoginStatus() {
        try {
            CommandResult commandResult = logic.execute(GetEmailCommand.COMMAND_WORD);
            if (commandResult.feedbackToUser.equals(GetEmailCommand.MESSAGE_NOT_LOGGED_IN)) {
                loginStatus.setText("Currently not logged in");
            } else {
                loginStatus.setText("Currently logged in as " + commandResult.feedbackToUser);
            }
        } catch (CommandException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
        } catch (ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * toggle state of login / logout button
     */
    public void toggleLoginLogout() {
        if (loginButton.getText().equals("Login")) {
            loginButton.setText("Logout");
        } else {
            loginButton.setText("Login");
        }
    }
}
```
###### \main\java\seedu\address\ui\FunctionButtons.java
``` java

```
###### \main\java\seedu\address\ui\SearchBar.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Search bar on the GUI to filter contacts
 */
public class SearchBar extends UiPart<Region> {

    private static final String FXML = "SearchBar.fxml";

    private final Logger logger = LogsCenter.getLogger(SearchBar.class);
    private Logic logic;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField tagsField;

    public SearchBar(Logic logic) {
        super(FXML);
        this.logic = logic;

        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });

        phoneField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });

        addressField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });

        emailField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });

        tagsField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                onSearchbarChanged();
            }
        });
    }

    /**
     * Fires a new FindContainsCommand everytime search bar changes
     */
    @FXML
    private void onSearchbarChanged() {
        String commandString = "find_contain ";

        if (!nameField.getText().equals("")) {
            commandString += "n/" + nameField.getText() + " ";
        }

        if (!phoneField.getText().equals("")) {
            commandString += "p/" + phoneField.getText() + " ";
        }

        if (!emailField.getText().equals("")) {
            commandString += "e/" + emailField.getText() + " ";
        }

        if (!addressField.getText().equals("")) {
            commandString += "a/" + addressField.getText() + " ";
        }

        if (!tagsField.getText().equals("")) {
            commandString += "r/" + tagsField.getText() + " ";
        }

        if (commandString.equals("find_contain ")) {
            try {
                CommandResult commandResult = logic.execute("list");
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            } catch (ParseException e) {
                //wont happen
            } catch (CommandException e) {
                //wont happen
            }
        } else {
            try {
                CommandResult commandResult = logic.execute(commandString);
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            } catch (ParseException e) {
                //wont happen
            } catch (CommandException e) {
                //wont happen
            }
        }
    }
}
```
###### \main\java\seedu\address\ui\SearchBar.java
``` java

```
###### \test\java\seedu\address\logic\commands\EmailLoginCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.EmailLoginCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.logic.commands.exceptions.CommandException;

public class EmailLoginCommandTest {
    private EmailLoginCommand command;
    private Email emailManager = new EmailManager();

    //test for wrong email / password
    @Test
    public void wrongCredentials() throws CommandException {
        command = new EmailLoginCommand("phungtuanhoang1996@gmail.com", "thispasswordiswrong");
        command.setData(null, null, null, emailManager);
        CommandResult result = command.execute();
        assertEquals(EmailLoginCommand.MESSAGE_FAILED + EmailManager.MESSAGE_LOGIN_FAILED,
                result.feedbackToUser);
    }

    //test for correct email / password
    @Test
    public void correctCredentials() throws CommandException {
        String email = "cs2103testacc@gmail.com";
        command = new EmailLoginCommand(email, "testpass");
        command.setData(null, null, null, emailManager);
        CommandResult result = command.execute();
        assertEquals(MESSAGE_SUCCESS + email,
                result.feedbackToUser);
    }
}
```
###### \test\java\seedu\address\logic\commands\EmailLoginCommandTest.java
``` java

```
###### \test\java\seedu\address\logic\commands\EmailSendCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.email.exceptions.LoginFailedException;
import seedu.address.logic.commands.exceptions.CommandException;

public class EmailSendCommandTest {
    private EmailSendCommand command;
    private Email emailManager = new EmailManager();

    //Test case of not logged in
    @Test
    public void notLoggedIn() throws CommandException {
        command = new EmailSendCommand(new String[]{"cs2103testacc@gmail.com"}, "Title", "Body");
        command.setData(null, null, null, emailManager);

        CommandResult result = command.execute();
        assertEquals("No email logged in", result.feedbackToUser);
    }

    //Test case of invalid recipients address
    @Test
    public void notValidRecipients() throws LoginFailedException, CommandException {
        //logged in email
        emailManager.login("cs2103testacc@gmail.com", "testpass");

        command = new EmailSendCommand(new String[]{"cs2103testacc;adffd"}, "Title", "Body");
        command.setData(null, null, null, emailManager);

        CommandResult result = command.execute();
        assertEquals("One or more of the given emails is not valid", result.feedbackToUser);
    }
}
```
###### \test\java\seedu\address\logic\commands\EmailSendCommandTest.java
``` java

```
###### \test\java\seedu\address\logic\commands\ExportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.ExportCommand.MESSAGE_ACCESS_DENIED;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.GuiUnitTest;

public class ExportCommandTest extends GuiUnitTest {
    private Model model;
    private String os;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        os = System.getProperty("os.name").toLowerCase();

    }

    @Test
    public void accessDeniedFolder() throws CommandException {
        //when trying to create parent folder
        ExportCommand command = new ExportCommand(".txt", "C:/Windows/a");
        command.setData(model, null, null, null);
        if (os.indexOf("win") > 0) {
            assertEquals(command.execute(), new CommandException(MESSAGE_ACCESS_DENIED));
        }
    }

    @Test
    public void accessDeniedFile() throws CommandException {
        //when trying to create file
        ExportCommand command = new ExportCommand(".txt", "C:/Windows/a");
        command.setData(model, null, null, null);
        if (os.indexOf("win") > 0) {
            assertEquals(command.execute(), new CommandException(MESSAGE_ACCESS_DENIED));
        }
    }

    @Test
    public void success() throws CommandException {
        ExportCommand command = new ExportCommand(".txt", "C:/a");
        command.setData(model, null, null, null);
        assertTrue(command.execute().feedbackToUser.equals(new CommandResult(MESSAGE_SUCCESS).feedbackToUser));
    }
}
```
###### \test\java\seedu\address\logic\commands\ExportCommandTest.java
``` java

```
###### \test\java\seedu\address\logic\parser\EmailLoginParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.EmailLoginCommand;

public class EmailLoginParserTest {
    private EmailLoginParser parser = new EmailLoginParser();

    //wrong arguments testing
    @Test
    public void parse_wrongArguments() {
        //missing arguments
        assertParseFailure(parser, "email_login \"a@gmail.com\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailLoginCommand.MESSAGE_USAGE));

        //excessive arguments
        assertParseFailure(parser, "email_login \"a@gmail.com\" \"password\" \"password\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailLoginCommand.MESSAGE_USAGE));

        //wrong arguments
        assertParseFailure(parser, "email_login a@gmail.com \"password\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailLoginCommand.MESSAGE_USAGE));
    }

    //invalid email testing
    @Test
    public void parse_invalidEmail() {
        assertParseFailure(parser, "email_login \"agmail\" \"password\"",
               EmailLoginCommand.MESSAGE_INVALID_EMAIL);
    }
}
```
###### \test\java\seedu\address\logic\parser\EmailLoginParserTest.java
``` java

```
###### \test\java\seedu\address\logic\parser\EmailSendParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.logic.commands.EmailSendCommand;

public class EmailSendParserTest {
    private EmailSendParser parser = new EmailSendParser();
    private Email emailManager = new EmailManager();

    //test for missing / excessive arguments
    @Test
    public void parse_wrongArguments() {
        //missing arguments
        assertParseFailure(parser, "email_send \"a@a.a\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailSendCommand.MESSAGE_USAGE));

        //excessive arguments
        assertParseFailure(parser, "email_send \"a@a.a\" \"title\" \"title\" \"body\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailSendCommand.MESSAGE_USAGE));

        //not putting in quotation mark
        assertParseFailure(parser, "email_send a@a.a title title \"body\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailSendCommand.MESSAGE_USAGE));
    }
}
```
###### \test\java\seedu\address\logic\parser\EmailSendParserTest.java
``` java

```
###### \test\java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_TYPE_NOT_SUPPORTED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_wrongFileType() {
        assertParseFailure(parser, "export .pdf C:/", MESSAGE_FILE_TYPE_NOT_SUPPORTED);
    }

    @Test
    public void parse_missingArguments() {
        assertParseFailure(parser, "export C:/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
    }
}
```
###### \test\java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java

```
