package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.logic.Logic;
import seedu.address.model.module.ReadOnlyLesson;

/**
 * The timetable panel of the App.
 */
public class Timetable extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";

    private static final String FXML = "Timetable.fxml";

    private static final String LESSON_NODE = "lessonNode";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final Logic logic;

    @FXML
    private StackPane stackPane;
    @FXML
    private GridPane timetableGrid;
    @FXML
    private HBox timeHeader;

    public Timetable(Logic logic) {
        super(FXML);
        this.logic = logic;
        initializeTimeTableGrid();
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleViewedLessonEvent(ViewedLessonEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

    }

    /**
     * Initialize timetable grid.
     */
    public void initializeTimeTableGrid() {
        String text = "";
        int weekDayRow = -1;
        int startHourCol = -1;
        int endHourSpan = -1;
        ObservableList<ReadOnlyLesson> lessons =  logic.getFilteredLessonList();
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println(lessons.get(i).toString());
            ReadOnlyLesson lesson = lessons.get(i);

            text = lesson.getTimeSlot().toString();
            weekDayRow = getWeekDay(text.substring(0, 3));
            startHourCol = getTime(text.substring(4, 6));
            endHourSpan = getTime(text.substring(9, 11)) - startHourCol;
        }

        int cols = 13;
        int rows = 6;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == weekDayRow && j == startHourCol) {
                    TextArea lbl = new TextArea(text);
                    lbl.setWrapText(true);
                    lbl.setId(LESSON_NODE);
                    timetableGrid.setGridLinesVisible(true);
                    timetableGrid.add(lbl, j, i, endHourSpan, 1);
                }
            }
        }
    }

    private int getWeekDay(String text) {
        text = text.toUpperCase();
        switch (text) {
        case "MON":
            return 0;
        case "TUE":
            return 1;
        case "WED":
            return 2;
        case "THU":
            return 3;
        case "FRI":
            return 4;
        default:
            return -1;
        }
    }


    private int getTime(String text) {
        int time = Integer.parseInt(text);
        return time - 8;
    }
}
