package seedu.address.model.insurance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Person;

/**
 * Represents a life insurance in LISA.
 * Guarantees: details are present and not null.
 */
public class LifeInsurance extends Insurance {

    private ObjectProperty<Person> owner;
    private ObjectProperty<Person> insured;
    private ObjectProperty<Person> beneficiary;
    private ObjectProperty<Double> premium;
    private ObjectProperty<File> contract;

    public LifeInsurance(Person owner, Person insured, Person beneficiary, Double premium, File contract) {
        requireAllNonNull(owner, insured, beneficiary, premium, contract);
        this.owner = new SimpleObjectProperty<>(owner);
        this.insured = new SimpleObjectProperty<>(insured);
        this.beneficiary = new SimpleObjectProperty<>(beneficiary);
        this.premium = new SimpleObjectProperty<>(premium);
        this.contract = new SimpleObjectProperty<>(contract);
    }
}
