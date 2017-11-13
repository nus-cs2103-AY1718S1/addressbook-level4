package seedu.address.ui;

import static seedu.address.logic.commands.FontSizeCommand.MAXIMUM_FONT_SIZE_MULTIPLIER;
import static seedu.address.logic.commands.FontSizeCommand.MINIMUM_FONT_SIZE_MULTIPLIER;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.commons.events.ui.BrowserPanelNavigateEvent;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.JumpToListRequestTaskEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;

//@@author Esilocke
/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<TaskCard> taskListView;

    private int fontSizeMultiplier;
    private ObservableList<ReadOnlyTask> taskList;

    public TaskListPanel(ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        this.taskList = taskList;
        fontSizeMultiplier = MINIMUM_FONT_SIZE_MULTIPLIER;
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1, fontSizeMultiplier));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    //@@author charlesgoh
    /**
     * Increases all task cards' font sizes in person list
     */
    public void increaseFontSize() {
        logger.info("TaskListPanel: Increasing font sizes");
        setFontSizeMultiplier(fontSizeMultiplier + 1);
        setConnections(taskList);
    }

    /**
     * Decreases all task cards' font sizes in person list
     */
    public void decreaseFontSize() {
        logger.info("TaskListPanel: Decreasing font sizes");
        setFontSizeMultiplier(fontSizeMultiplier - 1);
        setConnections(taskList);
    }

    /**
     * Resets all task cards' font sizes in person list
     */
    public void resetFontSize() {
        logger.info("TaskListPanel: Resetting font sizes");
        fontSizeMultiplier = MINIMUM_FONT_SIZE_MULTIPLIER;
        setConnections(taskList);
    }

    /**
     * Gets integer value of font size multiplier
     */
    public int getFontSizeMultiplier() {
        return fontSizeMultiplier;
    }

    /**
     * Set integer value of font size multiplier
     */
    public void setFontSizeMultiplier(int fontSizeMultiplier) {
        // Set new font size
        this.fontSizeMultiplier = fontSizeMultiplier;

        // Restrict from minimum
        this.fontSizeMultiplier = Math.max(MINIMUM_FONT_SIZE_MULTIPLIER, this.fontSizeMultiplier);

        // Restrict from maximum
        this.fontSizeMultiplier = Math.min(MAXIMUM_FONT_SIZE_MULTIPLIER, this.fontSizeMultiplier);

        logger.info("New task font size multiplier: " + Integer.toString(this.fontSizeMultiplier));
    }

    /**
     * Handles command induced change font size event for task cards
     * @param event
     */
    @Subscribe
    private void handleTaskCardChangeFontSizeEvent (ChangeFontSizeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.getTriggerOption()) {
        case 0:
            logger.info("Attempting to increase font size");
            increaseFontSize();
            break;
        case 1:
            decreaseFontSize();
            logger.info("Attempting to decrease font size");
            break;
        case 2:
            resetFontSize();
            logger.info("Attempting to reset font size");
            break;
        default:
            logger.info("Unable to handle change font size event. Stopping execution now");
        }
    }
    //@@author

    /**
     * Scrolls to the {@code TaskCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    //@@author Esilocke
    @Subscribe
    private void handleJumpToListRequestTaskEvent(JumpToListRequestTaskEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info("Attempting to clear selection in task list view");
        Platform.runLater(taskListView.getSelectionModel()::clearSelection);
    }

    @Subscribe
    private void handleBrowserPanelLocateEvent(BrowserPanelLocateEvent event) {
        logger.info("Attempting to clear selection in person list view");
        Platform.runLater(taskListView.getSelectionModel()::clearSelection);
    }

    @Subscribe
    private void handleBrowserPanelNavigateEvent(BrowserPanelNavigateEvent event) {
        logger.info("Attempting to clear selection in person list view");
        Platform.runLater(taskListView.getSelectionModel()::clearSelection);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(task.getRoot());
            }
        }
    }
}
