//@@author danielbrzn
package seedu.address.commons.events.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show the progress window of a task.
 */
public class ShowProgressEvent extends BaseEvent {

    private ReadOnlyDoubleProperty progress;

    public ShowProgressEvent(ReadOnlyDoubleProperty progress) {
        this.progress = progress;
    }

    public ReadOnlyDoubleProperty getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
