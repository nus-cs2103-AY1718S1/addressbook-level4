package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.model.event.Date;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.SchEmail;
import seedu.address.model.person.Website;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    //@@author itsdickson
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (isCommandFailure(event.message)) {
            setStyleToIndicateCommandFailure();
        } else {
            setStyleToDefault();
        }
        Platform.runLater(() -> displayed.setValue(event.message));
    }

    /**
     * Sets the result display style to use the default style.
     */
    private void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the result display style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }
    //@@author

    /**
     * Checks if the event from the result is a command failure.
     */
    private boolean isCommandFailure(String message) {
        return (message.equals(Messages.MESSAGE_UNKNOWN_COMMAND)
                || message.equals(Messages.MESSAGE_INVALID_DISPLAYED_INDEX)
                || message.equals(Messages.MESSAGE_INVALID_LOCATION)
                || message.equals(Messages.MESSAGE_INVALID_SWITCH)
                || message.equals(Messages.MESSAGE_INVALID_WEBSITE)
                || message.equals(Name.MESSAGE_NAME_CONSTRAINTS)
                || message.equals(Phone.MESSAGE_PHONE_CONSTRAINTS)
                || message.equals(HomeNumber.MESSAGE_HOME_NUMBER_CONSTRAINTS)
                || message.equals(Email.MESSAGE_EMAIL_CONSTRAINTS)
                || message.equals(SchEmail.MESSAGE_SCH_EMAIL_CONSTRAINTS)
                || message.equals(Address.MESSAGE_ADDRESS_CONSTRAINTS)
                || message.equals(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS)
                || message.equals(Website.MESSAGE_WEBSITE_CONSTRAINTS)
                || message.equals(Date.MESSAGE_DATE_CONSTRAINTS))
                || message.contains(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ""));
    }

}
