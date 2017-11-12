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
###### /java/seedu/address/ui/event/EventCalendar.java
``` java
public class EventCalendar extends UiPart<Region> {
    private static final String FXML = "event/EventCalendar.fxml";

    @FXML
    private Text date1, date2, date3, date4, date5, date6, date7, date8, date9, date10, date11, date12, date13, date14,
            date15, date16, date17, date18, date19, date20, date21, date22, date23, date24, date25, date26, date27, date28,
            date29, date30, date31, date32, date33, date34, date35, date36, date37, date38, date39, date40, date41, date42;
    @FXML
    private Label monthName;


    private Text[] dateArray;

    private MonthDateBuilder monthDateBuilder;


    public EventCalendar() {
        super(FXML);
        monthDateBuilder = new MonthDateBuilder();
        inits(); // to put all text views into array to facilitate looping through
        setDates(monthDateBuilder.getMonthDateArray());
        monthName.setText(monthDateBuilder.getNameOfMonth());
    }

    public void setDates(String[] _monthDateArray) {
        dateArray[0].setText(_monthDateArray[0]);
        dateArray[1].setText(_monthDateArray[1]);
        dateArray[2].setText(_monthDateArray[2]);
        dateArray[3].setText(_monthDateArray[3]);
        dateArray[4].setText(_monthDateArray[4]);
        dateArray[5].setText(_monthDateArray[5]);
        dateArray[6].setText(_monthDateArray[6]);
        dateArray[7].setText(_monthDateArray[7]);
        dateArray[8].setText(_monthDateArray[8]);
        dateArray[9].setText(_monthDateArray[9]);
        dateArray[10].setText(_monthDateArray[10]);
        dateArray[11].setText(_monthDateArray[11]);
        dateArray[12].setText(_monthDateArray[12]);
        dateArray[13].setText(_monthDateArray[13]);
        dateArray[14].setText(_monthDateArray[14]);
        dateArray[15].setText(_monthDateArray[15]);
        dateArray[16].setText(_monthDateArray[16]);
        dateArray[17].setText(_monthDateArray[17]);
        dateArray[18].setText(_monthDateArray[18]);
        dateArray[19].setText(_monthDateArray[19]);
        dateArray[20].setText(_monthDateArray[20]);
        dateArray[21].setText(_monthDateArray[21]);
        dateArray[22].setText(_monthDateArray[22]);
        dateArray[23].setText(_monthDateArray[23]);
        dateArray[24].setText(_monthDateArray[24]);
        dateArray[25].setText(_monthDateArray[25]);
        dateArray[26].setText(_monthDateArray[26]);
        dateArray[27].setText(_monthDateArray[27]);
        dateArray[28].setText(_monthDateArray[28]);
        dateArray[29].setText(_monthDateArray[29]);
        dateArray[30].setText(_monthDateArray[30]);
        dateArray[31].setText(_monthDateArray[31]);
        dateArray[32].setText(_monthDateArray[32]);
        dateArray[33].setText(_monthDateArray[33]);
        dateArray[34].setText(_monthDateArray[34]);
        dateArray[35].setText(_monthDateArray[35]);
        dateArray[36].setText(_monthDateArray[36]);
        dateArray[37].setText(_monthDateArray[37]);
        dateArray[38].setText(_monthDateArray[38]);
        dateArray[39].setText(_monthDateArray[39]);
        dateArray[40].setText(_monthDateArray[40]);
        dateArray[41].setText(_monthDateArray[41]);

    }

    public void inits() {
        dateArray = new Text[42];
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


class MonthDateBuilder {
    private Integer[] monthYearArray;
    private String[] monthDateArray;
    private Calendar calendar;
    private Integer firstDayOfMonth, maxDayOfMonth;
    private String nameOfMonth;

    public MonthDateBuilder() {
        calendar = Calendar.getInstance();
        firstDayOfMonth = 1; // random inits, can delete
        maxDayOfMonth = 5; //random inits, can delete for testing purposes


        monthDateArray = new String[42];// FOR STORING THE VALUES FOR THE DATE YOU SEE
        monthYearArray = new Integer[2];// FOR USE IN CALENDAR IF YOU WANT TO IN THE FUTURE USE CUSTOM MONTHS MONTH/YEAR
        monthYearArray[0] = calendar.get(Calendar.MONTH); //RETURNS YOU CURRENT MONTH
        monthYearArray[1] = calendar.get(Calendar.YEAR); //RETURNS YOU CURRENT YEAR
        setNameOfMonth();


        calendar.set(Calendar.MONTH, monthYearArray[0]); //SETS YOUR CALENDAR OBJECT AS WHATEVER MONTH YOU WANT
        calendar.set(Calendar.YEAR, monthYearArray[1]); //SETS YOUR CALENDAR OBJECT YEAR VALUES AS WTV YOU WANT


        setMonthAchors(); // THIS IS TO SET THE MONTH ANCHORS, BEING THE FIRST DAY NUMBER AND THE MAx days
        setMonthArrays(); //THIS IS TO BUILD THE ARRAY OF 42, FOR BUILDING THE VIEW OF THE MONTH
    }

    private void setMonthAchors() {
        Integer dayOfWeek;
        maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); //getting max day of month
        calendar.set(Calendar.DAY_OF_MONTH, 1); //setting calendar to first day of month, numerical 1 to find weekday
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            default:
                firstDayOfMonth = 0;
                break;
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
        }


    }

    public void setMonthArrays() {
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
        while (a < 42) {
            monthDateArray[a] = " ";
            a++;
        }


    }


    public String[] getMonthDateArray() {
        return monthDateArray;
    }

    public void setNameOfMonth(){
        switch(monthYearArray[0]){
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

        }
    }

    public String getNameOfMonth(){return nameOfMonth;}


}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @FXML
    private void handleEventCalendar() {
        dataDetailsPanelPlaceholder.getChildren().clear();
        dataDetailsPanelPlaceholder.getChildren().add(new EventCalendar().getRoot());
    }
```
###### /resources/view/event/EventCalendar.fxml
``` fxml

<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<?import javafx.scene.text.Text?>
<?import javafx.scene.text.Font?>

<GridPane alignment="CENTER" prefHeight="600.0" prefWidth="580.0" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <Label fx:id="monthName" text="January" GridPane.rowIndex="0" GridPane.columnSpan="7"/>
    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="0" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="daySun" styleClass="calender-propertyS-value" text="S" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="1" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayM" styleClass="calender-propertyM-value" text="M" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="2" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayT" styleClass="calender-propertyT-value" text="T" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="3" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayW" styleClass="calender-propertyW-value" text="W" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="4" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayTh" styleClass="calender-propertyS-value" text="T" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="5" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="dayF" styleClass="calender-propertyM-value" text="F" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="80" prefHeight="80"
          GridPane.columnIndex="6" GridPane.rowIndex="1"
          GridPane.columnSpan="1">
        <Label fx:id="daySat" styleClass="calender-propertyT-value" text="S" wrapText="true">
        </Label>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Text fx:id="date1" text="1">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Text fx:id="date2" text="2">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Text fx:id="date3" text="3">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Text fx:id="date4" text="4">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Text fx:id="date5" text="5">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Text fx:id="date6" text="6">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="2"
          GridPane.columnSpan="1">
        <Text fx:id="date7" text="7">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Text fx:id="date8" text="8">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Text fx:id="date9" text="9">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Text fx:id="date10" text="10">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Text fx:id="date11" text="11">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Text fx:id="date12" text="12">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Text fx:id="date13" text="13">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="3"
          GridPane.columnSpan="1">
        <Text fx:id="date14" text="14">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Text fx:id="date15" text="15">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Text fx:id="date16" text="16">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Text fx:id="date17" text="17">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Text fx:id="date18" text="18">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Text fx:id="date19" text="19">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Text fx:id="date20" text="20">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="4"
          GridPane.columnSpan="1">
        <Text fx:id="date21" text="21">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Text fx:id="date22" text="22">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Text fx:id="date23" text="23">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Text fx:id="date24" text="24">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Text fx:id="date25" text="25">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Text fx:id="date26" text="26">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Text fx:id="date27" text="27">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="5"
          GridPane.columnSpan="1">
        <Text fx:id="date28" text="28">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Text fx:id="date29" text="29">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Text fx:id="date30" text="30">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Text fx:id="date31" text="31">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Text fx:id="date32" text="32">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Text fx:id="date33" text="33">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Text fx:id="date34" text="34">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="6"
          GridPane.columnSpan="1">
        <Text fx:id="date35" text="35">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="0" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Text fx:id="date36" text="36">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="1" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Text fx:id="date37" text="37">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="2" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Text fx:id="date38" text="38">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="3" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Text fx:id="date39" text="39">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="4" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Text fx:id="date40" text="40">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="5" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Text fx:id="date41" text="41">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

    <VBox prefWidth="50" prefHeight="50"
          GridPane.columnIndex="6" GridPane.rowIndex="7"
          GridPane.columnSpan="1">
        <Text fx:id="date42" text="42">
            <font>
                <Font size="30"/>
            </font>
        </Text>
    </VBox>

</GridPane>
```
