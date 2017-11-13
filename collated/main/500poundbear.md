# 500poundbear
###### /java/seedu/address/model/person/Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "The remark of a person can take any value.";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || ((other instanceof Remark)
                && this.value.equals(((Remark) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/Statistics.java
``` java
/**
 * A model for calculating the values for Statistics Panel
 */
public class Statistics {

    private static final String ZONE_ID = "UTC";

    private ObservableList<ReadOnlyPerson> personList;

    private Integer totalNumberOfPeople = 0;
    private Integer hasNoFacebook = 0;
    private Integer hasNoTwitter = 0;
    private Integer hasNoInstagram = 0;

    private Integer currentYear;
    private Integer currentMonth;

    public Statistics (ObservableList<ReadOnlyPerson> list, int currentMonth, int currentYear) {
        setStatisticAttributes(list, currentMonth, currentYear);
        tabulateSocialMediaUsage();
    }

    /**
     * Returns Array for each month in past displayYears with a count of new persons added in that month
     * Current month is also included
     *
     * @param displayYears The number of years to be displayed
     * @return ArrayList of integers
     */
    public ArrayList<Integer> getNewPersonsAddByMonth(int displayYears) {
        int totalMonthsDisplayed = yearsToMonth(displayYears) + 1;

        ArrayList<Integer> countByMonth = new ArrayList<>(
                Collections.nCopies(totalMonthsDisplayed, 0));

        personList.forEach(p -> {
            Date givenDate = p.getCreatedAt();
            ZonedDateTime given = givenDate.toInstant().atZone(ZoneId.of(ZONE_ID));

            int personAddedYear = Integer.parseInt(Year.from(given).toString());
            int personAddedMonth = Month.from(given).getValue();

            int indOffset = calculateCountByMonthOffset(personAddedMonth, personAddedYear);

            if (isOffsetWithinDisplayRange(indOffset, totalMonthsDisplayed)) {
                countByMonth.set(indOffset, countByMonth.get(indOffset) + 1);
            }
        });

        return countByMonth;
    }

    /**
     * Fetches number of persons with no facebook information added
     *
     * @return Number of Persons with no facebook
     */
    public Integer getHasNoFacebook() {
        return this.hasNoFacebook;
    }

    /**
     * Fetches number of persons with no twitter information added
     *
     * @return Number of Persons with no twitter
     */
    public Integer getHasNoTwitter() {
        return this.hasNoTwitter;
    }

    /**
     * Fetches number of persons with no instagram information added
     *
     * @return Number of Persons with no twitter
     */
    public Integer getHasNoInstagram() {
        return this.hasNoInstagram;
    }

    /**
     * Fetches total number of persons
     *
     * @return Number of Persons added
     */
    public Integer getTotalNumberOfPeople() {
        return this.totalNumberOfPeople;
    }

    /**
     * Set the initialisation parameters into the Statistics instance attributes
     */
    private void setStatisticAttributes(ObservableList<ReadOnlyPerson> list, int currentMonth, int currentYear) {
        this.personList = list;
        this.currentMonth = currentMonth;
        this.currentYear = currentYear;

        this.totalNumberOfPeople = personList.size();
    }

    /**
     * Count the offset when adding to the array list of sum by months
     */
    private int calculateCountByMonthOffset(int personAddedMonth, int personAddedYear) {
        return yearsToMonth(this.currentYear - personAddedYear) + (this.currentMonth - personAddedMonth);
    }

    /**
     * Tabulates number of users of each social media platform
     */
    private void tabulateSocialMediaUsage() {
        for (ReadOnlyPerson person : personList) {
            SocialMedia personSocialMedia = person.getSocialMedia();
            if (personSocialMedia.facebook.isEmpty()) {
                this.hasNoFacebook++;
            }
            if (personSocialMedia.twitter.isEmpty()) {
                this.hasNoTwitter++;
            }
            if (personSocialMedia.instagram.isEmpty()) {
                this.hasNoInstagram++;
            }
        }
    }

    /**
     * Sort by ReadOnlyPerson.getAccessCount()
     *
     * @return new comparator
     */
    private static Comparator<ReadOnlyPerson> sortByGetAccessCount() {
        return new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson s1, ReadOnlyPerson s2) {
                return s1.getAccessCount().numAccess() - s2.getAccessCount().numAccess();
            }
        };
    }

    /**
     * Converts the number of years into number of months
     *
     * @param years
     * @return Number of months
     */
    private int yearsToMonth(int years) {
        return years * 12;
    }


    /**
     * Returns whether month offset provided is within range of [0, displayYear * 12]
     *
     * @param indOffset Value to be checked
     * @param totalMonthsDisplayed To determine maximum bound of offset accepted
     * @return Boolean whether offset is accepted or not
     */
    private boolean isOffsetWithinDisplayRange(int indOffset, int totalMonthsDisplayed) {
        return (indOffset >= 0) && (indOffset < totalMonthsDisplayed);
    }
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Instantiates and adds the statistics panel to the UI
     */
    private void switchToStatisticsPanel() {
        logger.info("Switched to statistics panel");

        statisticsPanel = new StatisticsPanel(logic.getAllPersonList());
        browserOrStatisticsPlaceholder.getChildren().clear();
        browserOrStatisticsPlaceholder.getChildren().add(statisticsPanel.getRoot());
        statisticsPanelOpen = true;
    }


    /**
     * Instantiates and adds the browser panel to the UI
     */
    private void switchToBrowserPanel() {
        logger.info("Switched to browser panel");

        browserPanel = new BrowserPanel();
        browserOrStatisticsPlaceholder.getChildren().clear();
        browserOrStatisticsPlaceholder.getChildren().add(browserPanel.getRoot());
        statisticsPanelOpen = false;
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleToggleBrowserPanelEvent(ToggleBrowserPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToBrowserPanel();
    }

    @Subscribe
    private void handleToggleStatisticsPanelEvent(ToggleStatisticsPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToStatisticsPanel();
    }

    @Subscribe
    private void handleRefreshStatisticsPanelIfOpenEvent(RefreshStatisticsPanelIfOpenEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (statisticsPanelOpen) {
            switchToStatisticsPanel();
        }
    }
}
```
###### /java/seedu/address/ui/PeopleCount.java
``` java
/**
 * A ui for the people count that is displayed at the header of the application.
 */
public class PeopleCount extends UiPart<Region> {

    private static final String FXML = "PeopleCount.fxml";

    @FXML
    private StatusBar totalPersons;

    public PeopleCount(int totalPersons) {
        super(FXML);
        setTotalPersons(totalPersons);
        registerAsAnEventHandler(this);
    }

    private void setTotalPersons(int totalPersons) {
        this.totalPersons.setText(Integer.toString(totalPersons));
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        setTotalPersons(abce.data.getPersonList().size());
    }
}
```
###### /java/seedu/address/ui/StatisticsPanel.java
``` java
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
```
