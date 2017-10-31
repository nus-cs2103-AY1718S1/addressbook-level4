package seedu.address.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

/**
 * The CalendarPanel panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;
    private final Model model;

    private DatePicker datePicker;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    private StackPane calendarPane;

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
        // TODO: 23/10/17 Able to execute findCommand when a colour date is selected
        // TODO: 26/10/17 Able to not execute findCommand when cell is not colour, able to find birthday with dd-MM
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate date = datePicker.getValue();
                String dateString = date.format(formatter);
                String birthdayString = dateString.substring(0, 5);
                logger.info("Date selected: " + dateString);
                try {
                    String command = FindTaskCommand.COMMAND_WORD;
                    List<String> mode = new ArrayList<>();
                    List<String> dateMode = new ArrayList<>();
                    int order = 0;

                    // load value to be selected base on current mode
                    mode.add("ab");
                    mode.add("tm");
                    dateMode.add(birthdayString);
                    dateMode.add(dateString);

                    if (model.getCommandMode().equals(mode.get(1))) {
                        order = 1;
                    }
                    int changedOrder = ((order - 1) == 0) ? 0 : 1;

                    logic.execute(command + " " + dateMode.get(order));
                    logic.execute(ChangeModeCommand.COMMAND_WORD + " " + mode.get(changedOrder));
                    logic.execute(command + " " + dateMode.get(changedOrder));
                    logic.execute(ChangeModeCommand.COMMAND_WORD + " " + mode.get(order));
                } catch (CommandException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
                        StringBuilder s = new StringBuilder();
                        int bCount = 0;
                        int dCount = 0;
                        StringBuilder colour = new StringBuilder();

                        for (ReadOnlyPerson person: personList) {
                            try {
                                if (!person.getBirthday().isEmpty()) {
                                    if (MonthDay.from(item).equals
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
                                }
                            } catch (DateTimeParseException exc) {
                                logger.warning("Not parsable: " + person.getBirthday().toString());
                                throw exc;
                            }
                        }
                        for (ReadOnlyTask task: taskList) {
                            String taskDate = "";
                            try {
                                if (!task.getDeadline().isEmpty()) {
                                    Date deadline = ParserUtil.parseDate(task.getDeadline().date);
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    taskDate = dateFormat.format(deadline);
                                }
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
                                logger.warning("Not parsable: " + finalTaskDate);
                                throw exc;
                            }
                        }
                        if (!s.toString().equals("")) {
                            setTooltip(new Tooltip(s.toString()));
                        }
                        setStyle(colour.toString());
                    }
                };
            }
        };
        return dayCellFactory;
    }

    /**
     * Select the date on calendar
     * @param date
     */
    /*private void selectDate(DatePickerContent content, String date) {
        ObservableList<Node> dateCellList = content.getChildren();
        LocalDate localDate = LocalDate.parse(date, formatter);

        logger.info("day of month: " + localDate.getDayOfMonth());
        for (Node cell: dateCellList) {
            logger.info("cell id: " + cell.getId());
            if (cell.getId().equals(localDate.getDayOfMonth())) {
                datePicker.setValue(localDate);
                logger.info("date picked: " + localDate);
                break;
            }
        }
    }

    // TODO: 26/10/17 implement event in the calendar, such that binding of dates is possible
    /*
    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    } */
}
