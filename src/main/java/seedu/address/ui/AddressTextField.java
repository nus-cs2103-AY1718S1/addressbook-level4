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
public class AddressTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "AddressTextField.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private TextField addressTextField;

    public AddressTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    public String getAddressTextField() {
        return addressTextField.getText();
    }

    public void setAddressTextField(String text) {
        addressTextField.setText(text);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setAddressTextField(event.getNewSelection().person.getAddress().toString());
    }


}

