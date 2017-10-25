package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedLifeInsurance {
    /*
    @XmlAttribute(required = true)
    private String id;
    */
    @XmlElement(required = true)
    private String owner;
    @XmlElement(required = true)
    private String insured;
    @XmlElement(required = true)
    private String beneficiary;
    @XmlElement(required = true)
    private Double premium;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLifeInsurance() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedLifeInsurance(ReadOnlyInsurance source) {
        //id = source.getId();
        /*
        owner = source.getOwner();
        insured = source.getInsured();
        beneficiary = source.getBeneficiary();
        */
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */

    public LifeInsurance toModelType() throws IllegalValueException {
        //final UUID id = UUID.fromString(this.id);
        //return new LifeInsurance(this.premium);
        return new LifeInsurance(this.premium);
    }
}