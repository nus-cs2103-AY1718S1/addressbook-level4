package seedu.address.email;

import seedu.address.commons.exceptions.NotAnEmailException;
import seedu.address.model.person.Address;

import javax.mail.*;
import javax.mail.internet.AddressException;
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
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("phungtuanhoang1996@gmail.com", "zasxcdfv");
            }
        });

        Store store = session.getStore("imaps");
        try {
            store.connect("imap.googlemail.com","phungtuanhoang1996@gmail.com", "zasxcdfv");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        Message emailMessage = new MimeMessage(session);

        try {
            emailMessage.setFrom(new InternetAddress("phungtuanhoang1996@gmail.com"));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse("phungtuanhoang1996@yahoo.com"));
            emailMessage.setSubject("Test");
            emailMessage.setText("Test");

            Transport.send(emailMessage);
        } catch (AddressException e) {

        } catch (MessagingException e) {

        }



        /*
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
        */
        return null;
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
