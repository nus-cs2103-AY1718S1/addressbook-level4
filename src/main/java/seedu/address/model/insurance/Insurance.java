package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;

import java.io.File;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.Person;

/**
 * Represents an insurance.
 */
public abstract class Insurance {

    private ObjectProperty<Person> owner;
    private ObjectProperty<Person> insured;
    private ObjectProperty<Person> beneficiary;
    private ObjectProperty<Double> premium;
    private ObjectProperty<File> contract;

    public void setOwner(Person owner) {
        this.owner.set(requireNonNull(owner));
    }

    public Person getOwner() {
        return owner.get();
    }

    public void setInsured(Person insured) {
        this.insured.set(requireNonNull(insured));
    }

    public Person getInsured() {
        return insured.get();
    }

    public void setBeneficiary(Person beneficiary) {
        this.beneficiary.set(requireNonNull(beneficiary));
    }

    public Person getBeneficiary() {
        return beneficiary.get();
    }

    public void setPremium(Double premium) {
        this.premium.set(requireNonNull(premium));
    }

    public Double getPremium() {
        return premium.get();
    }

    public void setContract(File contract) {
        this.contract.set(requireNonNull(contract));
    }

    public File getContract() {
        return contract.get();
    }
}
