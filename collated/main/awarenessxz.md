# awarenessxz
###### \java\seedu\address\commons\events\ui\EmailDraftChangedEvent.java
``` java
/**
 * Indicates that the email draft have changed
 */
public class EmailDraftChangedEvent extends BaseEvent {

    public final ReadOnlyMessageDraft message;

    public EmailDraftChangedEvent(ReadOnlyMessageDraft message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ToggleTabEvent.java
``` java
/**
 * Indicates the change of tabs after command is executed.
 */
public class ToggleTabEvent extends BaseEvent {

    public final int leftTabIndex;

    public ToggleTabEvent(int leftTabIndex) {
        this.leftTabIndex = leftTabIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and a full word match is not required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == true //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsNonFullWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.toLowerCase().contains(preppedWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
```
###### \java\seedu\address\email\Email.java
``` java
/**
 * API of Email component
 */
public interface Email {

    /**
     * Logins to Email Component with given login details
     *
     * @throws EmailLoginInvalidException if login fails
     */
    void loginEmail(String [] loginDetails) throws EmailLoginInvalidException;

    /** Returns true if user is Log in */
    boolean isUserLogin();

    /** Creates Email Draft with given message */
    void composeEmail(MessageDraft message);

    /** Views Email Draft */
    ReadOnlyMessageDraft getEmailDraft();

    /** Views Email Send Status */
    String getEmailStatus();

    /** Clears Email Draft content */
    void clearEmailDraft();

    /**
     * Sends Email Draft to all users
     *
     * @throws EmailLoginInvalidException if login details is empty
     * @throws EmailMessageEmptyException if message is empty
     * @throws EmailRecipientsEmptyException if recipients list is empty
     * @throws AuthenticationFailedException if gmail account can't be logged in
     */
    void sendEmail() throws EmailLoginInvalidException, EmailMessageEmptyException,
            EmailRecipientsEmptyException, AuthenticationFailedException;

}
```
###### \java\seedu\address\email\EmailCompose.java
``` java
/**
 * Holds a list of messageDraft
 * Handles how messageDraft are created and edited
 */
public class EmailCompose {
    private DraftList emailDraftsList;

    /** Creates an EmailCompose with an empty draft list **/
    public EmailCompose() {
        emailDraftsList = new DraftList();
    }

    /**
     * Compose an Email or edit the current one
     *
     * @param message
     */
    public void composeEmail(MessageDraft message) {
        emailDraftsList.composeEmail(message);
    }

    public ReadOnlyMessageDraft getMessage() {
        return emailDraftsList.getMessage(0);
    }

    /**
     * Resets the existing data of this {@code emailDraftsList} with an empty draft list
     */
    public void resetData() {
        emailDraftsList = new DraftList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCompose // instanceof handles nulls
                && this.emailDraftsList.equals(((EmailCompose) other).emailDraftsList));
    }
}
```
###### \java\seedu\address\email\EmailLogin.java
``` java
/**
 * Handles how user logs into email
 */
public class EmailLogin {
    private static final Pattern GMAIL_FORMAT = Pattern.compile("^[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(@gmail.com)$");

    private String [] loginDetails;

    /** Creates an EmailLogin with an empty login detail */
    public EmailLogin() {
        loginDetails = new String[0];
    }

    /**
     * Saves user's login details
     *
     * @param loginDetails login email and password
     * @throws EmailLoginInvalidException if loginDetails is in wrong format
     */
    public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
        //replace login details and ignore if login details is omitted.
        if (loginDetails.length != 0 && loginDetails.length == 2) {
            if (wrongUserEmailFormat(loginDetails)) {
                throw new EmailLoginInvalidException();
            } else {
                this.loginDetails = loginDetails;
            }
        }
    }

    /**
     * Checks if user's login details have been stored
     *
     * @return true if loginDetails is available
     */
    public boolean isUserLogin() {
        if (loginDetails.length != 2) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Verifies if the user is using a gmail account
     *
     * @return true if gmail account, false for everything else
     */
    private boolean wrongUserEmailFormat(String [] loginDetails) {
        if (loginDetails.length == 2) {
            final Matcher matcher = GMAIL_FORMAT.matcher(loginDetails[0].trim());
            if (!matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /** Returns user's login email */
    public String getEmailLogin() {
        if (loginDetails.length == 2) {
            return loginDetails[0];
        } else {
            return "";
        }
    }

    /** Returns user's login password */
    public String getPassword() {
        if (loginDetails.length == 2) {
            return loginDetails[1];
        } else {
            return "";
        }
    }

    /**
     * Resets the existing data of this {@code loginDetails} with an empty login
     */
    public void resetData() {
        loginDetails = new String[0];
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailLogin // instanceof handles nulls
                && this.loginDetailsEquals(((EmailLogin) other).loginDetails));
    }

    /** Returns true if both have the same loginDetails */
    private boolean loginDetailsEquals(String [] other) {
        if (loginDetails.length == other.length) {
            for (int i = 0; i < loginDetails.length; i++) {
                if (loginDetails[i] != other[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
```
###### \java\seedu\address\email\EmailManager.java
``` java
/**
 * Handles how email are sent out of the application.
 */
public class EmailManager extends ComponentManager implements Email {
    private static final Logger logger = LogsCenter.getLogger(EmailManager.class);

    private static final String STATUS_CLEARED = "cleared.";
    private static final String STATUS_DRAFTED = "drafted.\n";
    private static final String STATUS_SENT = "sent ";
    private static final String STATUS_LOGIN_FAIL = "You are not logged in to any Gmail account.";
    private static final String STATUS_LOGIN_SENT = "using %1$s";
    private static final String STATUS_LOGIN_SUCCESS = "You are logged in to %1$s";

    private final EmailLogin emailLogin;
    private final EmailCompose emailCompose;
    private final EmailSend emailSend;

    private String emailStatus;
    private String emailLoginStatus;

    /**
     * Initializes a EmailManager with new EmailLogin, EmailCompose and EmailSend
     */
    public EmailManager() {
        logger.fine("Initializing Default Email component");

        emailLogin = new EmailLogin();
        emailCompose = new EmailCompose();
        emailSend = new EmailSend();
        emailStatus = "";
        emailLoginStatus = STATUS_LOGIN_FAIL;
    }

    @Override
    public void composeEmail(MessageDraft message) {
        emailCompose.composeEmail(message);
        emailStatus = STATUS_DRAFTED;
    }

    @Override
    public ReadOnlyMessageDraft getEmailDraft() {
        return emailCompose.getMessage();
    }

    @Override
    public String getEmailStatus() {
        return emailStatus + emailLoginStatus;
    }

    @Override
    public void sendEmail() throws EmailLoginInvalidException, EmailMessageEmptyException,
            EmailRecipientsEmptyException, AuthenticationFailedException {
        logger.info("-------------------[Sending Email] ");

        emailSend.sendEmail(emailCompose, emailLogin);

        //reset the email draft after email have been sent
        emailStatus = STATUS_SENT;
        emailLoginStatus = String.format(STATUS_LOGIN_SENT, emailLogin.getEmailLogin());
        resetData();
    }

    @Override
    public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
        emailLogin.loginEmail(loginDetails);
        if (emailLogin.isUserLogin()) {
            emailLoginStatus = String.format(STATUS_LOGIN_SUCCESS, emailLogin.getEmailLogin());
        } else {
            emailLoginStatus = STATUS_LOGIN_FAIL;
        }
    }

    /**
     * Returns true if the emailLogin contains user's login details
     */
    @Override
    public boolean isUserLogin() {
        return emailLogin.isUserLogin();
    }


    @Override
    public void clearEmailDraft() {
        resetData();
        emailStatus = STATUS_CLEARED;
        emailLoginStatus = "";
    }

    /**
     * Resets the existing data of this {@code emailCompose} and this {@code emailLogin}
     */
    private void resetData() {
        emailCompose.resetData();
        emailLogin.resetData();
    }

    @Override
    public boolean equals(Object obj) {

        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof EmailManager)) {
            return false;
        }

        // state check
        EmailManager other = (EmailManager) obj;
        return this.emailCompose.equals(((EmailManager) obj).emailCompose)
                && this.emailLogin.equals(((EmailManager) obj).emailLogin);
    }

}
```
###### \java\seedu\address\email\EmailSend.java
``` java
/**
 * Handles how email is send via JavaAPI
 */
public class EmailSend {
    private Properties props;

    /** Creates an EmailSend with an default properties */
    public EmailSend() {
        prepEmailProperties();
    }

    /** Prepares Email Default Properties */
    private void prepEmailProperties() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "465");
    }

    /**
     * Handles sending email process
     *
     * @param emailCompose contains message to be send
     * @param emailLogin contains login details of user
     * @throws EmailLoginInvalidException if login details is empty
     * @throws EmailMessageEmptyException if message is empty
     * @throws EmailRecipientsEmptyException if recipients list is empty
     * @throws AuthenticationFailedException if gmail account can't be logged in
     */
    public void sendEmail(EmailCompose emailCompose, EmailLogin emailLogin) throws EmailLoginInvalidException,
            EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {

        //Step 1. Verify that the email draft consists of message and subject
        if (!emailCompose.getMessage().containsContent()) {
            //throw exception that user needs to enter message and subject to send email
            throw new EmailMessageEmptyException();
        }

        //Step 2. Verify that the user is logged in with a gmail account
        if (!emailLogin.isUserLogin()) {
            //throw exception that user needs to enter gmail login details to send email
            throw new EmailLoginInvalidException();
        }

        //Step 3. Verify that Recipient's list is not empty
        if (emailCompose.getMessage().getRecipientsEmails().length <= 0) {
            throw new EmailRecipientsEmptyException();
        }

        //Step 4. sending Email out using JavaMail API
        sendingEmail(emailLogin.getEmailLogin(), emailLogin.getPassword(), emailCompose.getMessage());
    }

    /**
     * Sends email out using JavaMail API
     *
     * @param login email login account
     * @param pass email login password
     * @param message message to send
     * @throws AuthenticationFailedException if gmail account can't be logged in
     */
    private void sendingEmail(String login, String pass, ReadOnlyMessageDraft message)
            throws AuthenticationFailedException {
        final String username = login;
        final String password = pass;

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message newMessage = new MimeMessage(session);
            newMessage.setFrom(new InternetAddress(username));
            newMessage.setRecipients(Message.RecipientType.TO, message.getRecipientsEmails());
            newMessage.setSubject(message.getSubject());
            newMessage.setText(message.getMessage());

            Transport.send(newMessage);
        } catch (AuthenticationFailedException e) {
            throw new AuthenticationFailedException();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
```
###### \java\seedu\address\email\EmailTask.java
``` java
/**
 * Keeps track of Current Email Command Task
 */
public class EmailTask {
    public static final String TASK_SEND = "send";
    public static final String TASK_CLEAR = "clear";

    private String task;

    public EmailTask() {
        task = "";
    }

    public EmailTask(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    /** Returns true if task is valid */
    public boolean isValid() {
        switch (task) {
        case TASK_CLEAR:
            return true;
        case TASK_SEND:
            return true;
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailTask // instanceof handles nulls
                && this.task.equals(((EmailTask) other).task));
    }
}
```
###### \java\seedu\address\email\exceptions\EmailLoginInvalidException.java
``` java
/**
 * Signals that the email is unable to be send due to invalid login details
 */
public class EmailLoginInvalidException extends Exception {
}
```
###### \java\seedu\address\email\exceptions\EmailMessageEmptyException.java
``` java
/**
 * Signals that the email is unable to be send without message or subject
 */
public class EmailMessageEmptyException extends Exception {
}
```
###### \java\seedu\address\email\exceptions\EmailRecipientsEmptyException.java
``` java
/**
 * Signals that the email is unable to be send without recipients
 */
public class EmailRecipientsEmptyException extends Exception {
}
```
###### \java\seedu\address\email\message\DraftList.java
``` java
/**
 * Contains a list of Email Message Drafts
 */
public class DraftList {
    private MessageDraft [] messages = new MessageDraft[1];

    public DraftList() {
        messages[0] = new MessageDraft();
    }

    /**
     * Compose a new email or edit the current one
     *
     * @param newMessage new email
     */
    public void composeEmail(MessageDraft newMessage) {
        MessageDraft message = messages[0];
        if (newMessage.getSubject().isEmpty()) {
            newMessage.setSubject(message.getSubject());
        }
        if (newMessage.getMessage().isEmpty()) {
            newMessage.setMessage(message.getMessage());
        }
        messages[0] = newMessage;
    }

    /**
     * Returns draft at requested index
     *
     * @param i index of message in draftlist
     * @return Unmodifiable message draft
     */
    public ReadOnlyMessageDraft getMessage(int i) {
        return messages[i];
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DraftList // instanceof handles nulls
                && this.draftListEquals(((DraftList) other).messages));
    }

    /** Returns true if both have the same draft list */
    private boolean draftListEquals(MessageDraft [] other) {
        if (other.length == this.messages.length) {
            for (int i = 0; i < this.messages.length; i++) {
                if (!this.messages[i].equals(other[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
```
###### \java\seedu\address\email\message\MessageDraft.java
``` java
/**
 * Represents a Email Message Draft in addressbook.
 */
public class MessageDraft implements ReadOnlyMessageDraft {

    private String message;
    private String subject;
    private InternetAddress [] recipientsEmail;

    public MessageDraft() {
        message = "";
        subject = "";
        recipientsEmail = new InternetAddress[0];
    }

    public MessageDraft(String message, String subject) {
        this.message = message;
        this.subject = subject;
        recipientsEmail = new InternetAddress[0];
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public InternetAddress[] getRecipientsEmails() {
        return recipientsEmail;
    }

    public void setRecipientsEmail(InternetAddress[] recipientsEmail) {
        this.recipientsEmail = new InternetAddress[recipientsEmail.length];
        System.arraycopy(recipientsEmail, 0, this.recipientsEmail, 0, recipientsEmail.length);
    }

    @Override
    public String getRecipientsEmailtoString() {
        String receipients = "";
        for (int i = 0; i < recipientsEmail.length; i++) {
            receipients += recipientsEmail[i].getAddress();
            if (i != recipientsEmail.length - 1) {
                receipients += ", ";
            }
        }
        return receipients;
    }

    @Override
    public boolean containsContent() {
        if (message.isEmpty() || subject.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyMessageDraft // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyMessageDraft) other));
    }

    @Override
    public boolean recipientsEquals(InternetAddress [] other) {
        if (other.length == recipientsEmail.length) {
            for (int i = 0; i < recipientsEmail.length; i++) {
                if (other[i] != recipientsEmail[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
```
###### \java\seedu\address\email\message\ReadOnlyMessageDraft.java
``` java
/**
 * A read-only immutable interface for a email message in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMessageDraft {
    String getMessage();
    String getSubject();
    InternetAddress [] getRecipientsEmails();

    /** Converts all recipient's email address to String */
    String getRecipientsEmailtoString();

    /** Returns true if message and subject is not empty */
    boolean containsContent();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMessageDraft other) {

        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getMessage().equals(this.getMessage()) // state checks here onwards
                && other.getSubject().equals(this.getSubject())
                && other.recipientsEquals(this.getRecipientsEmails()));
    }

    /** Returns true if both recipient addresses are the same */
    boolean recipientsEquals(InternetAddress [] other);
}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
/**
 * Composes an email draft or sends the draft out using gmail account
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails all contacts in the last displayed list\n"
            + "Parameters:\n"
            + "email [ et/<send|clear> ] [ em/MESSAGE ] [ es/SUBJECT ]  [ el/user@gmail.com:password ]\n"
            + "Examples:\n"
            + "1) email em/what is your message?\n"
            + "2) email es/new subject\n"
            + "3) email el/adam@gmail.com:password\n"
            + "4) email et/send\n"
            + "5) email em/message es/subject el/adam@gamil.com:password et/send\n"
            + "6) email et/clear";

    public static final String MESSAGE_SUCCESS = "Email have been %1$s";
    public static final String MESSAGE_LOGIN_INVALID = "You must log in with a gmail email account before you can send "
            + "an email.\n"
            + "Command: email el/<username@gmail.com>:<password>";
    public static final String MESSAGE_EMPTY_INVALID = "You must fill in the message and subject before you can send "
            + "an email.\n"
            + "Command: email em/<messages> es/<subjects>";
    public static final String MESSAGE_RECIPIENT_INVALID = "You must have at least 1 recipients in your contacts "
            + "display list before you can send an email.\n"
            + "Command: use the list or find command";
    public static final String MESSAGE_AUTHENTICATION_FAIL = "You are unable to log in to your gmail account. Please "
            + "check the following:\n"
            + "1) You have entered the correct email address and password.\n"
            + "2) You have enabled 'Allow less secure app' to sign in to your gmail account settings";
    public static final String MESSAGE_FAIL_UNKNOWN = "Unexpected error have occurred...Please try again later";

    private final MessageDraft message;
    private final String [] loginDetails;
    private final EmailTask task;

    public EmailCommand(String message, String subject, String [] loginDetails, EmailTask task) {
        this.message = new MessageDraft(message, subject);
        this.task = task;
        this.loginDetails = loginDetails;
    }

    /**
     * Extracts Email from last display person {@code lastshownList} into an InternetAddresss[] for sending email
     *
     * @param: lastshownList last shown display person list in user interface
     * @return: list of internet email address
     */
    private InternetAddress[] extractEmailFromContacts(List<ReadOnlyPerson> lastShownList) throws AddressException {
        InternetAddress [] recipientsEmail = new InternetAddress[lastShownList.size()];
        try {
            for (int i = 0; i < lastShownList.size(); i++) {
                recipientsEmail[i] = new InternetAddress(lastShownList.get(i).getEmailAddress().value);
            }
        } catch (AddressException e) {
            throw new AddressException();
        }
        return recipientsEmail;
    }

    /**
     * Identifies the Email Command Execution Task purpose
     *
     * @throws EmailLoginInvalidException if login details is empty
     * @throws EmailMessageEmptyException if message is empty
     * @throws EmailRecipientsEmptyException if recipients list is empty
     * @throws AuthenticationFailedException if gmail account can't be logged in
     */
    private void identifyEmailTask() throws EmailLoginInvalidException, EmailMessageEmptyException,
            EmailRecipientsEmptyException, AuthenticationFailedException {
        switch (task.getTask()) {
        case EmailTask.TASK_SEND:
            model.sendEmail(message);
            break;
        case EmailTask.TASK_CLEAR:
            model.clearEmailDraft();
            break;
        default:
            model.draftEmail(message);
            break;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        //Update recipient list based on last displayed list
        try {
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
            InternetAddress [] recipientsEmail = extractEmailFromContacts(lastShownList);
            message.setRecipientsEmail(recipientsEmail);
        } catch (AddressException e) {
            assert false : "The target email cannot be missing or be wrong format";
        }

        try {
            model.loginEmail(loginDetails);
            identifyEmailTask();
            return new CommandResult(String.format(MESSAGE_SUCCESS, model.getEmailStatus()));
        } catch (EmailLoginInvalidException e) {
            throw new CommandException(MESSAGE_LOGIN_INVALID);
        } catch (EmailMessageEmptyException e) {
            throw new CommandException(MESSAGE_EMPTY_INVALID);
        } catch (EmailRecipientsEmptyException e) {
            throw new CommandException(MESSAGE_RECIPIENT_INVALID);
        } catch (AuthenticationFailedException e) {
            throw new CommandException(MESSAGE_AUTHENTICATION_FAIL);
        } catch (RuntimeException e) {
            throw new CommandException(MESSAGE_FAIL_UNKNOWN);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && ((EmailCommand) other).message.equals(this.message)
                && ((EmailCommand) other).loginDetailsEquals(this.loginDetails)
                && ((EmailCommand) other).task.equals(this.task));
    }

    /** Returns */
    private boolean loginDetailsEquals(String [] other) {
        if (this.loginDetails.length == other.length) {
            for (int i = 0; i < this.loginDetails.length; i++) {
                if (this.loginDetails[i] != other[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name or tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or tag contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[ " + PREFIX_NAME + "KEYWORD [MORE_KEYWORDS]... ] [ " + PREFIX_TAG + "KEYWORD [MORE_KEYWORDS]... ] "
            + "[ " + PREFIX_SORT + "<name|tag|email|address> ]\n"
            + "Examples:\n"
            + "1) " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie\n"
            + "2) " + COMMAND_WORD + " " + PREFIX_TAG + "tag1 tag2 tag3\n"
            + "3) " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie " + PREFIX_TAG + "tag1 tag2 tag3\n"
            + "4) " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie " + PREFIX_SORT + "name\n"
            + "5) " + COMMAND_WORD + " " + PREFIX_TAG + "tag1 tag2 tag3 " + PREFIX_SORT + "tag";

    private final NameContainsKeywordsPredicate predicate;
    private int sortOrder = 0;

    public FindCommand(NameContainsKeywordsPredicate predicate, int sortOrder) {
        this.predicate = predicate;
        this.sortOrder = sortOrder;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        model.sortFilteredPersons(sortOrder);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL_MESSAGE, PREFIX_EMAIL_SUBJECT, PREFIX_EMAIL_LOGIN,
                        PREFIX_EMAIL_TASK);

        EmailTask task = new EmailTask();
        String message;
        String subject;
        String [] loginDetails;

        try {

            message = getArgumentMessage(argMultimap);
            subject = getArgumentSubject(argMultimap);
            loginDetails = getArgumentLoginDetails(argMultimap);
            task = getArgumentEmailTask(argMultimap, task);

            /** checks if only "email" command is run */
            if (message.isEmpty() && subject.isEmpty() && (loginDetails.length == 0) && !task.isValid()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new EmailCommand(message, subject, loginDetails, task);
    }

    /**
     * Returns argument message values if available
     *
     * @param argMultimap
     * @return argument message values
     * @throws IllegalValueException if value is empty
     */
    private String getArgumentMessage(ArgumentMultimap argMultimap) throws IllegalValueException {
        String message = "";

        if (argMultimap.getValue(PREFIX_EMAIL_MESSAGE).isPresent()) {
            message = ParserUtil.parseEmailMessage(argMultimap.getValue(PREFIX_EMAIL_MESSAGE)).trim();
            if (message.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
            }
        }
        return message;
    }

    /**
     * Returns argument subject values if available
     *
     * @param argMultimap
     * @return argument subject values
     * @throws IllegalValueException if value is empty
     */
    private String getArgumentSubject(ArgumentMultimap argMultimap) throws IllegalValueException {
        String subject = "";

        if (argMultimap.getValue(PREFIX_EMAIL_SUBJECT).isPresent()) {
            subject = ParserUtil.parseEmailMessage(argMultimap.getValue(PREFIX_EMAIL_SUBJECT)).trim();
            if (subject.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
            }
        }
        return subject;
    }

    /**
     * Returns argument login values if available
     *
     * @param argMultimap
     * @return argument login values
     * @throws IllegalValueException if value is empty
     */
    private String [] getArgumentLoginDetails(ArgumentMultimap argMultimap) throws IllegalValueException {
        String login = "";
        String [] loginDetails = new String[0];

        if (argMultimap.getValue(PREFIX_EMAIL_LOGIN).isPresent()) {
            login = ParserUtil.parseLoginDetails(argMultimap.getValue(PREFIX_EMAIL_LOGIN)).trim();
            loginDetails = login.split(":");
            if (loginDetails.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
            }
        }
        return loginDetails;
    }

    /**
     * Returns argument task values if available
     *
     * @param argMultimap
     * @param task new EmailTask object
     * @return task with argument task values
     * @throws IllegalValueException if task is not "send" or "clear"
     */
    private EmailTask getArgumentEmailTask(ArgumentMultimap argMultimap, EmailTask task) throws IllegalValueException {
        if (argMultimap.getValue(PREFIX_EMAIL_TASK).isPresent()) {
            task.setTask(ParserUtil.parseEmailTask(argMultimap.getValue(PREFIX_EMAIL_TASK)).trim());
            if (!task.isValid()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
            }
        }
        return task;
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_SORT);

        String trimmedArgsName = "";
        String trimmedArgsTag = "";
        int sortOrder = 0;
        String [] nameKeywords = new String[0];
        String [] tagKeywords = new String[0];

        try {
            trimmedArgsName = getArgumentName(argMultimap);
            trimmedArgsTag = getArgumentTag(argMultimap);
            sortOrder = getArgumentSortOrder(argMultimap);

            if (trimmedArgsName.isEmpty() && trimmedArgsTag.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            } else {
                if (!trimmedArgsName.isEmpty()) {
                    nameKeywords = trimmedArgsName.split("\\s+");
                }
                if (!trimmedArgsTag.isEmpty()) {
                    tagKeywords = trimmedArgsTag.split("\\s+");
                }
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                Arrays.asList(tagKeywords)), sortOrder);
    }

    /**
     * Returns argument name values if available
     *
     * @param argMultimap
     * @return argument name values
     * @throws IllegalValueException if value is empty
     */
    private String getArgumentName(ArgumentMultimap argMultimap) throws IllegalValueException {
        String trimmedArgsName = "";

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            trimmedArgsName = ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_NAME)).get().trim();
            if (trimmedArgsName.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
        return trimmedArgsName;
    }

    /**
     * Returns argument tag values if available
     *
     * @param argMultimap
     * @return argument tag values
     * @throws IllegalValueException if value is empty
     */
    private String getArgumentTag(ArgumentMultimap argMultimap) throws IllegalValueException {
        String trimmedArgsTag = "";

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            trimmedArgsTag = ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_TAG)).get().trim();
            if (trimmedArgsTag.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
        return trimmedArgsTag;
    }

    /**
     * Returns sort option if available
     *
     * @param argMultimap
     * @return int sort option
     * @throws IllegalValueException if sort < 0
     */
    private int getArgumentSortOrder(ArgumentMultimap argMultimap) throws IllegalValueException {
        int sortOrder = 0;

        if (argMultimap.getValue(PREFIX_SORT).isPresent()) {
            sortOrder = ParserUtil.parseSortOrder(argMultimap.getValue(PREFIX_SORT));
            if (sortOrder < 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
        return sortOrder;
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code Optional<String> sort} into an {@code int} and returns it.
     * @throws IllegalValueException if the specified sort is invalid
     */
    public static int parseSortOrder(Optional<String> sort) throws IllegalValueException {
        requireNonNull(sort);
        int sortOrder = 0;
        if (sort.isPresent()) {
            switch(sort.get().trim()) {
            case SORT_NAME:
                sortOrder = 0;
                break;
            case SORT_TAG:
                sortOrder = 1;
                break;
            case SORT_EMAIL:
                sortOrder = 2;
                break;
            case SORT_ADDRESS:
                sortOrder = 3;
                break;
            default:
                sortOrder = -1;
                break;
            }
        } else {
            sortOrder = -1;
        }
        return sortOrder;
    }

    /**
     * Parses a {@code Optional<String> message} into an {@code String}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseEmailMessage(Optional<String> message) throws IllegalValueException {
        requireNonNull(message);
        return message.isPresent() ? message.get() : "";
    }

    /**
     * Parses a {@code Optional<String> subject} into an {@code String}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseEmailSubject(Optional<String> subject) throws IllegalValueException {
        requireNonNull(subject);
        return subject.isPresent() ? subject.get() : "";
    }

    /**
     * Parses a {@code Optional<String> loginDetails} into an {@code String}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseLoginDetails(Optional<String> loginDetails) throws IllegalValueException {
        requireNonNull(loginDetails);
        return loginDetails.isPresent() ? loginDetails.get() : "";
    }

    /**
     * Parses a {@code Optional<String> task} into an {@code String}
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseEmailTask(Optional<String> task) throws IllegalValueException {
        requireNonNull(task);
        return task.isPresent() ? task.get() : "";
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Email getEmailManager() {
        return email;
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedPersonsList);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortFilteredPersons(int sortOrder) {

        //sort by name by default
        Comparator<ReadOnlyPerson> sort = new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                return o1.getName().fullName.toUpperCase().compareTo(o2.getName().fullName.toUpperCase());
            }
        };

        if (sortOrder == 1) {
            //sort by tags
            sort = new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                    TreeSet<Tag> o1SortedTags = new TreeSet<Tag>(o1.getTags());
                    TreeSet<Tag> o2SortedTags = new TreeSet<Tag>(o2.getTags());

                    if (o1SortedTags.size() == 0) {
                        return 1;
                    } else if (o2SortedTags.size() == 0) {
                        return -1;
                    } else {
                        return o1SortedTags.first().tagName.compareTo(o2SortedTags.first().tagName);
                    }
                }
            };
        } else if (sortOrder == 2) {
            //sort by emails
            sort = new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                    return o1.getEmailAddress().value.toUpperCase().compareTo(o2.getEmailAddress().value.toUpperCase());
                }
            };
        } else if (sortOrder == 3) {
            //sort by address
            sort = new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                    return o1.getAddress().value.toUpperCase().compareTo(o2.getAddress().value.toUpperCase());
                }
            };
        }

        sortedPersonsList.setComparator(sort);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
        email.loginEmail(loginDetails);
    }

    @Override
    public void sendEmail(MessageDraft message) throws EmailLoginInvalidException, EmailMessageEmptyException,
            EmailRecipientsEmptyException, AuthenticationFailedException {
        email.composeEmail(message);
        email.sendEmail();

        raise(new EmailDraftChangedEvent(email.getEmailDraft()));
    }

    @Override
    public String getEmailStatus() {
        return email.getEmailStatus();
    }

    @Override
    public void clearEmailDraft() {
        email.clearEmailDraft();

        raise(new EmailDraftChangedEvent(email.getEmailDraft()));
    }

    @Override
    public void draftEmail(MessageDraft message) {
        email.composeEmail(message);

        raise(new EmailDraftChangedEvent(email.getEmailDraft()));
    }
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> namekeywords;
    private final List<String> tagkeywords;

    public NameContainsKeywordsPredicate(List<String> namekeywords, List<String> tagkeywords) {
        this.namekeywords = namekeywords;
        this.tagkeywords = tagkeywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        if (!namekeywords.isEmpty() && !tagkeywords.isEmpty()) {
            return namekeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsNonFullWordIgnoreCase(person.getName().fullName, keyword)
                            && person.containsTags(tagkeywords));
        } else if (!namekeywords.isEmpty()) {
            return namekeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsNonFullWordIgnoreCase(person.getName().fullName, keyword));
        } else if (!tagkeywords.isEmpty()) {
            return person.containsTags(tagkeywords);
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.namekeywords.equals(((NameContainsKeywordsPredicate) other).namekeywords)
                && this.tagkeywords.equals(((NameContainsKeywordsPredicate) other).tagkeywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns true if all tags in {@code List<String> tags} are found in this {@code tags}
     */
    @Override
    public boolean containsTags(List<String> tags) {
        for (Tag t : this.tags.get().toSet()) {
            boolean found = tags.stream().anyMatch(tag ->
                    StringUtil.containsNonFullWordIgnoreCase(t.tagName, tag));
            if (found) {
                return true;
            }
        }
        return false;
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sort the list in lexicographically name order.
     */
    private void sortInternalList() {
        Comparator<Person> sort = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().fullName.toUpperCase().compareTo(o2.getName().fullName.toUpperCase());
            }
        };
        sortedInternalList.setComparator(sort);
    }
```
###### \java\seedu\address\model\tag\Tag.java
``` java
    @Override
    public int compareTo(Tag o) {
        return this.tagName.compareTo(o.tagName);
    }
```
###### \java\seedu\address\ui\LeftDisplayPanel.java
``` java
/**
 * Left Tab Panel containing personListPanel, EmailDraftPanel, and BirthdateTab
 */
public class LeftDisplayPanel extends UiPart<Region> {
    private static final String FXML = "LeftDisplayPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LeftDisplayPanel.class);

    //Independent UI parts residing in this UI container
    private PersonListPanel personListPanel;
    private PersonListBirthdatePanel birthdayListPanel;
    private MessageDisplay messageDisplay;
    private int tabIndex;

    @FXML
    private TabPane leftDisplayPanel;

    @FXML
    private Tab personListTab;

    @FXML
    private Tab emailDraftTab;

    @FXML
    private Tab birthdateTab;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane messageDraftPanelPlaceholder;

    @FXML
    private StackPane birthdatePanelPlaceholder;

    public LeftDisplayPanel(ObservableList<ReadOnlyPerson> personList,
                            ObservableList<ReadOnlyPerson> personListBirthdate) {
        super(FXML);

        personListPanel = new PersonListPanel(personList);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        birthdayListPanel = new PersonListBirthdatePanel(personListBirthdate);
        birthdatePanelPlaceholder.getChildren().add(birthdayListPanel.getRoot());

        messageDisplay = new MessageDisplay();
        messageDraftPanelPlaceholder.getChildren().add(messageDisplay.getRoot());

        tabIndex = 0;
    }

    /**
     * Toggles Tabs based on index
     */
    public void toggleTabs(int index) {
        if (index >= 0) {
            tabIndex = index;
        } else {
            tabIndex = (tabIndex + 1) % 3;
        }

        switch (tabIndex) {
        case 0:
            leftDisplayPanel.getSelectionModel().select(personListTab);
            break;
        case 1:
            leftDisplayPanel.getSelectionModel().select(emailDraftTab);
            break;
        case 2:
            leftDisplayPanel.getSelectionModel().select(birthdateTab);
            break;
        default:
            assert false : "This should not happen";
            break;
        }
    }

    /**
     * Scrolls one page down
     */
    public void scrollDown() {
        if (tabIndex == 0) {
            personListPanel.scrollDown();
        } else if (tabIndex == 2) {
            birthdayListPanel.scrollDown();
        }
    }

    /**
     * Scrolls one page up
     */
    public void scrollUp() {
        if (tabIndex == 0) {
            personListPanel.scrollUp();
        } else if (tabIndex == 2) {
            birthdayListPanel.scrollUp();
        }
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Calls UI to toggle Tabs
     */
    @FXML
    private void handleToggleTabs() {
        leftDisplayPanel.toggleTabs(-1);
    }

```
###### \java\seedu\address\ui\MessageDisplay.java
``` java
/**
 * A ui for the display of the current email draft
 */
public class MessageDisplay extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "MessageDisplay.fxml";

    private final StringProperty messageDisplay = new SimpleStringProperty("");
    private final StringProperty recipientsDisplay = new SimpleStringProperty("");
    private final StringProperty subjectDisplay = new SimpleStringProperty("");

    @FXML
    private TextArea messageArea;
    @FXML
    private TextArea recipientsArea;
    @FXML
    private TextArea subjectArea;


    public MessageDisplay() {
        super(FXML);

        messageArea.setWrapText(true);
        recipientsArea.setWrapText(true);
        subjectArea.setWrapText(true);

        messageArea.textProperty().bind(messageDisplay);
        recipientsArea.textProperty().bind(recipientsDisplay);
        subjectArea.textProperty().bind(subjectDisplay);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleEmailDraftChangedEvent(EmailDraftChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageDisplay.setValue(event.message.getMessage());
                recipientsDisplay.setValue(event.message.getRecipientsEmailtoString());
                subjectDisplay.setValue(event.message.getSubject());
            }
        });
    }
}
```
###### \resources\view\LeftDisplayPanel.fxml
``` fxml

<?import javafx.scene.control.Tab?>
<?import javafx.scene.control.TabPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<TabPane fx:id="leftDisplayPanel" minWidth="450" prefWidth="450" tabClosingPolicy="UNAVAILABLE" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <tabs>
    <Tab fx:id="personListTab" text="Contact List">
         <content>
            <VBox fx:id="personList" minWidth="450" prefWidth="450">
               <children>
                  <StackPane fx:id="personListPanelPlaceholder" prefHeight="150.0" prefWidth="200.0" VBox.vgrow="ALWAYS" />
               </children>
            </VBox>
         </content>
    </Tab>
    <Tab fx:id="emailDraftTab" text="Email Draft">
         <content>
            <StackPane fx:id="messageDraftPanelPlaceholder" prefHeight="150.0" prefWidth="200.0" VBox.vgrow="ALWAYS" />
         </content>
    </Tab>
      <Tab fx:id="birthdateTab" text="Birthdays">
          <content>
              <VBox fx:id="birthdayList" minWidth="450" prefWidth="450">
                  <children>
                      <StackPane fx:id="birthdatePanelPlaceholder" prefHeight="150.0" prefWidth="200.0" VBox.vgrow="ALWAYS" />
                  </children>
              </VBox>
          </content>
      </Tab>
  </tabs>
</TabPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
      <MenuItem fx:id="shortcutMenuToggleTab" mnemonicParsing="false" onAction="#handleToggleTabs" text="Toggle Tabs" />
```
###### \resources\view\MessageDisplay.fxml
``` fxml
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.text.Font?>

<AnchorPane minWidth="450.0" prefHeight="475.0" prefWidth="450" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Label layoutX="14.0" layoutY="14.0" prefHeight="27.0" prefWidth="113.0" text="Subject:" styleClass="email-Display"/>
      <Label layoutX="14.0" layoutY="87.0" prefHeight="27.0" prefWidth="127.0" text="Recipients:" styleClass="email-Display"/>
      <Label layoutX="14.0" layoutY="190.0" prefHeight="27.0" prefWidth="103.0" text="Message:" styleClass="email-Display"/>
      <TextArea fx:id="subjectArea" editable="false" layoutX="13.0" layoutY="42.0" prefHeight="43.0" prefWidth="419.0" />
      <TextArea fx:id="recipientsArea" editable="false" layoutX="14.0" layoutY="114.0" prefHeight="74.0" prefWidth="419.0" />
      <TextArea fx:id="messageArea" editable="false" layoutX="14.0" layoutY="220.0" prefHeight="242.0" prefWidth="419.0" />
   </children>
</AnchorPane>
```
