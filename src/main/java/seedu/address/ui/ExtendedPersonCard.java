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
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Extended Person Card Panel that displays the details of a Person
 */
public class ExtendedPersonCard extends UiPart<Region> {

    private static final String FXML = "ExtendedPersonCard.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<ReadOnlyPerson> people;
    private XYChart.Series<String, Double> series = new XYChart.Series<>();

    @FXML
    private HBox cardpane;
    @FXML
    private CategoryAxis xNameAxis;
    @FXML
    private NumberAxis yGradeAxis;
    @FXML
    private LineChart<String, Double> lineChart;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label formClass;
    @FXML
    private Label grades;
    @FXML
    private Label postalCode;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    public ExtendedPersonCard(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        this.people = personList;
        registerAsAnEventHandler(this);
    }

    /**
     * Updates person details as displayed on the extended person card panel
     */
    protected void loadPersonDetails(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        formClass.setText(person.getFormClass().toString());
        grades.setText(person.getGrades().toString());
        postalCode.setText(person.getPostalCode().toString());
        email.setText(person.getEmail().toString());
        remark.setText(person.getRemark().toString());
        tags.getChildren().clear();
        initTags(person);
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
        loadPersonDetails(event.getNewSelection().person);
        resetGraphStats();
        displayGraphStats(event.getNewSelection().person);
    }

    /**
     * Initializes and styles tags belonging to a person.
     * @param person must be a valid.
     */
    protected void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-font-size:15px");
            tags.getChildren().add(tagLabel);
        });
    }
}
