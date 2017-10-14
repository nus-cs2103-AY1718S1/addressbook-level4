package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Lecturer.Lecturer;

import javax.xml.bind.annotation.XmlValue;

public class XmlAdaptedLecturer {

    @XmlValue
    private String name;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLecturer() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedLecturer(Lecturer source) {
        name = source.lecturerName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Lecturer toModelType() throws IllegalValueException {
        return new Lecturer(name);
    }

}
