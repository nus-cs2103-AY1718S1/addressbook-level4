# 500poundbear
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Instantiates and adds the browser panel to the UI
     */
    private void switchToBrowserPanel() {
        browserPanel = new BrowserPanel();
        browserOrStatisticsPlaceholder.getChildren().clear();
        browserOrStatisticsPlaceholder.getChildren().add(browserPanel.getRoot());
        statisticsPanelOpen = false;
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleToggleBrowserPanelEvent(ToggleBrowserPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToBrowserPanel();
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleToggleStatisticsPanelEvent(ToggleStatisticsPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToStatisticsPanel();
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleRefreshStatisticsPanelIfOpenEvent(RefreshStatisticsPanelIfOpenEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (statisticsPanelOpen) {
            switchToStatisticsPanel();
        }
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
        fbChart.setData(formatTwitterData());
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
###### /java/seedu/address/model/Statistics.java
``` java
    public Statistics (ObservableList<ReadOnlyPerson> list, int currentMonth, int currentYear) {

        this.currentYear = currentYear;
        this.currentMonth = currentMonth;

        this.personList = list;

        tabulateTotalNumberOfPeople();
        tabulateSocialMediaUsage();
    }

    public ArrayList<Integer> getNewPersonsAddByMonth(int displayYears) {

        ArrayList<Integer> countByMonth = new ArrayList<>(Collections.nCopies(displayYears * 12 + 1, 0));

        personList.forEach((p) -> {
            Date givenDate = p.getCreatedAt();
            ZonedDateTime given = givenDate.toInstant().atZone(ZoneId.of("UTC"));

            int personAddedYear = Integer.parseInt(Year.from(given).toString());
            int personAddedMonth = Month.from(given).getValue();

            int indOffset = calculateCountByMonthOffset(personAddedMonth, personAddedYear);
            if (indOffset >= 0 && indOffset <= displayYears * 12) {
                countByMonth.set(indOffset, countByMonth.get(indOffset) + 1);
            }
        });

        return countByMonth;
    }

    /**
     * Count the offset when adding to the array list of sum by months
     */
    public int calculateCountByMonthOffset(int personAddedMonth, int personAddedYear) {
        return (this.currentYear - personAddedYear) * 12
                + (this.currentMonth - personAddedMonth);
    }

    /**
     * Tabulate the total number of people in the list
     */
    public void tabulateTotalNumberOfPeople() {
        this.totalNumberOfPeople = personList.size();
    }

    /**
     * Tabulates number of users of each social media platform
     */
    public void tabulateSocialMediaUsage() {
        for (ReadOnlyPerson aList : personList) {
            SocialMedia current = aList.getSocialMedia();
            if (current.facebook.isEmpty()) {
                this.hasNoFacebook++;
            }
            if (current.twitter.isEmpty()) {
                this.hasNoTwitter++;
            }
            if (current.instagram.isEmpty()) {
                this.hasNoInstagram++;
            }
        }
    }

    /**
     * Fetches n people with the highest access count
     */
    public List<ReadOnlyPerson> getAllTimeMostAccesses() {
        ArrayList<ReadOnlyPerson> sortedByMostAccesses = new ArrayList<>(personList);
        sortedByMostAccesses.sort(sortByGetAccessCount());

        int startingIndex = this.totalNumberOfPeople - NUMBER_OF_PERSONS_IN_TOP_LIST;
        int endingIndex = this.totalNumberOfPeople - 1;

        return sortedByMostAccesses.subList(startingIndex, endingIndex);
    }

    /**
     * Fetches n people with the lowest access count
     */
    public List<ReadOnlyPerson> getAllTimeLeastAccesses() {
        ArrayList<ReadOnlyPerson> sortedByMostAccesses = new ArrayList<>(personList);
        sortedByMostAccesses.sort(sortByGetAccessCount());

        int startingIndex = 0;
        int endingIndex = NUMBER_OF_PERSONS_IN_BOTTOM_LIST - 1;

        return sortedByMostAccesses.subList(startingIndex, endingIndex);
    }

    /**
     * Fetches number of persons with no facebook information added
     */
    public Integer getHasNoFacebook() {
        return this.hasNoFacebook;
    }

    /**
     * Fetches number of persons with no twitter information added
     */
    public Integer getHasNoTwitter() {
        return this.hasNoTwitter;
    }

    /**
     * Fetches number of persons with no instagram information added
     */
    public Integer getHasNoInstagram() {
        return this.hasNoInstagram;
    }

    /**
     * Fetches total number of persons
     */
    public Integer getTotalNumberOfPeople() {
        return this.totalNumberOfPeople;
    }

    /**
     * Sort by ReadOnlyPerson.getAccessCount()
     * @return
     */
    private static Comparator<ReadOnlyPerson> sortByGetAccessCount() {
        return new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson s1, ReadOnlyPerson s2) {
                return s1.getAccessCount().numAccess() - s2.getAccessCount().numAccess();
            }
        };
    }
}
```
