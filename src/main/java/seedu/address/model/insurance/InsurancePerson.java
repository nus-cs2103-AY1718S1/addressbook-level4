package seedu.address.model.insurance;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

//@@author OscarWang114
/**
 * Represents a person and his/her name in an insurance inside LISA.
 */
public class InsurancePerson {

    private ObjectProperty<Name> name;
    private Optional<ReadOnlyPerson> person;

    public InsurancePerson(String nameString) throws IllegalValueException {
        final Name name = new Name(nameString);
        this.name = new SimpleObjectProperty(name);
        this.person = Optional.empty();
    }

    public InsurancePerson(ReadOnlyPerson person) {
        this.name = new SimpleObjectProperty<>(person.getName());
        this.person = Optional.of(person);
    }

    public InsurancePerson(Name name) {
        this.name = new SimpleObjectProperty<>(name);
        this.person = Optional.empty();
    }

    public String getName() {
        return name.get().toString();
    }

    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    public void setPerson(ReadOnlyPerson person) {
        this.person = Optional.of(person);
    }

    public Optional<ReadOnlyPerson> getOptionalPerson() {
        return person;
    }
}
