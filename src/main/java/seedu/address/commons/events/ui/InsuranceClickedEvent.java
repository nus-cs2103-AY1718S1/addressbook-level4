package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;

//@@author Juxarius
/**
 * Represents a click on one of the names in Insurance Profile
 */
public class InsuranceClickedEvent extends BaseEvent {


    private final ReadOnlyInsurance insurance;

    public InsuranceClickedEvent(ReadOnlyInsurance insurance) {
        this.insurance = insurance;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }


    public ReadOnlyInsurance getInsurance() {
        return insurance;
    }
}

