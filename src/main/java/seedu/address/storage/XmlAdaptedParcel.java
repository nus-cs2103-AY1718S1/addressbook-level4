package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.parcel.Address;
import seedu.address.model.parcel.DeliveryDate;
import seedu.address.model.parcel.Email;
import seedu.address.model.parcel.Name;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.Phone;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.Status;
import seedu.address.model.parcel.TrackingNumber;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Parcel.
 */
public class XmlAdaptedParcel {

    @XmlElement(required = true)
    private String trackingNumber;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String deliveryDate;
    @XmlElement(required = true)
    private String status;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedParcel.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedParcel() {}


    /**
     * Converts a given Parcel into this class for JAXB use.
     * @param source future changes to this will not affect the created XmlAdaptedParcel
     */
    public XmlAdaptedParcel(ReadOnlyParcel source) {
        trackingNumber = source.getTrackingNumber().value;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().toString();
        deliveryDate = source.getDeliveryDate().toString();
        status = source.getStatus().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted parcel object into the model's Parcel object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted parcel
     */
    public Parcel toModelType() throws IllegalValueException {
        final List<Tag> parcelTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            parcelTags.add(tag.toModelType());
        }
        final TrackingNumber trackingNumber = new TrackingNumber(this.trackingNumber);
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final DeliveryDate deliveryDate = new DeliveryDate(this.deliveryDate);
        final Status status = Status.getInstance(this.status);
        final Set<Tag> tags = new HashSet<>(parcelTags);
        return new Parcel(trackingNumber, name, phone, email, address, deliveryDate, status, tags);
    }
}
