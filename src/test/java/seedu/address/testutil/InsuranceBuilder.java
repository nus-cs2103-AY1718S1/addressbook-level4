package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.Person;

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
     * Sets the {@code Name} of the {@code LifeInsurance} that we are building.
     */
    public InsuranceBuilder withOwner(Person owner) {
        this.insurance.setOwner(owner);
        return this;
    }

    public InsuranceBuilder WithBeneficiary(Person beneficiary) {
        this.insurance.setBeneficiary(beneficiary);
        return this;
    }

    public InsuranceBuilder withInsured(Person insured) {
        this.insurance.setInsured(insured);
        return this;
    }

    public InsuranceBuilder withPremium(double premium) {
        this.insurance.setPremium(premium);
        return this;
    }

    public InsuranceBuilder withContractPath(String contractPath) {
        this.insurance.setContractPath(contractPath);
        return this;
    }

    public InsuranceBuilder withSigningDate(String signingDate) {
        try {
            this.insurance.setSigningDateString(signingDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Invalid signing date");
        }
        return this;
    }

    public InsuranceBuilder withExpiryDate(String expiryDate) {
        try {
            this.insurance.setExpiryDateString(expiryDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Invalid expiry date");
        }
        return this;
    }

    public InsuranceBuilder withExpiryDate(Person insured) {
        this.insurance.setInsured(insured);
        return this;
    }

    public LifeInsurance build() {
        return this.insurance;
    }

}
