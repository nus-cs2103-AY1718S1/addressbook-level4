package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Property;
import seedu.address.model.person.exceptions.PropertyNotFoundException;

/**
 * JAXB-friendly adapted version of the {@link seedu.address.model.person.Property}.
 */
public class XmlAdaptedProperty {
    @XmlValue
    private String shortName;
    @XmlValue
    private String value;

    /**
     * Constructs an XmlAdaptedProperty.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedProperty() {}

    /**
     * Converts a given Property into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedProperty(Property source) {
        this.shortName = source.getShortName();
        this.value = source.getValue();
    }

    /**
     * Converts this jaxb-friendly adapted property object into the model's Property object.
     *
     * @return a Property object used in model.
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     * @throws PropertyNotFoundException the same as above.
     */
    public Property toModelType() throws IllegalValueException, PropertyNotFoundException {
        return new Property(shortName, value);
    }
}
