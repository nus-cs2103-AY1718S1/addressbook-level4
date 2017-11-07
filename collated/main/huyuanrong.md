# huyuanrong
###### \java\seedu\address\commons\events\ui\ShowContactsEvent.java
``` java
/**
 * An event requesting to view the contacts page.
 */
public class ShowContactsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\ContactsCommand.java
``` java
/**
 * Format useful contact instructions for every command for display.
 */
public class ContactsCommand extends Command {

    public static final String COMMAND_WORD = "emergency";
    public static final String COMMAND_WORD_ALIAS = "sos";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows useful contacts.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_CONTACT_MESSAGE = "Opened contacts window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowContactsEvent());
        return new CommandResult(SHOWING_CONTACT_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String COMMAND_WORD_NAME_PREFIX = "n/";
    public static final String COMMAND_WORD_TAG_PREFIX = "t/";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all places whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + COMMAND_WORD_NAME_PREFIX + "alice bob charlie"
            + "Example: " + COMMAND_WORD + COMMAND_WORD_TAG_PREFIX + "[bookmark] [attractions]";
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ContactsCommand.COMMAND_WORD:
        case ContactsCommand.COMMAND_WORD_ALIAS:
            return new ContactsCommand();
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
        final Pattern prefixFormat = Pattern.compile("(?<prefix>\\w/)(?<arguments>.*)");
        final Matcher prefixMatcher = prefixFormat.matcher(trimmedArgs);

        if (!prefixMatcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        /* Group the prefixes together and the arguments together */
        final String prefix = prefixMatcher.group("prefix");
        final String arguments = prefixMatcher.group("arguments");

        String[] keywords = arguments.split("\\s+");

        switch (prefix) {

        case COMMAND_WORD_NAME_PREFIX:
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));

        case COMMAND_WORD_TAG_PREFIX:
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
```
###### \java\seedu\address\model\place\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPlace}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPlace> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPlace place) {
        Set<Tag> tags = place.getTags();
        List<Tag> tagsList = tags.stream().collect(Collectors.toList());
        return keywords.stream()
                .anyMatch(keyword -> tagsList.stream().anyMatch(tag ->
                        keyword.contains(tag.tagName.toString())) & true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
                new Place(new Name("Marina Bay Sands"), new Phone("66888868"),
                        new Website("https://www.marinabaysands.com"),
                    new Address("10 Bayfront Avenue"), new PostalCode("018956"),
                    getTagSet("attractions")),
                new Place(new Name("Singapore Flyer"), new Phone("6333311"),
                        new Website("https://www.singaporeflyer.com"),
                    new Address("30 Raffles Ave"), new PostalCode("039803"),
                    getTagSet("attractions")),
                new Place(new Name("Singapore Zoo"), new Phone("62693411"),
                        new Website("https://www.wrs.com.sg"),
                    new Address("80 Mandai Lake Rd"), new PostalCode("729826"),
                    getTagSet("attractions")),
                new Place(new Name("Beni Singapore"), new Phone("91593177"),
                        new Website("https://www.beni-sg.com"),
                    new Address("333A Orchard Rd, #02-37"), new PostalCode("238897"),
                    getTagSet("onestar")),
                new Place(new Name("Odette"), new Phone("63850498"),
                        new Website("https://www.odetterestaurant.com"),
                    new Address("1 Saint Andrew's Rd, #01-04, National Gallery"), new PostalCode("178957"),
                    getTagSet("twostars")),
                new Place(new Name("Joël Robuchon Restaurant"), new Phone("65777888"),
                        new Website("https://www.rwsentosa.com"),
                    new Address("26 Sentosa Gateway, Hotel Michael, #01-104 and 105"), new PostalCode("098138"),
                    getTagSet("threestars"))
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "-backup.xml");
    }
```
###### \java\seedu\address\ui\ContactWindow.java
``` java
/**
 * Controller for a Useful Contacts page
 */
public class ContactWindow extends UiPart<Region> {

    public static final String USERGUIDE_FILE_PATH = "/docs/UsefulContacts.html";

    private static final Logger logger = LogsCenter.getLogger(ContactWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "ContactWindow.fxml";
    private static final String TITLE = "Useful Numbers";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public ContactWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        String usefulNumbersUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
        browser.getEngine().load(usefulNumbersUrl);
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing useful numbers to note in Singapore.");
        dialogStage.showAndWait();
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private MenuItem contactsMenuItem;
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        setAccelerator(contactsMenuItem, KeyCombination.valueOf("F2"));
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the useful contacts window.
     */
    @FXML
    public void showNumbers() {
        ContactWindow contactWindow = new ContactWindow();
        contactWindow.show();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowContactsEvent(ShowContactsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showNumbers();
    }
```
###### \resources\view\ContactWindow.fxml
``` fxml

<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.web.WebView?>

<StackPane fx:id="contactWindowRoot" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="browser" />
</StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
      <MenuItem fx:id="contactsMenuItem" mnemonicParsing="false" onAction="#showNumbers" text="Useful Numbers" />
```
