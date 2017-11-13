//@@author duyson98

package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.ui.ReminderCard;

/**
 * Provides a handle for {@code ReminderListPanel} containing the list of {@code ReminderCard}.
 */
public class ReminderListPanelHandle extends NodeHandle<ListView<ReminderCard>> {
    public static final String REMINDER_LIST_VIEW_ID = "#reminderListView";

    private Optional<ReminderCard> lastRememberedSelectedReminderCard;

    public ReminderListPanelHandle(ListView<ReminderCard> reminderListPanelNode) {
        super(reminderListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ReminderCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public ReminderCardHandle getHandleToSelectedCard() {
        List<ReminderCard> reminderList = getRootNode().getSelectionModel().getSelectedItems();

        if (reminderList.size() != 1) {
            throw new AssertionError("Reminder list size expected 1.");
        }

        return new ReminderCardHandle(reminderList.get(0).getRoot());
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
        List<ReminderCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the reminder.
     */
    public void navigateToCard(ReadOnlyReminder reminder) {
        List<ReminderCard> cards = getRootNode().getItems();
        Optional<ReminderCard> matchingCard = cards.stream().filter(card -> card.reminder.equals(reminder)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Reminder does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the reminder card handle of a reminder associated with the {@code index} in the list.
     */
    public ReminderCardHandle getReminderCardHandle(int index) {
        return getReminderCardHandle(getRootNode().getItems().get(index).reminder);
    }

    /**
     * Returns the {@code ReminderCardHandle} of the specified {@code reminder} in the list.
     */
    public ReminderCardHandle getReminderCardHandle(ReadOnlyReminder reminder) {
        Optional<ReminderCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.reminder.equals(reminder))
                .map(card -> new ReminderCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Reminder does not exist."));
    }

    /**
     * Selects the {@code ReminderCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code ReminderCard} in the list.
     */
    public void rememberSelectedReminderCard() {
        List<ReminderCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedReminderCard = Optional.empty();
        } else {
            lastRememberedSelectedReminderCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ReminderCard} is different from the value remembered by the most recent
     * {@code rememberSelectedReminderCard()} call.
     */
    public boolean isSelectedReminderCardChanged() {
        List<ReminderCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedReminderCard.isPresent();
        } else {
            return !lastRememberedSelectedReminderCard.isPresent()
                    || !lastRememberedSelectedReminderCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
