package seedu.address.storage.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColorManager;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTag {
    @XmlValue
    private String tagName;
    @XmlAttribute
    private String color;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTag() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTag(Tag source) {
        tagName = source.tagName;
        try {
            color = TagColorManager.getColor(source);
        } catch (TagNotFoundException e) {
            System.err.println("Should never come to here.");
        }
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        return new Tag(tagName, color);
    }
}
