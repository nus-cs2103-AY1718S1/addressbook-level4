package seedu.address.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewPersonInfoEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author eldonng
/**
 * Shows the Person's full contact information
 */
public class PersonInfo extends UiPart<Region> {

    private static final String FXML = "PersonInfoPanel.fxml";

    private static final Color[] colors = {Color.BLUE, Color.BROWN, Color.GREEN, Color.RED, Color.YELLOW, Color.PURPLE,
        Color.ORANGE, Color.CHOCOLATE, Color.AQUAMARINE, Color.INDIGO, Color.GRAY};

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObjectProperty<HashMap<String, String>> colourMap;

    @FXML
    private Circle circle;
    @FXML
    private Text initial;
    @FXML
    private Text name;
    @FXML
    private Text phone;
    @FXML
    private Label emailHeader;
    @FXML
    private Label addressHeader;
    @FXML
    private Label birthdayHeader;
    @FXML
    private Label tagsHeader;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label birthday;
    @FXML
    private FlowPane tags;

    public PersonInfo(HashMap<String, String> colourMap) {
        super(FXML);

        this.colourMap = new SimpleObjectProperty<>(colourMap);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads default welcome page
     */
    private void loadDefaultPage() {
        circle.setRadius(70);
        initial.setText("C");
        name.setText("Welcome to Circles!");
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(person);
    }

    /**
     * Loads the contact information of the specific person
     * @param person
     */
    public void loadPage(ReadOnlyPerson person) {
        circle.setRadius(70);
        initial.setText(person.getName().fullName.substring(0, 1));
        circle.setFill(colors[initial.getText().hashCode() % colors.length]);
        emailHeader.setText("Email:");
        addressHeader.setText("Address:");
        birthdayHeader.setText("Birthday: ");
        tagsHeader.setText("Tags:");
        bindListeners(person);

    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        person.tagProperty().addListener(((observable, oldValue, newValue) -> initTags(person)));
        colourMap.addListener(((observable, oldValue, newValue) -> initTags(person)));
        initTags(person);
    }


    /**
     * Initializes tags for the person
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            setTagColour(tagLabel, tag);
            tags.getChildren().add(tagLabel);
        });
    }

    private void setTagColour(Label tagLabel, Tag tag) {
        if (colourProperty().containsKey(tag.tagName)) {
            tagLabel.setStyle("-fx-background-color: " + colourProperty().get(tag.tagName));
        } else {
            tagLabel.setStyle(null);
        }
    }

    private HashMap<String, String> colourProperty() {
        return colourMap.get();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }

    @Subscribe
    private void handleNewPersonInfoEvent(NewPersonInfoEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getPerson());
    }
}
