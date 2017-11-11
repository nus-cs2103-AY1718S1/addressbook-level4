package seedu.address.testutil;

import java.time.LocalDate;
import java.util.UUID;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateParser;
import seedu.address.model.insurance.ContractFileName;
import seedu.address.model.insurance.InsuranceName;
import seedu.address.model.insurance.InsurancePerson;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.Premium;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.ReadOnlyPerson;

//@@author OscarWang114
/**
 * A utility class to help with building LifeInsurance objects.
 */
public class LifeInsuranceBuilder {

    public static final String DEFAULT_UUID = "6e5a4761-f578-4311-8ccc-d3f258c9e461";
    public static final String DEFAULT_NAME = "Term Life";
    public static final String DEFAULT_OWNER = "Alex Yeoh";
    public static final String DEFAULT_INSURED = "Bernice Yu";
    public static final String DEFAULT_BENEFICIARY = "John Doe";
    public static final String DEFAULT_PREMIUM = "600";
    public static final String DEFAULT_SIGNGING_DATE = "01 11 2017";
    public static final String DEFAULT_EXPIRY_DATE = "01 11 2037";
    public static final String DEFAULT_CONTRACT_FILE_NAME = "AlexYeoh-TermLife";

    private LifeInsurance lifeInsurance;

    public LifeInsuranceBuilder() {
        try {
            UUID defaultId = UUID.fromString(DEFAULT_UUID);
            InsuranceName defaultName = new InsuranceName(DEFAULT_NAME);
            InsurancePerson defaultOwner = new InsurancePerson(DEFAULT_OWNER);
            InsurancePerson defaultInsured = new InsurancePerson(DEFAULT_INSURED);
            InsurancePerson defaultBeneficiary = new InsurancePerson(DEFAULT_BENEFICIARY);
            Premium defaultPremium = new Premium(DEFAULT_PREMIUM);
            DateParser dateParser = new DateParser();
            LocalDate defaultSigningDate = dateParser.parse(DEFAULT_SIGNGING_DATE);
            LocalDate defaultExpiryDate = dateParser.parse(DEFAULT_EXPIRY_DATE);
            ContractFileName contractFileName = new ContractFileName(DEFAULT_CONTRACT_FILE_NAME);
            this.lifeInsurance = new LifeInsurance(defaultId, defaultName, defaultOwner, defaultInsured,
                    defaultBeneficiary, defaultPremium, contractFileName, defaultSigningDate, defaultExpiryDate);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default life insurance's values are invalid.");
        }
    }

    /**
     * Initializes the LifeInsuranceBuilder with the data of {@code lifeInsuranceToCopy}.
     */
    public LifeInsuranceBuilder(ReadOnlyInsurance lifeInsuranceToCopy) {
        this.lifeInsurance = new LifeInsurance(lifeInsuranceToCopy);
    }

    /**
     * Sets the {@code UUID} of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withId(String id) {
        try {
            this.lifeInsurance.setId(UUID.fromString(id));
        } catch (IllegalArgumentException ive) {
            throw new IllegalArgumentException("id is expected to be unique");
        }
        return this;
    }

    /**
     * Sets the {@code InsuranceName} of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withInsuranceName(String name) {
        try {
            this.lifeInsurance.setInsuranceName(new InsuranceName(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} owner of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withOwner(String owner) {
        try {
            this.lifeInsurance.setOwner(new InsurancePerson(owner));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("owner is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} owner of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withOwner(ReadOnlyPerson owner) {
        this.lifeInsurance.setOwner(new InsurancePerson(owner));
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} insured of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withInsured(String insured) {
        try {
            this.lifeInsurance.setInsured(new InsurancePerson(insured));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("insured is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} insured of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withInsured(ReadOnlyPerson insured) {
        this.lifeInsurance.setInsured(new InsurancePerson(insured));
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} beneficiary of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withBeneficiary(String beneficiary) {
        try {
            this.lifeInsurance.setBeneficiary(new InsurancePerson(beneficiary));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("beneficiary is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code InsurancePerson} beneficiary of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withBeneficiary(ReadOnlyPerson beneficiary) {
        this.lifeInsurance.setBeneficiary(new InsurancePerson(beneficiary));
        return this;
    }

    /**
     * Sets the {@code Premium} of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withPremium(String premium) {
        try {
            this.lifeInsurance.setPremium(new Premium(premium));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("premium is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code LocalDate} signingDate of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withSigningDate(String date) {
        DateParser dateParser = new DateParser();
        try {
            this.lifeInsurance.setSigningDate(dateParser.parse(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("signing date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code LocalDate} expiryDate of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withExpiryDate(String date) {
        DateParser dateParser = new DateParser();
        try {
            this.lifeInsurance.setExpiryDate(dateParser.parse(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("expiry date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code ContractFileName} of the {@code LifeInsurance} that we are building.
     */
    public LifeInsuranceBuilder withContractFileName(String contractFileName) {
        try {
            this.lifeInsurance.setContractFileName(new ContractFileName(contractFileName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("contract file name is expected to be unique.");
        }
        return this;
    }

    public LifeInsurance build() {
        return this.lifeInsurance;
    }

}
