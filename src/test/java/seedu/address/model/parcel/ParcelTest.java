package seedu.address.model.parcel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_ADDRESS;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_ARTICLE_NUMBER;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_EMAIL;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_NAME;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_PHONE;
import static seedu.address.testutil.ParcelBuilder.DEFAULT_TAGS;

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
        Parcel sameParcel = new Parcel(new TrackingNumber(DEFAULT_ARTICLE_NUMBER),
                new Name(DEFAULT_NAME), new Phone(DEFAULT_PHONE),
                new Email(DEFAULT_EMAIL), new Address(DEFAULT_ADDRESS),
                SampleDataUtil.getTagSet(DEFAULT_TAGS));
        Parcel differentParcel = new Parcel(parcel);

        // parcel equality
        assertEquals(parcel, sameParcel);

        // parcel inequality
        differentParcel.setTrackingNumber(new TrackingNumber("RR123661123SG"));
        assertEquals(differentParcel.getTrackingNumber(), new TrackingNumber("RR123661123SG"));
        assertEquals(differentParcel.trackingNumberProperty().get(),
                new SimpleObjectProperty<>(new TrackingNumber("RR123661123SG")).get());
        assertFalse(parcel.equals(differentParcel));

        differentParcel.setName(new Name("John"));
        assertEquals(differentParcel.getName(), new Name("John"));
        assertEquals(differentParcel.nameProperty().get(), new SimpleObjectProperty<>(new Name("John")).get());

        differentParcel.setPhone(new Phone("111"));
        assertEquals(differentParcel.getPhone(), new Phone("111"));
        assertEquals(differentParcel.phoneProperty().get(), new SimpleObjectProperty<>(new Phone("111")).get());

        differentParcel.setEmail(new Email("John@john.com"));
        assertEquals(differentParcel.getEmail(), new Email("John@john.com"));
        assertEquals(differentParcel.emailProperty().get(),
                new SimpleObjectProperty<>(new Email("John@john.com")).get());

        differentParcel.setAddress(new Address("test drive S123661"));
        assertEquals(differentParcel.getAddress(), new Address("test drive S123661"));
        assertEquals(differentParcel.addressProperty().get(),
                new SimpleObjectProperty<>(new Address("test drive S123661")).get());

        differentParcel.setTags(SampleDataUtil.getTagSet("test"));
        assertEquals(differentParcel.getTags(), SampleDataUtil.getTagSet("test"));
        assertEquals(differentParcel.tagProperty().get(), new SimpleObjectProperty<>(
                new UniqueTagList(SampleDataUtil.getTagSet("test"))).get());

        // check state equality
        assertEquals(parcel.getTrackingNumber(), new TrackingNumber(DEFAULT_ARTICLE_NUMBER));
        assertEquals(parcel.getName(), new Name(DEFAULT_NAME));
        assertEquals(parcel.getPhone(), new Phone(DEFAULT_PHONE));
        assertEquals(parcel.getEmail(), new Email(DEFAULT_EMAIL));
        assertEquals(parcel.getAddress(), new Address(DEFAULT_ADDRESS));
        assertEquals(parcel.getTags(), SampleDataUtil.getTagSet(DEFAULT_TAGS));

        // toString() equality
        assertEquals(parcel.toString(), sameParcel.toString());
        assertEquals(parcel.toString(), "Article No.: " + DEFAULT_ARTICLE_NUMBER + " Recipient Name: "
                + DEFAULT_NAME + " Phone: " + DEFAULT_PHONE  + " Email: " + DEFAULT_EMAIL + " Address: "
                + DEFAULT_ADDRESS + " Tags: [" + DEFAULT_TAGS + "]");

    }
}
