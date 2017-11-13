package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.place.Address;
import seedu.address.model.place.Name;
import seedu.address.model.place.Phone;
import seedu.address.model.place.Place;
import seedu.address.model.place.PostalCode;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.Website;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Place.
 */
public class XmlAdaptedPlace {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String website;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String postalcode;

    @XmlElement
    private List<XmlAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPlace.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPlace() {}


    /**
     * Converts a given Place into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPlace
     */
    public XmlAdaptedPlace(ReadOnlyPlace source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        website = source.getWebsite().value;
        address = source.getAddress().value;
        postalcode = source.getPostalCode().value;
        tags = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tags.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted place object into the model's Place object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted place
     */
    public Place toModelType() throws IllegalValueException {
        final List<Tag> placeTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tags) {
            placeTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Website website = new Website(this.website);
        final Address address = new Address(this.address);
        final PostalCode postalcode = new PostalCode((this.postalcode));
        final Set<Tag> tags = new HashSet<>(placeTags);
        return new Place(name, phone, website, address, postalcode, tags);
    }
}
