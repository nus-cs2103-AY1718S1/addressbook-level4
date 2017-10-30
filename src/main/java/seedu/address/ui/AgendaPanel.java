package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.schedule.Schedule;

/**
 * Panel containing the list of persons.
 */
public class AgendaPanel extends UiPart<Region> {
    private static final String FXML = "AgendaPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AgendaPanel.class);

    @FXML
    private ListView<ScheduleCard> scheduleCardListView;

    public AgendaPanel(ObservableList<Schedule> scheduleList) {
        super(FXML);
        setConnections(scheduleList);
        registerAsAnEventHandler(this);
    }

    /**
     * Creates a list of {@code ScheduleCard} from {@code scheduleList}, sets them to the {@code scheduleCardListView}
     * and adds listener to {@code scheduleCardListView} for selection change.
     */
    private void setConnections(ObservableList<Schedule> scheduleList) {
        for (Schedule i : scheduleList) {
            logger.info(i.toString() + " Index: " + scheduleList.indexOf(i));
        }
        ObservableList<ScheduleCard> mappedList = EasyBind.map(
                scheduleList, (schedule) -> new ScheduleCard(schedule, scheduleList.indexOf(schedule) + 1));
        scheduleCardListView.setItems(mappedList);
        scheduleCardListView.setCellFactory(listView -> new ScheduleCardListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScheduleCard}.
     */
    class ScheduleCardListViewCell extends ListCell<ScheduleCard> {

        @Override
        protected void updateItem(ScheduleCard schedule, boolean empty) {
            super.updateItem(schedule, empty);

            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(schedule.getRoot());
            }
        }
    }
}
