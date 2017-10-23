package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel that displays the additional details of a Person
 */
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
        remark.setText(person.getRemark().toString());
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Tag(tag.tagName).getRoot()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        try {
            Image image = AppUtil.getImage(event.getNewSelection().person.getAvatar().value);
            avatar.setImage(image);
        } catch (NullPointerException e) {
            BufferedImage loadedImage = event.getNewSelection().person.getAvatar().loadedImage;

            if(loadedImage == null) {
                Image image = AppUtil.getImage(Avatar.AVATAR_DEFAULT_LOCATION);
                avatar.setImage(image);
            } else {
                avatar.setImage(SwingFXUtils.toFXImage(event.getNewSelection().person.getAvatar().loadedImage, null));
            }

        }

        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
}
