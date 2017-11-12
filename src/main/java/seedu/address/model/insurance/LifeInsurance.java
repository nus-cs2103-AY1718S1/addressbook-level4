package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateParser;
import seedu.address.model.person.Name;

//@@author OscarWang114
/**
 * Represents a life insurance in LISA.
 * Guarantees: details are present and not null.
 */
public class LifeInsurance implements ReadOnlyInsurance {

    /**
     * Represents the three person-roles inside an insurance in LISA.
     */
    enum Roles { OWNER, INSURED, BENEFICIARY }
    private ObjectProperty<UUID> id;
    private ObjectProperty<InsuranceName> insuranceName;
    private EnumMap<Roles, String> roleToPersonNameMap;
    private ObjectProperty<InsurancePerson> owner;
    private ObjectProperty<InsurancePerson> insured;
    private ObjectProperty<InsurancePerson> beneficiary;
    private ObjectProperty<Premium> premium;
    private ObjectProperty<ContractFileName> contractFileName;

    //@@author Juxarius
    private LocalDate signingDate;
    private LocalDate expiryDate;
    private StringProperty signingDateString;
    private StringProperty expiryDateString;
    //@@author

    //@author OscarWang114
    /**
     * Constructor for {@code XmlAdaptedLifeInsurance.toModelType()}
     */
    public LifeInsurance(UUID id, InsuranceName insuranceName, InsurancePerson owner, InsurancePerson insured,
                         InsurancePerson beneficiary, Premium premium, ContractFileName contractFileName,
                         LocalDate signingDate, LocalDate expiryDate)
            throws IllegalValueException {
        this.id = new SimpleObjectProperty<>(id);
        this.insuranceName = new SimpleObjectProperty<>(insuranceName);
        this.roleToPersonNameMap = new EnumMap<>(Roles.class);
        this.roleToPersonNameMap.put(Roles.OWNER, owner.getName());
        this.roleToPersonNameMap.put(Roles.INSURED, insured.getName());
        this.roleToPersonNameMap.put(Roles.BENEFICIARY, beneficiary.getName());
        this.owner = new SimpleObjectProperty<>(owner);
        this.insured = new SimpleObjectProperty<>(insured);
        this.beneficiary = new SimpleObjectProperty<>(beneficiary);
        this.premium = new SimpleObjectProperty<>(premium);
        this.contractFileName = new SimpleObjectProperty(contractFileName);
        this.signingDate = signingDate;
        this.signingDateString = new SimpleStringProperty(this.signingDate.format(DateParser.DATE_FORMAT));
        this.expiryDate = expiryDate;
        this.expiryDateString = new SimpleStringProperty(this.expiryDate.format(DateParser.DATE_FORMAT));
    }

    /**
     * Constructor for {@code AddLifeInsuranceCommand}
     */
    public LifeInsurance(InsuranceName insuranceName, InsurancePerson owner, InsurancePerson insured,
                         InsurancePerson beneficiary, Premium premium, ContractFileName contractFileName,
                         LocalDate signingDate, LocalDate expiryDate) {
        requireAllNonNull(owner, insured, beneficiary, premium, contractFileName, signingDate, expiryDate);
        this.id = new SimpleObjectProperty<>(UUID.randomUUID());
        this.insuranceName = new SimpleObjectProperty<>(insuranceName);
        this.roleToPersonNameMap = new EnumMap<>(Roles.class);
        this.roleToPersonNameMap.put(Roles.OWNER, owner.getName());
        this.roleToPersonNameMap.put(Roles.INSURED, insured.getName());
        this.roleToPersonNameMap.put(Roles.BENEFICIARY, beneficiary.getName());
        this.owner = new SimpleObjectProperty<>(owner);
        this.insured = new SimpleObjectProperty<>(insured);
        this.beneficiary = new SimpleObjectProperty<>(beneficiary);
        this.premium = new SimpleObjectProperty<>(premium);
        this.contractFileName = new SimpleObjectProperty<>(contractFileName);
        this.signingDate = signingDate;
        this.signingDateString = new SimpleStringProperty(this.signingDate.format(DateParser.DATE_FORMAT));
        this.expiryDate = expiryDate;
        this.expiryDateString = new SimpleStringProperty(this.expiryDate.format(DateParser.DATE_FORMAT));
    }

    /**
     * Creates a copy of the given ReadOnlyInsurance.
     */
    public LifeInsurance(ReadOnlyInsurance source) {
        this(source.getInsuranceName(), source.getOwner(), source.getInsured(), source.getBeneficiary(),
                source.getPremium(), source.getContractFileName(), source.getSigningDate(), source.getExpiryDate());
        this.id = new SimpleObjectProperty<>(source.idProperty().get());
    }

    /**
     * Resets the value of the owner, insured, beneficiary of this insurance. Every new {@code InsurancePerson}
     * object is constructed with the name field assigned as {@code Name} and the person field
     * assigned as {@code Optional.empty()}.
     */
    public void resetAllInsurancePersons() {
        final Name ownerName = this.owner.get().nameProperty().get();
        final Name insuredName = this.insured.get().nameProperty().get();
        final Name beneficiaryName = this.beneficiary.get().nameProperty().get();
        this.owner = new SimpleObjectProperty<>(new InsurancePerson(ownerName));
        this.insured = new SimpleObjectProperty<>(new InsurancePerson(insuredName));
        this.beneficiary = new SimpleObjectProperty<>(new InsurancePerson(beneficiaryName));

    }

    public void setId(UUID id) {
        this.id = new SimpleObjectProperty<>(id);
    }

    @Override
    public ObjectProperty<UUID> idProperty() {
        return id;
    }

    @Override
    public UUID getId() {
        return id.get();
    }

    public void setInsuranceName(InsuranceName insuranceName) {
        this.insuranceName = new SimpleObjectProperty<>(insuranceName);
    }

    @Override
    public ObjectProperty<InsuranceName> insuranceNameProperty() {
        return insuranceName;
    }

    @Override
    public InsuranceName getInsuranceName() {
        return insuranceName.get();
    }

    @Override
    public EnumMap getRoleToPersonNameMap() {
        return roleToPersonNameMap;
    }

    public void setOwner(InsurancePerson owner) {
        requireNonNull(owner);
        this.owner = new SimpleObjectProperty<>(owner);
    }

    @Override
    public ObjectProperty<InsurancePerson> ownerProperty() {
        return owner;
    }

    @Override
    public InsurancePerson getOwner() {
        return owner.get();
    }

    @Override
    public String getOwnerName() {
        return owner.get().getName();
    }

    public void setInsured(InsurancePerson insured) {
        requireNonNull(insured);
        this.insured = new SimpleObjectProperty<>(insured);
    }

    @Override
    public ObjectProperty<InsurancePerson> insuredProperty() {
        return insured;
    }

    @Override
    public InsurancePerson getInsured() {
        return insured.get();
    }

    @Override
    public String getInsuredName() {
        return insured.get().getName();
    }

    public void setBeneficiary(InsurancePerson beneficiary) {
        requireNonNull(beneficiary);
        this.beneficiary = new SimpleObjectProperty<>(beneficiary);
    }

    @Override
    public ObjectProperty<InsurancePerson> beneficiaryProperty() {
        return beneficiary;
    }

    @Override
    public InsurancePerson getBeneficiary() {
        return beneficiary.get();
    }

    @Override
    public String getBeneficiaryName() {
        return beneficiary.get().getName();
    }

    public void setPremium(Premium premium) {
        this.premium.set(requireNonNull(premium));
    }

    @Override
    public ObjectProperty<Premium> premiumProperty() {
        return premium;
    }

    @Override
    public Premium getPremium() {
        return premium.get();
    }

    public void setContractFileName(ContractFileName contractFileName) {
        this.contractFileName = new SimpleObjectProperty<>(contractFileName);
    }

    @Override
    public ObjectProperty<ContractFileName> contractFileNameProperty() {
        return contractFileName;
    }

    @Override
    public ContractFileName getContractFileName() {
        return contractFileName.get();
    }

    public void setSigningDate(LocalDate signingDate) {
        this.signingDate = signingDate;
        setSigningDateString(signingDate.format(DateParser.DATE_FORMAT));
    }

    @Override
    public LocalDate getSigningDate() {
        return signingDate;
    }

    private void setSigningDateString(String signingDateString) {
        this.signingDateString = new SimpleStringProperty(signingDateString);
    }

    @Override
    public StringProperty signingDateStringProperty() {
        return signingDateString;
    }

    @Override
    public String getSigningDateString() {
        return signingDateString.get();
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        setExpiryDateString(expiryDate.format(DateParser.DATE_FORMAT));
    }

    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    private void setExpiryDateString(String expiryDateString) {
        this.expiryDateString = new SimpleStringProperty(expiryDateString);
    }

    @Override
    public StringProperty expiryDateStringProperty() {
        return expiryDateString;
    }

    @Override
    public String getExpiryDateString() {
        return expiryDateString.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LifeInsurance // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyInsurance) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }
}

