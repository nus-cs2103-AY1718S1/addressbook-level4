package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoggingCommand;
import seedu.address.logic.commands.exceptions.CommandException;
//@@author blaqkrow
/**
 * The UI component that is responsible for editing selected contacts in the PersonListPanel.
 */
public class EditButton extends UiPart<Region> {
    public static final String NAME_ERROR = "NAME_ERROR";
    public static final String EMAIL_ERROR = "EMAIL_ERROR";
    public static final String PHONE_ERROR = "PHONE_ERROR";
    public static final String TAG_ERROR = "TAG_ERROR";
    public static final String VALIDATION_SUCCESS = "VALIDATION_SUCCESS";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EditButton.fxml";
    private static NameTextField nameTextField;
    private static PhoneTextField phoneTextField;
    private static EmailTextField emailTextField;
    private static AddressTextField addressTextFieldTextField;
    private static TagTextField tagTextField;
    private static int selectedIndex;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private LoggingCommand lg = new LoggingCommand();

    @FXML
    private Button editButton;
    public EditButton(Logic logic, NameTextField ntf, PhoneTextField ptf,
                      EmailTextField etf, AddressTextField atf, TagTextField ttf) {
        super(FXML);
        this.logic = logic;
        this.nameTextField = ntf;
        this.phoneTextField = ptf;
        this.emailTextField = etf;
        this.addressTextFieldTextField = atf;
        this.tagTextField = ttf;
        registerAsAnEventHandler(this);
    }

    /**
     * Handles the edit button pressed event.
     */
    @FXML
    private void handleEditButtonPressed() throws CommandException, IllegalValueException, IOException {
        StringBuilder command = new StringBuilder();
        CommandResult commandResult;
        Alert alert;
        String checkInputResult = checkInput(nameTextField.getNameTextField(), phoneTextField.getPhoneTextField(),
                emailTextField.getEmailTextField(), addressTextFieldTextField.getAddressTextField(),
                tagTextField.getTagTextArea());

        if (checkInputResult.equals(NAME_ERROR)) {
            nameTextField.getObject().setStyle("-fx-text-inner-color: red;");
            alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid name!",
                    ButtonType.OK);
            alert.showAndWait();
            throw new IllegalValueException("Name contains illegal characters!");
        }
        if (checkInputResult.equals(PHONE_ERROR)) {
            phoneTextField.getObject().setStyle("-fx-text-inner-color: red;");
            alert = new Alert(Alert.AlertType.ERROR, "Please enter phone no. without character values!",
                    ButtonType.OK);
            alert.showAndWait();
            throw new IllegalValueException("Phone number contains illegal characters!");
        }
        if (checkInputResult.equals(EMAIL_ERROR)) {
            emailTextField.getObject().setStyle("-fx-text-inner-color: red;");
            alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid email address!",
                    ButtonType.OK);
            alert.showAndWait();
            throw new IllegalValueException("Email contains illegal characters!");
        }
        if (checkInputResult.equals(TAG_ERROR)) {
            tagTextField.getObject().setStyle("-fx-text-inner-color: red;");
            alert = new Alert(Alert.AlertType.ERROR, "Please enter a tag in the"
                    + " form: 'tag1','tag2','tag3',... Tags should contain Alphanumeric characters only!",
                    ButtonType.OK);
            alert.showAndWait();
            throw new IllegalValueException("Tags contains illegal characters!");
        } else {
            nameTextField.getObject().setStyle("-fx-text-inner-color: black;");
            phoneTextField.getObject().setStyle("-fx-text-inner-color: black;");
            emailTextField.getObject().setStyle("-fx-text-inner-color: black;");
            tagTextField.getObject().setStyle("-fx-text-inner-color: black;");
            command.append("edit " + getSelectedIndex() + " n/"
                    + nameTextField.getNameTextField() + " p/" + phoneTextField.getPhoneTextField() + " e/"
                    + emailTextField.getEmailTextField()
                    + " a/" + addressTextFieldTextField.getAddressTextField());
            String tagTextArea = tagTextField.getTagTextArea();
            String[] tagSplit = tagTextArea.split(",");
            for (String s : tagSplit) {
                command.append(" t/" + s.trim());
            }
            commandResult = logic.execute(command.toString());
            logger.info("Result: " + commandResult.feedbackToUser);
            lg.keepLog(commandResult.feedbackToUser, "Edited");
        }
    }
    /**
     * Handles checking of content passed into the form
     * @param name the name entered in nameTextField
     * @param phone the name entered in phoneTextField
     * @param email the name entered in emailTextField
     * @param address the name entered in addressTextField
     * @param tag the name entered in tagTextArea
     * @return the corresponding format error, else if no error, return success
     */
    public static String checkInput(String name, String phone, String email, String address, String tag) {
        if (name.matches(".*\\d+.*") || name.isEmpty()) {
            return NAME_ERROR;
        }
        if (!phone.matches("[0-9]+")) {
            return PHONE_ERROR;
        }
        if (!email.contains("@") || !email.contains(".")) {
            return EMAIL_ERROR;
        }
        //check tag doesnt end with a special character
        String[] tagSplit = tag.split(",");
        for (int i = 0; i < tagSplit.length; i++) {
            if (!tagSplit[i].matches("[a-zA-Z0-9]*")) {
                return TAG_ERROR;
            }
        }
        return VALIDATION_SUCCESS;
    }
    private void setSelectedIndex(int i) {
        selectedIndex = i;
    }

    private int getSelectedIndex() {
        return selectedIndex;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        int baseOneIndex = event.getSelectionIndex() + 1;
        setSelectedIndex(baseOneIndex);
    }
}
