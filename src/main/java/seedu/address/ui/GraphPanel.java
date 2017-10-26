package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Displays the specified graphs that the user wants
 */
public class GraphPanel extends UiPart<Region> {


    private static final String FXML = "GraphPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<ReadOnlyPerson> people;
    private XYChart.Series<String, Double> series = new XYChart.Series<>();

    @FXML
    private CategoryAxis xNameAxis;
    @FXML
    private NumberAxis yGradeAxis;
    @FXML
    private LineChart<String, Double> lineChart;

    public GraphPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        this.people = personList;
        registerAsAnEventHandler(this);
    }

    /**
     * Displays statistics of @param person according to class
     */
    private void displayGraphStats (ReadOnlyPerson person) {
        xNameAxis.setLabel("Student Names");
        yGradeAxis.setLabel("Grades");
        lineChart.setTitle(person.getFormClass().toString());
        lineChart.layout();
        lineChart.setAnimated(false);

        for (ReadOnlyPerson people : people) {
            if (people.getFormClass().equals(person.getFormClass())) {
                series.getData().add(new XYChart.Data<>(people.getName().toString(),
                        Double.parseDouble(people.getGrades().toString())));
            }
        }

        try {
            series.getData().sort(Comparator.comparingDouble(d -> d.getYValue()));
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
        lineChart.getData().add(series);
        lineChart.setLegendVisible(false);
    }


    private void resetGraphStats() {
        series.getData().clear();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetGraphStats();
        displayGraphStats(event.getNewSelection().person);
    }
}
