package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.Person;

//@@author RSJunior37
/**
 * A utility class to help with building LifeInsurance objects.
 */
public class InsuranceBuilder {

    public static final String DEFAULT_INSURANCE_NAME = "Sample Insurance";
    public static final String DEFAULT_OWNER = "Alice Pauline";
    public static final String DEFAULT_BENEFICIARY = "Benson Meier";
    public static final String DEFAULT_INSURED = "Carl Kurz";
    public static final String DEFAULT_CONTRACT_PATH = "sample.pdf";
    public static final Double DEFAULT_PREMIUM = 1234.0;
    public static final String DEFAULT_SIGNING_DATE = "01 Nov 2000";
    public static final String DEFAULT_EXPIRY_DATE = "02 Dec 2011";

    private LifeInsurance insurance;

    public InsuranceBuilder() {
        try {
            this.insurance = new LifeInsurance(DEFAULT_INSURANCE_NAME, DEFAULT_OWNER,
                    DEFAULT_INSURED, DEFAULT_BENEFICIARY, DEFAULT_PREMIUM, DEFAULT_CONTRACT_PATH,
                    DEFAULT_SIGNING_DATE, DEFAULT_EXPIRY_DATE);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default insurance's values are invalid.");
        }
    }

    /**
     * Initializes the InsuranceBuilder with the data of {@code insuranceToCopy}.
     */
    public InsuranceBuilder(ReadOnlyInsurance insuranceToCopy) {
        this.insurance = new LifeInsurance(insuranceToCopy);
    }

    /**
     * Sets the Insurance Name of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withInsuranceName(String insuranceName) {
        this.insurance.setInsuranceName(insuranceName);
        return this;
    }

    /**
     * Sets the Owner of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withOwner(Person owner) {
        this.insurance.setOwner(owner);
        return this;
    }

    /**
     * Sets the Beneficiary of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withBeneficiary(Person beneficiary) {
        this.insurance.setBeneficiary(beneficiary);
        return this;
    }

    /**
     * Sets the Insured of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withInsured(Person insured) {
        this.insurance.setInsured(insured);
        return this;
    }

    /**
     * Sets the Premium of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withPremium(double premium) {
        this.insurance.setPremium(premium);
        return this;
    }

    /**
     * Sets the Contract Path of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withContractPath(String contractPath) {
        this.insurance.setContractPath(contractPath);
        return this;
    }

    /**
     * Sets the Signing Date of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withSigningDate(String signingDate) {
        try {
            this.insurance.setSigningDateString(signingDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Invalid signing date");
        }
        return this;
    }

    /**
     * Sets the Expiry Date of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withExpiryDate(String expiryDate) {
        try {
            this.insurance.setExpiryDateString(expiryDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Invalid expiry date");
        }
        return this;
    }

    public LifeInsurance build() {
        return this.insurance;
    }

}
