package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/** 
 *A UI component that displays the selected Person's details.
 * */
public class ExtendedPersonDetails extends UiPart<Region> {
    //@@author wishingmaid 
    private static final String FXML = "ExtendDetailsPerson.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final String defaultPicture = "/images/PEERSONAL_icon.png";
    //@@author
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
    @FXML
    private Circle circle;
    //@@author wishingmaid
    /** 
     * This class loads the persons details in the UI for the extended person's panel.
     * */
    public ExtendedPersonDetails() {
        super(FXML);
        registerAsAnEventHandler(this);
        Image image = new Image(getClass().getResource(defaultPicture).toExternalForm());
        setCircle(image);
    }//@@author
    
    /** */
    private void loadPersonDetails(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        age.textProperty().bind(Bindings.convert(person.ageProperty()));
        setImage(person);
    }

    /** */
    private void setImage(ReadOnlyPerson person) {
        String url = person.getPhoto().getFilePath(); //gets the filepath directly from the resources folder.
        if (url.equals("")) {
            Image image = new Image(getClass().getResource("/images/noPhoto.png").toExternalForm());
            setCircle(image);
        } else {
            Image image = new Image("file:" + person.getPhoto().getFilePath());
            setCircle(image);
        }
    }
    private void setCircle(Image image) {
        ImagePattern pattern = new ImagePattern(image);
        circle.setFill(pattern);
        circle.setEffect(new DropShadow(10, Color.STEELBLUE));
    }
    //@@author wishingmaid
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }//@@author
   

}
