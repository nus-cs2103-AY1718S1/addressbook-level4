package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.ui.MeetingCard;

/**
 * Provides a handle for {@code MeetingListPanel} containing the list of {@code MeetingCard}.
 */

public class MeetingListPanelHandle extends NodeHandle<ListView<MeetingCard>> {
    public static final String MEETING_LIST_VIEW_ID = "#MeetingListView";

    private Optional<MeetingCard> lastRememberedSelectedMeetingCard;

    public MeetingListPanelHandle(ListView<MeetingCard> MeetingListPanelNode) {
        super(MeetingListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code MeetingCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public MeetingCardHandle getHandleToSelectedCard() {
        List<MeetingCard> MeetingList = getRootNode().getSelectionModel().getSelectedItems();

        if (MeetingList.size() != 1) {
            throw new AssertionError("Meeting list size expected 1.");
        }

        return new MeetingCardHandle(MeetingList.get(0).getRoot());
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
        List<MeetingCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the meeting.
     */
    public void navigateToCard(ReadOnlyMeeting meeting) {
        List<MeetingCard> cards = getRootNode().getItems();
        Optional<MeetingCard> matchingCard = cards.stream().filter(card -> card.meeting.equals(meeting)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Meeting does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the meeting card handle of a meeting associated with the {@code index} in the list.
     */
    public MeetingCardHandle getMeetingCardHandle(int index) {
        return getMeetingCardHandle(getRootNode().getItems().get(index).meeting);
    }

    /**
     * Returns the {@code MeetingCardHandle} of the specified {@code meeting} in the list.
     */
    public MeetingCardHandle getMeetingCardHandle(ReadOnlyMeeting meeting) {
        Optional<MeetingCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.meeting.equals(meeting))
                .map(card -> new MeetingCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Meeting does not exist."));
    }

    /**
     * Selects the {@code MeetingCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code MeetingCard} in the list.
     */
    public void rememberSelectedMeetingCard() {
        List<MeetingCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedMeetingCard = Optional.empty();
        } else {
            lastRememberedSelectedMeetingCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code MeetingCard} is different from the value remembered by the most recent
     * {@code rememberSelectedMeetingCard()} call.
     */
    public boolean isSelectedMeetingCardChanged() {
        List<MeetingCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedMeetingCard.isPresent();
        } else {
            return !lastRememberedSelectedMeetingCard.isPresent()
                    || !lastRememberedSelectedMeetingCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
