package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.module.ReadOnlyLesson;

import java.util.function.Predicate;

/***
 * Indicate request to find a lesson
 */

public class FindLessonRequestEvent extends BaseEvent {

    public final Predicate<ReadOnlyLesson> predicate;

    public FindLessonRequestEvent(Predicate<ReadOnlyLesson> currentPredicate){ this.predicate = currentPredicate;}

    public Predicate<ReadOnlyLesson> getPredicate() {
        return predicate;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
