package seedu.address.ui;

import java.time.Clock;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Statistics Panel of the App.
 */
public class    StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";

    private static final Integer PERSON_ADDED_EARLIEST_YEAR = 2000;

    private static Clock clock = Clock.systemDefaultZone();


    @FXML
    private PieChart pieChart;

    public StatisticsPanel(ObservableList<ReadOnlyPerson> list) {
        super(FXML);

        pieChart.setTitle("Record of Persons Added By Year");
        pieChart.setData(tabulateAddedByYear(list));
    }

    /**
     * Formats the data into PieChart.Data for display
     */
    private ObservableList<PieChart.Data> tabulateAddedByYear(ObservableList<ReadOnlyPerson> list) {

        ArrayList<PieChart.Data> data = new ArrayList<>();

        ArrayList<Integer> yearData = collectYear(list);
        for (int y = 0; y < yearData.size(); y++) {
            if (yearData.get(y) > 0) {
                String yearLabel = Integer.toString(y + PERSON_ADDED_EARLIEST_YEAR)
                        + " - " + yearData.get(y);
                data.add(new PieChart.Data(yearLabel, yearData.get(y)));
            }
        }

        return FXCollections.observableArrayList(data);
    }

    /**
     * Collects the observablelist by year
     */
    private ArrayList<Integer> collectYear(ObservableList<ReadOnlyPerson> list) {

        int yearsToCollect = getCurrentYear() - PERSON_ADDED_EARLIEST_YEAR + 1;

        ArrayList<Integer> count = new ArrayList<>(Collections.nCopies(yearsToCollect + 1, 0));

        list.forEach((p) -> {
            Date givenDate = p.getCreatedAt();
            ZonedDateTime given = givenDate.toInstant().atZone(ZoneId.of("UTC"));

            int personAddedYear = Integer.parseInt(Year.from(given).toString());

            if (personAddedYear >= PERSON_ADDED_EARLIEST_YEAR && personAddedYear <= getCurrentYear()) {

                int indexOffset = personAddedYear - PERSON_ADDED_EARLIEST_YEAR;
                int oldValue = count.get(indexOffset);
                count.set(indexOffset, oldValue + 1);
            }
        });

        return count;
    }
    /**
     * Fetches the current year
     */
    private Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
