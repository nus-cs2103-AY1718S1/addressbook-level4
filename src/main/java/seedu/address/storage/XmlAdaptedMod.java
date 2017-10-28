package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mod.Mod;

/**
 * JAXB-friendly adapted version of the Mod.
 */
public class XmlAdaptedMod {

    @XmlValue
    private String modName;

    /**
     * Constructs an XmlAdaptedMod.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMod() {}

    /**
     * Converts a given Mod into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedMod(Mod source) {
        modName = source.modName;
    }

    /**
     * Converts this jaxb-friendly adapted mod object into the model's Mod object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Mod toModelType() throws IllegalValueException {
        return new Mod(modName);
    }

}
