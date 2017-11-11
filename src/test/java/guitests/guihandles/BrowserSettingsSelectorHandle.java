package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.ui.BrowserSelectorCard;

//@@author fongwz
/**
 * Provides a handle for {@code browserSelectorList} containing the list of {@code BrowserSelectorCard}.
 */
public class BrowserSettingsSelectorHandle extends NodeHandle<ListView<BrowserSelectorCard>> {
    public static final String BROWSER_LIST_VIEW_ID = "#browserSelectorList";

    //private Optional<PersonCard> lastRememberedSelectedPersonCard;

    public BrowserSettingsSelectorHandle(ListView<BrowserSelectorCard> browserListPanelNode) {
        super(browserListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public BrowserSelectorCardHandle getHandleToSelectedCard() {
        List<BrowserSelectorCard> browserList = getRootNode().getSelectionModel().getSelectedItems();

        if (browserList.size() != 1) {
            throw new AssertionError("Browser list size expected 1.");
        }

        return new BrowserSelectorCardHandle(browserList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }


    /**
     * Navigates the listview to display and select the browser.
     */
    public void navigateToCard(BrowserSelectorCard browserType) {
        List<BrowserSelectorCard> cards = getRootNode().getItems();
        Optional<BrowserSelectorCard> matchingCard = cards.stream()
                .filter(card -> card.getImageString().equals(browserType.getImageString())).findFirst();

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
     * Returns the person card handle of a person associated with the {@code index} in the list.
     */
    public BrowserSelectorCardHandle getBrowserSelectorCardHandle(int index) {
        return new BrowserSelectorCardHandle(getRootNode().getItems().get(index).getRoot());
    }
}
