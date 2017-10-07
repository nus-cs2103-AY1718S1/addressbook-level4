package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Address;

/**
 * An UI component that displays information of a {@code Address}.
 */
public class AddressCard extends UiPart<Region> {

    private static final String FXML = "AddressListCard.fxml";

    public final Address address;

    @FXML
    private Label addressLabel;

    @FXML
    private Label id;

    public AddressCard(Address address, int displayedIndex) {
        super(FXML);
        this.address = address;
        id.setText(displayedIndex + ". ");
        bindListeners(address);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Address address) {

        addressLabel.textProperty().bind(Bindings.convert(address.getAddressProperty()));

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
       AddressCard card = (AddressCard) other;
        return id.getText().equals(card.id.getText())
                && address.equals(card.address);
    }
}
