package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.util.AppUtil.PanelChoice;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    //@@author Juxarius
    public final PanelChoice panelChoice;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
        this.panelChoice = PanelChoice.PERSON;
    }

    public JumpToListRequestEvent(Index targetIndex, PanelChoice panelChoice) {
        this.targetIndex = targetIndex.getZeroBased();
        this.panelChoice = panelChoice;
    }
    //@@author

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
