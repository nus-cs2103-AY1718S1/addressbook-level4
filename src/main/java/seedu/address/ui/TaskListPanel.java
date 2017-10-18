/*import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.ui.PersonCard;
import seedu.address.ui.PersonListPanel;
import seedu.address.ui.TaskCard;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of tasks.
 */
/*public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(seedu.address.ui.TaskListPanel.class);

    @javafx.fxml.FXML
    private ListView<TaskCard> taskListView;

    public TaskListPanel(ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
        personListView.setItems(mappedList);
        personListView.setCellFactory(listView -> new PersonListPanel.PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }
}*/

