package seedu.address.model.insurance;

import java.util.EnumMap;
import java.util.UUID;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

//@@author OscarWang114
/**
 * Represents an insurance.
 */
public interface ReadOnlyInsurance {

    ObjectProperty<UUID> idProperty();
    String getId();
    EnumMap getRoleToPersonNameMap();
    ObjectProperty<InsurancePerson> ownerProperty();
    InsurancePerson getOwner();
    ObjectProperty<InsurancePerson> insuredProperty();
    InsurancePerson getInsured();
    ObjectProperty<InsurancePerson> beneficiaryProperty();
    InsurancePerson getBeneficiary();
    DoubleProperty premiumProperty();
    Double getPremium();
    StringProperty premiumStringProperty();
    String getPremiumString();
    StringProperty contractPathProperty();
    String getContractPath();
    StringProperty signingDateProperty();
    String getSigningDate();
    StringProperty expiryDateProperty();
    String getExpiryDate();
}
