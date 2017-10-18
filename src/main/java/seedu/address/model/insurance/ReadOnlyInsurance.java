package seedu.address.model.insurance;

import java.time.LocalDate;

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
    ObjectProperty<LocalDate> signingDateProperty();
    LocalDate getSigningDate();
    ObjectProperty<LocalDate> expiryDateProperty();
    LocalDate getExpiryDate();
}
