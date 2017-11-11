package seedu.address.ui;

import static seedu.address.model.person.Address.MESSAGE_ADDRESS_CONSTRAINTS;
import static seedu.address.model.person.Email.MESSAGE_EMAIL_CONSTRAINTS;
import static seedu.address.model.person.Name.MESSAGE_NAME_CONSTRAINTS;
import static seedu.address.model.person.Phone.MESSAGE_PHONE_CONSTRAINTS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.UserPersonChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UserPerson;
import seedu.address.model.person.weblink.WebLink;

//@@author bladerail
/**
 * Controller for the User Profile Window
 */
public class UserProfileWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(UserProfileWindow.class);
    private static final String FXML = "UserProfileWindow.fxml";
    private static final String TITLE = "User Profile";

    private final UserPerson userPerson;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField webLinkTextField;

    @FXML
    private Label statusLabel;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private final Stage stage;

    public UserProfileWindow(UserPerson userPerson) {
        super(FXML);
        registerAsAnEventHandler(this);

        Scene scene = new Scene(getRoot());
        stage = createDialogStage(TITLE, null, scene);
        statusLabel.setText("");
        this.userPerson = userPerson;
        nameTextField.setText(userPerson.getName().toString());
        emailTextField.setText(userPerson.getEmailAsText());
        phoneTextField.setText(userPerson.getPhone().toString());
        addressTextField.setText(userPerson.getAddress().toString());
        webLinkTextField.setText(userPerson.getWebLinksAsText());

        setAccelerators(scene);

    }

    /**
     * Sets accelerators for the UserProfileWindow
     * @param scene Current scene
     */
    private void setAccelerators(Scene scene) {
        scene.getAccelerators().put(KeyCombination.valueOf("ESC"), this::handleCancel);
        scene.getAccelerators().put(KeyCombination.valueOf("ENTER"), this::handleOk);

    }

    /**
     *
     */
    public void show() {
        logger.fine("Showing User Profile.");
        stage.showAndWait();
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }

    /**
     * Handles the OK button
     */
    @FXML
    private void handleOk() {
        try {
            updateUserPerson();
            raise(new UserPersonChangedEvent(userPerson));
            logger.fine("UserPerson updated via UserProfileWindow, saving");
            stage.close();
        } catch (Exception e) {
            logger.fine("Invalid UserPerson modification");
        }
    }

    @Subscribe
    private void handleUserPersonChangedEvent(UserPersonChangedEvent event) {
        logger.fine(LogsCenter.getEventHandlingLogMessage(event));
    }

    /**
     * Updates the user person
     */
    private void updateUserPerson() throws Exception {
        try {
            userPerson.setName(new Name(nameTextField.getText()));
        } catch (IllegalValueException e) {
            statusLabel.setText(MESSAGE_NAME_CONSTRAINTS);
            throw new Exception();
        }

        try {
            String[] emails = emailTextField.getText().split(", ");
            ArrayList<Email> emailList = new ArrayList<>();
            for (String curr : emails) {
                emailList.add(new Email(curr));
            }
            userPerson.setEmail(emailList);

        } catch (IllegalValueException e) {
            statusLabel.setText(MESSAGE_EMAIL_CONSTRAINTS);
            throw new Exception();
        }

        try {
            if (phoneTextField.getText().equals("")) {
                userPerson.setPhone(new Phone(null));
            } else {
                userPerson.setPhone(new Phone(phoneTextField.getText()));
            }
        } catch (IllegalValueException e) {
            statusLabel.setText(MESSAGE_PHONE_CONSTRAINTS);
            throw new Exception();
        }

        try {
            if (phoneTextField.getText().equals("")) {
                userPerson.setAddress(new Address(null));
            } else {
                userPerson.setAddress(new Address(addressTextField.getText()));
            }
        } catch (IllegalValueException e) {
            statusLabel.setText(MESSAGE_ADDRESS_CONSTRAINTS);
            throw new Exception();
        }

        try {
            String[] webLinks = webLinkTextField.getText().split(", ");

            Comparator<WebLink> comparator = Comparator.comparing(WebLink::toStringWebLink);

            Set<WebLink> webLinkSet = new TreeSet<WebLink>(comparator);

            for (String curr : webLinks) {
                webLinkSet.add(new WebLink(curr));
            }
            userPerson.setWebLinks(webLinkSet);
        } catch (IllegalValueException e) {
            statusLabel.setText("Please input a valid webLink");
            throw new Exception();
        } catch (ClassCastException e) {
            statusLabel.setText("Class cast exception");
            throw new Exception();
        }
    }
}
