package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.UUID;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateParser;
import seedu.address.model.person.Person;
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
    private EnumMap<Roles, String> roleToPersonNameMap;
    private ObjectProperty<InsurancePerson> owner;
    private ObjectProperty<InsurancePerson> insured;
    private ObjectProperty<InsurancePerson> beneficiary;
    private DoubleProperty premium;
    private StringProperty premiumString;
    private StringProperty contractPath;
    private StringProperty signingDateString;
    private StringProperty expiryDateString;
    //@@author Juxarius
    private LocalDate signingDate;
    private LocalDate expiryDate;
    //@@author

    /**
     * Constructor for {@code XmlAdaptedLifeInsurance.toModelType()}
     */
    public LifeInsurance(String owner, String insured, String beneficiary, Double premium,
                         String contractPath, String signingDateInput, String expiryDateInput)
            throws IllegalValueException {
        this.roleToPersonNameMap = new EnumMap<>(Roles.class);
        this.roleToPersonNameMap.put(Roles.OWNER, owner);
        this.roleToPersonNameMap.put(Roles.INSURED, insured);
        this.roleToPersonNameMap.put(Roles.BENEFICIARY, beneficiary);
        this.owner = new SimpleObjectProperty<>(new InsurancePerson(owner));
        this.insured = new SimpleObjectProperty<>(new InsurancePerson(insured));
        this.beneficiary = new SimpleObjectProperty<>(new InsurancePerson(beneficiary));
        this.premium = new SimpleDoubleProperty(premium);
        this.contractPath = new SimpleStringProperty(contractPath);
        this.signingDate = new DateParser().parse(signingDateInput);
        this.signingDateString = new SimpleStringProperty(this.signingDate.format(DateParser.DATE_FORMAT));
        this.expiryDate = new DateParser().parse(expiryDateInput);
        this.expiryDateString = new SimpleStringProperty(this.expiryDate.format(DateParser.DATE_FORMAT));
        this.premiumString = new SimpleStringProperty(this.getPremiumString());
    }

    /**
     * Constructor for {@code AddLifeInsuranceCommand}
     */
    public LifeInsurance(ReadOnlyPerson owner, ReadOnlyPerson insured, ReadOnlyPerson beneficiary, Double premium,
                         String contractPath, String signingDateInput, String expiryDateInput)
            throws IllegalValueException {
        requireAllNonNull(owner, insured, beneficiary, premium, contractPath);
        this.roleToPersonNameMap = new EnumMap<>(Roles.class);
        this.roleToPersonNameMap.put(Roles.OWNER, owner.getName().fullName);
        this.roleToPersonNameMap.put(Roles.INSURED, insured.getName().fullName);
        this.roleToPersonNameMap.put(Roles.BENEFICIARY, beneficiary.getName().fullName);
        this.owner = new SimpleObjectProperty<>(new InsurancePerson(owner));
        this.insured = new SimpleObjectProperty<>(new InsurancePerson(insured));
        this.beneficiary = new SimpleObjectProperty<>(new InsurancePerson(beneficiary));
        this.premium = new SimpleDoubleProperty(premium);
        this.contractPath = new SimpleStringProperty(contractPath);
        this.signingDate = new DateParser().parse(signingDateInput);
        this.signingDateString = new SimpleStringProperty(this.signingDate.format(DateParser.DATE_FORMAT));
        this.expiryDate = new DateParser().parse(expiryDateInput);
        this.expiryDateString = new SimpleStringProperty(this.expiryDate.format(DateParser.DATE_FORMAT));
        this.premiumString = new SimpleStringProperty(this.getPremiumString());
    }

    /**
     * Creates a copy of the given ReadOnlyInsurance.
     */
    public LifeInsurance(ReadOnlyInsurance source) {
        //TODO: fix
        if (source.ownerProperty() != null) {
            this.owner = new SimpleObjectProperty<>(source.getOwner());
        }
        if (source.insuredProperty() != null) {
            this.insured = new SimpleObjectProperty<>(source.getInsured());
        }
        if (source.beneficiaryProperty() != null) {
            this.beneficiary = new SimpleObjectProperty<>(source.getBeneficiary());
        }
        this.premium = new SimpleDoubleProperty(source.getPremium());
        this.premiumString = new SimpleStringProperty(this.getPremiumString());
        this.contractPath = new SimpleStringProperty(source.getContractPath());
        this.signingDateString = new SimpleStringProperty(source.getSigningDateString());
        this.expiryDateString = new SimpleStringProperty(source.getExpiryDateString());
        if (source.getRoleToPersonNameMap() != null) {
            this.roleToPersonNameMap = source.getRoleToPersonNameMap();
        }
    }

    public void setInsuranceRole(Person person) {
        String fullName = person.getName().fullName;
        roleToPersonNameMap.forEach((role, name) -> {
            if (name == fullName) {
                switch (role) {
                case OWNER:
                    this.owner = new SimpleObjectProperty<>(new InsurancePerson(person));
                    break;
                case INSURED:
                    this.insured = new SimpleObjectProperty<>(new InsurancePerson(person));
                    break;
                case BENEFICIARY:
                    this.beneficiary = new SimpleObjectProperty<>(new InsurancePerson(person));
                    break;
                default:
                    assert (false);
                }
            }
        });
    }

    @Override
    public ObjectProperty<UUID> idProperty() {
        return id;
    }

    @Override
    public String getId() {
        return id.toString();
    }

    @Override
    public EnumMap getRoleToPersonNameMap() {
        return roleToPersonNameMap;
    }

    public void setOwner(Person owner) {
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

    public void setInsured(Person insured) {
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

    public void setBeneficiary(Person beneficiary) {
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

    //@author Juxarius
    @Override
    public StringProperty premiumStringProperty() {
        return premiumString;
    }

    @Override
    public String getPremiumString() {
        return "S$ " + String.format("%.2f", premium.get());
    }
    //@author

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

    public void setSigningDateString(String signingDateString) {
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

    public void setExpiryDateString(String expiryDateString) {
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
}
