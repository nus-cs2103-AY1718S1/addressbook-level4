package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.UserPersonChangedEvent;
import seedu.address.model.person.UserPerson;

/**
 * Controller for the User Profile Window
 */
public class UserProfileWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "UserProfileWindow.fxml";
    private static final String TITLE = "UserProfile";

    private UserPerson userPerson;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField addressTextField;

    private final Stage stage;

    public UserProfileWindow(UserPerson userPerson) {
        super(FXML);
        registerAsAnEventHandler(this);

        Scene scene = new Scene(getRoot());
        stage = createDialogStage(TITLE, null, scene);
        this.userPerson = userPerson;
        nameTextField.setText(userPerson.getName().toString());
        emailTextField.setText(userPerson.getEmail().toString());
        phoneTextField.setText(userPerson.getPhone().toString());
        addressTextField.setText(userPerson.getAddress().toString());

    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(UserPerson userPerson) {
        nameTextField.textProperty().bind(Bindings.convert(userPerson.nameProperty()));
        phoneTextField.textProperty().bind(Bindings.convert(userPerson.phoneProperty()));
        addressTextField.textProperty().bind(Bindings.convert(userPerson.addressProperty()));
        emailTextField.textProperty().bind(Bindings.convert(userPerson.emailProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailedPersonCard)) {
            return false;
        }

        // state check
        UserProfileWindow otherUserProfileWindow = (UserProfileWindow) other;
        return userPerson.equals(otherUserProfileWindow.userPerson);
    }

    /**
     *
     */
    public void show() {
        logger.fine("Showing User Profile.");
        stage.showAndWait();
    }

    @FXML
    void handleCancel(){
        stage.close();
    }

    @FXML
    void handleOK(){
        //raise(new UserPersonChangedEvent(userPerson));
        stage.close();
    }

    @Subscribe
    private void handleUserPersonChangedEvent(UserPersonChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setUserPerson(event.getUserPerson());
        //bindListeners(userPerson);
    }

    void setUserPerson(UserPerson userPerson){
        this.userPerson = userPerson;

    }
}
