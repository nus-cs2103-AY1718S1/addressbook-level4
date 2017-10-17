package guitests.guihandles;

import javafx.scene.control.ListView;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.ui.LessonListCard;

import java.util.List;
import java.util.Optional;

/**
 * Provides a handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<LessonListCard>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    private Optional<LessonListCard> lastRememberedSelectedLessonListCard;

    public PersonListPanelHandle(ListView<LessonListCard> lessonListPanelNode) {
        super(lessonListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PersonCardHandle getHandleToSelectedCard() {
        List<LessonListCard> lessonList = getRootNode().getSelectionModel().getSelectedItems();

        if (lessonList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return new PersonCardHandle(lessonList.get(0).getRoot());
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
        List<LessonListCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public void navigateToCard(ReadOnlyLesson lesson) {
        List<LessonListCard> cards = getRootNode().getItems();
        Optional<LessonListCard> matchingCard = cards.stream().filter(card -> card.lesson.equals(lesson)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Lesson does not exist.");
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
    public PersonCardHandle getLessonListCardHandle(int index) {
        return getLessonListCardHandle(getRootNode().getItems().get(index).lesson);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code person} in the list.
     */
    public PersonCardHandle getLessonListCardHandle(ReadOnlyLesson lesson) {
        Optional<PersonCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.lesson.equals(lesson))
                .map(card -> new PersonCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Person does not exist."));
    }

    /**
     * Selects the {@code PersonCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PersonCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<LessonListCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedLessonListCard = Optional.empty();
        } else {
            lastRememberedSelectedLessonListCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<LessonListCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedLessonListCard.isPresent();
        } else {
            return !lastRememberedSelectedLessonListCard.isPresent()
                    || !lastRememberedSelectedLessonListCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
