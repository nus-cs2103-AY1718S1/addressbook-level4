package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

/**
 * The Browser Panel of the App.
 */
public class CalendarView extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    @FXML
    private BorderPane root;

    public CalendarView() {
        super(FXML);
        VCalendar vCalendar = new VCalendar();
        ICalendarAgenda agenda = new ICalendarAgenda(vCalendar);

        root = new BorderPane();
        root.setCenter(agenda);
    }

    public BorderPane getRoot() {
        return this.root;
    }

}
