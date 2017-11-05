package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.LessonListCard;

/**
 * Represents a selection change in the Lesson List Panel
 */
public class LessonPanelSelectionChangedEvent extends BaseEvent {


    private final LessonListCard newSelection;

    public LessonPanelSelectionChangedEvent(LessonListCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public LessonListCard getNewSelection() {
        return newSelection;
    }
}
