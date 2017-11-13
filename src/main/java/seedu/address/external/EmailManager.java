package seedu.address.external;

import static seedu.address.commons.core.Messages.MESSAGE_EMAIL_SUCCESS;

import java.io.IOException;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.api.services.gmail.Gmail;
import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.external.SendEmailRequestEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

//@@author ReneeSeet
/**
 * Follows Singleton and Facade design pattern,
 * For Application to interact with the Google Gmail service
 */

public class EmailManager {

    private static final Logger logger = LogsCenter.getLogger(EmailManager.class);
    private static final String DELIMITER = ";";
    private static final String LOGGER_EMAIL_SENT = "EMAIL SENT SUCCESS";
    private static final String LOGGER_EMAIL_IO_EXCEPTION = "EMAIL: IO EXCEPTION";
    private static final String LOGGER_EMAIL_MESSAGE_EXCEPTION = "EMAIL: MESSAGE EXCEPTION";

    private static EmailManager instance = null;

    protected EmailManager() {
        registerAsAnEventHandler(this);
    }

    /**
     * Creates an instance of the EmailManager and registers it as an event handler
     * @return The Singleton instance of the EmailManager
     */
    public static EmailManager init() {
        if (instance == null) {
            instance = new EmailManager();
            new GmailApi();
        }
        return instance;
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    @Subscribe
    public void handleSendEmailRequestEvent(SendEmailRequestEvent event) throws IOException, MessagingException {
        // Build a new authorized API client service.
        try {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            Gmail service = GmailApi.getGmailService();
            String user = "me";
            String[] recipients = event.getRecipients();
            for (String s : recipients) {
                MimeMessage email = GmailApi.createEmail(s, user, event.getSubject(), event.getMessage());
                GmailApi.sendMessage(service, user, email);
                logger.info(LOGGER_EMAIL_SENT);
            }
            EventsCenter.getInstance().post(new NewResultAvailableEvent(MESSAGE_EMAIL_SUCCESS));
        } catch (IOException e) {
            logger.info(LOGGER_EMAIL_IO_EXCEPTION);
        } catch (MessagingException d) {
            logger.info(LOGGER_EMAIL_MESSAGE_EXCEPTION);
        }
    }
}
