package seedu.address.ui;

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
    private final String phoneIcon = "/images/Mobile-Phone-icon.png";
    private final String addressIcon = "/images/address.png";
    private final String emailIcon = "/images/email-icon.png";
    private final String birthdayIcon = "/images/BirthdayIcon.png";
    private final String remarkIcon = "/images/RemarkIcon.png";
    private final String ageIcon = "/images/AgeIcon.png";
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
    @FXML
    private Circle circlePhone;
    @FXML
    private Circle circleAddress;
    @FXML
    private Circle circleEmail;
    @FXML
    private Circle circleRemark;
    @FXML
    private Circle circleBirthday;
    @FXML
    private Circle circleAge;
    //@@author wishingmaid
    /**
     * This class loads the persons details in the UI for the extended person's panel.
     * */
    public ExtendedPersonDetails() {
        super(FXML);
        registerAsAnEventHandler(this);
        setUpUi();
    }
    //@@author
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
            setCircle(circle, image);
        } else {
            Image image = new Image("file:" + person.getPhoto().getFilePath());
            setCircle(circle, image);
        }
    }
    private void setCircle(Circle circle, Image image) {
        ImagePattern pattern = new ImagePattern(image);
        circle.setFill(pattern);
        circle.setEffect(new DropShadow(10, Color.STEELBLUE));
    }
    //@@author wishingmaid
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
    private void setUpUi() {
        Image image = new Image(getClass().getResource(defaultPicture).toExternalForm());
        setCircle(circle, image);
        Image imagePhone = new Image(getClass().getResource(phoneIcon).toExternalForm());
        setCircle(circlePhone, imagePhone);
        Image imageAddress = new Image(getClass().getResource(addressIcon).toExternalForm());
        setCircle(circleAddress, imageAddress);
        Image imageEmail = new Image(getClass().getResource(emailIcon).toExternalForm());
        setCircle(circleEmail, imageEmail);
        Image imageRemark = new Image(getClass().getResource(remarkIcon).toExternalForm());
        setCircle(circleRemark, imageRemark);
        Image imageBirthday = new Image(getClass().getResource(birthdayIcon).toExternalForm());
        setCircle(circleBirthday, imageBirthday);
        Image imageAge = new Image(getClass().getResource(ageIcon).toExternalForm());
        setCircle(circleAge, imageAge);
    }
    //@@author
}
