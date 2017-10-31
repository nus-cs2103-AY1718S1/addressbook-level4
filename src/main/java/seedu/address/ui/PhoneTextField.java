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
public class PhoneTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "PhoneTextField.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private int selectedIndex;

    @FXML
    private TextField phoneTextField;

    public PhoneTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    public String getPhoneTextField() {
        return phoneTextField.getText();
    }

    public void setPhoneTextField(String text) {
        phoneTextField.setText(text);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setPhoneTextField(event.getNewSelection().person.getPhone().toString());
    }


}
