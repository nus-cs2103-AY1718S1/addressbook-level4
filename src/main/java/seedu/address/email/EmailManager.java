package seedu.address.email;

import seedu.address.commons.exceptions.NotAnEmailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.channels.SeekableByteChannel;
import java.util.Properties;

public class EmailManager implements Email {
    private Session currentSession;
    private String currentEmail;

    public EmailManager() {
        currentSession = null;
        currentEmail = null;
    }

    @Override
    public Session login(String email, String password) throws NotAnEmailException, NoSuchProviderException {
        Properties properties = new Properties();
        properties.put("mail.pop3s.host", "pop.gmail.com");
        properties.put("mail.pop3s.port", "995");
        properties.put("mail.pop3s.starttls.enable", true);

        Session emailSession = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        currentSession = emailSession;
        currentEmail = email;

        Store store = emailSession.getStore("pop3s");

        try {
            store.connect();
        } catch (MessagingException e) {
            System.out.println("Exception");
        }

        return emailSession;
    }

    @Override
    public void sendEmail(Session session, String recipient, String title, String message) throws NotAnEmailException, MessagingException {
        Message emailMessage = new MimeMessage(session);

        emailMessage.setFrom(new InternetAddress(currentEmail));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        emailMessage.setSubject(title);
        emailMessage.setText(message);

        Transport.send(emailMessage);
    }

    @Override
    public Session getSession() {
        return this.currentSession;
    }

    @Override
    public String getEmail() {
        return null;
    }
}
