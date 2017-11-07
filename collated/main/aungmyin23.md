# aungmyin23
###### \java\seedu\address\commons\events\ui\ShowMrtRequestEvent.java
``` java
/**
 * An event requesting to view the mrt page.
 */
public class ShowMrtRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowPsiRequestEvent.java
``` java
/**
 * An event requesting to view the psi webpage.
 */
public class ShowPsiRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\MrtCommand.java
``` java
/**
 * Format full help instructions for every command for display.
 */
public class MrtCommand extends Command {

    public static final String COMMAND_WORD = "mrt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows MRT map.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_MRT_MESSAGE = "Opened mrt window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMrtRequestEvent());
        return new CommandResult(SHOWING_MRT_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\commands\PsiCommand.java
``` java
/**
 * Format full help instructions for every command for display.
 */
public class PsiCommand extends Command {

    public static final String COMMAND_WORD = "psi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows PSI of Singapore.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_PSI_MESSAGE = "Latest PSI webpage loaded.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowPsiRequestEvent());
        return new CommandResult(SHOWING_PSI_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Optional<Phone> optionalPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            if (optionalPhone.isPresent()) {
                phone = optionalPhone.get();
            } else {
                phone = new Phone (null);
            }
            Optional<Website> optionalWebsite = ParserUtil.parseWebsite(argMultimap.getValue(PREFIX_WEBSITE));
            if (optionalWebsite.isPresent()) {
                website = optionalWebsite.get();
            } else {
                website = new Website(null);
            }
            Optional<Address> optionalAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            if (optionalAddress.isPresent()) {
                address = optionalAddress.get();
            } else {
                address = new Address(null);
            }
            PostalCode postalcode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTAL_CODE)).get();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case MrtCommand.COMMAND_WORD:
            return new MrtCommand();
        case PsiCommand.COMMAND_WORD:
            return new PsiCommand();
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> postalcode} into an {@code Optional<PostalCode>} if {@code PostalCode}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<PostalCode> parsePostalCode(Optional<String> postalcode) throws IllegalValueException {
        requireNonNull(postalcode);
        return postalcode.isPresent() ? Optional.of(new PostalCode(postalcode.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Website> parseWebsite(Optional<String> website) throws IllegalValueException {
        return website.isPresent() ? Optional.of(new Website(website.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\place\PostalCode.java
``` java
/**
 * Represents a place's postal code in the Tourist-book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {


    public static final String MESSAGE_POSTAL_CODE_CONSTRAINTS =
            "Singapore Postal Code can only contain numbers, and should be just 6 digits long";
    public static final String POSTAL_CODE_VALIDATION_REGEX = "\\d{6,6}";
    public final String value;

    /**
     * Validates given postal ccode.
     *
     * @throws IllegalValueException if given postal code string is invalid.
     */
    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!isValidPostalCode(trimmedPostalCode)) {
            throw new IllegalValueException(MESSAGE_POSTAL_CODE_CONSTRAINTS);
        }
        this.value = trimmedPostalCode;
    }

    /**
     * Returns true if a given string is a valid postal code of the place.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTAL_CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalCode // instanceof handles nulls
                && this.value.equals(((PostalCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\place\Website.java
``` java
/**
 * Represents a Place's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */
public class Website {

    public static final String MESSAGE_WEBSITE_CONSTRAINTS =
            "Place website should contain http://www https://www";
    public static final String WEBSITE_VALIDATION_REGEX =
            "https?://(www\\.)?[-a-z0-9]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&//=]*)";
    public static final String WEBSITE_UNKNOWN = "http://www.-.com";

    public final String value;

    /**
     * Validates given website.
     *
     * @throws IllegalValueException if given website string is invalid.
     */
    public Website(String website) throws IllegalValueException {
        if (website == null) {
            this.value = WEBSITE_UNKNOWN;
        } else {
            String trimmedWebsite = website.trim();
            if (!isValidWebsite(trimmedWebsite)) {
                throw new IllegalValueException((MESSAGE_WEBSITE_CONSTRAINTS));
            }
            this.value = trimmedWebsite;
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private MenuItem mrtMapItem;
    @FXML
    private MenuItem psiItem;
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowMrtEvent(ShowMrtRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleMrtMap();

    }

    @Subscribe
    private void handleShowPsiEvent(ShowPsiRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handlePsi();

    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
}
```
###### \java\seedu\address\ui\MrtWindow.java
``` java
/**
 * Controller for a help page
 */
public class MrtWindow extends UiPart<Region> {

    public static final String MRT_FILE_PATH = "/images/MrtMap.jpg";

    private static final Logger logger = LogsCenter.getLogger(MrtWindow.class);
    private static final String ICON = "/images/mrt_logo.png";
    private static final String FXML = "MrtMapWindow.fxml";
    private static final String TITLE = "MRT Map";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public MrtWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        String mrtMapUrl = getClass().getResource(MRT_FILE_PATH).toString();
        browser.getEngine().load(mrtMapUrl);
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
        logger.fine("Showing MRT map of Singapore.");
        dialogStage.showAndWait();
    }
}
```
###### \resources\view\MrtMapWindow.fxml
``` fxml

<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.web.WebView?>

<StackPane fx:id="mrtWindowRoot" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <WebView fx:id="browser" />
</StackPane>
```
###### \resources\view\PlaceListCard.fxml
``` fxml
      <Label fx:id="postalcode" styleClass="cell_small_label" text="\$postalcode" />
      <Label fx:id="website" styleClass="cell_small_label" text="\$website" />
```
