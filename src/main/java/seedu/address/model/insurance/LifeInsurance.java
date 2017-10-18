package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a life insurance in LISA.
 * Guarantees: details are present and not null.
 */
public class LifeInsurance implements ReadOnlyInsurance {

    private ObjectProperty<ReadOnlyPerson> owner;
    private ObjectProperty<ReadOnlyPerson> insured;
    private ObjectProperty<ReadOnlyPerson> beneficiary;
    private ObjectProperty<Double> premium;
    private ObjectProperty<File> contract;
    private ObjectProperty<LocalDate> signingDate;
    private ObjectProperty<LocalDate> expiryDate;

    public LifeInsurance(Person owner, Person insured, Person beneficiary, Double premium,
                         File contract, LocalDate signingDate, LocalDate expiryDate) {
        requireAllNonNull(owner, insured, beneficiary, premium, contract);
        this.owner = new SimpleObjectProperty<>(owner);
        this.insured = new SimpleObjectProperty<>(insured);
        this.beneficiary = new SimpleObjectProperty<>(beneficiary);
        this.premium = new SimpleObjectProperty<>(premium);
        this.contract = new SimpleObjectProperty<>(contract);
        this.signingDate = new SimpleObjectProperty<>(signingDate);
        this.expiryDate = new SimpleObjectProperty<>(expiryDate);
    }

    public void setOwner(Person owner) {
        this.owner.set(requireNonNull(owner));
    }

    @Override
    public ObjectProperty<ReadOnlyPerson> ownerProperty() {
        return owner;
    }

    @Override
    public ReadOnlyPerson getOwner() {
        return owner.get();
    }

    public void setInsured(Person insured) {
        this.insured.set(requireNonNull(insured));
    }

    @Override
    public ObjectProperty<ReadOnlyPerson> insuredProperty() {
        return insured;
    }

    @Override
    public ReadOnlyPerson getInsured() {
        return insured.get();
    }

    public void setBeneficiary(Person beneficiary) {
        this.beneficiary.set(requireNonNull(beneficiary));
    }

    @Override
    public ObjectProperty<ReadOnlyPerson> beneficiaryProperty() {
        return owner;
    }

    @Override
    public ReadOnlyPerson getBeneficiary() {
        return beneficiary.get();
    }

    public void setPremium(Double premium) {
        this.premium.set(requireNonNull(premium));
    }

    @Override
    public ObjectProperty<Double> premiumProperty() {
        return premium;
    }

    @Override
    public Double getPremium() {
        return premium.get();
    }

    public void setContract(File contract) {
        this.contract.set(requireNonNull(contract));
    }

    @Override
    public ObjectProperty<File> contractProperty() {
        return contract;
    }

    @Override
    public File getContract() {
        return contract.get();
    }

    public void setSigningDate(LocalDate signingDate) {
        this.signingDate.set(requireNonNull(signingDate));
    }

    @Override
    public ObjectProperty<LocalDate> signingDateProperty() {
        return signingDate;
    }

    @Override
    public LocalDate getSigningDate() {
        return signingDate.get();
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate.set(requireNonNull(expiryDate));
    }

    @Override
    public ObjectProperty<LocalDate> expiryDateProperty() {
        return expiryDate;
    }

    @Override
    public LocalDate getExpiryDate() {
        return expiryDate.get();
    }
}
