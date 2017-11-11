package seedu.address.storage;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insurance.InsuranceName;
import seedu.address.model.insurance.InsurancePerson;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.Premium;
import seedu.address.model.insurance.ReadOnlyInsurance;

//@@author OscarWang114
/**
 * JAXB-friendly version of the LifeInsurance.
 */
public class XmlAdaptedLifeInsurance {
    @XmlElement(required = true)
    private String id;
    @XmlElement(required = true)
    private String insuranceName;
    @XmlElement(required = true)
    private String owner;
    @XmlElement(required = true)
    private String insured;
    @XmlElement(required = true)
    private String beneficiary;
    @XmlElement(required = true)
    private String premium;
    @XmlElement(required = true)
    private String contractName;
    @XmlElement(required = true)
    private String signingDate;
    @XmlElement(required = true)
    private String expiryDate;

    /**
     * Constructs an XmlAdaptedLifeInsurance.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLifeInsurance() {}


    /**
     * Converts a given LifeInsurance into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLifeInsurance
     */
    public XmlAdaptedLifeInsurance(ReadOnlyInsurance source) {
        id = source.getId().toString();
        insuranceName = source.getInsuranceName().toString();
        owner = source.getOwner().getName();
        insured = source.getInsured().getName();
        beneficiary = source.getBeneficiary().getName();
        premium = source.getPremium().toString();
        contractName = source.getContractName();
        signingDate = source.getSigningDateString();
        expiryDate = source.getExpiryDateString();
    }

    /**
     * Converts this jaxb-friendly adapted life insurance object into the model's LifeInsurance object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted life insurance
     */

    public LifeInsurance toModelType() throws IllegalValueException {
        final UUID id = UUID.fromString(this.id);
        final InsuranceName insuranceName = new InsuranceName(this.insuranceName);
        final InsurancePerson owner = new InsurancePerson(this.owner);
        final InsurancePerson insured = new InsurancePerson(this.insured);
        final InsurancePerson beneficiary = new InsurancePerson(this.beneficiary);
        final Premium premium = new Premium(this.premium);
        return new LifeInsurance(id, insuranceName, owner, insured, beneficiary, premium,
                this.contractName, this.signingDate, this.expiryDate);
    }
}
