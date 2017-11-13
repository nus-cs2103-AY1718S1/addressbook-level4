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
import seedu.address.commons.events.external.SendSmsRequestEvent;
import seedu.address.external.sms.SmsManager;

//@@author zameschua
/**
 * The Sms Panel of the App
 * Appears on the main page after typing the "sms" command
 */
public class SmsPanel extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "SmsPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(SmsPanel.class);

    @FXML
    private TextField recipientsBox;
    @FXML
    private TextArea smsMessage;
    @FXML
    private Button sendButton;

    public SmsPanel(ArrayList<String> phoneNumbersList) {
        super(FXML);

        SmsManager.init();
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sendSms();
                logger.info("SEND BUTTON CLICKED");
            }
        });
        String recipients = appendPhoneNumbers(phoneNumbersList);
        recipientsBox.setText(recipients);
    }

    /**
     * Method returns a string of phone numbers, joined by semicolons `;`
     * to be displayed in recipientBox
     */
    private String appendPhoneNumbers(ArrayList<String> phoneNumbers) {
        StringBuilder phoneNumbersStringBuilder = new StringBuilder();
        for (String phoneNumber: phoneNumbers) {
            phoneNumbersStringBuilder.append(phoneNumber).append(";");
        }
        return phoneNumbersStringBuilder.toString();
    }

    /**
     * Method posts {@link SendSmsRequestEvent}
     */
    private void sendSms() {
        String message = smsMessage.getText();
        String[] recipients = recipientsBox.getText().split(";");
        EventsCenter.getInstance().post(new SendSmsRequestEvent(message, recipients));
    }
}
