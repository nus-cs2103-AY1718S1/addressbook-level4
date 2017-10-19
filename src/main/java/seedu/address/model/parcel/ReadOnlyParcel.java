package seedu.address.model.parcel;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Parcel in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyParcel {

    ObjectProperty<TrackingNumber> trackingNumberProperty();
    TrackingNumber getTrackingNumber();
    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Phone> phoneProperty();
    Phone getPhone();
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<DeliveryDate> deliveryDateProperty();
    DeliveryDate getDeliveryDate();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyParcel other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTrackingNumber().equals(this.getTrackingNumber())
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress()))
                && other.getDeliveryDate().equals(this.getDeliveryDate());
    }

    /**
     * Formats the parcel as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Tracking No.: ")
                .append(getTrackingNumber())
                .append(" Recipient Name: ")
                .append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Delivery Date: ")
                .append(getDeliveryDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
