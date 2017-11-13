//author huiyiiih
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.relationship.Relationship;

/**
 * JAXB-friendly adapted version of the Relationship.
 */
public class XmlAdaptedRelationship {

    @XmlValue
    private String relType;

    /**
     * Constructs an XmlAdaptedRel.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRelationship() {
    }

    /**
     * Converts a given Relationship into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedRelationship(Relationship source) {
        relType = source.relType;
    }

    /**
     * Converts this jaxb-friendly adapted relationship object into the model's Relationship object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Relationship toModelType() throws IllegalValueException {
        return new Relationship(relType);
    }

}
//@@author
