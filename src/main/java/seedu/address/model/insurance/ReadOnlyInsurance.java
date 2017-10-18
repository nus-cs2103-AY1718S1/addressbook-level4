package seedu.address.model.insurance;

import java.io.File;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
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
    ObjectProperty<Double> premiumProperty();
    Double getPremium();
    ObjectProperty<File> contractProperty();
    File getContract();
    ObjectProperty<LocalDate> signingDateProperty();
    LocalDate getSigningDate();
    ObjectProperty<LocalDate> expiryDateProperty();
    LocalDate getExpiryDate();
}
