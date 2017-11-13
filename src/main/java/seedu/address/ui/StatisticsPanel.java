package seedu.address.ui;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

import seedu.address.model.Statistics;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Statistics Panel of the App.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private static final Integer PERSON_ADDED_DISPLAY_YEARS = 2;

    private static final String FACEBOOK_BREAKDOWN_CHART_TITLE = "Facebook Usage";
    private static final String INSTAGRAM_BREAKDOWN_CHART_TITLE = "Instagram Usage";
    private static final String TWITTER_BREAKDOWN_CHART_TITLE = "Twitter Usage";

    private static final String CHART_USING_LABEL = "Using (%d)";
    private static final String CHART_NOT_USING_LABEL = "Not Using (%d)";

    private static final Double PERSON_ADDED_CHART_BAR_GAP = 0.1;
    private static final String PERSON_ADDED_CHART_TITLE = "Persons Added in Recent Months";

    private Statistics statistics;

    private Integer totalNumberOfPeople = 0;
    private Integer hasNoFacebook = 0;
    private Integer hasNoTwitter = 0;
    private Integer hasNoInstagram = 0;

    private Integer currentYear;
    private Integer currentMonth;

    @FXML
    private BarChart personAddedChart;
    @FXML
    private PieChart fbChart;
    @FXML
    private PieChart twChart;
    @FXML
    private PieChart igChart;

    //@@author 500poundbear
    public StatisticsPanel(ObservableList<ReadOnlyPerson> list) {
        super(FXML);

        currentYear = this.getCurrentYear();
        currentMonth = this.getCurrentMonth();

        statistics = new Statistics(list, this.currentMonth, this.currentYear);

        totalNumberOfPeople = statistics.getTotalNumberOfPeople();

        hasNoFacebook = statistics.getHasNoFacebook();
        hasNoTwitter = statistics.getHasNoTwitter();
        hasNoInstagram = statistics.getHasNoInstagram();

        initialiseStatisticsPanel(list);
    }

    /**
     * Sets up the fxml objects with data
     */
    private void initialiseStatisticsPanel(ObservableList<ReadOnlyPerson> list) {

        personAddedChart.setTitle(PERSON_ADDED_CHART_TITLE);
        personAddedChart.setData(getPersonAddedChartData(list));
        personAddedChart.setBarGap(PERSON_ADDED_CHART_BAR_GAP);

        fbChart.setTitle(FACEBOOK_BREAKDOWN_CHART_TITLE);
        fbChart.setData(formatFacebookData());
        twChart.setTitle(TWITTER_BREAKDOWN_CHART_TITLE);
        twChart.setData(formatTwitterData());
        igChart.setTitle(INSTAGRAM_BREAKDOWN_CHART_TITLE);
        igChart.setData(formatInstagramData());
    }

    private ObservableList<XYChart.Series<String, Integer>> getPersonAddedChartData(
            ObservableList<ReadOnlyPerson> list) {

        ObservableList<XYChart.Series<String, Integer>> answer = FXCollections.observableArrayList();
        XYChart.Series<String, Integer> aSeries = new XYChart.Series<String, Integer>();
        aSeries.setName("Persons added");

        int endYear = this.currentYear;
        int startYear = endYear - PERSON_ADDED_DISPLAY_YEARS;

        int startMonth;
        int endMonth;
        int monthCount = PERSON_ADDED_DISPLAY_YEARS * 12;

        ArrayList<Integer> monthPersonsAdded = statistics.getNewPersonsAddByMonth(PERSON_ADDED_DISPLAY_YEARS);

        for (int i = startYear; i <= endYear; i++) {

            startMonth = 1;
            endMonth = 12;

            if (i == startYear) {
                startMonth = this.currentMonth;
            }

            if (i == endYear) {
                endMonth = this.currentMonth;
            }

            for (int m = startMonth; m <= endMonth; m++) {
                String labelName = Month.of(m).name().substring(0, 3) + " " + Integer.toString(i);
                aSeries.getData().add(new XYChart.Data(labelName, monthPersonsAdded.get(monthCount)));

                monthCount--;
            }
        }
        answer.addAll(aSeries);
        return answer;
    }

    /**
     * Formats the number of users with Facebook recorded
     */
    private ObservableList<PieChart.Data> formatFacebookData() {

        ArrayList<PieChart.Data> data = new ArrayList<>();

        int hasFacebook = this.totalNumberOfPeople - this.hasNoFacebook;

        String onFacebookLabel = String.format(CHART_USING_LABEL, hasFacebook);
        String notOnFacebookLabel = String.format(CHART_NOT_USING_LABEL, this.hasNoFacebook);
        data.add(new PieChart.Data(onFacebookLabel, hasFacebook));
        data.add(new PieChart.Data(notOnFacebookLabel, this.hasNoFacebook));

        return FXCollections.observableArrayList(data);
    }

    /**
     * Formats the number of users with Twitter recorded
     */
    private ObservableList<PieChart.Data> formatTwitterData() {

        ArrayList<PieChart.Data> data = new ArrayList<>();

        int hasTwitter = this.totalNumberOfPeople - this.hasNoTwitter;

        String onTwitterLabel = String.format(CHART_USING_LABEL, hasTwitter);
        String notOnTwitterLabel = String.format(CHART_NOT_USING_LABEL, this.hasNoTwitter);
        data.add(new PieChart.Data(onTwitterLabel, hasTwitter));
        data.add(new PieChart.Data(notOnTwitterLabel, this.hasNoTwitter));

        return FXCollections.observableArrayList(data);
    }

    /**
     * Formats the number of users with Instagram recorded
     */
    private ObservableList<PieChart.Data> formatInstagramData() {

        ArrayList<PieChart.Data> data = new ArrayList<>();

        int hasInstagram = this.totalNumberOfPeople - this.hasNoInstagram;

        String onInstagramLabel = String.format(CHART_USING_LABEL, hasInstagram);
        String notOnInstagramLabel = String.format(CHART_NOT_USING_LABEL, this.hasNoInstagram);
        data.add(new PieChart.Data(onInstagramLabel, hasInstagram));
        data.add(new PieChart.Data(notOnInstagramLabel, this.hasNoInstagram));

        return FXCollections.observableArrayList(data);
    }

    /**
     * Fetches the current year
     */
    private Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * Fetches the current month
     */
    private Integer getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

}
