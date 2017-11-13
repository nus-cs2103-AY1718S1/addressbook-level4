package seedu.address.commons.events.ui;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.insurance.InsurancePerson;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

//@@author RSJunior37
/**
 * Represents a click on one of the names in Insurance Profile
 */
public class PersonNameClickedEvent extends BaseEvent {


    private final InsurancePerson target;

    public PersonNameClickedEvent(InsurancePerson target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getName() {
        return target.getName();
    }

    //@@author OscarWang114
    public Optional<ReadOnlyPerson> getPerson() {
        return target.getOptionalPerson();
    }

    public ObjectProperty<Name> getPersonName() {
        return target.nameProperty();
    }
    //@@author
}

