package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.ui.ParcelCard;

/**
 * Provides a handle for {@code PersonListPanel} containing the list of {@code ParcelCard}.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<ParcelCard>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    private Optional<ParcelCard> lastRememberedSelectedPersonCard;

    public PersonListPanelHandle(ListView<ParcelCard> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PersonCardHandle getHandleToSelectedCard() {
        List<ParcelCard> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() != 1) {
            throw new AssertionError("Parcel list size expected 1.");
        }

        return new PersonCardHandle(personList.get(0).getRoot());
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
    public void navigateToCard(ReadOnlyParcel person) {
        List<ParcelCard> cards = getRootNode().getItems();
        Optional<ParcelCard> matchingCard = cards.stream().filter(card -> card.parcel.equals(person)).findFirst();

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
    public PersonCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(getRootNode().getItems().get(index).parcel);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code parcel} in the list.
     */
    public PersonCardHandle getPersonCardHandle(ReadOnlyParcel person) {
        Optional<PersonCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.parcel.equals(person))
                .map(card -> new PersonCardHandle(card.getRoot()))
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
    public void rememberSelectedPersonCard() {
        List<ParcelCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ParcelCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<ParcelCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
