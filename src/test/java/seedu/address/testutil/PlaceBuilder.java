package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.place.Address;
import seedu.address.model.place.Name;
import seedu.address.model.place.Phone;
import seedu.address.model.place.Place;
import seedu.address.model.place.PostalCode;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.Website;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Place objects.
 */
public class PlaceBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_WEBSITE = "https://www.example.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_POSTAL_CODE = "111111";
    public static final String DEFAULT_TAGS = "friends";

    private Place place;

    public PlaceBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Website defaultWebsite = new Website(DEFAULT_WEBSITE);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            PostalCode defaultPostalCode = new PostalCode(DEFAULT_POSTAL_CODE);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.place = new Place(defaultName, defaultPhone, defaultWebsite, defaultAddress,
                    defaultPostalCode,
                    defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default place's values are invalid.");
        }
    }

    /**
     * Initializes the PlaceBuilder with the data of {@code placeToCopy}.
     */
    public PlaceBuilder(ReadOnlyPlace placeToCopy) {
        this.place = new Place(placeToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Place} that we are building.
     */
    public PlaceBuilder withName(String name) {
        try {
            this.place.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Place} that we are building.
     */
    public PlaceBuilder withTags(String ... tags) {
        try {
            this.place.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Place} that we are building.
     */
    public PlaceBuilder withAddress(String address) {
        try {
            this.place.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Place} that we are building.
     */
    public PlaceBuilder withPhone(String phone) {
        try {
            this.place.setPhone(new Phone(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    //@@author aungmyin23
    /**
     * Sets the {@code PostalCode} of the {@code PostalCode} that we are building.
     */
    public PlaceBuilder withPostalCode(String postalcode) {
        try {
            this.place.setPostalcode(new PostalCode(postalcode));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phostalcode is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code Place} that we are building.
     */
    public PlaceBuilder withWebsite(String website) {
        try {
            this.place.setWebsite(new Website(website));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique.");
        }
        return this;
    }
    //@@author

    public Place build() {
        return this.place;
    }

}
