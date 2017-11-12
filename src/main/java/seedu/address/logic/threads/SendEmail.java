package seedu.address.logic.threads;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author hanselblack
/**
 * Creates a new thread to send email, this is to prevent UI thread from freezing
 */
public class SendEmail extends Thread {

    private String recipientEmail;
    private ReadOnlyPerson person;

    private final Logger logger = LogsCenter.getLogger(SendEmail.class);

    public SendEmail(String recipientEmail, ReadOnlyPerson person) {
        this.recipientEmail = recipientEmail;
        this.person = person;
    }

    /**
     * Opens buffer input stream to stream radio source
     */
    public void run() {
        // Sender's email ID needs to be mentioned
        String senderEmail = "unifycs2103@gmail.com";
        String password = "CS2103CS2103";
        // For Gmail host
        String host = "smtp.gmail.com";
        // Get system properties
        Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, password);
                    }
                }
        );
        String name = person.getName().fullName;
        String phone = person.getPhone().toString();
        String address = person.getAddress().toString();
        String email = person.getEmail().toString();
        String remark  = person.getRemark().toString();
        String tags = "";
        for (Tag tag :  person.getTags()) {
            tags += tag.tagName + " ";
        }
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(senderEmail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject("Unify: Address Book: " + name + " Exported Data");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            //set the actual message
            messageBodyPart.setContent("<img src='https://github.com/CS2103AUG2017-W11-B4/"
                            + "main/blob/master/docs/images/email_header.png?raw=true'/>"
                            + "<br/><br/><br/><img src='https://github.com/CS2103AUG2017-W11-B4/"
                            + "main/blob/master/docs/images/email_subheader.png?raw=true'/>"
                            + "<br/><br/>"
                            + "<table>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Name</b></td><td>" + name + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Phone</b></td><td>" + phone + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Address</b></td><td>" + address + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Email</b></td><td>" + email + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Remark</b></td><td>" + remark + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Tags</b></td><td>" + tags + "</td></tr>"
                            + "</table>",
                    "text/html");

            Multipart multipart = new MimeMultipart();

            //set text message part
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException msg) {
            logger.info(msg.toString());
        }
    }
}
