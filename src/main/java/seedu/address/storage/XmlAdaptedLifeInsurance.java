package seedu.address.storage;

import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateParser;
import seedu.address.model.insurance.ContractFileName;
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
    private Double premium;
    @XmlElement(required = true)
    private String contractFileName;
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
        premium = source.getPremium().toDouble();
        contractFileName = source.getContractFileName().toString();
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
        final Premium premium = new Premium(this.premium.toString());
        final ContractFileName contractName = new ContractFileName(this.contractFileName);
        final DateParser dateParser = new DateParser();
        final LocalDate signingDate = dateParser.parse(this.signingDate);
        final LocalDate expiryDate = dateParser.parse(this.expiryDate);
        return new LifeInsurance(id, insuranceName, owner, insured, beneficiary, premium,
                contractName, signingDate, expiryDate);
    }
}
