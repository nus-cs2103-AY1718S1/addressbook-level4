# Valerieyue
###### \java\seedu\address\logic\commands\BirthdayStatisticsCommand.java
``` java
/**
 * Shows the birthday statistics.
 */
public class BirthdayStatisticsCommand extends Command {

    public static final String COMMAND_WORD = "birthdaystatistics";
    public static final String COMMAND_ALIAS = "bstats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the birthday statistics.\n"
            + "Example: " + COMMAND_WORD;
    public static final String BIRTHDAY_STATISTICS_PANEL_REQUEST_EVENT = "BirthdayStatisticsPanel";

    public static final String MESSAGE_SUCCESS = "Birthday statistics displayed";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance()
                .post(new ChangeInformationPanelRequestEvent(BIRTHDAY_STATISTICS_PANEL_REQUEST_EVENT));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

```
###### \java\seedu\address\logic\commands\TagStatisticsCommand.java
``` java
/**
 * Shows the tag statistics.
 */
public class TagStatisticsCommand extends Command {

    public static final String COMMAND_WORD = "tagstatistics";
    public static final String COMMAND_ALIAS = "tstats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the tag statistics.\n"
            + "Example: " + COMMAND_WORD;
    public static final String TAG_STATISTICS_PANEL_REQUEST_EVENT = "TagStatisticsPanel";

    public static final String MESSAGE_SUCCESS = "Tag statistics displayed";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance()
                .post(new ChangeInformationPanelRequestEvent(TAG_STATISTICS_PANEL_REQUEST_EVENT));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}


```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    private static Optional<String> areValuePresent(Optional<String> checkPresent) {
        return Optional.of(checkPresent.orElse(""));
    }
```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday should be in the format DDMMYY, separated by '/' or '-' or '.' or ' '. \n"
                   + "The month can be entered as a number or name of the month."
                   + "The date entered must be valid.\n"
                   + "eg. b/09 8 1987, b/23-Mar-98";
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.| )(?:0?[13578]|1[02]|"
           + "(?:jan|mar|may|jul|aug|oct|dec)))\\1|(?:(?:29|30)(\\/|-|\\.| )(?:0?[1,3-9]|1[0-2]|"
           + "(?:jan|mar|apr|may|jun|jul|aug|sep|oct|nov|dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.| )"
           + "(?:0?2|(?:feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
           + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.| )(?:(?:0?[1-9]|"
           + "(?:jan|feb|mar|apr|may|jun|jul|aug|sep))|(?:1[0-2]|(?:oct|nov|dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim().toLowerCase();
        String temp = trimmedBirthday;
        if (!trimmedBirthday.isEmpty() & !isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        } else if (!"".equals(trimmedBirthday)) {
            String[] array = trimmedBirthday.replace("/", " ").replace("-", " ")
                    .replace(".", " ").split(" ");
            int monthInt = 0;
            String day;
            String month;
            String year;

            // day
            if (array[0].length() == 2) {
                if (array[0].startsWith("0")) {
                    day = array[0].substring(1); //0X
                } else {
                    day = array[0]; // XX
                }
            } else {
                day = array[0]; // X
            }

            // year
            if (array[2].length() == 2) {
                if (Integer.valueOf(array[2]) < 18) {
                    year = "20" + array[2];
                } else {
                    year = "19" + array[2];
                }
            } else {
                year = array[2];
            }

            // month
            if (array[1].length() < 3) {
                monthInt = Integer.valueOf(array[1]);
            }

            switch (monthInt) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
            default:
                month = "";
                break;
            }
            if (array[1].length() == 3) {
                month = array[1].substring(0, 1).toUpperCase() + array[1].substring(1);
            }
            temp = day + " " + month + " " + year;
        }
        this.value = temp;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicate.java
``` java
                keywords.stream().filter(input -> input.substring(0, 2).equals("m/"))
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getBirthday().value,
                                keyword.substring(2)))
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts person name in alphabetical order
     */
    public void sort() {
        Comparator<Person> comparator = (p1, p2) -> (comparePeople(p1, p2));
        Collections.sort(internalList, comparator);
    }
```
###### \java\seedu\address\model\tag\Tag.java
``` java
    public String getTagName() {
        return this.tagName;
    }
```
###### \java\seedu\address\ui\BirthdayStatisticsPanel.java
``` java
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
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the birthday statistics panel.
     */
    @FXML
    public void handleBirthdayStatistics() {
        changeInformationPanel(new ChangeInformationPanelRequestEvent(BIRTHDAY_STATISTICS_PANEL));
    }

    /**
     * Opens the tag statistics panel.
     */
    @FXML
    public void handleTagStatistics() {
        changeInformationPanel(new ChangeInformationPanelRequestEvent(TAG_STATISTICS_PANEL));
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java

    /**
     * Opens the help panel.
     */
    @FXML
    public void handleHelp() {
        changeInformationPanel(new ChangeInformationPanelRequestEvent(HELP_PANEL));
    }
```
###### \java\seedu\address\ui\TagStatisticsPanel.java
``` java
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
                tagCounter[tagNames.indexOf(tag)]++;
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
```
###### \resources\view\BrightTheme.css
``` css
.chart-title {
    -fx-text-fill: black;
}

.axis-label {
    -fx-text-fill: black;
}

.axis {
    -fx-tick-label-fill: black;
}

.chart-plot-background {
    -fx-background-color: #D6D6D6
}

.default-color0.chart-bar {
    -fx-bar-fill: #7AD9C5
}

.chart-vertical-grid-lines {
    -fx-stroke: white
}
.chart-horizontal-grid-lines {
    -fx-stroke: white
}
```
###### \resources\view\DarkTheme.css
``` css
.chart-title {
    -fx-text-fill: white;
}

.axis-label {
    -fx-text-fill: white;
}

.axis {
    -fx-tick-label-fill: white;
}

.chart-plot-background {
    -fx-background-color: grey
}

.default-color0.chart-bar {
    -fx-bar-fill: #D099DF
}
```
