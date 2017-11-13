# dennaloh
###### \java\seedu\address\logic\commands\person\EmailCommand.java
``` java
/**
 * Emails a contact from the address book.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails the person identified "
            + "by the index number used in the last person listing. Needs Outlook or Apple Mail as default app.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Opened email to %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered person list to email
     */
    public EmailCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Desktop desktop;
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEmail = lastShownList.get(targetIndex.getZeroBased());
        String email = personToEmail.getEmail().getValue();

        try {
            if (Desktop.isDesktopSupported()
                    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                URI mailto = new URI("mailto:" + email);
                desktop.mail(mailto);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEmail));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\person\FbCommand.java
``` java
/**
 * Searches for your contact on Facebook
 */
public class FbCommand extends Command {

    public static final String COMMAND_WORD = "facebook";
    public static final String COMMAND_ALIAS = "fb";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for the person identified by the index "
            + "number used in the last person listing on Facebook.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Opened Facebook to search for %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered person list to search on Facebook for
     */
    public FbCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToSearch = lastShownList.get(targetIndex.getZeroBased());

        String fbUrl = model.getFbUrl(personToSearch);
        model.openUrl(fbUrl);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToSearch));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FbCommand // instanceof handles nulls
                && this.targetIndex.equals(((FbCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\person\FindTagCommand.java
``` java
/**
 * Finds and lists all persons in address book who has tags which contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_ALIAS = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who has tags which contains any of "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends neighbours ";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\person\GMapCommand.java
``` java
/**
 * Opens Google Maps in browser with address of person identified using it's last displayed index from the address book
 * as the destination.
 */
public class GMapCommand extends Command {

    public static final String COMMAND_WORD = "gmap";
    public static final String COMMAND_ALIAS = "gm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens Google Maps in default browser with the address "
            + "of the person identified by the index number used in the last person listing being the Destination.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Opened Google Maps to get to %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered person list to get directions to
     */
    public GMapCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToGetDirectionsTo = lastShownList.get(targetIndex.getZeroBased());

        String gmapUrl = model.getGMapUrl(personToGetDirectionsTo);
        model.openUrl(gmapUrl);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToGetDirectionsTo));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GMapCommand // instanceof handles nulls
                && this.targetIndex.equals(((GMapCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindTagCommand.COMMAND_WORD:
        case FindTagCommand.COMMAND_ALIAS:
            return new FindTagCommandParser().parse(arguments);

        case EmailCommand.COMMAND_WORD:
        case EmailCommand.COMMAND_ALIAS:
            return new EmailCommandParser().parse(arguments);

        case GMapCommand.COMMAND_WORD:
        case GMapCommand.COMMAND_ALIAS:
            return new GMapCommandParser().parse(arguments);

        case FbCommand.COMMAND_WORD:
        case FbCommand.COMMAND_ALIAS:
            return new FbCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\person\EmailCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EmailCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
        }
    }
}
```
###### \java\seedu\address\logic\parser\person\FbCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FbCommand object
 */
public class FbCommandParser implements Parser<FbCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FbCommand
     * and returns an FbCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FbCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new FbCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FbCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\person\FindTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\person\GMapCommandParser.java
``` java
/**
 * Parses input arguments and creates a new GMapCommand object
 */
public class GMapCommandParser implements Parser<GMapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GMapCommand
     * and returns an GMapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GMapCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new GMapCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GMapCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Returns URL for google maps using the person's address
     * @param key is target person
     * @return URL
     */
    public String getGMapUrl (ReadOnlyPerson key) {

        String address = key.getAddress().toString();
        String replacedAddress = address.replaceAll(" ", "+");
        StringBuilder sb = new StringBuilder();
        sb.append("http://maps.google.com/maps?saddr=");
        sb.append("&daddr=");
        sb.append(replacedAddress);
        String gMapUrl = sb.toString();

        return gMapUrl;
    }

    /**
     * Returns URL to search for person on facebook
     * @param key is target person
     * @return URL
     */
    public String getFbUrl (ReadOnlyPerson key) {
        String name = key.getName().toString();
        String replacedName = name.replaceAll(" ", "%20");
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.facebook.com/search/top/?q=");
        sb.append(replacedName);
        String fbUrl = sb.toString();

        return fbUrl;
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Gets URL for google maps. */
    String getGMapUrl(ReadOnlyPerson target);

    /** Gets URL to search on facebook. */
    String getFbUrl (ReadOnlyPerson target);

    /** Opens URL in default browser. */
    void openUrl (String url);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized String getGMapUrl(ReadOnlyPerson target) {
        String gMapUrl = addressBook.getGMapUrl(target);
        return gMapUrl;
    }

    @Override
    public synchronized String getFbUrl (ReadOnlyPerson target) {
        String fbUrl = addressBook.getFbUrl(target);
        return fbUrl;
    }

    /**
     * Opens the url in the default browser.
     * @param url is the url that will be opened.
     */
    @Override
    public void openUrl (String url) {
        Desktop desktop = Desktop.getDesktop();
        try {
            if (Desktop.isDesktopSupported()) {
                URI urlToOpen = new URI(url);
                desktop.browse(urlToOpen);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns the set of tags joined into a string
     * @return
     */
    public String joinTagsToString() {
        Set<Tag> tags = getTags();
        StringBuilder sb = new StringBuilder();
        for (Tag t : tags) {
            sb.append(t.tagName);
            sb.append(" ");
        }
        return sb.toString();
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the persons based on name
     *
     */
    public void sortPersons() {
        internalList.sort((e1, e2) -> e1.getName().toString().compareToIgnoreCase(e2.getName().toString()));
    }
```
###### \java\seedu\address\model\property\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.joinTagsToString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\ui\event\EventCalendar.java
``` java
/**
 * The panel on the right side of {@link EventListPanel}. Used to show a calendar.
 */
public class EventCalendar extends UiPart<Region> {
    private static final String FXML = "event/EventCalendar.fxml";
    private static final int MAX_NUMBER_DAYS = 42;

    @FXML
    private Label date1;
    @FXML
    private Label date2;
    @FXML
    private Label date3;
    @FXML
    private Label date4;
    @FXML
    private Label date5;
    @FXML
    private Label date6;
    @FXML
    private Label date7;
    @FXML
    private Label date8;
    @FXML
    private Label date9;
    @FXML
    private Label date10;
    @FXML
    private Label date11;
    @FXML
    private Label date12;
    @FXML
    private Label date13;
    @FXML
    private Label date14;
    @FXML
    private Label date15;
    @FXML
    private Label date16;
    @FXML
    private Label date17;
    @FXML
    private Label date18;
    @FXML
    private Label date19;
    @FXML
    private Label date20;
    @FXML
    private Label date21;
    @FXML
    private Label date22;
    @FXML
    private Label date23;
    @FXML
    private Label date24;
    @FXML
    private Label date25;
    @FXML
    private Label date26;
    @FXML
    private Label date27;
    @FXML
    private Label date28;
    @FXML
    private Label date29;
    @FXML
    private Label date30;
    @FXML
    private Label date31;
    @FXML
    private Label date32;
    @FXML
    private Label date33;
    @FXML
    private Label date34;
    @FXML
    private Label date35;
    @FXML
    private Label date36;
    @FXML
    private Label date37;
    @FXML
    private Label date38;
    @FXML
    private Label date39;
    @FXML
    private Label date40;
    @FXML
    private Label date41;
    @FXML
    private Label date42;

    @FXML
    private Label monthName;

    private Label[] dateArray;
    private MonthDateBuilder monthDateBuilder;

    public EventCalendar() {
        super(FXML);
        monthDateBuilder = new MonthDateBuilder();
        inits();
        setDates(monthDateBuilder.getMonthDateArray());
        monthName.setText(monthDateBuilder.getNameOfMonth());
    }

    /**
     * Puts the dates from monthDateArray into the dateArray
     */
    public void setDates(String[] monthDateArray) {
        for (int i = 0; i < MAX_NUMBER_DAYS; i++) {
            dateArray[i].setText(monthDateArray[i]);
        }
    }

    /**
     * Puts all text views into array to facilitate looping through
     */
    public void inits() {
        dateArray = new Label[MAX_NUMBER_DAYS];
        dateArray[0] = date1;
        dateArray[1] = date2;
        dateArray[2] = date3;
        dateArray[3] = date4;
        dateArray[4] = date5;
        dateArray[5] = date6;
        dateArray[6] = date7;
        dateArray[7] = date8;
        dateArray[8] = date9;
        dateArray[9] = date10;
        dateArray[10] = date11;
        dateArray[11] = date12;
        dateArray[12] = date13;
        dateArray[13] = date14;
        dateArray[14] = date15;
        dateArray[15] = date16;
        dateArray[16] = date17;
        dateArray[17] = date18;
        dateArray[18] = date19;
        dateArray[19] = date20;
        dateArray[20] = date21;
        dateArray[21] = date22;
        dateArray[22] = date23;
        dateArray[23] = date24;
        dateArray[24] = date25;
        dateArray[25] = date26;
        dateArray[26] = date27;
        dateArray[27] = date28;
        dateArray[28] = date29;
        dateArray[29] = date30;
        dateArray[30] = date31;
        dateArray[31] = date32;
        dateArray[32] = date33;
        dateArray[33] = date34;
        dateArray[34] = date35;
        dateArray[35] = date36;
        dateArray[36] = date37;
        dateArray[37] = date38;
        dateArray[38] = date39;
        dateArray[39] = date40;
        dateArray[40] = date41;
        dateArray[41] = date42;
    }
}
```
###### \java\seedu\address\ui\event\MonthDateBuilder.java
``` java
/**
 * Creates the dates in the month and name of month
 */
public class MonthDateBuilder {
    private static final int MAX_NUMBER_DAYS = 42; //Maximum number of cells every month has
    private Integer[] monthYearArray;
    private String[] monthDateArray;
    private Calendar calendar;
    private Integer firstDayOfMonth; //First day in the month
    private Integer maxDayOfMonth;   //Number of days in the month
    private String nameOfMonth;

    /**
     * The constructor without parameter is used to get the current month;
     * the other one is used as to get an arbitrary month and is used for testing.
     */
    public MonthDateBuilder() {
        calendar = Calendar.getInstance();

        monthDateArray = new String[MAX_NUMBER_DAYS];
        monthYearArray = new Integer[2];
        setMonthYearArray(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)); //Set current month and year
        setNameOfMonth();
        calendar.set(Calendar.MONTH, monthYearArray[0]);   //Sets the given calendar MONTH to monthYearArray[0]
        calendar.set(Calendar.YEAR, monthYearArray[1]);    //Sets the given calendar YEAR to monthYearArray[1]

        setMonthAnchors();
        buildMonthArrays();
    }

    public MonthDateBuilder(Integer month, Integer year) {
        calendar = Calendar.getInstance();

        monthDateArray = new String[MAX_NUMBER_DAYS];
        monthYearArray = new Integer[2];
        setMonthYearArray(month, year); //Sets arbitrary month and year
        setNameOfMonth();
        calendar.set(Calendar.MONTH, monthYearArray[0]);   //Sets the given calendar MONTH to monthYearArray[0]
        calendar.set(Calendar.YEAR, monthYearArray[1]);    //Sets the given calendar YEAR to monthYearArray[1]

        setMonthAnchors();
        buildMonthArrays();
    }

    /**
     * Stores desired month and year in monthYearArray
     * @param month the value used to set the <code>MONTH</code> calendar field.
     * Month value is 0-based. e.g., 0 for January.
     * @param year the value used to set the <code>YEAR</code> calendar field.
     */
    private void setMonthYearArray(Integer month, Integer year) {
        monthYearArray[0] = month;
        monthYearArray[1] = year;
    }

    /**
     * Sets the month anchors (first day and number of days in the month)
     * by setting the calender to 1st date of the month and finding out the day of the week it falls on
     */
    private void setMonthAnchors() {
        maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Integer dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
        case Calendar.SUNDAY:
            firstDayOfMonth = 0;
            break;
        case Calendar.MONDAY:
            firstDayOfMonth = 1;
            break;
        case Calendar.TUESDAY:
            firstDayOfMonth = 2;
            break;
        case Calendar.WEDNESDAY:
            firstDayOfMonth = 3;
            break;
        case Calendar.THURSDAY:
            firstDayOfMonth = 4;
            break;
        case Calendar.FRIDAY:
            firstDayOfMonth = 5;
            break;
        case Calendar.SATURDAY:
            firstDayOfMonth = 6;
            break;
        default:
            firstDayOfMonth = 0;
        }
    }

    public Integer getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    /**
     * Builds the monthly view with the required spaces and numbers
     */
    public void buildMonthArrays() {
        Integer i = 0;
        Integer j = firstDayOfMonth;
        Integer a = firstDayOfMonth + maxDayOfMonth;
        while (i < firstDayOfMonth) {
            monthDateArray[i] = " ";
            i++;
        }
        while (j < maxDayOfMonth + firstDayOfMonth) {
            monthDateArray[j] = String.valueOf(j - firstDayOfMonth + 1);
            j++;
        }
        while (a < MAX_NUMBER_DAYS) {
            monthDateArray[a] = " ";
            a++;
        }
    }

    public String[] getMonthDateArray() {
        return monthDateArray;
    }

    public void setNameOfMonth() {
        switch(monthYearArray[0]) {
        case Calendar.JANUARY: nameOfMonth = "January";
            break;
        case Calendar.FEBRUARY: nameOfMonth = "February";
            break;
        case Calendar.MARCH: nameOfMonth = "March";
            break;
        case Calendar.APRIL: nameOfMonth = "April";
            break;
        case Calendar.MAY: nameOfMonth = "May";
            break;
        case Calendar.JUNE: nameOfMonth = "June";
            break;
        case Calendar.JULY: nameOfMonth = "July";
            break;
        case Calendar.AUGUST: nameOfMonth = "August";
            break;
        case Calendar.SEPTEMBER: nameOfMonth = "September";
            break;
        case Calendar.OCTOBER: nameOfMonth = "October";
            break;
        case Calendar.NOVEMBER: nameOfMonth = "November";
            break;
        case Calendar.DECEMBER: nameOfMonth = "December";
            break;
        default:
            nameOfMonth = null;
        }
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private void handleEventCalendar() {
        dataDetailsPanelPlaceholder.getChildren().clear();
        dataDetailsPanelPlaceholder.getChildren().add(new EventCalendar().getRoot());
    }
```
###### \resources\css\Extensions.css
``` css
.calender-title-property-value {
    -fx-font-family: "Bradley Hand";
    -fx-font-size: 40px;
    -fx-text-fill: lightskyblue;
    -fx-text-alignment: left;
}

.calender-property-value {
    -fx-font-family: "Arial";
    -fx-font-size: 30px;
    -fx-text-fill: white;
    -fx-text-alignment: left;
}

.calender-property1-value {
    -fx-font-family: "Arial Black";
    -fx-font-size: 50px;
    -fx-text-fill: lightpink;
    -fx-text-alignment: right;
}

.calender-property2-value {
    -fx-font-family: "Arial Black";
    -fx-font-size: 50px;
    -fx-text-fill: lightgoldenrodyellow;
    -fx-text-alignment: right;
}

.calender-property3-value {
    -fx-font-family: "Arial Black";
    -fx-font-size: 50px;
    -fx-text-fill: lightsalmon;
    -fx-text-alignment: right;
}

.calender-property4-value {
    -fx-font-family: "Arial Black";
    -fx-font-size: 50px;
    -fx-text-fill: lightgreen;
    -fx-text-alignment: right;
}
```
###### \resources\view\event\EventCalendar.fxml
``` fxml


<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<GridPane alignment="CENTER" prefHeight="600.0" prefWidth="580.0" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <Label fx:id="monthName" text="January" styleClass="calender-title-property-value" GridPane.rowIndex="0" GridPane.columnSpan="7"/>
    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="0" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="daySun" styleClass="calender-property1-value" text="S" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="1" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayM" styleClass="calender-property2-value" text="M" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="2" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayT" styleClass="calender-property3-value" text="T" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="3" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayW" styleClass="calender-property4-value" text="W" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="4" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayTh" styleClass="calender-property1-value" text="T" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="5" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayF" styleClass="calender-property2-value" text="F" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="6" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="daySat" styleClass="calender-property3-value" text="S" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Label fx:id="date1" styleClass="calender-property-value" text="1" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Label fx:id="date2" styleClass="calender-property-value" text="2" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Label fx:id="date3" styleClass="calender-property-value" text="3" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Label fx:id="date4" styleClass="calender-property-value" text="4" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Label fx:id="date5" styleClass="calender-property-value" text="5" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Label fx:id="date6" styleClass="calender-property-value" text="6" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Label fx:id="date7" styleClass="calender-property-value" text="7" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Label fx:id="date8" styleClass="calender-property-value" text="8" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Label fx:id="date9" styleClass="calender-property-value" text="9" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Label fx:id="date10" styleClass="calender-property-value" text="10" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Label fx:id="date11" styleClass="calender-property-value" text="11" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Label fx:id="date12" styleClass="calender-property-value" text="12" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Label fx:id="date13" styleClass="calender-property-value" text="13" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Label fx:id="date14" styleClass="calender-property-value" text="14" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Label fx:id="date15" styleClass="calender-property-value" text="15" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Label fx:id="date16" styleClass="calender-property-value" text="16" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Label fx:id="date17" styleClass="calender-property-value" text="17" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Label fx:id="date18" styleClass="calender-property-value" text="18" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Label fx:id="date19" styleClass="calender-property-value" text="19" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Label fx:id="date20" styleClass="calender-property-value" text="20" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Label fx:id="date21" styleClass="calender-property-value" text="21" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Label fx:id="date22" styleClass="calender-property-value" text="22" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Label fx:id="date23" styleClass="calender-property-value" text="23" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Label fx:id="date24" styleClass="calender-property-value" text="24" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Label fx:id="date25" styleClass="calender-property-value" text="25" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Label fx:id="date26" styleClass="calender-property-value" text="26" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Label fx:id="date27" styleClass="calender-property-value" text="27" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Label fx:id="date28" styleClass="calender-property-value" text="28" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Label fx:id="date29" styleClass="calender-property-value" text="29" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Label fx:id="date30" styleClass="calender-property-value" text="30" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Label fx:id="date31" styleClass="calender-property-value" text="31" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Label fx:id="date32" styleClass="calender-property-value" text="32" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Label fx:id="date33" styleClass="calender-property-value" text="33" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Label fx:id="date34" styleClass="calender-property-value" text="34" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Label fx:id="date35" styleClass="calender-property-value" text="35" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Label fx:id="date36" styleClass="calender-property-value" text="36" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Label fx:id="date37" styleClass="calender-property-value" text="37" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Label fx:id="date38" styleClass="calender-property-value" text="38" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Label fx:id="date39" styleClass="calender-property-value" text="39" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Label fx:id="date40" styleClass="calender-property-value" text="40" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Label fx:id="date41" styleClass="calender-property-value" text="41" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Label fx:id="date42" styleClass="calender-property-value" text="42" wrapText="true">
        </Label>
    </VBox>

</GridPane>
```
