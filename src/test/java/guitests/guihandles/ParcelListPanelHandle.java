package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.ui.ParcelCard;

/**
 * Provides a handle for {@code ParcelListPanel} containing the list of {@code ParcelCard}.
 */
public class ParcelListPanelHandle extends NodeHandle<ListView<ParcelCard>> {
    public static final String UNDELIVERED_PARCEL_LIST_VIEW_ID = "#allUncompletedParcelListView";
    public static final String DELIVERED_PARCEL_LIST_VIEW_ID = "#allCompletedParcelListView";

    private Optional<ParcelCard> lastRememberedSelectedParcelCard;

    public ParcelListPanelHandle(ListView<ParcelCard> parcelListPanelNode) {
        super(parcelListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ParcelCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public ParcelCardHandle getHandleToSelectedCard() {
        List<ParcelCard> parcelList = getRootNode().getSelectionModel().getSelectedItems();

        if (parcelList.size() != 1) {
            throw new AssertionError("Parcel list size expected 1.");
        }

        return new ParcelCardHandle(parcelList.get(0).getRoot());
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
        List<ParcelCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the parcel.
     */
    public void navigateToCard(ReadOnlyParcel parcel) {
        List<ParcelCard> cards = getRootNode().getItems();
        Optional<ParcelCard> matchingCard = cards.stream().filter(card -> card.parcel.equals(parcel)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Parcel does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the parcel card handle of a parcel associated with the {@code index} in the list.
     */
    public ParcelCardHandle getParcelCardHandle(int index) {
        return getParcelCardHandle(getRootNode().getItems().get(index).parcel);
    }

    /**
     * Returns the {@code ParcelCardHandle} of the specified {@code parcel} in the list.
     */
    public ParcelCardHandle getParcelCardHandle(ReadOnlyParcel parcel) {
        Optional<ParcelCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.parcel.equals(parcel))
                .map(card -> new ParcelCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Parcel does not exist."));
    }

    /**
     * Selects the {@code ParcelCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code ParcelCard} in the list.
     */
    public void rememberSelectedParcelCard() {
        List<ParcelCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedParcelCard = Optional.empty();
        } else {
            lastRememberedSelectedParcelCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ParcelCard} is different from the value remembered by the most recent
     * {@code rememberSelectedParcelCard()} call.
     */
    public boolean isSelectedParcelCardChanged() {
        List<ParcelCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedParcelCard.isPresent();
        } else {
            return !lastRememberedSelectedParcelCard.isPresent()
                    || !lastRememberedSelectedParcelCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
