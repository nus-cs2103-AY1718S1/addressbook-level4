package seedu.room.ui;


import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class CalendarBoxPanel extends UiPart<Region>{
    private static final String FXML = "CalendarBox.fxml";

    @FXML
    private Pane calendar;

    public CalendarBoxPanel() {
        super(FXML);
    }

}
