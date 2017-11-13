package seedu.address.storage.elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.property.Property;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.tag.Tag;

//@@author yunpengn
/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {
    private static final Logger logger = LogsCenter.getLogger(XmlAdaptedPerson.class);
    private static final String IMAGE_NOT_FOUND = "One avatar has been deleted or moved.\n%1$s";

    @XmlElement
    private String avatar;

    @XmlElement
    private List<XmlAdaptedProperty> properties = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    //@@author yunpengn
    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        if (source.getAvatar() != null) {
            avatar = source.getAvatar().getPath();
        }

        properties = new ArrayList<>();
        for (Property property: source.getProperties()) {
            properties.add(new XmlAdaptedProperty(property));
        }

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException, PropertyNotFoundException, DuplicatePropertyException {
        final List<Property> personProperties = new ArrayList<>();
        for (XmlAdaptedProperty property: properties) {
            personProperties.add(property.toModelType());
        }

        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final Set<Property> properties = new HashSet<>(personProperties);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Person person = new Person(properties, tags);

        if (avatar != null) {
            try {
                person.setAvatar(new Avatar(avatar));
            } catch (IllegalValueException ive) {
                logger.warning(String.format(IMAGE_NOT_FOUND, ive.getMessage()));
            }
        }

        return person;
    }
}
