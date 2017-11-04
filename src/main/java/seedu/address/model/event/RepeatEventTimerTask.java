//@@author shuangyang
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.TimerTask;

import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.model.Model;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.EventTimeClashException;
import seedu.address.model.event.timeslot.Timeslot;

public class RepeatEventTimerTask extends TimerTask {

    private ReadOnlyEvent targetEvent;
    private EditEventCommand.EditEventDescriptor editEventDescriptor;
    private Model model;

    /**
     * @param event event to edit
     * @param editEventDescriptor details to edit the event with
     */
    public RepeatEventTimerTask(ReadOnlyEvent event, EditEventCommand.EditEventDescriptor editEventDescriptor) {
        requireNonNull(event);
        requireNonNull(editEventDescriptor);

        this.targetEvent = event;
        this.editEventDescriptor = new EditEventCommand.EditEventDescriptor(editEventDescriptor);
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                           EditEventCommand.EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Title updatedTitle = editEventDescriptor.getTitle().orElse(eventToEdit.getTitle());
        Timeslot updatedTimeslot = editEventDescriptor.getTimeslot().orElse(eventToEdit.getTimeslot());
        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());
        Period updatedPeriod = editEventDescriptor.getPeriod().orElse(eventToEdit.getPeriod());

        return new Event(updatedTitle, updatedTimeslot, updatedDescription, updatedPeriod);
    }

    @Override
    public void run() {
        Event editedEvent = createEditedEvent(targetEvent, editEventDescriptor);

        try {
            model.updateEvent(targetEvent, editedEvent);
        } catch (EventNotFoundException enfe) {
            cancel();
        } catch (EventTimeClashException etce) {
            cancel();
        }
    }
}
