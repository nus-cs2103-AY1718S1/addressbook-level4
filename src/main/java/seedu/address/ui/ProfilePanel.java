package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.commons.events.ui.SwitchPanelRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Profile Panel of the App.
 */
public class ProfilePanel extends UiPart<Region> {

    public static final String DEFAULT_MESSAGE = "Ain't Nobody here but us chickens!";
    private static final String FXML = "ProfilePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane profilePanel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label dob;
    @FXML
    private Label email;

    public ProfilePanel() {
        super(FXML);
        scrollPane.setFitToWidth(true);
        profilePanel.prefWidthProperty().bind(scrollPane.widthProperty());
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Updates selected person
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        bindListeners(person);
    }

    /**
     * Load default page with empty fields and default message
     */
    private void loadDefaultPage() {
        name.setText(DEFAULT_MESSAGE);
        phone.setText("");
        address.setText("");
        dob.setText("");
        email.setText("");
    }

    /**
     * To be called everytime a new person is selected and bind all information for real-time update
     * @param person
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        dob.textProperty().bind(Bindings.convert(person.dobProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
    }

    @Subscribe
    private void handlePersonNameClickedEvent(PersonNameClickedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getPerson());

        raise(new SwitchPanelRequestEvent());
    }
}
