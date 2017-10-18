package seedu.address.commons.events.ui;

import seedu.address.ui.TaskCard;

public class TaskPanelSelectionChangedEvent {

    private final TaskCard newSelection;

    public TaskPanelSelectionChangedEvent(TaskCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskCard getNewSelection() {
        return newSelection;
    }
}
