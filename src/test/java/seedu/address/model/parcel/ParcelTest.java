package seedu.address.model.parcel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_ADDRESS;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_DELIVERY_DATE;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_EMAIL;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_NAME;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_PHONE;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_STATUS;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_TAGS;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_TRACKING_NUMBER;

import org.junit.Test;

import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.ParcelBuilder;

public class ParcelTest {

    @Test
    public void equals() throws IllegalValueException {
        Parcel parcel = new Parcel(new ParcelBuilder().build());
        Parcel sameParcel = new Parcel(new TrackingNumber(DEFAULT_TRACKING_NUMBER),
                new Name(DEFAULT_NAME), new Phone(DEFAULT_PHONE),
                new Email(DEFAULT_EMAIL), new Address(DEFAULT_ADDRESS),
                new DeliveryDate(DEFAULT_DELIVERY_DATE),
                Status.getInstance(DEFAULT_STATUS),
                SampleDataUtil.getTagSet(DEFAULT_TAGS));
        Parcel differentParcel;
        Parcel sameParcelWithDifferentStatus = new Parcel(parcel);
        sameParcelWithDifferentStatus.setStatus(Status.OVERDUE);

        // parcel equality
        assertEquals(parcel, sameParcel); // check different object reference but attributes are the same.
        assertEquals(parcel, sameParcelWithDifferentStatus); // Status will not influence Parcel#equals()
        sameParcelWithDifferentStatus.setStatus(Status.DELIVERING);
        assertEquals(parcel, sameParcelWithDifferentStatus);

        // Internal state checks for Parcel
        // Tracking number
        differentParcel = new Parcel(parcel);
        differentParcel.setTrackingNumber(new TrackingNumber("RR123661123SG"));
        assertEquals(differentParcel.getTrackingNumber(), new TrackingNumber("RR123661123SG"));
        assertEquals(differentParcel.trackingNumberProperty().get(),
                new SimpleObjectProperty<>(new TrackingNumber("RR123661123SG")).get());
        assertFalse(parcel.equals(differentParcel));

        differentParcel = new Parcel(parcel);
        // Name equality
        differentParcel.setName(new Name("John"));
        assertEquals(differentParcel.getName(), new Name("John"));
        assertEquals(differentParcel.nameProperty().get(), new SimpleObjectProperty<>(new Name("John")).get());

        // Phone equality
        differentParcel.setPhone(new Phone("111"));
        assertEquals(differentParcel.getPhone(), new Phone("111"));
        assertEquals(differentParcel.phoneProperty().get(), new SimpleObjectProperty<>(new Phone("111")).get());

        // Email equality
        differentParcel.setEmail(new Email("John@john.com"));
        assertEquals(differentParcel.getEmail(), new Email("John@john.com"));
        assertEquals(differentParcel.emailProperty().get(),
                new SimpleObjectProperty<>(new Email("John@john.com")).get());

        // Address Equality
        differentParcel.setAddress(new Address("test drive S123661"));
        assertEquals(differentParcel.getAddress(), new Address("test drive S123661"));
        assertEquals(differentParcel.addressProperty().get(),
                new SimpleObjectProperty<>(new Address("test drive S123661")).get());

        // PostalCode Equality
        assertEquals(differentParcel.addressProperty().get().postalCode,
                new SimpleObjectProperty<>(new Address("test drive S123661")).get().postalCode);

        // Delivery Date Equality
        differentParcel.setDeliveryDate(new DeliveryDate("05-05-2005"));
        assertEquals(differentParcel.getDeliveryDate(), new DeliveryDate("05-05-2005"));
        assertEquals(differentParcel.deliveryDateProperty().get(),
                new SimpleObjectProperty<>(new DeliveryDate("05-05-2005")).get());

        // Status Equality
        differentParcel.setStatus(Status.getInstance("Completed"));
        assertEquals(differentParcel.getStatus(), Status.getInstance("Completed"));
        assertEquals(differentParcel.statusProperty().get(),
                new SimpleObjectProperty<>(Status.getInstance("Completed")).get());

        // Tags Equality
        differentParcel.setTags(SampleDataUtil.getTagSet("test"));
        assertEquals(differentParcel.getTags(), SampleDataUtil.getTagSet("test"));
        assertEquals(differentParcel.tagProperty().get(), new SimpleObjectProperty<>(
                new UniqueTagList(SampleDataUtil.getTagSet("test"))).get());

        // check state equality
        assertEquals(parcel.getTrackingNumber(), new TrackingNumber(DEFAULT_TRACKING_NUMBER));
        assertEquals(parcel.getName(), new Name(DEFAULT_NAME));
        assertEquals(parcel.getPhone(), new Phone(DEFAULT_PHONE));
        assertEquals(parcel.getEmail(), new Email(DEFAULT_EMAIL));
        assertEquals(parcel.getAddress(), new Address(DEFAULT_ADDRESS));
        assertEquals(parcel.getStatus(), Status.getInstance(DEFAULT_STATUS));
        assertEquals(parcel.getTags(), SampleDataUtil.getTagSet(DEFAULT_TAGS));

        // toString() equality
        assertEquals(parcel.toString(), sameParcel.toString());
        assertEquals(parcel.toString(), "Tracking No.: " + DEFAULT_TRACKING_NUMBER + " Recipient Name: "
                + DEFAULT_NAME + " Phone: " + DEFAULT_PHONE  + " Email: " + DEFAULT_EMAIL + " Address: "
                + DEFAULT_ADDRESS + " Delivery Date: " + DEFAULT_DELIVERY_DATE + " Status: " + DEFAULT_STATUS
                + " Tags: ["  + DEFAULT_TAGS + "]");

    }

    @Test
    public void compareTo() throws IllegalValueException {
        Parcel parcel = new Parcel(new ParcelBuilder().build());
        Parcel sameParcel = new Parcel(new TrackingNumber(DEFAULT_TRACKING_NUMBER),
                new Name(DEFAULT_NAME), new Phone(DEFAULT_PHONE),
                new Email(DEFAULT_EMAIL), new Address(DEFAULT_ADDRESS),
                new DeliveryDate(DEFAULT_DELIVERY_DATE),
                Status.getInstance(DEFAULT_STATUS),
                SampleDataUtil.getTagSet(DEFAULT_TAGS));
        int parcelHash = parcel.hashCode();
        int sameParcelHash = sameParcel.hashCode();
        assertFalse(parcelHash == sameParcelHash);
        assertEquals(parcel.compareTo(sameParcel), 0);
    }

}
