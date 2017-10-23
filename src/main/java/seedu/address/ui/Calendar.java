package seedu.address.ui;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;

import java.time.LocalDate;
import java.util.logging.Logger;


public class Calendar extends UiPart<Region>{

    private static String FXML = "Calendar.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private DatePickerSkin datePickerSkin;

    @FXML
    private DatePicker datePicker;

    @FXML
    private StackPane calendarPane;

    public Calendar() {
        super(FXML);
        loadDefaultPage();
    }

    private void loadDefaultPage() {
        datePicker = new DatePicker((LocalDate.now()));
        datePickerSkin = new DatePickerSkin(datePicker);
        DatePickerContent popupContent = (DatePickerContent) datePickerSkin.getPopupContent();

        popupContent.setPrefHeight(calendarPane.getPrefHeight());
        popupContent.setPrefWidth(calendarPane.getPrefWidth());
        calendarPane.getChildren().add(popupContent);
    }
}
