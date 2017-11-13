package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.ui.GroupCard;

/**
 * Provides a handle for {@code GroupListPanel} containing the list of {@code GroupCard}.
 */
public class GroupListPanelHandle extends NodeHandle<ListView<GroupCard>> {
    public static final String GROUP_LIST_VIEW_ID = "#groupListView";

    private Optional<GroupCard> lastRememberedSelectedGroupCard;

    public GroupListPanelHandle(ListView<GroupCard> groupListPanelNode) {
        super(groupListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code GroupCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public GroupCardHandle getHandleToSelectedCard() {
        List<GroupCard> groupList = getRootNode().getSelectionModel().getSelectedItems();
        if (groupList.size() != 1) {
            throw new AssertionError("Group list size expected 1.");
        }

        return new GroupCardHandle(groupList.get(0).getRoot());
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
        List<GroupCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the group.
     */
    public void navigateToCard(ReadOnlyGroup group) {
        List<GroupCard> cards = getRootNode().getItems();
        Optional<GroupCard> matchingCard = cards.stream().filter(card -> card.group.equals(group)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Group does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the group card handle of a group associated with the {@code index} in the list.
     */
    public GroupCardHandle getGroupCardHandle(int index) {
        return getGroupCardHandle(getRootNode().getItems().get(index).group);
    }

    /**
     * Returns the {@code GroupCardHandle} of the specified {@code group} in the list.
     */
    public GroupCardHandle getGroupCardHandle(ReadOnlyGroup group) {
        Optional<GroupCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.group.equals(group))
                .map(card -> new GroupCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Group does not exist."));
    }

    /**
     * Selects the {@code GroupCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code GroupCard} in the list.
     */
    public void rememberSelectedGroupCard() {
        List<GroupCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedGroupCard = Optional.empty();
        } else {
            lastRememberedSelectedGroupCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code GroupCard} is different from the value remembered by the most recent
     * {@code rememberSelectedGroupCard()} call.
     */
    public boolean isSelectedGroupCardChanged() {
        List<GroupCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedGroupCard.isPresent();
        } else {
            return !lastRememberedSelectedGroupCard.isPresent()
                    || !lastRememberedSelectedGroupCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
