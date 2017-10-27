package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Remark;

/**
 * Stores remark data in an XML file
 */
public class XmlAdaptedRemark {

    @XmlElement(required = true)
    private String content;

    @XmlElement(required = true)
    private String code;

    /**
     * Constructs an XmlAdaptedRemark.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRemark() {}

    /**
     * Converts a given Lecturer into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedRemark(Remark source) {
        content = source.value;
        code = source.moduleCode.fullCodeName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Remark object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Remark
     */
    public Remark toModelType() throws IllegalValueException {
        final Code code = new Code(this.code);
        return new Remark(content, code);
    }

}
