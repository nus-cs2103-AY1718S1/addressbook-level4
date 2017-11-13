//@@author 17navasaw
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.schedule.Schedule;
import seedu.address.ui.ScheduleCard;

/**
 * Provides a handle for {@code reminderWindowBottom} containing the list of {@code ScheduleCard}.
 */
public class ReminderWindowBottomHandle extends NodeHandle<ListView<ScheduleCard>> {
    public static final String SCHEDULE_LIST_VIEW_ID = "#reminderSchedulesListView";

    //private Optional<ScheduleCard> lastRememberedSelectedScheduleCard;

    public ReminderWindowBottomHandle(ListView<ScheduleCard> reminderWindowBottomNode) {
        super(reminderWindowBottomNode);
    }

    /**
     * Navigates the listview to display and select the schedule.
     */
    public void navigateToCard(Schedule schedule) {
        List<ScheduleCard> cards = getRootNode().getItems();
        Optional<ScheduleCard> matchingCard = cards.stream().filter(card -> card.schedule.equals(schedule)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the schedule card handle of a schedule associated with the {@code index} in the list.
     */
    public ScheduleCardHandle getScheduleCardHandle(int index) {
        return getScheduleCardHandle(getRootNode().getItems().get(index).schedule);
    }

    /**
     * Returns the {@code ScheduleCardHandle} of the specified {@code schedule} in the list.
     */
    public ScheduleCardHandle getScheduleCardHandle(Schedule schedule) {
        Optional<ScheduleCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.schedule.equals(schedule))
                .map(card -> new ScheduleCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Schedule does not exist."));
    }
}
