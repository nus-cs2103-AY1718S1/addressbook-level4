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
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Profile Panel of the App.
 */
public class ProfilePanel extends UiPart<Region> {

    public static final String DEFAULT_MESSAGE = "Ain't Nobody here but us chickens!";
    private static final String FXML = "ProfilePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public ReadOnlyPerson person;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
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
        anchorPane.prefWidthProperty().bind(scrollPane.widthProperty());
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        this.person = person;
        bindListeners(person);
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
   private void loadDefaultPage() {
       name.setText(DEFAULT_MESSAGE);
       phone.setText("");
       address.setText("");
       dob.setText("");
       email.setText("");
    }


    private void bindListeners(ReadOnlyPerson person) {

        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        dob.textProperty().bind(Bindings.convert(person.dobProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
    }

    public String getName() { return name.getText(); }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
       loadPersonPage(event.getNewSelection().person);
    }
}
