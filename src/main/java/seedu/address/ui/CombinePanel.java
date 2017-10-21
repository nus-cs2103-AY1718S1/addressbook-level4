package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LessonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.logic.Logic;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class CombinePanel extends UiPart<Region>{


    private static final int ROW = 6;
    private static final int COL = 13;
    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";

    private static final String FXML = "CombinePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;
    private GridData[][] gridData;

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

    public CombinePanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        gridData = new GridData[ROW][COL];
        initGridData();
        generateTimeTableGrid();
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultPage();
        timeBox.setVisible(false);
        browser.setVisible(false);
        registerAsAnEventHandler(this);

    }

    private void initGridData() {
        for(int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++){
                gridData[i][j] = new GridData();
            }
        }
    }

    @Subscribe
    private void handleViewedLessonEvent(ViewedLessonEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        generateTimeTableGrid();
        if(ListingUnit.getCurrentListingUnit().equals(ListingUnit.LESSON)){
            timeBox.setVisible(true);
            browser.setVisible(false);
        }else if(ListingUnit.getCurrentListingUnit().equals(ListingUnit.LOCATION)){
            timeBox.setVisible(false);
            browser.setVisible(true);
        }else{
            timeBox.setVisible(false);
            browser.setVisible(false);
        }

    }


    public void generateTimeTableGrid(){

        ObservableList<ReadOnlyLesson> lessons =  logic.getFilteredLessonList();
        initGridData();

        Node node = timetableGrid.getChildren().get(0);
        timetableGrid.getChildren().clear();
        timetableGrid.getChildren().add(0,node);

        for(int i = 0;i < lessons.size(); i++){
            ReadOnlyLesson lesson = lessons.get(i);
            String text = lesson.getCode() + " " + lesson.getClassType() +
                    "(" + lesson.getGroup() + ") " + lesson.getLocation();
            String timeText = lesson.getTimeSlot().toString();
            int weekDayRow = getWeekDay(timeText.substring(0,3));
            int startHourCol = getTime(timeText.substring(4, 6));
            int endHourSpan = getTime(timeText.substring(9, 11)) - startHourCol;
            gridData[weekDayRow][startHourCol] = new GridData(text,weekDayRow,startHourCol,endHourSpan);

        }

        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++){
                GridData data = gridData[i][j];
                String text = data.text;
                int weekDayRow = data.weekDayRow;
                int startHourCol = data.startHourCol;
                int endHourSpan = data.endHourSpan;
                if( i == weekDayRow &&  j == startHourCol ){
                    TextArea lbl = new TextArea(text);
                    lbl.setId("lbl");
                    lbl.setWrapText(true);
                    lbl.setStyle("-fx-control-inner-background:black;" +
                            " -fx-background-color: #383838;" +
                            " -fx-border-color: #EEEEEE" +
                            " -fx-border-width: 10" +
                            " -fx-padding: 5 5 5 5; " +
                            " -fx-font-family: Consolas; " +
                            "-fx-text-fill: #00ff00;");
                    timetableGrid.setGridLinesVisible(true);
                    timetableGrid.add(lbl, j, i, endHourSpan, 1);
                }
            }
        }
    }




    private int getWeekDay(String text){
        text = text.toUpperCase();
        switch (text){
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

    private int getTime(String text){
        int time = Integer.parseInt(text);
        return time - 8;
    }



    private void clearGrid() {
        ObservableList<Node> list = timetableGrid.getChildren();
        final List<Node> removalCandidates = new ArrayList<>();

        Iterator<Node> iter = list.iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node.getId().contains("lbl")) {
                node.setVisible(false);
                removalCandidates.add(node);
            }
        }
        timetableGrid.getChildren().removeAll(removalCandidates);
    }


    /************* BROWSER PANNEL *********/

    private void loadLessonPage(ReadOnlyLesson lesson) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + lesson.getCode().fullCodeName.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
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
        loadLessonPage(event.getNewSelection().lesson);
        if(ListingUnit.getCurrentListingUnit().equals(ListingUnit.LOCATION)){
            timeBox.setVisible(false);
            browser.setVisible(true);
        }
    }

}

class GridData{
    String text;
    Integer weekDayRow, startHourCol, endHourSpan;

    public GridData(){
        this("",-1,-1,-1);
    }

    public GridData(String text, int weekDayRow, int startHourCol, int endHourSpan){
        this.text = text;
        this.weekDayRow = weekDayRow;
        this.startHourCol = startHourCol;
        this.endHourSpan = endHourSpan;
    }
    @Override
    public int hashCode() {
        return Objects.hash(text, weekDayRow, startHourCol, endHourSpan);
    }
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GridData other = (GridData) obj;
        if (!text.equals(other.text))
            return false;
        if (!weekDayRow.equals(other.weekDayRow))
            return false;
        if (!startHourCol.equals(other.startHourCol))
            return false;
        if (!endHourSpan.equals(other.endHourSpan))
            return false;
        return true;
    }
}