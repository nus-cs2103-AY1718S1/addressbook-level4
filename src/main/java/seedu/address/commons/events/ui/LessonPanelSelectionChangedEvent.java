package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.LessonCard;

/**
 * Represents a selection change in the Lesson List Panel
 */
public class LessonPanelSelectionChangedEvent extends BaseEvent {


    private final LessonCard newSelection;

    public LessonPanelSelectionChangedEvent(LessonCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public LessonCard getNewSelection() {
        return newSelection;
    }
}
