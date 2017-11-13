package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@@author yewshengkai
/**
 * Indicates a request to jump to the list of themes
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    public final int targetIndex;

    public ChangeThemeRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getOneBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
