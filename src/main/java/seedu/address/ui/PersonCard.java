package seedu.address.ui;

import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_LARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_NORMAL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_SMALL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XLARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XSMALL;
import static seedu.address.logic.commands.CustomiseCommand.MESSAGE_SUCCESS;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
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

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
        registerAsAnEventHandler(this);
        ListingUnit currentUnit = ListingUnit.getCurrentListingUnit();

        switch (currentUnit) {
        case ADDRESS:
            switchToAddressCard();
            break;

        case EMAIL:
            switchToEmailCard();
            break;

        case PHONE:
            switchToPhoneCard();
            break;

        default:
        }
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }


    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * change the card state to hide irrelevant information and only show address
     */
    private void switchToAddressCard() {
        name.setVisible(false);
        phone.setVisible(false);
        email.setVisible(false);
        tags.setVisible(false);
        address.setStyle("-fx-font: 16 arial;");
    }

    /**
     * change the card state to hide irrelevant information and only show phone
     */
    private void switchToPhoneCard() {
        name.setVisible(false);
        address.setVisible(false);
        email.setVisible(false);
        tags.setVisible(false);
        phone.setStyle("-fx-font: 16 arial;");

    }

    /**
     * change the card state to hide irrelevant information and only show email
     */
    private void switchToEmailCard() {
        name.setVisible(false);
        phone.setVisible(false);
        address.setVisible(false);
        tags.setVisible(false);
        email.setStyle("-fx-font: 16 arial;");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }

    private void setFontSize(String userPref) {
        switch (userPref) {
        case MESSAGE_SUCCESS + FONT_SIZE_XSMALL + ".":
            name.setStyle("-fx-font-size: x-small;");
            id.setStyle("-fx-font-size: x-small;");
            phone.setStyle("-fx-font-size: x-small;");
            address.setStyle("-fx-font-size: x-small;");
            email.setStyle("-fx-font-size: x-small;");
            tags.setStyle("-fx-font-size: x-small;");
            break;

        case MESSAGE_SUCCESS + FONT_SIZE_SMALL + ".":
            name.setStyle("-fx-font-size: small;");
            id.setStyle("-fx-font-size: small;");
            phone.setStyle("-fx-font-size: small;");
            address.setStyle("-fx-font-size: small;");
            email.setStyle("-fx-font-size: small;");
            tags.setStyle("-fx-font-size: small;");
            break;

        case MESSAGE_SUCCESS + FONT_SIZE_NORMAL + ".":
            name.setStyle("-fx-font-size: normal;");
            id.setStyle("-fx-font-size: normal;");
            phone.setStyle("-fx-font-size: normal;");
            address.setStyle("-fx-font-size: normal;");
            email.setStyle("-fx-font-size: normal;");
            tags.setStyle("-fx-font-size: normal;");
            break;

        case MESSAGE_SUCCESS + FONT_SIZE_LARGE + ".":
            name.setStyle("-fx-font-size: x-large;");
            id.setStyle("-fx-font-size: x-large;");
            phone.setStyle("-fx-font-size: x-large;");
            address.setStyle("-fx-font-size: x-large;");
            email.setStyle("-fx-font-size: x-large;");
            tags.setStyle("-fx-font-size: x-large;");
            break;

        case MESSAGE_SUCCESS + FONT_SIZE_XLARGE + ".":
            name.setStyle("-fx-font-size: xx-large;");
            id.setStyle("-fx-font-size: xx-large;");
            phone.setStyle("-fx-font-size: xx-large;");
            address.setStyle("-fx-font-size: xx-large;");
            email.setStyle("-fx-font-size: xx-large;");
            tags.setStyle("-fx-font-size: xx-large;");
            break;

        default:
            break;
        }
    }

}
