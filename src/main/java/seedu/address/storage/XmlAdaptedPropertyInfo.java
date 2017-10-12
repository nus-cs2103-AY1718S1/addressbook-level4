package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyManager;
import seedu.address.model.property.exceptions.DuplicatePropertyException;

/**
 * JAXB-friendly adapted version of the {@link Property}, stores the general information of each property.
 */
public class XmlAdaptedPropertyInfo {
    @XmlElement
    private String shortName;
    @XmlElement
    private String fullName;
    @XmlElement
    private String message;
    @XmlElement
    private String regex;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPropertyInfo() {}

    public XmlAdaptedPropertyInfo(String shortName, String fullName, String message, String regex) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.message = message;
        this.regex = regex;
    }

    public void toModelType() throws DuplicatePropertyException {
        PropertyManager.addNewProperty(shortName, fullName, message, regex);
    }
}
