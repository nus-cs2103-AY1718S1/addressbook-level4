package seedu.address.ui;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
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
//@@author Valerieyue
/**
 * The birthday statistics panel of the App.
 */
public class BirthdayStatisticsPanel extends UiPart<Region> {

    private static final String FXML = "BirthdayStatisticsPanel.fxml";

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    public BirthdayStatisticsPanel(ReadOnlyAddressBook readOnlyAddressBook) {
        super(FXML);

        // Get an array with the English month names.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getShortMonths();
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
        setPersonData(readOnlyAddressBook);
        barChart.setCategoryGap(10);
        barChart.setTitle("Birthday Statistics");
        xAxis.setLabel("Month");
        registerAsAnEventHandler(this);
    }

    private void setPersonData(ReadOnlyAddressBook readOnlyAddressBook) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        // Count the number of people having their birthday in a specific month.
        int[] monthCounter = new int[12];
        int monthInt;
        for (ReadOnlyPerson p : readOnlyAddressBook.getPersonList()) {
            String month = p.getBirthday().toString().replaceAll("[^a-zA-Z]+", "");
            switch(month) {
            case "Feb":
                monthInt = 1;
                break;
            case "Mar":
                monthInt = 2;
                break;
            case "Apr":
                monthInt = 3;
                break;
            case "May":
                monthInt = 4;
                break;
            case "Jun":
                monthInt = 5;
                break;
            case "Jul":
                monthInt = 6;
                break;
            case "Aug":
                monthInt = 7;
                break;
            case "Sep":
                monthInt = 8;
                break;
            case "Oct":
                monthInt = 9;
                break;
            case "Nov":
                monthInt = 10;
                break;
            case "Dec":
                monthInt = 11;
                break;
            default: // Jan
                monthInt = 0;
                break;
            }
            monthCounter[monthInt]++;
        }

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        Platform.runLater(()-> {
            barChart.getData().clear();
            barChart.getData().add(series);
            barChart.setLegendVisible(false);
        });

    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info("Birthday statistics updated.");
        setPersonData(abce.data);
    }
}
