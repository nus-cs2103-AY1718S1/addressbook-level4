package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.AliasToken;
import seedu.address.model.alias.Keyword;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.Representation;

//@@author deep4k
/**
 * JAXB-friendly version of the AliasToken.
 */
public class XmlAdaptedAliasToken {

    @XmlElement(required = true)
    private String keyword;
    @XmlElement(required = true)
    private String representation;

    public XmlAdaptedAliasToken() {
    }

    /**
     * Converts a given AliasToken into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAliasToken
     */
    public XmlAdaptedAliasToken(ReadOnlyAliasToken source) {
        keyword = source.getKeyword().keyword;
        representation = source.getRepresentation().representation;
    }

    /**
     * Converts this jaxb-friendly adapted AliasToken object into the model's AliasToken object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted AliasToken
     */

    public AliasToken toModelType() throws IllegalValueException {
        final Keyword keyword = new Keyword(this.keyword);
        final Representation representation = new Representation(this.representation);
        return new AliasToken(keyword, representation);
    }


}
