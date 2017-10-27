package seedu.address.ui;
import java.util.HashMap;
import java.util.Random;

import com.google.common.eventbus.Subscribe;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class ExtendedPersonDetails extends UiPart<Region>{

    private static final String FXML = "ExtendDetailsPerson.fxml";
    private static String[] colors = { "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
    private final Logger logger = LogsCenter.getLogger(this.getClass());
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
    private Label birthday;
    @FXML
    private Label age;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView imageView;
    
    public ExtendedPersonDetails() {
        super(FXML);
        registerAsAnEventHandler(this);
    }
    private void loadPersonDetails(ReadOnlyPerson person) {
        initTags(person);
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        age.textProperty().bind(Bindings.convert(person.ageProperty()));
        setImage(person);
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    private void setImage(ReadOnlyPerson person) {
        String url = person.getPhoto().getUrl();
        if (!url.equals("")) { //if url is not empty, sets the image that overrides the default photo.
            Image image = new Image(url);
            imageView.setImage(image);
        } else {
            imageView.setImage(null);
        }
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }


}
