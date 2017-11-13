//@@author conantteo
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.tag.Tag;

/** Indicates that users want to search for this particular {@code tag} */
public class SearchTagEvent extends BaseEvent {

    public final Tag tag;

    public SearchTagEvent(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
