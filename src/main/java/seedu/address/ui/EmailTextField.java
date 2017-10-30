package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
//@@author blaqkrow
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's name.
 */
public class EmailTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EmailTextField.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private TextField emailTextField;

    public EmailTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    public String getEmailTextField() {
        return emailTextField.getText();
    }

    public void setEmailTextField(String text) {
        emailTextField.setText(text);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setEmailTextField(event.getNewSelection().person.getEmail().toString());
    }


}

