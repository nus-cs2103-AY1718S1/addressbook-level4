package seedu.address.ui;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonSideCardRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author yewshengkai
/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonSideCard extends UiPart<Region> {

    private static final String FXML = "PersonSideCard.fxml";
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private final Logger logger = LogsCenter.getLogger(PersonSideCard.class);

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label homepage;
    @FXML
    private Label remark;
    @FXML
    private ImageView avatar;

    public PersonSideCard() {
        super(FXML);
        registerAsAnEventHandler(this);
        getRoot().setManaged(false);
        getRoot().setOpacity(0);

    }
    //@@author

    //@@author yewshengkai-reused
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            int multiplier = 1;
            int asciiSum = (tagValue.hashCode() > 1) ? tagValue.hashCode() : tagValue.hashCode() * -1;

            int colorRed = asciiSum % 256;
            int colorGreen = (asciiSum / 2) % 256;
            int colorBlue = (asciiSum / 3) % 256;
            double luma = 0.2126 * colorRed + 0.7152 * colorGreen + 0.0722 * colorBlue; // per ITU-R BT.709
            while (luma < 128) {
                asciiSum = (asciiSum / multiplier) * ++multiplier;
                colorRed = asciiSum % 256;
                colorGreen = (asciiSum / 2) % 256;
                colorBlue = (asciiSum / 3) % 256;
                luma = 0.2126 * colorRed + 0.7152 * colorGreen + 0.0722 * colorBlue; // per ITU-R BT.709
            }
            String colorString = String.format("#%02x%02x%02x", colorRed, colorGreen, colorBlue);
            tagColors.put(tagValue, colorString);
        }

        return tagColors.get(tagValue);
    }
    //@@author

    //@@author yewshengkai
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        tags.getChildren().clear();
        initTags(person);
        homepage.textProperty().bind(Bindings.convert(person.homepageProperty()));

        initImage(person);
    }

    //@@author
    /**
     * Binds the correct image to the person.
     * If url is "", default display picture will be assigned, else image from URL will be assigned
     */
    private void initImage(ReadOnlyPerson person) {
        String path = person.getAvatar().toString();
        Image image;
        if (!"".equals(path)) {   // not default image
            File file = new File(path);
            image = new Image(file.toURI().toString());
            avatar.setImage(image);
            avatar.setFitHeight(90);
            avatar.setPreserveRatio(true);
            avatar.setCache(true);
        } else { //@author yewshengkai
            image = new Image("images/default_avatar.png");
            avatar.setImage(image);
            avatar.setFitHeight(90);
            avatar.setPreserveRatio(true);
            avatar.setCache(true);
        }
    }
    //@@author

    /**
     * Tags coloring
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    //@@author yewshengkai
    /**
     * Make PersonSideCard Panel visible or invisible to save a portion of GUi space for WebView
     */
    private void showSidePanel(boolean isVisible) {
        getRoot().setManaged(isVisible);
        int opacityLevel;
        if (isVisible) {
            opacityLevel =  100;
        } else {
            opacityLevel = 0;
        }
        getRoot().setOpacity(opacityLevel);
    }

    @Subscribe
     private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        bindListeners(event.getNewSelection().person);
    }

    @Subscribe
    private void handlePersonSideCardPanelChangedEvent(PersonSideCardRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showSidePanel(event.isVisible);
    }
}
