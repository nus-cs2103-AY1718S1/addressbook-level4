package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.AddCommandParser.INDEX_FIVE;
import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;
import static seedu.address.logic.parser.ParserUtil.INDEX_ZERO;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.net.ssl.internal.ssl.Provider;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author justintkj
/**
 * Email a person chosen by index
 * API usage referenced from https://www.javatpoint.com/example-of-sending-email-using-java-mail-api
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_SUCCESS = "Email sent to :";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Only two commas should be used.\n"
            + "Use one comma after index,one after subject message.\n"
            + "Body Message should not be empty"
            + "Email the person identified "
            + "by the index number used in the last person listing. "
            + "An email will be sent to the chosen person.\n"
            + "Parameters: INDEX (must be a positive integer),SUBJECT,MESSAGE "
            + "[Email Index,Subject,Message]\n"
            + "Example: " + COMMAND_WORD + " 1"
            + ",subjectmessage,textmessage";
    public static final String INCORRECT_EMAIL_FORMAT = "Incorrect Email format";
    public static final String CORRECT_EMAIL_FORMAT = "Email successfully sent to : ";
    public static final String HOST_EMAIL = "smtp.gmail.com";
    public static final String TEAM_EMAIL_ADDRESS = "cs2103f09b3@gmail.com";
    public static final String TEAM_EMAIL_PASSWORD = "pocketbook";
    public static final String SMTP_ENABLE = "mail.smtp.starttls.enable";
    public static final String TRUE = "true";
    public static final String SMTP_HOST = "mail.smtp.host";
    public static final String SMTP_PORT = "mail.smtp.port";
    public static final String PORT_NUMBER = "587";
    public static final String SMTP_AUTHENTICATION = "mail.smtp.auth";
    public static final String SMTP_STARTTLS = "mail.smtp.starttls.required";
    public static final String SMTP = "smtp";
    public static final String LINE_SEPARATOR = "line.separator";
    public static final String TEAM_SIGNATURE_MESSAGE = "This is a generated mail provided by CS2103F09B3 Team.";
    public static final String INTERNET_CONNECTION_ERROR_MESSAGE = "Check if you are connected to a valid"
            + " internet connection";
    public final Index index;
    public final String subject;
    public final String message;

    private String host;
    private String user;
    private String pass;
    private String to;
    private String from;

    private Session mailSession;
    private Message composedMessage;

    public EmailCommand(Index index, String subject, String message) {
        requireNonNull(index);
        requireNonNull(subject);
        requireNonNull(message);
        this.index = index;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        CommandUtil.checksIndexSmallerThanList(lastShownList, index);

        ReadOnlyPerson personToEmail = lastShownList.get(index.getZeroBased());

        try {
            //Initial Login credentials
            generateInitialLoginCred(personToEmail);

            //Adds signature to user's text
            String messageText = teamSignatureGenerator();
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            //updates the properties of the session
            updateProperties(host, props);

            //Compose the message
            composeMethod(messageText, sessionDebug, props);

            //Sends the Message
            sendMessage(host, user, pass, mailSession, composedMessage);

        } catch (Exception ex) {
            throw new CommandException(INTERNET_CONNECTION_ERROR_MESSAGE);
        }
        return new CommandResult(CORRECT_EMAIL_FORMAT + personToEmail.getName());
    }

    /**
     * Creates the Email object
     *
     * @param messageText The message to be sent out
     * @param sessionDebug
     * @param props
     * @throws MessagingException
     */
    private void composeMethod(String messageText, boolean sessionDebug, Properties props) throws MessagingException {
        Provider provider = new com.sun.net.ssl.internal.ssl.Provider();
        java.security.Security.addProvider(provider);
        mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(sessionDebug);
        composedMessage = composeMessage(to, from, subject, messageText, mailSession);
    }

    /**
     *  Initialises basic sending credentials
     *
     *  @param personToEmail Person to recieve the email
     */
    private void generateInitialLoginCred(ReadOnlyPerson personToEmail) {
        host = HOST_EMAIL;
        user = TEAM_EMAIL_ADDRESS;
        pass = TEAM_EMAIL_PASSWORD;
        to = personToEmail.getEmail().toString();
        from = TEAM_EMAIL_ADDRESS;
    }

    /**
     * Creates initial properties of javaMail
     *
     * @param host DNS name of a server
     * @param props Properties of javamail API
     */
    private void updateProperties(String host, Properties props) {
        props.put(SMTP_ENABLE, TRUE);
        props.put(SMTP_HOST, host);
        props.put(SMTP_PORT, PORT_NUMBER);
        props.put(SMTP_AUTHENTICATION, TRUE);
        props.put(SMTP_STARTTLS, TRUE);
    }

    /**
     * Sends the message to the email chosen
     *
     * @param host DNS name of a server
     * @param user Email of CS2103F09B3 Team
     * @param pass Password of CS2103F09B3 Team
     * @param mailSession Contains properties and defaults used by Mail API
     * @param msg Message to be sent out
     * @throws MessagingException Invalid parameters used
     */
    private void sendMessage(String host, String user, String pass, Session mailSession,
                             Message msg) throws MessagingException {
        assert host == HOST_EMAIL;
        assert user == TEAM_EMAIL_ADDRESS;
        assert pass == TEAM_EMAIL_PASSWORD;
        Transport transport = mailSession.getTransport(SMTP);
        transport.connect(host, user, pass);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

    /**
     * Creates the message using the valid parameters.
     *
     * @param to Email of person to send
     * @param from Email of Sender
     * @param subject Message to be attached in subject of Email
     * @param messageText Message to be attached in body of Email
     * @param mailSession Contains properties and defaults used by Mail API
     * @return Message compressed into Message objet
     * @throws MessagingException Invalid parameters used in composing Email
     */
    private Message composeMessage(String to, String from, String subject, String messageText,
                                   Session mailSession) throws MessagingException {
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(messageText);
        return msg;
    }

    /**
     * Creates the signature at the end of the email provided by CS2103F03B3 Team
     *
     * @return New message attached with signature
     */
    private String teamSignatureGenerator() {
        String newLine = EMPTY_STRING;
        for (int i = INDEX_ZERO; i < INDEX_FIVE; i++) {
            newLine += System.getProperty(LINE_SEPARATOR);
        }
        return this.message + newLine + TEAM_SIGNATURE_MESSAGE;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof EmailCommand)) {
            return false;
        }

        // state check
        EmailCommand e = (EmailCommand) other;
        return index.equals(e.index) && subject.equals(e.subject) && message.equals(e.message);
    }
}
