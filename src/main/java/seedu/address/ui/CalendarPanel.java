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
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.persons.FindCommand;
import seedu.address.logic.commands.tasks.FindTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;

/**
 * The CalendarPanel panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;

    private DatePickerSkin datePickerSkin;
    private DatePicker datePicker;

    @FXML
    private StackPane calendarPane;

    public CalendarPanel(Logic logic, ObservableList<ReadOnlyPerson> personList,
                         ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        this.logic = logic;
        setDate(personList, taskList);
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Load the calendar which is the datePickerSkin with DateCell from datePicker
     */
    private void loadDefaultPage() {
        datePickerSkin = new DatePickerSkin(datePicker);
        DatePickerContent popupContent = (DatePickerContent) datePickerSkin.getPopupContent();

        popupContent.setPrefHeight(calendarPane.getPrefHeight());
        popupContent.setPrefWidth(calendarPane.getPrefWidth());
        calendarPane.getChildren().add(popupContent);
    }

    /**
     * Load datePicker with various dates, birthday from personList and deadline from taskList
     * @param personList
     * @param taskList
     */
    private void setDate(ObservableList<ReadOnlyPerson> personList, ObservableList<ReadOnlyTask> taskList) {
        datePicker = new DatePicker((LocalDate.now()));
        //ObservableList<PersonCard> mappedList = EasyBind.map(
        //        personList, (markdate) -> new PersonCard(personList));
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory(personList, taskList);
        datePicker.setDayCellFactory(dayCellFactory);
        findDateForSelection();
    }

    /**
     * Makes the dateCell in datePickerSkin editable, and pass on the selected markdate to handler
     */
    private void findDateForSelection() {
        // Make datePicker editable (i.e. i think can select and update value)
        datePicker.setEditable(true);
        // TODO: 23/10/17 Able to execute findCommand when new markdate is selected
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate date = datePicker.getValue();
                logger.info("Date selected: " + date.toString());
/*
                try {
                    logic.execute(FindTaskCommand.COMMAND_WORD + " " + markdate.toString());
                    logic.execute(FindCommand.COMMAND_WORD + " " + markdate.toString());
                } catch (CommandException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

    /**
     * Go through personList and taskList for birthday and deadline respectively.
     * Update dateCell with different colour and tooltip
     * @param personList
     * @param taskList
     * @return
     */
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
                                DateTimeFormatter format1 = DateTimeFormatter.ofPattern("ddMMyy");
                                if (MonthDay.from(item).equals
                                        (MonthDay.from(LocalDate.parse(person.getBirthday().toString(), format1)))) {
                                    setTooltip(new Tooltip(person.getName() + "'s Birthday!"));
                                    setStyle("-fx-background-color: #f1a3ff;");
                                }
                            } catch (DateTimeParseException exc) {
                                logger.warning("Not parsable: " + person.getBirthday().toString());
                                throw exc;
                            }
                        }
                        for (ReadOnlyTask task: taskList) {
                            try {
                                DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                //ensure that Deadline/Startdate is valid, after computer is invented
                                assert LocalDate.parse(task.getDeadline().toString(), format2).getYear()
                                        >= (LocalDate.now().getYear() - 100);
                                if (item.equals(LocalDate.parse(task.getDeadline().toString(), format2))) {
                                    setTooltip(new Tooltip("Deadline!"));
                                    setStyle("-fx-background-color: #ff444d;");
                                }
                            } catch (DateTimeParseException exc) {
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
