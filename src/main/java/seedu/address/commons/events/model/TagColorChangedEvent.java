package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.configs.ChangeTagColorCommand;
import seedu.address.model.tag.Tag;

//@@author yunpengn
/**
 * Indicates the color of some tag(s) has been changed.
 */
public class TagColorChangedEvent extends BaseEvent {
    private Tag tag;
    private String color;

    public TagColorChangedEvent(Tag tag, String color) {
        this.tag = tag;
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format(ChangeTagColorCommand.MESSAGE_SUCCESS, tag, color);
    }
}
