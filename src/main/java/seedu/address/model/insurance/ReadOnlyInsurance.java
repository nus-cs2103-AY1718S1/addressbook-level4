package seedu.address.model.insurance;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents an insurance.
 */
public interface ReadOnlyInsurance {

    ObjectProperty<ReadOnlyPerson> ownerProperty();
    ReadOnlyPerson getOwner();
    ObjectProperty<ReadOnlyPerson> insuredProperty();
    ReadOnlyPerson getInsured();
    ObjectProperty<ReadOnlyPerson> beneficiaryProperty();
    ReadOnlyPerson getBeneficiary();
    DoubleProperty premiumProperty();
    Double getPremium();
    StringProperty contractPathProperty();
    String getContractPath();
    StringProperty signingDateProperty();
    String getSigningDate();
    StringProperty expiryDateProperty();
    String getExpiryDate();
}
