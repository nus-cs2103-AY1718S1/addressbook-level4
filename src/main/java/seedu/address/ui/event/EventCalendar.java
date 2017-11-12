package seedu.address.ui.event;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

//@@author dennaloh
/**
 * The panel on the right side of {@link EventListPanel}. Used to show a calendar.
 */
public class EventCalendar extends UiPart<Region> {
    private static final String FXML = "event/EventCalendar.fxml";
    private static final int MAX_NUMBER_DAYS = 42;

    @FXML
    private Label date1;
    @FXML
    private Label date2;
    @FXML
    private Label date3;
    @FXML
    private Label date4;
    @FXML
    private Label date5;
    @FXML
    private Label date6;
    @FXML
    private Label date7;
    @FXML
    private Label date8;
    @FXML
    private Label date9;
    @FXML
    private Label date10;
    @FXML
    private Label date11;
    @FXML
    private Label date12;
    @FXML
    private Label date13;
    @FXML
    private Label date14;
    @FXML
    private Label date15;
    @FXML
    private Label date16;
    @FXML
    private Label date17;
    @FXML
    private Label date18;
    @FXML
    private Label date19;
    @FXML
    private Label date20;
    @FXML
    private Label date21;
    @FXML
    private Label date22;
    @FXML
    private Label date23;
    @FXML
    private Label date24;
    @FXML
    private Label date25;
    @FXML
    private Label date26;
    @FXML
    private Label date27;
    @FXML
    private Label date28;
    @FXML
    private Label date29;
    @FXML
    private Label date30;
    @FXML
    private Label date31;
    @FXML
    private Label date32;
    @FXML
    private Label date33;
    @FXML
    private Label date34;
    @FXML
    private Label date35;
    @FXML
    private Label date36;
    @FXML
    private Label date37;
    @FXML
    private Label date38;
    @FXML
    private Label date39;
    @FXML
    private Label date40;
    @FXML
    private Label date41;
    @FXML
    private Label date42;

    @FXML
    private Label monthName;

    private Label[] dateArray;
    private MonthDateBuilder monthDateBuilder;

    public EventCalendar() {
        super(FXML);
        monthDateBuilder = new MonthDateBuilder();
        inits();
        setDates(monthDateBuilder.getMonthDateArray());
        monthName.setText(monthDateBuilder.getNameOfMonth());
    }

    /**
     * Puts the dates from monthDateArray into the dateArray
     */
    public void setDates(String[] monthDateArray) {
        for (int i = 0; i < MAX_NUMBER_DAYS; i++) {
            dateArray[i].setText(monthDateArray[i]);
        }
    }

    /**
     * Puts all text views into array to facilitate looping through
     */
    public void inits() {
        dateArray = new Label[MAX_NUMBER_DAYS];
        dateArray[0] = date1;
        dateArray[1] = date2;
        dateArray[2] = date3;
        dateArray[3] = date4;
        dateArray[4] = date5;
        dateArray[5] = date6;
        dateArray[6] = date7;
        dateArray[7] = date8;
        dateArray[8] = date9;
        dateArray[9] = date10;
        dateArray[10] = date11;
        dateArray[11] = date12;
        dateArray[12] = date13;
        dateArray[13] = date14;
        dateArray[14] = date15;
        dateArray[15] = date16;
        dateArray[16] = date17;
        dateArray[17] = date18;
        dateArray[18] = date19;
        dateArray[19] = date20;
        dateArray[20] = date21;
        dateArray[21] = date22;
        dateArray[22] = date23;
        dateArray[23] = date24;
        dateArray[24] = date25;
        dateArray[25] = date26;
        dateArray[26] = date27;
        dateArray[27] = date28;
        dateArray[28] = date29;
        dateArray[29] = date30;
        dateArray[30] = date31;
        dateArray[31] = date32;
        dateArray[32] = date33;
        dateArray[33] = date34;
        dateArray[34] = date35;
        dateArray[35] = date36;
        dateArray[36] = date37;
        dateArray[37] = date38;
        dateArray[38] = date39;
        dateArray[39] = date40;
        dateArray[40] = date41;
        dateArray[41] = date42;
    }
}
