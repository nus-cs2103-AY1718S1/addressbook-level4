package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * An UI component that displays information of a {@code Parcel}.
 */
public class ParcelCard extends UiPart<Region> {

    private static final String FXML = "ParcelListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    public final ReadOnlyParcel parcel;

    @FXML
    private HBox cardPane;

    //@@author kennard123661
    @FXML
    private Label trackingNumber;
    @FXML
    private Tooltip trackingNumberTooltip;

    @FXML
    private Label name;
    @FXML
    private Tooltip nameTooltip;

    @FXML
    private Label id;
    @FXML
    private Tooltip idTooltip;

    @FXML
    private Label phone;
    @FXML
    private Tooltip phoneTooltip;

    @FXML
    private Label address;
    @FXML
    private Tooltip addressTooltip;

    @FXML
    private Label email;
    @FXML
    private Tooltip emailTooltip;

    @FXML
    private Label deliveryDate;
    @FXML
    private Tooltip deliveryDateTooltip;

    @FXML
    private Label status;
    @FXML
    private Tooltip statusTooltip;
    //@@author

    @FXML
    private FlowPane tags;

    public ParcelCard(ReadOnlyParcel parcel, int displayedIndex) {
        super(FXML);
        this.parcel = parcel;
        id.setText(displayedIndex + ". ");
        idTooltip.setText(displayedIndex + ". ");
        initTags(parcel);
        bindListeners(parcel);
    }

    //@@author kennard123661
    /**
     * Binds the individual UI elements to observe their respective {@code Parcel} properties
     * so that they will be notified of any changes. Tooltips will also be assigned to their respective {@code Label}
     */
    private void bindListeners(ReadOnlyParcel parcel) {
        trackingNumber.textProperty().bind(Bindings.convert(parcel.trackingNumberProperty()));
        trackingNumberTooltip.textProperty().bind(Bindings.convert(parcel.trackingNumberProperty()));

        name.textProperty().bind(Bindings.convert(parcel.nameProperty()));
        nameTooltip.textProperty().bind(Bindings.convert(parcel.nameProperty()));

        phone.textProperty().bind(Bindings.convert(parcel.phoneProperty()));
        phoneTooltip.textProperty().bind(Bindings.convert(parcel.phoneProperty()));

        address.textProperty().bind(Bindings.convert(parcel.addressProperty()));
        addressTooltip.textProperty().bind(Bindings.convert(parcel.addressProperty()));

        email.textProperty().bind(Bindings.convert(parcel.emailProperty()));
        emailTooltip.textProperty().bind(Bindings.convert(parcel.emailProperty()));

        deliveryDate.textProperty().bind(Bindings.convert(parcel.deliveryDateProperty()));
        deliveryDateTooltip.textProperty().bind(Bindings.convert(parcel.deliveryDateProperty()));

        status.textProperty().bind(Bindings.convert(parcel.statusProperty()));
        statusTooltip.textProperty().bind(Bindings.convert(parcel.statusProperty()));
        setColorForStatus();

        parcel.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(parcel);
        });
    }

    //@@author kennard123661
    /**
     * Sets color for the status {@code Label}s based on the {@link ParcelCard#status} text value.
     */
    private void setColorForStatus() {
        switch (status.textProperty().get()) {
        case "PENDING":
            status.setStyle("-fx-background-color: " + "#d68411");
            break;

        case "DELIVERING":
            status.setStyle("-fx-background-color: " + "#ffc200");
            break;

        case "OVERDUE":
            status.setStyle("-fx-background-color: " + "red");
            break;

        case "COMPLETED":
        default: // fall through
            status.setStyle("-fx-background-color: " + "#00bf00");
            break;

        }
    }
    //@@author

    //@@author vicisapotato
    /**
     * Initializes tags and sets their style based on their tag label
     */
    private void initTags(ReadOnlyParcel parcel) {
        parcel.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.toString());
            tagLabel.setStyle("-fx-background-color: " + setColorForTag(tag.toString()));
            tags.getChildren().add(tagLabel);
        });
    }

    //@@author kennard123661
    /**
     * Assign colour to Tag {@code Label} based on {@param tagValue}
     */
    private static String setColorForTag(String tagValue) {
        switch (tagValue) {
        case "FLAMMABLE":
            return "#d6130a"; // Chrysler Radiant Fire Red

        case "FROZEN":
            return "#1db1b8"; // light blue

        case "HEAVY":
            return "#8b8d7a"; // Stone Gray

        case "FRAGILE": //fallthrough
        default:
            return "#bf9900"; // gold yellow

        }
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ParcelCard)) {
            return false;
        }

        // state check
        ParcelCard card = (ParcelCard) other;
        return id.getText().equals(card.id.getText())
                && parcel.equals(card.parcel);
    }

}
