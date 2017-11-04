//@@author shuangyang
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.TimerTask;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.CreateEventInstanceEvent;
import seedu.address.model.Model;
import seedu.address.model.event.timeslot.Timeslot;

public class RepeatEventTimerTask extends TimerTask {

    private ReadOnlyEvent targetEvent;
    private int period;
    private Model model;

    /**
     * @param event event to edit
     * @param int period of repetition
     */
    public RepeatEventTimerTask(ReadOnlyEvent event, int period) {
        requireNonNull(event);

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
        event.setTimeslot(currentTimeslot.plusDays(period));

        return event;
    }

    @Override
    public void run() {
        Event editedEvent = createEditedEvent(targetEvent);
        EventsCenter.getInstance().post(new CreateEventInstanceEvent(editedEvent, period));
    }
}
