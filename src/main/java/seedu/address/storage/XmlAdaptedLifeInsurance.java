package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedLifeInsurance {
    @XmlElement(required = true)
    private String owner;
    @XmlElement(required = true)
    private String insured;
    @XmlElement(required = true)
    private String beneficiary;
    @XmlElement(required = true)
    private Double premium;
    @XmlElement(required = true)
    private String contractPath;
    @XmlElement(required = true)
    private String signingDate;
    @XmlElement(required = true)
    private String expiryDate;

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
        owner = source.getOwner().getName().fullName;
        insured = source.getInsured().getName().fullName;
        beneficiary = source.getBeneficiary().getName().fullName;
        premium = source.getPremium();
        contractPath = source.getContractPath();
        signingDate = source.getSigningDate();
        expiryDate = source.getExpiryDate();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */

    public LifeInsurance toModelType() throws IllegalValueException {
        return new LifeInsurance(this.owner, this.insured, this.beneficiary, this.premium,
                this.contractPath, this.signingDate, this.expiryDate);
    }
}