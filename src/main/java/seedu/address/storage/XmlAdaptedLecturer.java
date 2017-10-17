package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lecturer.Lecturer;

/**
 * Stores lecturer data in an XML file
 */
public class XmlAdaptedLecturer {

    @XmlValue
    private String name;

    /**
     * Constructs an XmlAdaptedLecturer.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLecturer() {}

    /**
     * Converts a given Lecturer into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedLecturer(Lecturer source) {
        name = source.lecturerName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Lecturer object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Lecturer
     */
    public Lecturer toModelType() throws IllegalValueException {
        return new Lecturer(name);
    }

}
