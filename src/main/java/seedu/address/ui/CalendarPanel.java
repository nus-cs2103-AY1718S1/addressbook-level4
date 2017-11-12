package seedu.address.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.logging.Logger;

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
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.ChangeModeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.tasks.FindTaskCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;

//@@author tpq95
/**
 * The CalendarPanel panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;
    private final Model model;

    //private DatePicker datePicker;

    @FXML
    private StackPane calendarPane;

    @FXML
    private DatePicker datePicker;

    public CalendarPanel(Logic logic, Model model) {
        super(FXML);
        this.logic = logic;
        this.model = model;
        setDate(model.getAddressBook().getPersonList(), model.getAddressBook().getTaskList());
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Load the calendar which is the datePickerSkin with DateCell from datePicker
     */
    private void loadDefaultPage() {
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        DatePickerContent popupContent = (DatePickerContent) datePickerSkin.getPopupContent();

        popupContent.setPrefHeight(calendarPane.getPrefHeight());
        popupContent.setPrefWidth(calendarPane.getPrefWidth());
        calendarPane.getChildren().add(popupContent);
        //selectDate(popupContent, "30-10-2017");
    }

    /**
     * Load datePicker with various dates, birthday from personList and deadline from taskList
     * @param personList
     * @param taskList
     */
    private void setDate(ObservableList<ReadOnlyPerson> personList, ObservableList<ReadOnlyTask> taskList) {
        datePicker = new DatePicker((LocalDate.now()));
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

        // TODO: 26/10/17 Able to not execute findCommand when cell is not colour
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String currentMode = model.getCommandMode().toString();
                String date = convertDateFromPicker();

                findPersonsWithBirthday(date);
                findTasksWithDeadline(date);
                changeToOriginalMode(currentMode);
            }
        });
    }

    /**
     * Perform a switch back to the original mode of Mobilize
     * @param currentMode
     */
    private void changeToOriginalMode(String currentMode) {
        try {
            logic.execute(ChangeModeCommand.COMMAND_WORD + " " + currentMode);
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String convertDateFromPicker() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return datePicker.getValue().format(formatter);
    }

    /**
     * Execute FindCommand to find tasks with deadline that match selected date
     * @param dateString
     */
    private void findTasksWithDeadline(String dateString) {
        try {
            // Change to TaskManager mode
            logic.execute(ChangeModeCommand.COMMAND_WORD + " tm");
            // Execute FindCommand for dateString
            logic.execute(FindTaskCommand.COMMAND_WORD + " " + dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (CommandException e) {
            e.printStackTrace();
        }

    }

    /**
     * Edit the given date string to contain only dd-MM
     * Execute FindCommand to find persons with birthday that match selected date
     * @param dateString
     */
    private void findPersonsWithBirthday(String dateString) {
        String birthdayString = dateString.substring(0, 5);
        logger.info("Date selected: " + dateString);
        try {
            // Change to AddressBook mode
            logic.execute(ChangeModeCommand.COMMAND_WORD + " ab");
            // Execute FindCommand for dateString
            logic.execute(FindTaskCommand.COMMAND_WORD + " " + birthdayString);
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                        StringBuilder s = new StringBuilder();
                        int bCount = 0;
                        int dCount = 0;
                        StringBuilder colour = new StringBuilder();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                        for (ReadOnlyPerson person: personList) {
                            try {
                                if (!person.getBirthday().isEmpty() && MonthDay.from(item).equals
                                            (MonthDay.from(LocalDate.parse(person.getBirthday().toString(),
                                                    formatter)))) {
                                    if (bCount == 0) {
                                        bCount++;
                                        s.append(person.getName() + "'s Birthday");
                                    } else if (bCount > 0) {
                                        int endIndex = s.indexOf("Birthday");
                                        s.delete(0, endIndex);
                                        bCount++;
                                        s.insert(0, bCount + " ");
                                        if (bCount == 2) {
                                            s.append("s");
                                        }
                                    }
                                    colour = new StringBuilder("-fx-background-color: #f1a3ff;");

                                }
                            } catch (DateTimeParseException exc) {
                                logger.warning("Not parsable: " + person.getBirthday().toString());
                                throw exc;
                            }
                        }
                        for (ReadOnlyTask task: taskList) {
                            String taskDate = "";
                            if (!task.getDeadline().isEmpty()) {
                                try {
                                    Date deadline = ParserUtil.parseDate(task.getDeadline().date);
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    taskDate = dateFormat.format(deadline);
                                } catch (IllegalValueException e) {
                                    e.printStackTrace();
                                }
                                String finalTaskDate = taskDate;
                                try {
                                    //ensure that Deadline/Startdate is valid, after computer is invented
                                    assert LocalDate.parse(finalTaskDate, formatter).getYear()
                                            >= (LocalDate.now().getYear() - 100);
                                    if (item.equals(LocalDate.parse(finalTaskDate, formatter))) {
                                        if ((bCount == 0) && (dCount == 0)) {
                                            dCount++;
                                            s.append(dCount + " Deadline");
                                        } else if ((bCount > 0) && (dCount == 0)) {
                                            dCount++;
                                            s.append(" + " + dCount + " Deadline");
                                        } else if (dCount > 0) {
                                            dCount++;
                                            int endIndex = s.indexOf(" Deadline");
                                            s.replace(endIndex - 1, endIndex, dCount + "");
                                            if (dCount == 2) {
                                                s.append("s");
                                            }
                                        }

                                        if (bCount == 0) {
                                            colour = new StringBuilder("-fx-background-color: #ff444d;");
                                        } else {
                                            colour = new StringBuilder("-fx-background-color: #feff31;");
                                        }
                                    }
                                } catch (DateTimeParseException exc) {
                                    logger.warning("Not parsable: " + task.getDeadline().date);
                                    throw exc;
                                }
                            }
                        }
                        if (s.length() != 0) {
                            setTooltip(new Tooltip(s.toString()));
                        }
                        setStyle(colour.toString());
                    }
                };
            }
        };
        return dayCellFactory;
    }

}
