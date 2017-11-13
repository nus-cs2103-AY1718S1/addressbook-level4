package seedu.address.commons.events.model;

import java.util.Set;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.tag.Tag;

//@@author nbriannl
/** Indicates that the master tag list has an unused tag not used by any person*/
public class MasterTagListHasAnUnusedTagEvent extends BaseEvent {

    public final Set<Tag> outdatedTags;

    public MasterTagListHasAnUnusedTagEvent (Set<Tag> outdatedTags) {
        this.outdatedTags = outdatedTags;
    }

    @Override
    public String toString() {
        return "The tag list is outdated. With outdated tags: " + outdatedTags.toString();
    }
}
