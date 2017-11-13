//@@author Hailinx
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.BILL;
import static seedu.address.testutil.TypicalPersons.DARWIN;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertTodoDisplaysTodoItem;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TodoCardHandle;
import guitests.guihandles.TodoPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowAllTodoItemsEvent;
import seedu.address.commons.events.ui.ShowPersonTodoEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;

public class TodoPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final ShowPersonTodoEvent SHOW_DARWIN_TODO_EVENT = new ShowPersonTodoEvent(DARWIN);
    private static final ShowAllTodoItemsEvent SHOW_ALL_TODO_EVENT = new ShowAllTodoItemsEvent();
    private static final JumpToListRequestEvent JUMP_TO_BILL_TODO_EVENT =
            new JumpToListRequestEvent(Index.fromOneBased(8));

    private TodoPanelHandle todoPanelHandle;

    @Before
    public void setUp() {
        TodoPanel todoPanel = new TodoPanel(TYPICAL_PERSONS);
        uiPartRule.setUiPart(todoPanel);

        todoPanelHandle = new TodoPanelHandle(getChildNode(todoPanel.getRoot(),
                TodoPanelHandle.TODO_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        List<TodoItem> expectedList = getAllTodoItemList();
        assertTodoDisplayTodoPanel(expectedList);
    }

    @Test
    public void handleShowPersonTodoEvent() {
        postNow(SHOW_DARWIN_TODO_EVENT);
        guiRobot.pauseForHuman();
        assertTodoDisplayTodoPanel(DARWIN.getTodoItems());
    }

    @Test
    public void handleShowAllTodoItemsEvent() {
        postNow(SHOW_ALL_TODO_EVENT);
        guiRobot.pauseForHuman();

        List<TodoItem> expectedList = getAllTodoItemList();
        assertTodoDisplayTodoPanel(expectedList);
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_BILL_TODO_EVENT);
        guiRobot.pauseForHuman();
        assertTodoDisplayTodoPanel(BILL.getTodoItems());
    }

    private List<TodoItem> getAllTodoItemList() {
        List<TodoItem> list = new ArrayList<>();
        for (ReadOnlyPerson person : TYPICAL_PERSONS) {
            list.addAll(person.getTodoItems());
        }
        return list;
    }

    /**
     * Asserts that all {@code actualCard} displays the details of {@code expectedItem} in {@code expectedList}.
     */
    private void assertTodoDisplayTodoPanel(List<TodoItem> expectedList) {
        for (int i = 0; i < expectedList.size(); i++) {
            TodoItem expectedItem = expectedList.get(i);
            TodoCardHandle actualCard = todoPanelHandle.getTodoCardHandle(i);
            guiRobot.pauseForHuman();

            assertTodoDisplaysTodoItem(expectedItem, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
