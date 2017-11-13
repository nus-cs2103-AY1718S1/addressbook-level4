//@@author shuang-yang
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.TimerTask;

import javafx.application.Platform;
import seedu.address.model.Model;
import seedu.address.model.event.exceptions.EventTimeClashException;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * Add a new instance of the event after the specified period.
 */
public class RepeatEventTimerTask extends TimerTask {

    private ReadOnlyEvent targetEvent;
    private int period;
    private Model model;

    /**
     * @param event event to edit
     * @param int period of repetition
     */
    public RepeatEventTimerTask(Model model, ReadOnlyEvent event, int period) {
        requireNonNull(event);

        this.model = model;
        this.targetEvent = event;
        this.period = period;
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}.
     */
    private Event createEditedEvent(ReadOnlyEvent eventToEdit) {
        assert eventToEdit != null;
        Event event = new Event(eventToEdit);
        Timeslot currentTimeslot = event.getTimeslot();

        // If the eventToEdit is far in the past, only add the repeated event in the next week to avoid adding too
        // many event at the same time
        while (currentTimeslot.isBefore(Timeslot.getNow())) {
            currentTimeslot = currentTimeslot.plusDays(period);
        }
        event.setTimeslot(currentTimeslot);

        return event;
    }

    @Override
    public void run() {
        Event editedEvent = createEditedEvent(targetEvent);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addEvent(editedEvent);
                } catch (EventTimeClashException etce) {
                    model.scheduleRepeatedEvent(editedEvent);
                }
            }
        });

    }
}
