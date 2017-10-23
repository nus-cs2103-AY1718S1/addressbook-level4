package seedu.address.email;

import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.email.exceptions.EmailLoginInvalidException;
import seedu.address.email.exceptions.EmailMessageEmptyException;
import seedu.address.email.message.MessageDraft;

/*
 * Handles how email are sent out of the application.
 */
public class EmailManager extends ComponentManager implements Email {
    private static final Logger logger = LogsCenter.getLogger(EmailManager.class);
    private static final Pattern GMAIL_FORMAT = Pattern.compile("^[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(@gmail.com)$");

    private MessageDraft message;
    private String [] loginDetails;
    private String emailStatus;

    public EmailManager() {
        this.message = new MessageDraft();
        this.loginDetails = new String[0];
        this.emailStatus = "";
    }

    @Override
    public void composeEmail(MessageDraft message) {
        if(message.getSubject().isEmpty()) {
            message.setSubject(this.message.getSubject());
        }
        if(message.getMessage().isEmpty()) {
            message.setMessage(this.message.getMessage());
        }
        this.message = message;
        this.emailStatus = "drafted";
    }

    @Override
    public MessageDraft getEmailDraft() {
        return this.message;
    }

    @Override
    public String getEmailStatus() {
        return this.emailStatus;
    }

    @Override
    public void sendEmail() throws EmailLoginInvalidException, EmailMessageEmptyException {

        //Step 1. Verify that the email draft consists of message and subject
        if (!message.containsContent()) {
            //throw exception that user needs to enter message and subject to send email
            throw new EmailMessageEmptyException();
        }
        //Step 2. Verify that the user have logged in.
        if (!isUserLogin()) {
            //throw exception that user needs to enter login details to send email
            throw new EmailLoginInvalidException();
        } else {
            //Step3 . Verify that the user have logged in with a gmail account
            verifyUserEmailFormat();
        }

        //Step 4. set up the email Object
        prepEmail();


        //send out details

        //reset the email draft after email have been sent
        this.emailStatus = "sent";
        resetData();
    }

    @Override
    public void loginEmail(String [] loginDetails) {
        //replace login details and ignore if login details is omitted.
        if (loginDetails.length != 0 && loginDetails.length == 2) {
            //command entered with login prefix
            this.loginDetails = loginDetails;
        }
    }

    public boolean isUserLogin() {
        if(this.loginDetails.length != 2) {
            //The loginDetails empty
            return false;
        } else {
            return true;
        }
    }

    /** Verify if the user is using a gmail account **/
    private void verifyUserEmailFormat() throws EmailLoginInvalidException {
        if(this.loginDetails.length == 2) {
            final Matcher matcher = GMAIL_FORMAT.matcher(this.loginDetails[0].trim());
            if (!matcher.matches()) {
                throw new EmailLoginInvalidException();
            }
        }
    }

    /** Prepare Email to be send **/
    private void prepEmail() {
        final String username = loginDetails[0];
        final String password = loginDetails[1];

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message newMessage = new MimeMessage(session);
            newMessage.setFrom(new InternetAddress(username));
            newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("email@hotmail.com"));
            newMessage.setSubject(message.getSubject());
            newMessage.setText(message.getMessage());

            Transport.send(newMessage);
            System.out.println("message sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /** reset Email Draft Data **/
    private void resetData() {
        this.message = new MessageDraft();
        this.loginDetails = new String[0];
    }

}
