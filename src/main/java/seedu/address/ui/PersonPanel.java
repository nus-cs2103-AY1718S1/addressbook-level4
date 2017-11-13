package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.TextToSpeech;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.ImageStorage;

/**
 * Panel that displays the additional details of a Person
 */

//@@author nicholaschuayunzhi
public class PersonPanel extends UiPart<Region> {

    private static final String FXML = "PersonPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private VBox panel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView avatar;

    public PersonPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Updates person details showcased on the panel
     */
    private void loadPersonDetails(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        email.setText(person.getEmail().toString());
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Tag(tag.tagName).getRoot()));
        //@@author hanselblack
        remark.setText(person.getRemark().toString());
        //Text to Speech
        new TextToSpeech(person.getName().fullName).speak();;
        //@@author
    }

    //@@author nicholaschuayunzhi
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {

        Image image = ImageStorage.getAvatar(event.getNewSelection().person.getAvatar().value);
        avatar.setImage(image);

        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
}
