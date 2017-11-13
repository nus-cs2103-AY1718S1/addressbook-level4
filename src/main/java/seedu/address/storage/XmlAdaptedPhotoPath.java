package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.photo.PhotoPath;

//@@author April0616
/**
 * It's the JAXB-friendly adapted version of the photo path.
 */
public class XmlAdaptedPhotoPath {

    @XmlValue
    private String photoPathName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPhotoPath() {}

    /**
     * Converts a given PhotoPath into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPhotoPath(PhotoPath source) {
        photoPathName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted PhotoPath object into the model's PhotoPath object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public PhotoPath toModelType() throws IllegalValueException {
        return new PhotoPath(photoPathName);
    }

}
