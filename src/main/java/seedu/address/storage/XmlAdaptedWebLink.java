package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.weblink.WebLink;

/**
 * JAXB-friendly adapted version of the WebLink.
 */
public class XmlAdaptedWebLink {

    @XmlValue
    private String webLinkInput;

    /**
     * Constructs an XmlAdaptedWebLink
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedWebLink() {}

    /**
     * Converts a given webLink into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedWebLink(WebLink source) {
        webLinkInput = source.webLinkInput;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public WebLink toModelType() throws IllegalValueException {
        return new WebLink(webLinkInput);
    }
}
