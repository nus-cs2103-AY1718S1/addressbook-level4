package seedu.address.model.insurance;

import seedu.address.model.person.ReadOnlyPerson;

public class InsurancePerson {

    //TODO: Change from String to Name
    String name;
    ReadOnlyPerson person;

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
