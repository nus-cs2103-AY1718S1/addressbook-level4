package seedu.address.model.insurance;

import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.model.person.ReadOnlyPerson;

//@@author OscarWang114
/**
 * Represents a person and his/her name in an insurance in LISA.
 */
public class InsurancePerson {

    //TODO: Change from String to Name
    private StringProperty name;
    private Optional<ReadOnlyPerson> person;

    public InsurancePerson(String name) {
        this.person = Optional.empty();
        this.name = new SimpleStringProperty(name);
    }

    public InsurancePerson(ReadOnlyPerson person) {
        this.person = Optional.of(person);
        this.name = new SimpleStringProperty(person.getName().fullName);
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }
    public String getName() {
        return name.getValue();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setPerson(ReadOnlyPerson person) {
        this.person = Optional.of(person);
    }
    //TODO: Fix
    public Optional<ReadOnlyPerson> getOptionalPerson() {
        return person;
    }
}
