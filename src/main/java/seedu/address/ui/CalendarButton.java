package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * An UI component that displays calendar information of a {@code Event}.
 *    //@@author yangshuang
 */
public class CalendarButton extends UiPart<Region> {

    private static final String FXML = "CalendarButton.fxml";

    @FXML
    private AnchorPane calendarButton;

    public CalendarButton() {
        super(FXML);
    }
}
