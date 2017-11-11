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
import seedu.address.model.person.ReadOnlyPerson;

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
        requireAllNonNull(owner, insured, beneficiary, premium, this.contractFileName);
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
        this.id = new SimpleObjectProperty<>(source.idProperty().get());
        this.insuranceName = new SimpleObjectProperty<>(source.getInsuranceName());
        this.roleToPersonNameMap = new EnumMap<>(Roles.class);
        this.roleToPersonNameMap.put(Roles.OWNER, source.getOwner().getName());
        this.roleToPersonNameMap.put(Roles.INSURED, source.getInsured().getName());
        this.roleToPersonNameMap.put(Roles.BENEFICIARY, source.getBeneficiary().getName());
        this.owner = new SimpleObjectProperty<>(source.getOwner());
        this.insured = new SimpleObjectProperty<>(source.getInsured());
        this.beneficiary = new SimpleObjectProperty<>(source.getBeneficiary());
        this.premium = new SimpleObjectProperty<>(source.getPremium());
        this.contractFileName = new SimpleObjectProperty<>(source.getContractFileName());
        this.signingDateString = new SimpleStringProperty(source.getSigningDateString());
        this.expiryDateString = new SimpleStringProperty(source.getExpiryDateString());
    }

    /**
     * Resets the value of the owner, insured, beneficiary of this insurance. Every new {@code InsurancePerson}
     * object is constructed with the {@code name} field assigned and the {@code person} field unassigned.
     */
    public void resetAllInsurancePerson() {
        String ownerName = this.roleToPersonNameMap.get(Roles.OWNER);
        String insuredName = this.roleToPersonNameMap.get(Roles.INSURED);
        String beneficiaryName = this.roleToPersonNameMap.get(Roles.BENEFICIARY);
        this.owner = new SimpleObjectProperty<>(new InsurancePerson(ownerName));
        this.insured = new SimpleObjectProperty<>(new InsurancePerson(insuredName));
        this.beneficiary = new SimpleObjectProperty<>(new InsurancePerson(beneficiaryName));

    }

    @Override
    public ObjectProperty<UUID> idProperty() {
        return id;
    }

    @Override
    public UUID getId() {
        return id.get();
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

    public void setOwner(ReadOnlyPerson owner) {
        requireNonNull(owner);
        this.owner.get().setPerson(owner);
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

    public void setInsured(ReadOnlyPerson insured) {
        requireNonNull(insured);
        this.insured.get().setPerson(insured);
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

    public void setBeneficiary(ReadOnlyPerson beneficiary) {
        requireNonNull(beneficiary);
        this.beneficiary.get().setPerson(beneficiary);
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

    //@@author OscarWang114
    @Override
    public ObjectProperty<ContractFileName> contractFileNameProperty() {
        return contractFileName;
    }

    @Override
    public ContractFileName getContractFileName() {
        return contractFileName.get();
    }

    public void setSigningDateString(String signingDateString) throws IllegalValueException {
        this.signingDate = new DateParser().parse(signingDateString);
        this.signingDateString.set(requireNonNull(signingDateString));
    }

    @Override
    public StringProperty signingDateStringProperty() {
        return signingDateString;
    }

    @Override
    public String getSigningDateString() {
        return signingDateString.get();
    }

    public void setExpiryDateString(String expiryDateString) throws IllegalValueException {
        this.expiryDate = new DateParser().parse(expiryDateString);
        this.expiryDateString.set(requireNonNull(expiryDateString));
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

