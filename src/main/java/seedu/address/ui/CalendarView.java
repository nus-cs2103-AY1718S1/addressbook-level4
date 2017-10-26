package seedu.address.ui;

import java.time.LocalDate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;

/**
 * An UI component that displays a clickable-Calendar.
 */
public class CalendarView extends UiPart<Region> {
    private static final String FXML = "CalendarView.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private AnchorPane box;

    private DatePicker datePicker;

    public CalendarView(ObservableList<Event> eventList) {

        super(FXML);
        initCalendar(eventList);
        initListener();


    }

    /**
     * Initialise the calendar and highlight dates with Event.
     * @param eventList
     */
    private void initCalendar(ObservableList<Event> eventList) {
        box.getChildren().clear();
        datePicker = new DatePicker(LocalDate.now());

        HashMap<LocalDate, Integer> countEventDate = new HashMap<>();
        List<LocalDate> masterEventDateList = eventList.stream().map(e ->
                e.getEventTime().getStart().toLocalDate()).collect(Collectors.toList());

        eventList.stream().distinct().forEach(event ->
                countEventDate.put(event.getEventTime().getStart().toLocalDate(),
                        Collections.frequency(masterEventDateList, event.getEventTime().getStart().toLocalDate())));

        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (countEventDate.containsKey(item)) {
                            setTooltip(new Tooltip(countEventDate.get(item) + " events!"));
                            if (!item.isEqual(LocalDate.now())) {
                                setStyle("-fx-background-color: #a7a7a7; -fx-text-fill: #ffffff;");

                            }
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
        Node pop = datePickerSkin.getPopupContent();

        box.getChildren().add(pop);
    }

    /**
     * Add listener to register users mouse click.
     */
    private void initListener() {
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("data: " + newValue.toString());
            LocalDate value = datePicker.getValue();
            System.out.println(value.toString());
        });


    }
}
