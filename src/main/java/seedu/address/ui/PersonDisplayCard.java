package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.commands.PersonDeletedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Display Panel of the App.
 */
public class PersonDisplayCard extends UiPart<Region> {

    private static final String FXML = "PersonDetailsCard.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label nameLarge;
    @FXML
    private Label phoneLarge;
    @FXML
    private Label birthdayLarge;
    @FXML
    private Label organisationLarge;
    @FXML
    private Label emailLarge;
    @FXML
    private Label remarkLarge;
    @FXML
    private FlowPane tagsLarge;

    public PersonDisplayCard() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    private void loadPersonDetails(ReadOnlyPerson person) {
        this.person = person;
        initTags(person);
        bindListeners(person);
    }

    //@@author frozventus
    /**
     * Resets details shown
     */
    private void resetPersonDetails() {
        nameLarge.textProperty().unbind();
        nameLarge.textProperty().setValue("");
        phoneLarge.textProperty().unbind();
        phoneLarge.textProperty().setValue("");
        birthdayLarge.textProperty().unbind();
        birthdayLarge.textProperty().setValue("");
        emailLarge.textProperty().unbind();
        emailLarge.textProperty().setValue("");
        organisationLarge.textProperty().unbind();
        organisationLarge.textProperty().setValue("");
        remarkLarge.textProperty().unbind();
        remarkLarge.textProperty().setValue("");
        tagsLarge.getChildren().clear();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        nameLarge.textProperty().bind(Bindings.convert(person.nameProperty()));
        phoneLarge.textProperty().bind(Bindings.convert(person.phoneProperty()));
        birthdayLarge.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        emailLarge.textProperty().bind(Bindings.convert(person.emailProperty()));
        organisationLarge.textProperty().bind(Bindings.convert(person.organisationProperty()));
        remarkLarge.textProperty().bind(Bindings.convert(person.remarkProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tagsLarge.getChildren().clear();
            person.getTags().forEach(tag -> tagsLarge.getChildren().add(new Label(tag.tagName)));
        });
    }

    //@@author
    private void initTags(ReadOnlyPerson person) {
        tagsLarge.getChildren().clear();
        person.getTags().forEach(tag -> tagsLarge.getChildren().add(new Label(tag.tagName)));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }

    //@@author frozventus
    @Subscribe
    private void handlePersonDeletedEvent(PersonDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetPersonDetails();
    }
}
