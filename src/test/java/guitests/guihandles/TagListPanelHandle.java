package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.tag.Tag;
import seedu.address.ui.TagCard;

//@@author pohjie
/**
 * Provides a handle for {@code TagListPanel} containing list of unique tags
 */
public class TagListPanelHandle extends NodeHandle<ListView<TagCard>> {
    public static final String TAG_LIST_VIEW_ID = "#tagListView";

    public TagListPanelHandle(ListView<TagCard> tagListPanelNode) {
        super (tagListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TagCardHandle}
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public TagCardHandle getHandleToSelectedCard() {
        List<TagCard> tagList = getRootNode().getSelectionModel().getSelectedItems();

        if (tagList.size() != 1) {
            throw new AssertionError("Tag list size expected 1.");
        }

        return new TagCardHandle(tagList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Navigates the listview to display and select the tag.
     */
    public void navigateToCard(Tag tag) {
        List<TagCard> cards = getRootNode().getItems();
        Optional<TagCard> matchingCard = cards.stream().filter(card -> card.tag.equals(tag)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Tag does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the tag card handle of a tag associated with the {@code index} in the list
     */
    public TagCardHandle getTagCardHandle(int index)  {
        return getTagCardHandle(getRootNode().getItems().get(index).tag);
    }

    /**
     * Returns the {@code TagCardHandle} of the specified {@code tag} in the list.
     */
    public TagCardHandle getTagCardHandle(Tag tag) {
        Optional<TagCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.tag.equals(tag))
                .map(card -> new TagCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Tag does not exist."));
    }

    /**
     * Selects the {@code TagCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
