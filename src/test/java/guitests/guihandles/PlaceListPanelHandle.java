package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.ui.PlaceCard;

/**
 * Provides a handle for {@code PlaceListPanel} containing the list of {@code PlaceCard}.
 */
public class PlaceListPanelHandle extends NodeHandle<ListView<PlaceCard>> {
    public static final String PLACE_LIST_VIEW_ID = "#placeListView";

    private Optional<PlaceCard> lastRememberedSelectedPlaceCard;

    public PlaceListPanelHandle(ListView<PlaceCard> placeListPanelNode) {
        super(placeListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PlaceCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PlaceCardHandle getHandleToSelectedCard() {
        List<PlaceCard> placeList = getRootNode().getSelectionModel().getSelectedItems();

        if (placeList.size() != 1) {
            throw new AssertionError("Place list size expected 1.");
        }

        return new PlaceCardHandle(placeList.get(0).getRoot());
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
        List<PlaceCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the place.
     */
    public void navigateToCard(ReadOnlyPlace place) {
        List<PlaceCard> cards = getRootNode().getItems();
        Optional<PlaceCard> matchingCard = cards.stream().filter(card -> card.place.equals(place)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Place does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the place card handle of a place associated with the {@code index} in the list.
     */
    public PlaceCardHandle getPlaceCardHandle(int index) {
        return getPlaceCardHandle(getRootNode().getItems().get(index).place);
    }

    /**
     * Returns the {@code PlaceCardHandle} of the specified {@code place} in the list.
     */
    public PlaceCardHandle getPlaceCardHandle(ReadOnlyPlace place) {
        Optional<PlaceCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.place.equals(place))
                .map(card -> new PlaceCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Place does not exist."));
    }

    /**
     * Selects the {@code PlaceCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PlaceCard} in the list.
     */
    public void rememberSelectedPlaceCard() {
        List<PlaceCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPlaceCard = Optional.empty();
        } else {
            lastRememberedSelectedPlaceCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PlaceCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPlaceCard()} call.
     */
    public boolean isSelectedPlaceCardChanged() {
        List<PlaceCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPlaceCard.isPresent();
        } else {
            return !lastRememberedSelectedPlaceCard.isPresent()
                    || !lastRememberedSelectedPlaceCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
