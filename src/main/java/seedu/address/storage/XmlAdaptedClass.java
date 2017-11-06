package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author newalter
/**
 * Represents all XmlAdapted Classes e.g. XmlAdaptedTag
 * @param <E> the corresponding Class of the XmlAdaptedClass e.g. Tag
 */
public interface XmlAdaptedClass<E> {

    /**
     * Converts the jaxb-friendly adapted object into the corresponding object in model.
     * @throws IllegalValueException if there were any data constraints violated
     */
    public E toModelType() throws IllegalValueException;

}
