package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * An UI component that displays information of a {@code Parcel}.
 */
public class ParcelCard extends UiPart<Region> {

    private static final String FXML = "ParcelListCard.fxml";
    private static String[] colors = { "#cc4f4f", "#57b233", "#2696b5", "#5045c6", "#7739ba", "#b534a1", "black" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

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
    @FXML
    private Label trackingNumber;
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
    private Label deliveryDate;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;

    public ParcelCard(ReadOnlyParcel parcel, int displayedIndex) {
        super(FXML);
        this.parcel = parcel;
        id.setText(displayedIndex + ". ");
        initTags(parcel);
        bindListeners(parcel);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Parcel} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyParcel parcel) {
        trackingNumber.textProperty().bind(Bindings.convert(parcel.trackingNumberProperty()));
        name.textProperty().bind(Bindings.convert(parcel.nameProperty()));
        phone.textProperty().bind(Bindings.convert(parcel.phoneProperty()));
        address.textProperty().bind(Bindings.convert(parcel.addressProperty()));
        email.textProperty().bind(Bindings.convert(parcel.emailProperty()));
        deliveryDate.textProperty().bind(Bindings.convert(parcel.deliveryDateProperty()));
        status.textProperty().bind(Bindings.convert(parcel.statusProperty()));
        setColorForStatus();
        parcel.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(parcel);
        });
    }

    /**
     * Sets color for the status labels based on the current status.
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

    /**
     * Initializes tags and sets their style based on their tag label
     */
    private void initTags(ReadOnlyParcel parcel) {
        parcel.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + setColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * tags with value tagValue will be assigned a random color from a list colors field
     */
    private static String setColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

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
