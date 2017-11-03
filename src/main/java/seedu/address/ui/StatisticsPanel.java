package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FilteredListChangedEvent;
import seedu.address.logic.statistics.Statistics;
import seedu.address.model.person.ReadOnlyPerson;

//@@author lincredibleJC
/**
 * Statistics Panel that displays the statistics of a filteredList
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private ObservableList<ReadOnlyPerson> currentList;
    private Statistics statistics;

    @FXML
    private VBox cardpane;
    @FXML
    private Label mean;
    @FXML
    private Label median;
    @FXML
    private Label mode;
    @FXML
    private Label variance;
    @FXML
    private Label standardDeviation;
    @FXML
    private Label quartile1;
    @FXML
    private Label quartile3;
    @FXML
    private Label interquartileRange;

    public StatisticsPanel(ObservableList<ReadOnlyPerson> currentList) {
        super(FXML);
        registerAsAnEventHandler(this);
        statistics = new Statistics(currentList);

    }

    /**
     * Updates list Statistics in the Statistics panel
     */
    protected void loadListStatistics() {
        mean.setText(statistics.getMeanString());
        median.setText(statistics.getMedianString());
        mode.setText(statistics.getModeString());
        variance.setText(statistics.getVarianceString());
        standardDeviation.setText(statistics.getStdDevString());
        quartile1.setText(statistics.getQuartile1String());
        quartile3.setText(statistics.getQuartile3String());
        interquartileRange.setText(statistics.getInterquartileRangeString());
    }

    @FXML
    @Subscribe
    private void handleFilteredListChangedEvent(FilteredListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        statistics.initScore(event.getCurrentFilteredList()); // Update currentList data
        loadListStatistics();
    }

}
