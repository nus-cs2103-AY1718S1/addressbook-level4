package seedu.address.ui;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;


public class Calendar extends UiPart<Region>{

    private static String FXML = "Calendar.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private DatePickerSkin datePickerSkin;

    @FXML
    private DatePicker datePicker;

    @FXML
    private StackPane calendarPane;

    public Calendar(ObservableList<ReadOnlyPerson> personList, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        setDate(personList, taskList);
        loadDefaultPage();
    }

    private void loadDefaultPage() {
        datePickerSkin = new DatePickerSkin(datePicker);
        DatePickerContent popupContent = (DatePickerContent) datePickerSkin.getPopupContent();

        popupContent.setPrefHeight(calendarPane.getPrefHeight());
        popupContent.setPrefWidth(calendarPane.getPrefWidth());
        calendarPane.getChildren().add(popupContent);
    }

    private void setDate(ObservableList<ReadOnlyPerson> personList, ObservableList<ReadOnlyTask> taskList) {
        datePicker = new DatePicker((LocalDate.now()));
        //ObservableList<PersonCard> mappedList = EasyBind.map(
        //        personList, (date) -> new PersonCard(personList));
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory(personList, taskList);
        datePicker.setDayCellFactory(dayCellFactory);
        findDateForSelection();
    }

    private void findDateForSelection() {
        // Make datePicker editable (i.e. i think can select and update value)
        datePicker.setEditable(true);
        // TODO: 23/10/17 Able to execute findCommand when new date is selected
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               LocalDate date = datePicker.getValue();
                logger.info("Date selected: " + date.toString());
            }
        });
    }

    private Callback<DatePicker, DateCell> getDayCellFactory(ObservableList<ReadOnlyPerson> personList,
                                                             ObservableList<ReadOnlyTask> taskList) {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        for (ReadOnlyPerson person: personList) {
                            // TODO: 22/10/17 change Birthday format to include full year
                            try {
                                DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyy");
                                if (MonthDay.from(item).equals
                                        (MonthDay.from(LocalDate.parse(person.getBirthday().toString(), format)))
                                        || MonthDay.from(item).equals(MonthDay.of(10, 23))) {
                                    setTooltip(new Tooltip("Birthday!"));
                                    setStyle("-fx-background-color: #f1a3ff;");
                                }
                            }
                            catch (DateTimeParseException exc) {
                                logger.warning("Not parsable: " + person.getBirthday().toString());
                                throw exc;
                            }
                        }
                        for (ReadOnlyTask task: taskList) {
                            try {
                                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                // TODO: 23/10/17 ensure that Deadline/Startdate is valid, after computer is invented
                                assert LocalDate.parse(task.getDeadline().toString(), format).getYear()
                                        >= (LocalDate.now().getYear() - 100);
                                if (item.equals(LocalDate.parse(task.getDeadline().toString(), format))) {
                                    setTooltip(new Tooltip("Deadline!"));
                                    setStyle("-fx-background-color: #ff444d;");
                                }
                            }
                            catch (DateTimeParseException exc) {
                                logger.warning("Not parsable: " + task.getDeadline().toString());
                                throw exc;
                            }
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
