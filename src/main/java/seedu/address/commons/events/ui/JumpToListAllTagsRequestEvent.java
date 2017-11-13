package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author pohjie
/**
 * Indicates a request to jump to the list of tags
 */

public class JumpToListAllTagsRequestEvent extends BaseEvent {

    public JumpToListAllTagsRequestEvent () { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
