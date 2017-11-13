package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.person.ReadOnlyPerson;

//@@author nelsonqyj
/**
 * Pop-up to show a contact's detail
 */
public class PersonWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(PersonWindow.class);
    private static final String ICON = "/images/contacts_icon.png";
    private static final String FXML = "PersonWindow.fxml";
    private static final String TITLE = "Contact Details";

    private final Stage dialogStage;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label email;



    public PersonWindow(ObservableList<ReadOnlyPerson> list, PersonCard personCard) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaxHeight(600);
        dialogStage.setMaxWidth(1000);
        dialogStage.setX(475);
        dialogStage.setY(300);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        name.setText("Name: " + list.get(personCard.getZeroBasedIndex()).getName().toString());
        phone.setText("Phone Number: " + list.get(personCard.getZeroBasedIndex()).getPhone().toString());
        address.setText("Address: " + list.get(personCard.getZeroBasedIndex()).getAddress().toString());
        email.setText("Email: " + list.get(personCard.getZeroBasedIndex()).getEmail().toString());
    }

    /**
     * Shows the Person window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing person page about the application.");
        dialogStage.showAndWait();
    }

    public void close() {
        dialogStage.close();
    }

    @FXML
    private void handleExit () {
        dialogStage.close();
    }

}
