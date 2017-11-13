# zameschua
###### \java\seedu\address\commons\events\external\SendSmsRequestEvent.java
``` java
/**
 * Indicates a request for sending an SMS
 */
public class SendSmsRequestEvent extends BaseEvent {

    private String message;
    private String[] recipients;

    public SendSmsRequestEvent(String message, String[] recipients) {
        this.message = message;
        this.recipients = recipients;
    }

    public String getMessage() {
        return message;
    }

    public String[] getRecipients() {
        return recipients;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\CommandBoxContentsChangedEvent.java
``` java
/**
 * Indicates change in the text of the CommandBox
 */
public class CommandBoxContentsChangedEvent extends BaseEvent {

    private String newCommandText;

    public CommandBoxContentsChangedEvent(String newCommandText) {
        this.newCommandText = newCommandText;
    }

    public String getCommandText() {
        return newCommandText;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\CommandPredictionPanelHideEvent.java
``` java
/**
 * Indicates a request to hide the CommandPredictionPanel
 */
public class CommandPredictionPanelHideEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\CommandPredictionPanelNextSelectionEvent.java
``` java
/**
 * Indicates a request to go the next selection in the CommandPredictionPanel
 */
public class CommandPredictionPanelNextSelectionEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\CommandPredictionPanelPreviousSelectionEvent.java
``` java
/**
 * Indicates a request to go the previous selection in the CommandPredictionPanel
 */
public class CommandPredictionPanelPreviousSelectionEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\CommandPredictionPanelSelectionChangedEvent.java
``` java
/**
 * Indicates a change in selection of the CommandPredictionPanel
 */
public class CommandPredictionPanelSelectionChangedEvent extends BaseEvent {

    private String currentSelection;

    public CommandPredictionPanelSelectionChangedEvent(String currentSelection) {
        this.currentSelection = currentSelection;
    }

    public String getCurrentSelection() {
        return currentSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\CommandPredictionPanelSelectionEvent.java
``` java
/**
 * Indicates a change in selection of the CommandPredictionPanel
 */
public class CommandPredictionPanelSelectionEvent extends BaseEvent {

    private String currentSelection;

    public CommandPredictionPanelSelectionEvent(String currentSelection) {
        this.currentSelection = currentSelection;
    }

    public String getCurrentSelection() {
        return currentSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\SmsCommandRequestEvent.java
``` java
/**
 * Indicates a request for SmsCommand
 */
public class SmsCommandRequestEvent extends BaseEvent {

    private final ArrayList<String> phoneNumbers;

    public SmsCommandRequestEvent(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\external\sms\SmsManager.java
``` java
/**
 * Follows Singleton and Facade design pattern,
 * for other parts of the app to interface with the Twilio SMS service
 */
public class SmsManager {

    private static SmsManager instance = null;

    protected SmsManager() {
        registerAsAnEventHandler(this);
    }

    /**
     * Creates an instance of the SmsManager and registers it as an event handler
     * @return The Singleton instance of the SmsManager
     */
    public static SmsManager init() {
        if (instance == null) {
            instance = new SmsManager();
            TwilioApiHelper.init();
        }
        return instance;
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    @Subscribe
    public void handleSendSmsRequestEvent(SendSmsRequestEvent event) {
        String[] recipients = event.getRecipients();
        String message = event.getMessage();

        for (String phoneNumber : recipients) {
            TwilioApiHelper.sendSms(message, phoneNumber);
        }
    }
}
```
###### \java\seedu\address\external\sms\TwilioApiHelper.java
``` java
/**
 * Helper class to handle the sending of SMS using Twilio API
 */
public class TwilioApiHelper {
    /*
    I know that I'm not supposed to store my API keys like this
    But storing it in a separate file will make it very difficult for my peers to pass tests
    and difficult for the tutotrs to grade
    Don't worry, it's just a trial Twilio account
    */
    private static final String ACCOUNT_SID = "AC8e7d80947bd2e877013c66d99b0faa06";
    private static final String AUTH_TOKEN = "46abad64b64c0b29c468434ff69e36ca";
    private static final String TWILIO_PHONE_NUMBER = "+1 954-320-0045";

    private static final String MESSAGE_SMS_SUCCESS = "SMS Successfully sent";
    private static final String MESSAGE_SMS_FAILURE = "SMS Sending failed, ";
    private static final String COUNTRY_CODE_SINGAPORE = "+65";
    private static final String PHONE_NUMBER_REGEX_SINGAPORE = "\\+65\\d{8}";
    private static final int PHONE_NUMBER_LENGTH_SINGAPORE = 8;

    /**
     * Initialises the Twilio API service
     */
    public static void init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    /**
     * Helper method that calls the Twilio REST API for sending SMS
     * @param smsReceipient the target phone number. Has to contain the country code like +65
     * @param message The message to send as the content of the SMS
     * @throws RuntimeException Catches the runtime exception so that we can show an error message to the user
     * in the {@link seedu.address.logic.commands.CommandResult}
     */
    public static void sendSms(String message, String smsReceipient) throws RuntimeException {
        try {
            smsReceipient = checkPhoneNumberFormat(smsReceipient);
            assert smsReceipient.matches(PHONE_NUMBER_REGEX_SINGAPORE);
            Message.creator(new PhoneNumber(smsReceipient), new PhoneNumber(TWILIO_PHONE_NUMBER), message).create();
            showToUser(MESSAGE_SMS_SUCCESS);
        } catch (RuntimeException rte) {
            // Show the error in the ResultDisplay so the user knows what's wrong
            showToUser(MESSAGE_SMS_FAILURE);
            throw rte;
        }
    }

    /**
     * Helper method which posts a {@link NewResultAvailableEvent} to show a message in
     * the {@link seedu.address.logic.commands.CommandResult}
     * Also handles logging in the form of events
     * @param message The message to show in the {@link seedu.address.logic.commands.CommandResult}
     */
    private static void showToUser(String message) {
        EventsCenter.getInstance().post(new NewResultAvailableEvent(message));
    }

    /**
     * Helper method that prepends the country code to a phone number if
     * it doesn't have the country code
     * @param phoneNumber The phone number to check
     */
    public static String checkPhoneNumberFormat(String phoneNumber) {
        assert phoneNumber.length() >= PHONE_NUMBER_LENGTH_SINGAPORE;
        if (!phoneNumber.startsWith(COUNTRY_CODE_SINGAPORE)) {
            return COUNTRY_CODE_SINGAPORE + phoneNumber;
        } else {
            return phoneNumber;
        }
    }
}
```
###### \java\seedu\address\logic\commands\Command.java
``` java
    /**
     * Constructs a feedback message to summarise an operation for mass emailing
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */

    public static String getMessageForSms(int displaySize, ArrayList<String> phoneNumbers) {
        if (displaySize != 0) {
            StringBuilder mess = new StringBuilder(String.format(Messages.MESSAGE_SMS_CONFIRMATION, displaySize));
            mess.append("\n");
            for (String phoneNumber : phoneNumbers) {
                mess.append(phoneNumber);
                mess.append("\n");
            }
            return mess.toString();
        } else {
            return Messages.MESSAGE_NOBODY_FOUND;
        }
    }
```
###### \java\seedu\address\logic\commands\SmsCommand.java
``` java
/**
 * Brings up the smsPanel
 * Then finds and lists all persons in address book whose tags match those in the command, for group SMSsing
 * Keyword matching is case-sensitive.
 */
public class SmsCommand extends Command {

    public static final String COMMAND_WORD = "sms";
    public static final String MESSAGE_SUCCESS = "Listed all required SMSses";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": To SMS all persons with tag\n"
            + COMMAND_WORD + " all : To send an SMS to everyone in CYNC\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]... \n"
            + "Example: " + COMMAND_WORD + " Sec 2 Sec 3\n";

    private final TagMatchingPredicate predicate;

    public SmsCommand(TagMatchingPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        ObservableList<ReadOnlyPerson> allPerson = model.getFilteredPersonList();
        ArrayList<String> phoneNumbers = new ArrayList<String>();
        for (ReadOnlyPerson person : allPerson) {
            phoneNumbers.add(person.getPhone().toString());
        }
        EventsCenter.getInstance().post(new SmsCommandRequestEvent(phoneNumbers));
        return new CommandResult(getMessageForSms(allPerson.size(), phoneNumbers));
    }

    public TagMatchingPredicate getPredicate() {
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SmsCommand // instanceof handles nulls
                && this.predicate.equals(((SmsCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SmsCommand.COMMAND_WORD:
            return new SmsCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\SmsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SmsCommand object
 */
public class SmsCommandParser implements Parser<SmsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SmsCommand
     * and returns an SmsCommand object for execution.
     */
    public SmsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SmsCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SmsCommand(new TagMatchingPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Changes the state of the {@link CommandBox} in commandPredictionSelectionText
     * which stores the current selection of the {@link CommandPredictionPanel}
     * @param event The event fired from the {@link CommandPredictionPanel}
     */
    @Subscribe
    private void handleCommandPredictionPanelSelectionChangedEvent(CommandPredictionPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionSelectionText = event.getCurrentSelection();
    }

    /**
     * Updates the state of the {@link CommandBox} in commandPredictionSelectionText
     * whenever the user changes its text. This is to produce the expected behaviour where
     * the user's command should not disappear upon pressing tab, when the user was not
     * expecting a command prediction
     * @param event The event fired from the constructor in {@link CommandBox}
     */
    @Subscribe
    private void handleCommandBoxContentsChangedEvent(CommandBoxContentsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionSelectionText = event.getCommandText();
    }
```
###### \java\seedu\address\ui\CommandPredictionPanel.java
``` java
/**
 * Panel containing command predictions
 * It only shows when the user types something into the search box
 * And a command prediction is expected
 * Works similarly to Google's search prediction.
 */
public class CommandPredictionPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(CommandPredictionPanel.class);
    private static final String FXML = "CommandPredictionPanel.fxml";
    private static final ArrayList<String> COMMAND_PREDICTION_RESULTS_INITIAL =
            new ArrayList<String>(Arrays.asList(
                    "help", "add", "list", "listalltags", "edit", "find", "delete", "select",
                    "history", "calendar", "addEvent", "mass", "sms", "undo", "redo", "clear", "exit"));

    private static ObservableList<String> commandPredictionResults;
    // tempPredictionResults used to store the results from filtering through COMMAND_PREDICTION_RESULTS_INITIAL
    private ArrayList<String> tempPredictionResults;

    @FXML
    private ListView<String> commandPredictionListView;

    public CommandPredictionPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        initDataStructures();
        initListView();
        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * Helper method for the constructor to initialise the various data structures used
     * in the CommandPredictionPanel
     */
    private void initDataStructures() {
        tempPredictionResults = new ArrayList<String>();
        commandPredictionResults = FXCollections.observableArrayList(tempPredictionResults);
    }

    /**
     * Helper method for the constructor to initialise the ListView UI
     */
    private void initListView() {
        commandPredictionListView.setVisible(false);
        // Attach ObservableList to ListView
        commandPredictionListView.setItems(commandPredictionResults);
    }

    /**
     * This method refreshes the CommandPredictionPanel with results that start with `newText`
     * @param newText
     */
    private void updatePredictionResults(String newText) {
        commandPredictionResults.clear();
        tempPredictionResults = COMMAND_PREDICTION_RESULTS_INITIAL
                .stream()
                .filter(p -> p.startsWith(newText))
                .collect(toCollection(ArrayList::new));

        commandPredictionResults.addAll(tempPredictionResults);
        commandPredictionListView.setItems(commandPredictionResults);
        commandPredictionListView.getSelectionModel().selectFirst();

        // Set the prediction to be invisible if there is nothing typed in the Command Box
        // Or if there is no prediction to show
        if (newText.equals("") || commandPredictionResults.isEmpty()) {
            commandPredictionListView.setVisible(false);
        } else {
            commandPredictionListView.setVisible(true);
        }
    }

    /**
     * Helper method for the constructor
     * Attaches an event handler to the CommandPredictionPanel to track when
     * the user changes the CommandPrediction
     *
     * The method fires another event to the {@link seedu.address.commons.core.EventsCenter},
     * which is handled by {@link CommandBox} and in turns changes its state
     */
    private void setEventHandlerForSelectionChangeEvent() {
        commandPredictionListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in command prediction panel changed to : '" + newValue + "'");
                        raise(new CommandPredictionPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    @Subscribe
    private void handleCommandBoxContentsChangedEvent(CommandBoxContentsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updatePredictionResults(event.getCommandText());
    }

    @Subscribe
    private void handleCommandPredictionPanelNextSelectionEvent(CommandPredictionPanelNextSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionListView.getSelectionModel().selectNext();
    }

    @Subscribe
    private void handleCommandPredictionPanelPreviousSelectionEvent(
            CommandPredictionPanelPreviousSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionListView.getSelectionModel().selectPrevious();
    }

    @Subscribe
    private void handleCommandPredictionPanelHideEvent(CommandPredictionPanelHideEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionListView.setVisible(false);
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleSmsCommandEvent(SmsCommandRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSms(event.getPhoneNumbers());
    }
```
###### \java\seedu\address\ui\SmsPanel.java
``` java
/**
 * The Sms Panel of the App
 * Appears on the main page after typing the "sms" command
 */
public class SmsPanel extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "SmsPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(SmsPanel.class);

    @FXML
    private TextField recipientsBox;
    @FXML
    private TextArea smsMessage;
    @FXML
    private Button sendButton;

    public SmsPanel(ArrayList<String> phoneNumbersList) {
        super(FXML);

        SmsManager.init();
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sendSms();
                logger.info("SEND BUTTON CLICKED");
            }
        });
        String recipients = appendPhoneNumbers(phoneNumbersList);
        recipientsBox.setText(recipients);
    }

    /**
     * Method returns a string of phone numbers, joined by semicolons `;`
     * to be displayed in recipientBox
     */
    private String appendPhoneNumbers(ArrayList<String> phoneNumbers) {
        StringBuilder phoneNumbersStringBuilder = new StringBuilder();
        for (String phoneNumber: phoneNumbers) {
            phoneNumbersStringBuilder.append(phoneNumber).append(";");
        }
        return phoneNumbersStringBuilder.toString();
    }

    /**
     * Method posts {@link SendSmsRequestEvent}
     */
    private void sendSms() {
        String message = smsMessage.getText();
        String[] recipients = recipientsBox.getText().split(";");
        EventsCenter.getInstance().post(new SendSmsRequestEvent(message, recipients));
    }
}
```
###### \resources\view\CommandPredictionPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <ListView fx:id="commandPredictionListView" VBox.vgrow="ALWAYS" id="command-prediction-panel" styleClass="card"/>
</VBox>
```
###### \resources\view\Styles.css
``` css
.root {
    main-background-color: #E1E2E1;
    main-foreground-color: #F5F5F6;
    primary-text-color: #212121;
    secondary-text-color: #757575;
    primary-color: #81d4fa;
    accent-color: #4ba3c7;
}

.background {
    -fx-background-color: main-background-color;
    background-color: main-background-color; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: main-foreground-color;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        transparent
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: primary-text-color;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: main-foreground-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: main-background-color;
    -fx-border-color: transparent;
}

.split-pane {
    -fx-background-color: main-background-color;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0;
    -fx-border-color: secondary-text-color;
    -fx-border-width: 0.04px;
}

.list-cell:filled:even {
    -fx-background-color: main-foreground-color;
}

.list-cell:filled:odd {
    -fx-background-color: main-foreground-color;
}

.list-cell:filled:selected {
    -fx-background-color: derive(primary-color, 0%);
}

.list-cell .label {
    -fx-text-fill: secondary-text-color;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 20px;
    -fx-text-fill: primary-text-color;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 16px;
    -fx-text-fill: secondary-text-color;
}

.anchor-pane {
     -fx-background-color: main-background-color;
}

.pane-with-border {
     -fx-background-color: main-background-color;
}

.status-bar {
    -fx-background-color: main-background-color;
    -fx-text-fill: primary-text-color;
}

.result-display {
    -fx-background-color: main-foreground-color;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 15pt;
    -fx-text-fill: secondary-text-color;
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 2, 2);
}

.result-display .label {
    -fx-text-fill: primary-text-color !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: primary-text-color;
}

.status-bar-with-border {
    -fx-background-color: main-foreground-color;
    -fx-border-color: transparent;
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: primary-text-color;
}

.grid-pane {
    -fx-background-color: main-foreground-color;
    -fx-border-color: transparent;
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: main-foreground-color
}

.context-menu {
    -fx-background-color: primary-color;
}

.context-menu .label {
    -fx-text-fill: primary-text-color;
}

.menu-bar {
    -fx-background-color: derive(main-background-color, 40%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: primary-text-color;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: main-background-color;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: transparent;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: primary-text-color;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: main-foreground-color;
  -fx-text-fill: primary-text-color;
}

.button:focused {
    -fx-border-color: transparent, transparent;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: main-background-color;
    -fx-text-fill: primary-text-color;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: primary-text-color;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: main-foreground-color;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: main-foreground-color;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: primary-text-color;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: main-foreground-color;
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: primary-text-color;
}

.scroll-bar {
    -fx-background-color: derive(main-background-color, -5%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(main-background-color, -25%);
    -fx-background-insets: 3;
    -fx-background-radius: 18 18 18 18;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: main-foreground-color;
    -fx-background-insets: 0;
    -fx-background-radius: 30;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 18pt;
    -fx-text-fill: primary-text-color;
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 2, 2);
}
#commandTextField:focused {
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -30%), 20, 0, 4, 4);
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 2, 2);
}

#resultDisplay .content {
    -fx-background-color: main-foreground-color;
    -fx-background-radius: 0;
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 2, 2);
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: derive(accent-color, -20%);
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#command-prediction-panel {
    -fx-font-size: 20px;
    -fx-font-family: "Segoe UI Light";
}

.card {
    -fx-effect: dropshadow(gaussian, derive(#f7f5f4, -15%), 10, 0, 2, 2);
    -fx-background-color: main-foreground-color;
}
```
