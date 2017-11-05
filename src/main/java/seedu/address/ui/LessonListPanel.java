package seedu.address.ui;

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
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.LessonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RefreshPanelEvent;
import seedu.address.model.module.ReadOnlyLesson;

/**
 * Panel containing the list of info.
 */
public class LessonListPanel extends UiPart<Region> {
    private static final String FXML = "LessonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LessonListPanel.class);

    private ObservableList<ReadOnlyLesson> lessonList;

    @FXML
    private ListView<LessonListCard> lessonListView;


    public LessonListPanel(ObservableList<ReadOnlyLesson> infoList) {
        super(FXML);
        this.lessonList = infoList;
        setConnections(infoList);
        registerAsAnEventHandler(this);
    }

    //@@author junming403
    private void setConnections(ObservableList<ReadOnlyLesson> infoList) {

        ObservableList<LessonListCard> mappedList = EasyBind.map(
                infoList, (person) -> new LessonListCard(person, infoList.indexOf(person) + 1));

        lessonListView.setItems(mappedList);
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
        setEventHandlerForSelectionChangeEvent();

    }
    //@@author


    private void setEventHandlerForSelectionChangeEvent() {
        lessonListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new LessonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            lessonListView.scrollTo(index);
            lessonListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    //@@author junming403
    @Subscribe
    private void handleRefreshPanelEvent(RefreshPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(lessonList);
    }
    //@@author


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class LessonListViewCell extends ListCell<LessonListCard> {

        @Override
        protected void updateItem(LessonListCard lesson, boolean empty) {
            super.updateItem(lesson, empty);

            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(lesson.getRoot());
            }
        }
    }
}
