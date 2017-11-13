
package seedu.address.ui;

import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_LARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_NORMAL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_SMALL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XLARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XSMALL;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.LessonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RemarkChangedEvent;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.logic.Logic;
import seedu.address.model.FontSizeUnit;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;
import seedu.address.model.module.predicates.SelectedStickyNotePredicate;
//@@author caoliangnus
/**
 * The UI component that is responsible for combining the web browser panel, the timetable panel and sticky notes panel.
 */
public class CombinePanel extends UiPart<Region> {


    public static final String DEFAULT_PAGE = "default.html";
    public static final String NUS_MAP_SEARCH_URL_PREFIX = "http://map.nus.edu.sg/#page=search&type=by&qword=";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1";

    private static final String FXML = "CombinePanel.fxml";
    private static final String LESSON_NODE_ID = "lessonNode";
    private static final String STICKY_NOTE = "stickyNote";
    private static final String HEADER = "header";
    private static final int ROW = 6;
    private static final int COL = 13;
    private static final int START_TIME = 8;

    private static final Pattern LOCATION_KEYWORD_PATTERN = Pattern.compile(".*(?=-)");

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;
    private GridData[][] gridData;
    private int[][]  gridDataCheckTable;
    private String[][]noteData;
    private ReadOnlyLesson selectedModule;

    @FXML
    private StackPane stackPane;
    @FXML
    private WebView browser;
    @FXML
    private HBox timeBox;
    @FXML
    private GridPane timetableGrid;
    @FXML
    private HBox timeHeader;
    @FXML
    private HBox noteBox;
    @FXML
    private GridPane noteGrid;

    public CombinePanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        gridData = new GridData[ROW][COL];
        gridDataCheckTable = new int[ROW][COL];
        initGridData();
        generateTimeTableGrid();
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultPage();
        timeBox.setVisible(false);
        browser.setVisible(false);
        registerAsAnEventHandler(this);
        selectedModule = null;

        noteBox.setVisible(true);
        stickyNotesInit();

    }

    @Subscribe
    private void handleViewedLessonEvent(ViewedLessonEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        generateTimeTableGrid();
        if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.LESSON)) {
            timeBox.setVisible(true);
            browser.setVisible(false);
            noteBox.setVisible(false);
        } else if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.LOCATION)) {
            timeBox.setVisible(false);
            browser.setVisible(false); //Only set as visible when location is selected
            noteBox.setVisible(false);
        } else {
            timeBox.setVisible(false);
            browser.setVisible(false);
            noteBox.setVisible(true);
        }
    }


    /**
     * Initialize grid data.
     */
    private void initGridData() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                gridData[i][j] = new GridData();
                gridDataCheckTable[i][j] = 0;
            }
        }
    }

    /**
     * Generate time slot header
     */
    public void generateTimeslotHeader() {
        String text;
        int k = START_TIME;
        for (int i = 1; i < COL + 1; i++) {

            if (k < 10) {
                text = "0" + k;
            } else {
                text = Integer.toString(k);
            }
            text += "00";
            Label header = new Label(text);
            header.setId(HEADER);
            timetableGrid.setHalignment(header, HPos.CENTER);
            timetableGrid.add(header, i, 0);
            k++;
        }
    }

    /**
     * Generate week day row header
     */
    public void generateWeekDay() {
        for (int i = 1; i < ROW; i++) {
            String dayOfWeek = DayOfWeek.of(i).toString().substring(0, 3); //The first 3 characters of WeekDay, eg MON
            Label label = new Label(dayOfWeek);
            label.setId(HEADER);
            timetableGrid.setValignment(label, VPos.CENTER);
            timetableGrid.setHalignment(label, HPos.CENTER);
            timetableGrid.add(label, 0, i);
        }
    }

    /**
     * Generate timetable data
     */
    public void generateTimeTableData() {
        ObservableList<ReadOnlyLesson> lessons = logic.getFilteredLessonList();
        initGridData();

        //This code allows the gridline to stay appear
        Node node = timetableGrid.getChildren().get(0);
        timetableGrid.getChildren().clear();
        timetableGrid.getChildren().add(0, node);

        for (int i = 0; i < lessons.size(); i++) {
            ReadOnlyLesson lesson = lessons.get(i);
            String text = lesson.getCode() + " " + lesson.getClassType()
                    + "(" + lesson.getGroup() + ") " + lesson.getLocation();
            String timeText = lesson.getTimeSlot().toString();
            int weekDayRow = getWeekDay(timeText.substring(0, 3)); //First 3 characters are Weekday
            int startHourCol = getTime(timeText.substring(4, 6));  //Next 2 characters are StartHour
            int endHourSpan = getTime(timeText.substring(9, 11)) - startHourCol; //find the number of hours for lesson
            boolean isAvailable = false;
            if (!isOccupy(weekDayRow, startHourCol, endHourSpan)) {
                isAvailable = true;
            }

            if (isAvailable && gridData[weekDayRow][startHourCol].getCount() == 0) {
                gridData[weekDayRow][startHourCol] = new GridData(text, weekDayRow, startHourCol,
                        endHourSpan, 1); //1 represents no other lessons occupy this time slot
            } else {
                int count = gridData[weekDayRow][startHourCol].getCount();
                gridData[weekDayRow][startHourCol] = new GridData(text, weekDayRow, startHourCol,
                        endHourSpan, count + 2); //count + 2 to indicate another lesson occupying this time slot
            }

            //Update gridDataCheckTable
            for (int j = 0; j < endHourSpan; j++) {
                gridDataCheckTable[weekDayRow][startHourCol + j] = 1;
            }
        }
    }

    /**
     * Check for timetable grid.
     */
    public boolean isOccupy(int row, int col, int span) {
        for (int i = 0; i < span; i++) {
            if (gridDataCheckTable[row][col + i] == 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * Generate timetable grid.
     */
    public void generateTimeTableGrid() {

        generateTimeTableData();
        generateTimeslotHeader();
        generateWeekDay();

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                GridData data = gridData[i][j];
                String text = data.getText();
                int weekDayRow = data.getWeekDayRow();
                int startHourCol = data.getStartHourCol();
                int endHourSpan = data.getEndHourSpan();
                int count = data.getCount();
                if (i == weekDayRow &&  j == startHourCol) {
                    TextArea lbl = new TextArea(text);
                    lbl.setWrapText(true);
                    lbl.setEditable(false);
                    lbl.setId(LESSON_NODE_ID);
                    timetableGrid.setGridLinesVisible(true);
                    if (endHourSpan == 1) {
                        lbl.setStyle("-fx-font-size: small");
                    }
                    timetableGrid.add(lbl, j + 1, i + 1, endHourSpan, 1);
                    if (count > 1) {
                        lbl.setStyle("-fx-control-inner-background: red");
                    }
                }
            }
        }
    }


    private int getWeekDay(String textInput) {
        textInput = textInput.toUpperCase();
        switch (textInput) {
        case "MON":
            return 0;
        case "TUE":
            return 1;
        case "WED":
            return 2;
        case "THU":
            return 3;
        case "FRI":
            return 4;
        default:
            return -1;
        }
    }

    private int getTime(String text) {
        int time = Integer.parseInt(text);
        return time - START_TIME;
    }


    /************* BROWSER PANNEL *********/

    private void loadLessonPage(ReadOnlyLesson lesson) {
        loadPage(NUS_MAP_SEARCH_URL_PREFIX + getImportantKeywords(lesson.getLocation().toString()));
    }

    /**
     * Get substring before hyphen.
     */
    private String getImportantKeywords(String location) {
        Matcher matcher = LOCATION_KEYWORD_PATTERN.matcher(location);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return location;
        }
    }


    /**
     * Load page for given url
     * @param url
     */
    public void loadPage(String url) {
        WebEngine engine = browser.getEngine();
        engine.setUserAgent(USER_AGENT);
        Platform.runLater(() -> engine.load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleLessonPanelSelectionChangedEvent(LessonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.LOCATION)) {
            loadLessonPage(event.getNewSelection().lesson);
            timeBox.setVisible(false);
            browser.setVisible(true);
            noteBox.setVisible(false);
        }

        if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.MODULE)) {
            selectedModule = event.getNewSelection().lesson;
            logic.setRemarkPredicate(new SelectedStickyNotePredicate(event.getNewSelection().lesson.getCode()));
            stickyNotesInit();
        }
    }


    /***************** Sticky Note *****************/
    //@@author junming403
    @Subscribe
    private void handleRemarkChangedEvent(RemarkChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        stickyNotesInit();
    }

    /**
     * This method will initilize the data for sticky notes screen
     */
    public void noteDataInit() {
        ObservableList<Remark> remarks = logic.getFilteredRemarkList();
        int size = remarks.size();
        int count = 0;
        int index = 1;

        //Only display 9 notes, so 3 x 3 matrix
        noteData = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (count >= size) {
                    continue;
                }
                Remark remark = remarks.get(count);
                if (count < size) {
                    noteData[i][j] = index + "." + remark.moduleCode.fullCodeName + " : " + remark.value;
                    index++;
                    count++;
                }
            }
        }
    }
    //@@author

    //@@author caoliangnus
    /**
     * This method will initialize StickyNote screen
     */
    public void stickyNotesInit() {
        noteGrid.getChildren().clear();
        noteDataInit();
        //noteGrid.setGridLinesVisible(true);
        noteGrid.setHgap(20); //horizontal gap in pixels => that's what you are asking for
        noteGrid.setVgap(20); //vertical gap in pixels

        //Only display 9 notes, so 3 x 3 Matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String text = noteData[i][j];
                if (text == null) {
                    return;
                }

                //Generate random RGB color
                int x = 120 + (int) (Math.random() * 255);
                int y = 120 + (int) (Math.random() * 255);
                int z = 120 + (int) (Math.random() * 255);

                TextArea ta = new TextArea(text);

                ta.setWrapText(true);
                ta.setEditable(false);

                StackPane noteStackPane = new StackPane();
                noteStackPane.setStyle("-fx-background-color: rgba(" + x + "," + y + ", " + z + ", 0.5);"
                        + "-fx-effect: dropshadow(gaussian, red, " + 20 + ", 0, 0, 0);"
                        + "-fx-background-insets: " + 10 + ";");
                ta.setId(STICKY_NOTE);
                noteStackPane.getChildren().add(ta);
                noteGrid.add(noteStackPane, j, i);
                FontSizeUnit currFontSize = FontSizeUnit.getCurrentFontSizeUnit();
                setFontSizeUnit(currFontSize);
            }
        }
    }

    //@@author cctdaniel
    /**
     * Sets the remark style to user preferred font size.
     */
    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            setFontSizeHelper("12");
            break;

        case FONT_SIZE_SMALL:
            setFontSizeHelper("17");
            break;

        case FONT_SIZE_NORMAL:
            setFontSizeHelper("25");
            break;

        case FONT_SIZE_LARGE:
            setFontSizeHelper("32");
            break;

        case FONT_SIZE_XLARGE:
            setFontSizeHelper("40");
            break;

        default:
            break;
        }
    }

    /**
     * Helper method to set font size.
     */
    private void setFontSizeHelper(String fontSize) {
        noteGrid.getChildren().forEach((node) -> {
            if (node instanceof StackPane) {
                for (Node n : ((StackPane) node).getChildren()) {
                    if (n instanceof TextArea) {
                        n.setStyle("-fx-font-size: " + fontSize + ";");
                    }
                }
            }
        });
    }

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }


    /**
     * Helper method to set font size based on FontSizeUnit.
     */
    private void setFontSizeUnit(FontSizeUnit currFontSizeUnit) {
        switch (currFontSizeUnit) {
        case FONT_SIZE_XSMALL_UNIT:
            setFontSize(FONT_SIZE_XSMALL);
            break;

        case FONT_SIZE_SMALL_UNIT:
            setFontSize(FONT_SIZE_SMALL);
            break;

        case FONT_SIZE_NORMAL_UNIT:
            setFontSize(FONT_SIZE_NORMAL);
            break;

        case FONT_SIZE_LARGE_UNIT:
            setFontSize(FONT_SIZE_LARGE);
            break;

        case FONT_SIZE_XLARGE_UNIT:
            setFontSize(FONT_SIZE_XLARGE);
            break;

        default:
            break;
        }
    }
    //@@author
}

//@@author caoliangnus
/**
 * Contains data related to timetable grid object in JavaFX.
 */
class GridData {
    private String text;
    private Integer weekDayRow;
    private Integer startHourCol;
    private Integer endHourSpan;
    private Integer count;

    public GridData() {
        this("", -1, -1, -1, 0);
    }

    public GridData(String text, int weekDayRow, int startHourCol, int endHourSpan, int count) {
        this.text = text;
        this.weekDayRow = weekDayRow;
        this.startHourCol = startHourCol;
        this.endHourSpan = endHourSpan;
        this.count = count;
    }

    public String getText() {
        return text;
    }

    public Integer getEndHourSpan() {
        return endHourSpan;
    }

    public Integer getStartHourCol() {
        return startHourCol;
    }

    public Integer getWeekDayRow() {
        return weekDayRow;
    }

    public Integer getCount() {
        return count;
    }


    @Override
    public int hashCode() {
        return Objects.hash(text, weekDayRow, startHourCol, endHourSpan);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GridData other = (GridData) obj;
        if (!text.equals(other.text)) {
            return false;
        }
        if (!weekDayRow.equals(other.weekDayRow)) {
            return false;
        }
        if (!startHourCol.equals(other.startHourCol)) {
            return false;
        }
        if (!endHourSpan.equals(other.endHourSpan)) {
            return false;
        }
        return true;
    }
}

