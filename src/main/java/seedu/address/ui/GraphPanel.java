package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.model.person.ReadOnlyPerson;

//@@author nahtanojmil
/**
 * Displays the specified graphs that the user wants
 */
public class GraphPanel extends UiPart<Region> {


    private static final String FXML = "GraphPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<ReadOnlyPerson> people;
    private XYChart.Series<String, Double> lineSeries = new XYChart.Series<>();
    private XYChart.Series<String, Double> barSeries = new XYChart.Series<>();

    private ListElementPointer historySnapshot;
    private String tagString;
    private Logic logic;

    @FXML
    private TabPane tabPaneGraphs;
    @FXML
    private CategoryAxis xLineNameAxis;
    @FXML
    private NumberAxis yLineGradeAxis;
    @FXML
    private CategoryAxis xBarNameAxis;
    @FXML
    private NumberAxis yBarGradeAxis;
    @FXML
    private LineChart<String, Double> lineChart;
    @FXML
    private BarChart<String, Double> barChart;

    public GraphPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        people = logic.getFilteredPersonList();
        historySnapshot = logic.getHistorySnapshot();
        registerAsAnEventHandler(this);
    }

    /**
     * Checks the history of input user made
     */
    private boolean ifEnteredFindTag() {
        if (historySnapshot.hasCurrent()) {
            String textInput = historySnapshot.current();
            String[] splitString = textInput.split(" ");
            if (splitString[0].equals("ft")) {
                tagString = splitString[1];
                return true;
            }
        }

        return false;
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Displays line statistics of @param person according to class/tags
     */
    private void displayLineGraphStats(ReadOnlyPerson person) {

        lineChart.setAnimated(false);
        lineChart.layout();
        yLineGradeAxis.setLabel("Grades");
        xLineNameAxis.setLabel("Student Names");

        if (ifEnteredFindTag()) {
            lineChart.setTitle(tagString);
        } else {
            lineChart.setTitle(person.getFormClass().toString());
        }

        for (ReadOnlyPerson people : people) {
            if (ifEnteredFindTag()) {
                lineSeries.getData().add(new XYChart.Data<>(people.getName().toString(),
                        Double.parseDouble(people.getGrades().toString())));
            } else {
                if (people.getFormClass().equals(person.getFormClass())) {
                    lineSeries.getData().add(new XYChart.Data<>(people.getName().toString(),
                            Double.parseDouble(people.getGrades().toString())));
                }
            }
        }

        try {
            lineSeries.getData().sort(Comparator.comparingDouble(d -> d.getYValue()));
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }

        lineChart.getData().add(lineSeries);
        lineChart.setLegendVisible(false);

    }

    /**
     * Displays bar statistics of @param person according to class/tags
     */
    private void displayBarGraphStats(ReadOnlyPerson person) {

        xBarNameAxis.setLabel("Student Names");
        yBarGradeAxis.setLabel("Grades");
        barChart.setAnimated(false);
        barChart.layout();

        if (ifEnteredFindTag()) {
            lineChart.setTitle(tagString);
        } else {
            lineChart.setTitle(person.getFormClass().toString());
        }

        for (ReadOnlyPerson people : people) {
            if (ifEnteredFindTag()) {
                barSeries.getData().add(new XYChart.Data<>(people.getName().toString(),
                        Double.parseDouble(people.getGrades().toString())));
            } else {
                if (people.getFormClass().equals(person.getFormClass())) {
                    barSeries.getData().add(new XYChart.Data<>(people.getName().toString(),
                            Double.parseDouble(people.getGrades().toString())));
                }
            }
        }

        try {
            barSeries.getData().sort(Comparator.comparingDouble(d -> d.getYValue()));
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }

        barChart.getData().add(barSeries);
        barChart.setLegendVisible(false);
    }

    /**
     * Resets both graphs and series when a new person is selected.
     */
    private void resetGraphStats() {
        lineChart.getData().clear();
        lineSeries.getData().clear();
        barChart.getData().clear();
        barSeries.getData().clear();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        initHistory();
        resetGraphStats();
        displayLineGraphStats(event.getNewSelection().person);
        displayBarGraphStats(event.getNewSelection().person);
    }
}
