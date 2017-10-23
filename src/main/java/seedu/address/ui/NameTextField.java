package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

/**
 * The UI component that is responsible for saving and displaying the currently selected contact's name.
 */
public class NameTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "NameTextField.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private TextField nameTextField;

    public NameTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }
    public String getNameTextField() {
        return nameTextField.getText();
    }

    public void setNameTextField(String text) {
        nameTextField.setText(text);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setNameTextField(event.getNewSelection().person.getName().toString());
    }
}
