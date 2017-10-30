package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.schedule.Schedule;
import seedu.address.ui.ScheduleCard;

/**
 * Provides a handle for {@code AgendaPanel} containing the list of {@code ScheduleCard}.
 */
public class AgendaPanelHandle extends NodeHandle<ListView<ScheduleCard>> {
    public static final String SCHEDULE_LIST_VIEW_ID = "#scheduleCardListView";

    private Optional<ScheduleCard> lastRememberedSelectedScheduleCard;

    public AgendaPanelHandle(ListView<ScheduleCard> agendaPanelNode) {
        super(agendaPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ScheduleCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public ScheduleCardHandle getHandleToSelectedCard() {
        List<ScheduleCard> scheduleList = getRootNode().getSelectionModel().getSelectedItems();

        if (scheduleList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return new ScheduleCardHandle(scheduleList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<ScheduleCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
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

    /**
     * Selects the {@code ScheduleCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code ScheduleCard} in the list.
     */
    public void rememberSelectedScheduleCard() {
        List<ScheduleCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedScheduleCard = Optional.empty();
        } else {
            lastRememberedSelectedScheduleCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ScheduleCard} is different from the value remembered by the most recent
     * {@code rememberSelectedScheduleCard()} call.
     */
    public boolean isSelectedScheduleCardChanged() {
        List<ScheduleCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedScheduleCard.isPresent();
        } else {
            return !lastRememberedSelectedScheduleCard.isPresent()
                    || !lastRememberedSelectedScheduleCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
