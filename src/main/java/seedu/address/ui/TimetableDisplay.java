package seedu.address.ui;

import static seedu.address.model.person.timetable.Timetable.ARRAY_DAYS;
import static seedu.address.model.person.timetable.Timetable.ARRAY_TIMES;
import static seedu.address.model.person.timetable.Timetable.ARRAY_WEEKS;
import static seedu.address.model.person.timetable.Timetable.WEEK_ODD;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.timetable.Timetable;

//@@author zacharytang
/**
 * Display for timetables in the UI
 */
public class TimetableDisplay extends UiPart<Region> {

    private static final String FXML = "TimetableDisplay.fxml";
    private ArrayList<Timetable> timetables;

    @FXML
    private GridPane oddGrid;

    @FXML
    private GridPane evenGrid;

    public TimetableDisplay(ArrayList<Timetable> timetables) {
        super(FXML);

        this.timetables = timetables;
        fillInitialGrid();
        fillTimetables();
    }

    /**
     * Initializes the timetable grid to an empty grid not containing any lessons
     */
    private void fillInitialGrid() {
        for (int weekType = 0; weekType < ARRAY_WEEKS.length; weekType++) {
            for (int day = 0; day < ARRAY_DAYS.length; day++) {
                for (int time = 0; time < ARRAY_TIMES.length; time++) {
                    markSlot(weekType, day, time, false);
                }
            }
        }
    }

    /**
     * Populates the initialized grid with lessons according to timetables passed
     */
    private void fillTimetables() {
        // If null passed into constructor, display empty timetable
        if (timetables == null) {
            return;
        }

        for (Timetable timetable : timetables) {
            fillSingleTimetable(timetable);
        }
    }

    /**
     * Fills the timetable grid with panes according to the timetable oject given
     */
    private void fillSingleTimetable(Timetable timetable) {
        for (int weekType = 0; weekType < ARRAY_WEEKS.length; weekType++) {
            for (int day = 0; day < ARRAY_DAYS.length; day++) {
                for (int time = 0; time < ARRAY_TIMES.length; time++) {
                    if (hasLesson(weekType, day, time, timetable)) {
                        markSlot(weekType, day, time, true);
                    }
                }
            }
        }
    }

    /**
     * Fills a slot in the timetable grid with a lesson
     */
    private void markSlot(int weekIndex, int dayIndex, int timeIndex, boolean hasLesson) {

        Pane pane = new Pane();
        GridPane.setColumnIndex(pane, timeIndex + 1);
        GridPane.setRowIndex(pane, dayIndex + 1);

        // Sets the borders such that half hour slots are combined into an hour
        String borderStyle = timeIndex % 2 == 0 ? "solid none solid solid" : "solid solid solid none";

        // Fixes the widths to be even across the grid
        String topWidth = dayIndex == 0 ? "2" : "1";
        String leftWidth = timeIndex == 0 ? "2" : "1";
        String bottomWidth = dayIndex == 4 ? "2" : "1";
        String rightWidth = timeIndex == 31 ? "2" : "1";
        String borderWidths = topWidth + " " + rightWidth + " " + bottomWidth + " " + leftWidth;

        pane.getStyleClass().add(hasLesson ? "timetable-cell-filled" : "timetable-cell-empty");
        pane.setStyle("-fx-border-width: " + borderWidths + ";\n"
                + "-fx-border-style: " + borderStyle);

        if (weekIndex == WEEK_ODD) {
            oddGrid.getChildren().add(pane);
        } else {
            evenGrid.getChildren().add(pane);
        }
    }

    /**
     * Checks if a lesson exists in the timetable at the given parameters
     */
    private boolean hasLesson(int weekIndex, int dayIndex, int timeIndex, Timetable timetable) {
        try {
            return timetable.doesSlotHaveLesson(ARRAY_WEEKS[weekIndex], ARRAY_DAYS[dayIndex], ARRAY_TIMES[timeIndex]);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            return false;
        }
    }
}
