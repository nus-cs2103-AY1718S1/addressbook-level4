package seedu.address.model.insurance;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a person and his/her name in an insurance in LISA.
 */
public class InsurancePerson {

    //TODO: Change from String to Name
    private String name;
    private ReadOnlyPerson person;

    public InsurancePerson(String name) {
        this.name = name;
    }

    public InsurancePerson(ReadOnlyPerson person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
