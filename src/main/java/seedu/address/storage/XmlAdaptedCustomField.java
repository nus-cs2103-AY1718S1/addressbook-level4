package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.customfields.CustomField;
//@@author syy94
/**
 * JAXB-friendly adapted version of the Field.
 */
public class XmlAdaptedCustomField {
    @XmlValue
    private String customField;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCustomField() {
    }

    public XmlAdaptedCustomField(CustomField field) {
        this.customField = field.toString();
    }

    public CustomField toModelType() throws IllegalValueException {
        return new CustomField(customField);
    }
}
