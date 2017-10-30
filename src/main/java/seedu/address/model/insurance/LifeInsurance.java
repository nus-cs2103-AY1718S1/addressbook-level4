package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

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

    /**
     * Represents the three person roles inside a insurance in LISA.
     */
    private enum LinkedRole { OWNER, INSURED, BENEFICIARY }
    private ObjectProperty<UUID> id;
    private EnumMap<LinkedRole, String> roleToPersonMap;
    private ObjectProperty<ReadOnlyPerson> owner;
    private ObjectProperty<ReadOnlyPerson> insured;
    private ObjectProperty<ReadOnlyPerson> beneficiary;
    private DoubleProperty premium;
    private StringProperty premiumString;
    private StringProperty contractPath;
    private StringProperty signingDate;
    private StringProperty expiryDate;

    public LifeInsurance() {
        this.roleToPersonMap = new EnumMap<>(LinkedRole.class);
    }

    public LifeInsurance(String owner, String insured, String beneficiary, Double premium,
                         String contractPath, String signingDate, String expiryDate) {
        this();
        this.roleToPersonMap.put(LinkedRole.OWNER, owner);
        this.roleToPersonMap.put(LinkedRole.INSURED, insured);
        this.roleToPersonMap.put(LinkedRole.BENEFICIARY, beneficiary);
        this.premium = new SimpleDoubleProperty(premium);
        this.contractPath = new SimpleStringProperty(contractPath);
        this.signingDate = new SimpleStringProperty(signingDate);
        this.expiryDate = new SimpleStringProperty(expiryDate);
        this.premiumString = new SimpleStringProperty(this.getPremiumString());
    }

    public LifeInsurance(ReadOnlyPerson owner, ReadOnlyPerson insured, ReadOnlyPerson beneficiary,
                         Double premium, String contractPath, String signingDate, String expiryDate) {
        requireAllNonNull(owner, insured, beneficiary, premium, contractPath);
        this.id = new SimpleObjectProperty<>(UUID.randomUUID());
        this.owner = new SimpleObjectProperty<>(owner);
        this.insured = new SimpleObjectProperty<>(insured);
        this.beneficiary = new SimpleObjectProperty<>(beneficiary);
        this.premium = new SimpleDoubleProperty(premium);
        this.contractPath = new SimpleStringProperty(contractPath);
        this.signingDate = new SimpleStringProperty(signingDate);
        this.expiryDate = new SimpleStringProperty(expiryDate);
        this.premiumString = new SimpleStringProperty(this.getPremiumString());
    }

    /**
     * Creates a copy of the given ReadOnlyInsurance.
     */
    public LifeInsurance(ReadOnlyInsurance source) {
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
        this.signingDate = new SimpleStringProperty(source.getSigningDate());
        this.expiryDate = new SimpleStringProperty(source.getExpiryDate());
        if (source.getRoleToPersonMap() != null) {
            this.roleToPersonMap = source.getRoleToPersonMap();
        }
    }

    public void setInsurancePerson(Person person) {
        String name = person.getName().fullName;
        Iterator it = this.roleToPersonMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals(name)) {
                switch (pair.getKey().toString()) {
                case "OWNER":
                    this.owner = new SimpleObjectProperty<>(person);
                    break;
                case "INSURED":
                    this.insured = new SimpleObjectProperty<>(person);
                    break;
                case "BENEFICIARY":
                    this.beneficiary = new SimpleObjectProperty<>(person);
                    break;
                default:
                    assert(false);
                }
            }
        }
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
    public EnumMap getRoleToPersonMap() {
        return roleToPersonMap;
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
        return beneficiary;
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

    @Override
    public StringProperty premiumStringProperty() {
        return premiumString;
    }

    @Override
    public String getPremiumString() {
        return "S$ " + String.format("%.2f", premium.get());
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
