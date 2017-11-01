package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import org.fxmisc.easybind.EasyBind;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowAllTodoItemsEvent;
import seedu.address.commons.events.ui.ShowPersonTodoEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TodoPanel extends UiPart<Region> {
    private static final String FXML = "TodoPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TodoPanel.class);

    @FXML
    private ListView<TodoCard> todoCardList;

    private List<ReadOnlyPerson> personList;

    public TodoPanel(List<ReadOnlyPerson> personList) {
        super(FXML);
        this.personList = personList;
        setAllConnections();
        registerAsAnEventHandler(this);
    }

    private void setAllConnections() {
        List<TodoItem> todoItemList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            todoItemList.addAll(person.getTodoItems());
        }

        setConnections(todoItemList);
    }

    private void setConnections(List<TodoItem> todoItemList) {
        ObservableList<TodoItem> allTodoItems = FXCollections.observableArrayList(todoItemList);

        ObservableList<TodoCard> mappedList = EasyBind.map(
                allTodoItems, (item) -> new TodoCard(item, allTodoItems.indexOf(item) + 1));
        todoCardList.setItems(mappedList);
        todoCardList.setCellFactory(listView -> new TodoPanel.TodoListViewCell());
    }

    @Subscribe
    private void handleShowPersonTodoEvent(ShowPersonTodoEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(event.person.getTodoItems());
    }

    @Subscribe
    private void handleShowAllTodoItemsEvent(ShowAllTodoItemsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setAllConnections();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(personList.get(event.targetIndex).getTodoItems());
    }

    class TodoListViewCell extends ListCell<TodoCard> {

        @Override
        protected void updateItem(TodoCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}
