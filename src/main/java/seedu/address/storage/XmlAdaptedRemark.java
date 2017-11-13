package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Remark;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedRemark {

    @XmlValue
    private String tagRemark;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRemark() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedRemark(Remark source) {
        tagRemark = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Remark toModelType() throws IllegalValueException {
        return new Remark(tagRemark);
    }

}
