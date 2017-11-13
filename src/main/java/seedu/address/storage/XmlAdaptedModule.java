package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;

/**
 * JAXB-friendly adapted version of the Module.
 */
public class XmlAdaptedModule {

    @XmlValue
    private String moduleName;

    /**
     * Constructs an XmlAdaptedModule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedModule() {}

    /**
     * Converts a given Module into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedModule(Module source) {
        moduleName = source.moduleName;
    }

    /**
     * Converts this jaxb-friendly adapted module object into the model's Module object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Module toModelType() throws IllegalValueException {
        return new Module(moduleName);
    }

}
