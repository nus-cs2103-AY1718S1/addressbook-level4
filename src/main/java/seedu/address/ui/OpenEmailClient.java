package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

//@@author blaqkrow
/**
 * Handles the opening of email client
 */
public class OpenEmailClient {
    private Desktop desktop = Desktop.getDesktop();
    private String mailTo;

    /**
     * Handles the opening of email client
     */
    public OpenEmailClient(String mailTo) {
        this.mailTo = mailTo.trim();
    }

    public void setMail (String m) {
        mailTo = m;
    }
    /**
     * Handles the sending mail
     */
    public void sendMail () throws IOException {

        URI uri = URI.create("mailto:" + this.mailTo);
        desktop.mail(uri);

    }
}
