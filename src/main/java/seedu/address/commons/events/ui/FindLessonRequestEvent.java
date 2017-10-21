package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.module.ReadOnlyLesson;

import java.util.function.Predicate;

/***
 * Indicate request to find a lesson
 */

public class FindLessonRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
