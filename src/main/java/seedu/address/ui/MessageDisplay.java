package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EmailDraftChangedEvent;

//@@author awarenessxz
/**
 * A ui for the display of the current email draft
 */
public class MessageDisplay extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "MessageDisplay.fxml";

    private final StringProperty messageDisplay = new SimpleStringProperty("");
    private final StringProperty recipientsDisplay = new SimpleStringProperty("");
    private final StringProperty subjectDisplay = new SimpleStringProperty("");

    @FXML
    private TextArea messageArea;
    @FXML
    private TextArea recipientsArea;
    @FXML
    private TextArea subjectArea;


    public MessageDisplay() {
        super(FXML);

        messageArea.setWrapText(true);
        recipientsArea.setWrapText(true);
        subjectArea.setWrapText(true);

        messageArea.textProperty().bind(messageDisplay);
        recipientsArea.textProperty().bind(recipientsDisplay);
        subjectArea.textProperty().bind(subjectDisplay);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleEmailDraftChangedEvent(EmailDraftChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageDisplay.setValue(event.message.getMessage());
                recipientsDisplay.setValue(event.message.getRecipientsEmailtoString());
                subjectDisplay.setValue(event.message.getSubject());
            }
        });
    }
}
