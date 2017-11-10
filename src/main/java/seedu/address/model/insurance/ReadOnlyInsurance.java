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

    String getInsuranceName();
    StringProperty insuranceNameProperty();
    ObjectProperty<UUID> idProperty();
    UUID getId();
    EnumMap getRoleToPersonNameMap();
    ObjectProperty<InsurancePerson> ownerProperty();
    InsurancePerson getOwner();
    String getOwnerName();
    ObjectProperty<InsurancePerson> insuredProperty();
    InsurancePerson getInsured();
    String getInsuredName();
    ObjectProperty<InsurancePerson> beneficiaryProperty();
    InsurancePerson getBeneficiary();
    String getBeneficiaryName();
    DoubleProperty premiumProperty();
    Double getPremium();
    StringProperty premiumStringProperty();
    String getPremiumString();
    StringProperty contractPathProperty();
    String getContractPath();
    StringProperty signingDateStringProperty();
    String getSigningDateString();
    StringProperty expiryDateStringProperty();
    String getExpiryDateString();

    /**
     * Formats the insurance as text, showing all the details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getInsuranceName())
                .append("  Owner: ")
                .append(getOwnerName())
                .append("  Insured: ")
                .append(getInsuredName())
                .append("  Beneficiary: ")
                .append(getBeneficiaryName())
                .append(" \nPremium: ")
                .append(getPremiumString())
                .append("  Contract File: ")
                .append(getContractPath())
                .append("  Signing Date: ")
                .append(getSigningDateString())
                .append("  Expiry Date: ")
                .append(getExpiryDateString());
        return builder.toString();
    }
}
