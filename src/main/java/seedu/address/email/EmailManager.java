package seedu.address.email;

import seedu.address.email.exceptions.EmailSendFailedException;
import seedu.address.email.exceptions.LoginFailedException;
import seedu.address.email.exceptions.NotAnEmailException;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Hashtable;
import java.util.Properties;

public class EmailManager implements Email {
    public static final String MESSAGE_LOGIN_FAILED = "It could be one of the following reasons: \n"
            + "1. Your Internet connection is not working\n"
            + "2. Your email and password combination is not correct\n"
            + "3. Allow less secure apps is not enable in your Gmail account";
    private String currentEmail;
    private String currentEmailProvider;
    private Authenticator currentAuthenticator;
    private Properties properties_SMTP;
    private Properties properties_IMAP;
    private Hashtable<String, String> hostHashTable;

    public EmailManager() {
        currentEmail = null;
        currentEmailProvider = null;
        currentAuthenticator = null;
        properties_SMTP = new Properties();
        properties_IMAP = System.getProperties();

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

        Session session = Session.getInstance(properties_SMTP, newAuthenticator);

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
    public void sendEmail(String[] recipients, String subject, String body) throws NotAnEmailException, EmailSendFailedException {
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
            Session session = Session.getInstance(properties_SMTP, this.currentAuthenticator);

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

    private void init(String email) throws LoginFailedException {
        String domain = (((email.split("@"))[1]).split("\\."))[0];

        switch (domain) {
            case "gmail":
                this.currentEmailProvider = "gmail";
                this.properties_IMAP.setProperty("mail.store.protocol", "imaps");
                this.properties_SMTP.put("mail.smtp.auth", "true");
                this.properties_SMTP.put("mail.smtp.starttls.enable", "true");
                this.properties_SMTP.put("mail.smtp.host", "smtp.gmail.com");
                this.properties_SMTP.put("mail.smtp.port", "587");
                break;
            case "yahoo":
                this.currentEmailProvider = "yahoo";
                this.properties_IMAP.setProperty("mail.store.protocol", "imaps");
                this.properties_SMTP.put("mail.smtp.auth", "true");
                this.properties_SMTP.put("mail.smtp.starttls.enable", "true");
                this.properties_SMTP.put("mail.smtp.host", "smtp.yahoo.com");
                this.properties_SMTP.put("mail.smtp.port", "465");
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
