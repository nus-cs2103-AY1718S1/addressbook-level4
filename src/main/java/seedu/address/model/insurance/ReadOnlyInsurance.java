package seedu.address.model.insurance;

import java.util.EnumMap;
import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

//@@author OscarWang114
/**
 * Represents an insurance.
 */
public interface ReadOnlyInsurance {

    InsuranceName getInsuranceName();
    ObjectProperty<InsuranceName> insuranceNameProperty();
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
    ObjectProperty<Premium> premiumProperty();
    Premium getPremium();
    ObjectProperty<ContractFileName> contractFileNameProperty();
    ContractFileName getContractFileName();
    StringProperty signingDateStringProperty();
    String getSigningDateString();
    StringProperty expiryDateStringProperty();
    String getExpiryDateString();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyInsurance other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getId().equals(this.getId()) // state checks here onwards
                && other.getOwnerName().equals(this.getOwnerName())
                && other.getInsuredName().equals(this.getInsuranceName())
                && other.getBeneficiaryName().equals(this.getBeneficiaryName())
                && other.getPremium().equals(this.getPremium())
                && other.getSigningDateString().equals(this.getSigningDateString())
                && other.getExpiryDateString().equals(this.getExpiryDateString()))
                && other.getContractFileName().equals(this.getContractFileName());
    }

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
                .append(getPremium())
                .append("  Contract File: ")
                .append(getContractFileName())
                .append("  Signing Date: ")
                .append(getSigningDateString())
                .append("  Expiry Date: ")
                .append(getExpiryDateString());
        return builder.toString();
    }
}
