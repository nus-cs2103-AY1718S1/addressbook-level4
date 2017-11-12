package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.socialmedia.ReadOnlySocialMedia;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.socialmedia.SocialMediaUrl;



/**
 * JAXB-friendly adapted version of the SocialMedia.
 */
public class XmlAdaptedSocialMedia {

    @XmlElement(required = true)
    private String socialMediaName;
    /**
     * Constructs an XmlAdaptedSocialMedia.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSocialMedia() {}

    /**
     * Converts a given SocialMedia into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedSocialMedia(ReadOnlySocialMedia source) {
        socialMediaName = source.getName().toString();
    }

    /**
     * Converts this jaxb-friendly adapted socialMedia object into the model's SocialMedia object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public SocialMedia toModelType() throws IllegalValueException {
        final SocialMediaUrl socialMediaName = new SocialMediaUrl(this.socialMediaName);
        return new SocialMedia(socialMediaName);
    }

}
