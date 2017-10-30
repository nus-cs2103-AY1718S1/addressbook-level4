package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FROZEN;
import static seedu.address.testutil.TypicalParcels.VALID_ADDRESS_ALICE;
import static seedu.address.testutil.TypicalParcels.VALID_DELIVERY_DATE_ALICE;
import static seedu.address.testutil.TypicalParcels.VALID_EMAIL_ALICE;
import static seedu.address.testutil.TypicalParcels.VALID_NAME_ALICE;
import static seedu.address.testutil.TypicalParcels.VALID_TRACKING_NUMBER_ALICE;

import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Parcel objects.
 */
public class ParcelBuilder {

    public static final String DEFAULT_NAME = VALID_NAME_ALICE;
    public static final String DEFAULT_PHONE = VALID_PHONE_AMY; // to ensure uniqueness
    public static final String DEFAULT_EMAIL = VALID_EMAIL_ALICE;
    public static final String DEFAULT_ADDRESS = VALID_ADDRESS_ALICE;
    public static final String DEFAULT_TAGS = VALID_TAG_FROZEN;
    public static final String DEFAULT_TRACKING_NUMBER = VALID_TRACKING_NUMBER_ALICE;
    public static final String DEFAULT_DELIVERY_DATE = VALID_DELIVERY_DATE_ALICE;
    public static final String DEFAULT_STATUS = VALID_STATUS_PENDING;

    private Parcel parcel;

    public ParcelBuilder() {
        try {
            TrackingNumber defaultTrackingNumber =  new TrackingNumber(DEFAULT_TRACKING_NUMBER);
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            DeliveryDate defaultDeliveryDate = new DeliveryDate(DEFAULT_DELIVERY_DATE);
            Status defaultStatus = Status.getInstance(DEFAULT_STATUS);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.parcel = new Parcel(defaultTrackingNumber, defaultName, defaultPhone, defaultEmail, defaultAddress,
                    defaultDeliveryDate, defaultStatus, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default parcel's values are invalid.");
        }
    }

    /**
     * Initializes the ParcelBuilder with the data of {@code parcelToCopy}.
     */
    public ParcelBuilder(ReadOnlyParcel parcelToCopy) {
        this.parcel = new Parcel(parcelToCopy);
    }

    /**
     * Sets the {@code TrackingNumber} of the {@code Parcel} that we are building.
     */
    public ParcelBuilder withTrackingNumber(String trackingNumber) {
        try {
            this.parcel.setTrackingNumber(new TrackingNumber(trackingNumber));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tracking number is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Parcel} that we are building.
     */
    public ParcelBuilder withName(String name) {
        try {
            this.parcel.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Parcel} that we are building.
     */
    public ParcelBuilder withTags(String ... tags) {
        try {
            this.parcel.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Parcel} that we are building.
     */
    public ParcelBuilder withAddress(String address) {
        try {
            this.parcel.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Parcel} that we are building.
     */
    public ParcelBuilder withPhone(String phone) {
        try {
            this.parcel.setPhone(new Phone(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Parcel} that we are building.
     */
    public ParcelBuilder withEmail(String email) {
        try {
            this.parcel.setEmail(new Email(email));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code DeliveryDate} of the {@code Parcel} that we are building.
     */
    public ParcelBuilder withDeliveryDate(String deliveryDate) {
        try {
            this.parcel.setDeliveryDate(new DeliveryDate(deliveryDate));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("delivery date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Parcel} that we are building.
     */
    public ParcelBuilder withStatus(String status) {
        try {
            this.parcel.setStatus(Status.getInstance(status));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return this;
    }

    public Parcel build() {
        return this.parcel;
    }

}
