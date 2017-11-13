package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.external.SendEmailRequestEvent;
import seedu.address.external.EmailManager;

//@@author ReneeSeet

/**
 * The Email Panel of the App.
 */

public class EmailPanel extends UiPart<Region> {
    private static final String FXML = "EmailPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(EmailPanel.class);
    private static final String DELIMITER = ";";
    private static final String LOGGER_SEND_BUTTON_CLICKED = "EMAIL SEND BUTTON CLICKED";

    @FXML
    private TextField emailSubjectBox;
    @FXML
    private TextField recipientsBox;
    @FXML
    private TextArea emailMessage;
    @FXML
    private Button sendButton;

    public  EmailPanel(ArrayList<String> emailList) {
        super(FXML);
        EmailManager.init();
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    sendEmail();
                logger.info(LOGGER_SEND_BUTTON_CLICKED);
            }
            });
        String recipients = appendEmails(emailList);
        recipientsBox.setText(recipients);
        registerAsAnEventHandler(this);
    }

    /**
     * Method returns a string to be displayed in recipientBox
     */
    private String appendEmails(ArrayList<String> emails) {
        StringBuilder emailList = new StringBuilder();
        for (String s: emails) {
            emailList.append(s).append(DELIMITER);
        }
        return emailList.toString();
    }
    /**
     * Method posts SendEmailRequestEvent
     */
    private void sendEmail() {
        String subject = emailSubjectBox.getText();
        String message = emailMessage.getText();
        String[] recipients = recipientsBox.getText().split(DELIMITER);
        EventsCenter.getInstance().post(new SendEmailRequestEvent(subject, message, recipients));
    }
}
