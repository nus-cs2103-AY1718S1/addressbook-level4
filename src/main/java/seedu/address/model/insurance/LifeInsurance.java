package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private DoubleProperty premium;
    private StringProperty contractPath;
    private StringProperty signingDate;
    private StringProperty expiryDate;

    public LifeInsurance(ReadOnlyPerson owner, ReadOnlyPerson insured, ReadOnlyPerson beneficiary,
                         Double premium, String contractPath, String signingDate, String expiryDate) {
        requireAllNonNull(owner, insured, beneficiary, premium, contractPath);
        this.owner = new SimpleObjectProperty<>(owner);
        this.insured = new SimpleObjectProperty<>(insured);
        this.beneficiary = new SimpleObjectProperty<>(beneficiary);
        this.premium = new SimpleDoubleProperty(premium);
        this.contractPath = new SimpleStringProperty(contractPath);
        this.signingDate = new SimpleStringProperty(signingDate);
        this.expiryDate = new SimpleStringProperty(expiryDate);
    }

    /**
     * Creates a copy of the given ReadOnlyInsurance.
     */
    public LifeInsurance(ReadOnlyInsurance source) {
        this(source.getOwner(), source.getInsured(), source.getBeneficiary(), source.getPremium(),
                source.getContractPath(), source.getSigningDate(), source.getExpiryDate());
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
    public DoubleProperty premiumProperty() {
        return premium;
    }

    @Override
    public Double getPremium() {
        return premium.get();
    }

    public void setContractPath(String contractPath) {
        this.contractPath.set(requireNonNull(contractPath));
    }

    @Override
    public StringProperty contractPathProperty() {
        return contractPath;
    }

    @Override
    public String getContractPath() {
        return contractPath.get();
    }

    public void setSigningDate(String signingDate) {
        this.signingDate.set(requireNonNull(signingDate));
    }

    @Override
    public StringProperty signingDateProperty() {
        return signingDate;
    }

    @Override
    public String getSigningDate() {
        return signingDate.get();
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate.set(requireNonNull(expiryDate));
    }

    @Override
    public StringProperty expiryDateProperty() {
        return expiryDate;
    }

    @Override
    public String getExpiryDate() {
        return expiryDate.get();
    }
}
