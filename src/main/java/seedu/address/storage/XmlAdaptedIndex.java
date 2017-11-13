package seedu.address.storage;
//@@author Esilocke
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * JAXB-friendly adapted version of the Index.
 */
public class XmlAdaptedIndex {

    @XmlValue
    private int index;

    /**
     * Constructs an XmlAdaptedIndex.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedIndex() {}

    /**
     * Converts a given Index into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedIndex(Index source) {
        index = source.getZeroBased();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into an Index object.
     *
     * @throws IllegalValueException if there were any data constraints\
     */
    public Index toModelType() throws IllegalValueException {
        return Index.fromZeroBased(index);
    }

}
