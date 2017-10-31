package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.phone.Phone;

/**
 * JAXB-friendly adapted version of the Phone.
 */
public class XmlAdaptedPhone {

    @XmlValue
    private String phoneNum;

    /**
     * Constructs an XmlAdaptedPhone.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPhone() {}

    /**
     * Converts a given Phone into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPhone(Phone source) {
        phoneNum = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted phone object into the model's Phone object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Phone toModelType() throws IllegalValueException {
        return new Phone(phoneNum);
    }

}
