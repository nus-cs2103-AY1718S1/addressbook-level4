//@@author cqhchan
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyDatabase;

/** Indicates the AddressBook in the model has changed*/
public class DatabaseChangedEvent extends BaseEvent {

    public final ReadOnlyDatabase data;

    public DatabaseChangedEvent(ReadOnlyDatabase data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getAccountList().size();
    }
}
