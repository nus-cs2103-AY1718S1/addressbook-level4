package seedu.address.ui;

import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author Valerieyue

/**
 * The birthday statistics panel of the App.
 */
public class TagStatisticsPanel extends UiPart<Region> {

    private static final String FXML = "TagStatisticsPanel.fxml";

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ObservableList<String> tagNames = FXCollections.observableArrayList();

    public TagStatisticsPanel(ReadOnlyAddressBook readOnlyAddressBook) {
        super(FXML);
        setPersonData(readOnlyAddressBook);
        barChart.setCategoryGap(10);
        barChart.setTitle("Tags Statistics");
        xAxis.setLabel("Tags");
        registerAsAnEventHandler(this);
    }

    private void setPersonData(ReadOnlyAddressBook readOnlyAddressBook) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Get an array with the tags.
        tagNames.clear();
        for (ReadOnlyPerson person : readOnlyAddressBook.getPersonList()) {
            Set<Tag> personTags = person.getTags();
            for (Tag tag : personTags) {
                // to prevent adding duplicated tags
                if (!tagNames.contains(tag.getTagName())) {
                    tagNames.add(tag.getTagName());
                }
            }
        }
        Logger.getLogger(tagNames.toString());

        // Assign the tag as categories for the horizontal axis.
        xAxis.setCategories(tagNames);

        // Count the number of people who have the same tag.
        int[] tagCounter = new int[tagNames.size()];
        for (ReadOnlyPerson p : readOnlyAddressBook.getPersonList()) {
            String tagString = p.getTags().toString();
            String[] tagArray = tagString.split(",");
            for (int k = 0; k < tagArray.length; k++) {
                String tag = tagArray[k].replaceAll("[^a-zA-Z]+", "");
                if (tagNames.indexOf(tag) >= 0 && tagNames.indexOf(tag) < tagNames.size()) {
                    tagCounter[tagNames.indexOf(tag)]++;
                }
            }
        }

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < tagCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(tagNames.get(i), tagCounter[i]));
        }

        Platform.runLater(()-> {
            barChart.getData().clear();
            barChart.getData().add(series);
            barChart.setLegendVisible(false);
        });

    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info("Tag statistics updated.");
        setPersonData(abce.data);
    }
}
