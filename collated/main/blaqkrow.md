# blaqkrow
###### \java\seedu\address\commons\events\ui\PersonPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final PersonCard newSelection;
    private final int selectionIndex;

    public PersonPanelSelectionChangedEvent(PersonCard newSelection, int selectionIndex) {
        this.newSelection = newSelection;
        this.selectionIndex = selectionIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }
    public int getSelectionIndex() {
        return  selectionIndex; }
}
```
###### /java/seedu/address/commons/events/ui/QrSaveEvent.java
``` java
/**
 * Represents a selection change in the Qr Event
 */
public class QrSaveEvent extends BaseEvent {
    private ReadOnlyPerson person;
    public QrSaveEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### /java/seedu/address/commons/events/ui/QrSmsEvent.java
``` java
/**
 * Represents a selection change in the Qr Event
 */
public class QrSmsEvent extends BaseEvent {
    private ReadOnlyPerson person;
    public QrSmsEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### /java/seedu/address/logic/commands/EmailCommand.java
``` java
/**
 * Emails the selected person using the specified index in the list.
 */
public class EmailCommand extends Command {
    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";
    public static final String MESSAGE_SUCCESS = "Email opened!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Emails the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    private LoggingCommand lg = new LoggingCommand();

    private final Index emailIndex;

    public EmailCommand(Index emailIndex) {
        this.emailIndex = emailIndex;
    }

    @Override
    public CommandResult execute() throws CommandException, IOException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (emailIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEmail = lastShownList.get(emailIndex.getZeroBased());

        OpenEmailClient emailClient = new OpenEmailClient(personToEmail.getEmail().toString());
        emailClient.sendMail();
        lg.keepLog("Email client opened!", "Email");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/parser/EmailCommandParser.java
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
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/ui/AddressTextField.java
``` java
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's address.
 */
public class AddressTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "AddressTextField.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private TextField addressTextField;

    public AddressTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    public String getAddressTextField() {
        return addressTextField.getText();
    }

    public void setAddressTextField(String text) {
        addressTextField.setText(text);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setAddressTextField(event.getNewSelection().person.getAddress().toString());
    }


}

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/place/";
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    /**
     * loads webpage
     */
    public void loadPersonPage(ReadOnlyPerson person) {
        browser.getEngine().setUserAgent("Mozilla/5.0 "
                + "(Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0");
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+"));
    }
    /**
     * Loads generated Call QR code
     */
    public void loadQrCode(ReadOnlyPerson person) {
        QrGenCallCommand qrGenCallCommand = new QrGenCallCommand();
        browser.getEngine().setUserAgent("Mozilla/5.0 "
                + "(Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0");
        loadPage(qrGenCallCommand.qrCall(person.getPhone().toString()));
    }
    /**
     * Loads generated SMS QR Code
     */
    public void loadSmsQrCode(ReadOnlyPerson person) {
        QrGenSmsCommand qrGenSmsCommand = new QrGenSmsCommand();
        browser.getEngine().setUserAgent("Mozilla/5.0 "
                + "(Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0");
        loadPage(qrGenSmsCommand.qrSms(person.getPhone().toString(), person.getName().fullName));
    }
    /**
     * Loads generated share QR code
     */
    public void loadSaveQrCode(ReadOnlyPerson person) {
        QrGenSaveContactCommand qrGenSaveContactCommand = new QrGenSaveContactCommand();
        browser.getEngine().setUserAgent("Mozilla/5.0 "
                + "(Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0");
        loadPage(qrGenSaveContactCommand.qrSaveContact(person.getPhone().toString(), person.getName().fullName,
                person.getEmail().toString()));
    }
    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
```
###### /java/seedu/address/ui/ClearLogButton.java
``` java
/**
 * The UI component that is responsible for clearing the log.
 */
public class ClearLogButton extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "ClearLogButton.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private LoggingCommand loggingCommand;

    @FXML
    private Button deleteButton;

    public ClearLogButton() {
        super(FXML);
        loggingCommand = new LoggingCommand();
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleClearLogButtonPressed() throws CommandException, ParseException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want clear the log?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            ClearLogCommand clearLogCommand = new ClearLogCommand();
            clearLogCommand.execute();
            logger.info("Log has been cleared.");
        }
    }
}
```
###### /java/seedu/address/ui/DeleteButton.java
``` java
/**
 * The UI component that is responsible for deleting selected contacts in the PersonListPanel.
 */
public class DeleteButton extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "DeleteButton.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private int selectedIndex;

    @FXML
    private Button deleteButton;

    public DeleteButton(Logic logic, int selectedIn) {
        super(FXML);
        this.logic = logic;
        this.selectedIndex = selectedIn;
        registerAsAnEventHandler(this);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleDeleteButtonPressed() throws CommandException, ParseException, IOException {
        CommandResult commandResult = logic.execute("delete " + getSelectedIndex());
        logger.info("Result: " + commandResult.feedbackToUser);
    }

    private void setSelectedIndex(int i) {
        selectedIndex = i;
    }

    private int getSelectedIndex() {
        return selectedIndex;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        int baseOneIndex = event.getSelectionIndex() + 1;
        setSelectedIndex(baseOneIndex);
    }
}
```
###### /java/seedu/address/ui/EditButton.java
``` java
/**
 * The UI component that is responsible for editing selected contacts in the PersonListPanel.
 */
public class EditButton extends UiPart<Region> {
    public static final String NAME_ERROR = "NAME_ERROR";
    public static final String EMAIL_ERROR = "EMAIL_ERROR";
    public static final String PHONE_ERROR = "PHONE_ERROR";
    public static final String TAG_ERROR = "TAG_ERROR";
    public static final String VALIDATION_SUCCESS = "VALIDATION_SUCCESS";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EditButton.fxml";
    private static NameTextField nameTextField;
    private static PhoneTextField phoneTextField;
    private static EmailTextField emailTextField;
    private static AddressTextField addressTextFieldTextField;
    private static TagTextField tagTextField;
    private static int selectedIndex;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private LoggingCommand lg = new LoggingCommand();

    @FXML
    private Button editButton;
    public EditButton(Logic logic, NameTextField ntf, PhoneTextField ptf,
                      EmailTextField etf, AddressTextField atf, TagTextField ttf) {
        super(FXML);
        this.logic = logic;
        this.nameTextField = ntf;
        this.phoneTextField = ptf;
        this.emailTextField = etf;
        this.addressTextFieldTextField = atf;
        this.tagTextField = ttf;
        registerAsAnEventHandler(this);
    }

    /**
     * Handles the edit button pressed event.
     */
    @FXML
    private void handleEditButtonPressed() throws CommandException, IllegalValueException, IOException {
        StringBuilder command = new StringBuilder();
        CommandResult commandResult;
        Alert alert;
        String checkInputResult = checkInput(nameTextField.getNameTextField(), phoneTextField.getPhoneTextField(),
                emailTextField.getEmailTextField(), addressTextFieldTextField.getAddressTextField(),
                tagTextField.getTagTextArea());

        if (checkInputResult.equals(NAME_ERROR)) {
            nameTextField.getObject().setStyle("-fx-text-inner-color: red;");
            alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid name!",
                    ButtonType.OK);
            alert.showAndWait();
            throw new IllegalValueException("Name contains illegal characters!");
        }
        if (checkInputResult.equals(PHONE_ERROR)) {
            phoneTextField.getObject().setStyle("-fx-text-inner-color: red;");
            alert = new Alert(Alert.AlertType.ERROR, "Please enter phone no. without character values!",
                    ButtonType.OK);
            alert.showAndWait();
            throw new IllegalValueException("Phone number contains illegal characters!");
        }
        if (checkInputResult.equals(EMAIL_ERROR)) {
            emailTextField.getObject().setStyle("-fx-text-inner-color: red;");
            alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid email address!",
                    ButtonType.OK);
            alert.showAndWait();
            throw new IllegalValueException("Email contains illegal characters!");
        }
        if (checkInputResult.equals(TAG_ERROR)) {
            tagTextField.getObject().setStyle("-fx-text-inner-color: red;");
            alert = new Alert(Alert.AlertType.ERROR, "Please enter a tag in the"
                    + " form: 'tag1','tag2','tag3',... Tags should contain Alphanumeric characters only!",
                    ButtonType.OK);
            alert.showAndWait();
            throw new IllegalValueException("Tags contains illegal characters!");
        } else {
            nameTextField.getObject().setStyle("-fx-text-inner-color: black;");
            phoneTextField.getObject().setStyle("-fx-text-inner-color: black;");
            emailTextField.getObject().setStyle("-fx-text-inner-color: black;");
            tagTextField.getObject().setStyle("-fx-text-inner-color: black;");
            command.append("edit " + getSelectedIndex() + " n/"
                    + nameTextField.getNameTextField() + " p/" + phoneTextField.getPhoneTextField() + " e/"
                    + emailTextField.getEmailTextField()
                    + " a/" + addressTextFieldTextField.getAddressTextField());
            String tagTextArea = tagTextField.getTagTextArea();
            String[] tagSplit = tagTextArea.split(",");
            for (String s : tagSplit) {
                command.append(" t/" + s.trim());
            }
            commandResult = logic.execute(command.toString());
            logger.info("Result: " + commandResult.feedbackToUser);
            lg.keepLog(commandResult.feedbackToUser, "Edited");
        }
    }
    /**
     * Handles checking of content passed into the form
     * @param name the name entered in nameTextField
     * @param phone the name entered in phoneTextField
     * @param email the name entered in emailTextField
     * @param address the name entered in addressTextField
     * @param tag the name entered in tagTextArea
     * @return the corresponding format error, else if no error, return success
     */
    public static String checkInput(String name, String phone, String email, String address, String tag) {
        if (name.matches(".*\\d+.*") || name.isEmpty()) {
            return NAME_ERROR;
        }
        if (!phone.matches("[0-9]+")) {
            return PHONE_ERROR;
        }
        if (!email.contains("@") || !email.contains(".")) {
            return EMAIL_ERROR;
        }
        //check tag doesnt end with a special character
        String[] tagSplit = tag.split(",");
        for (int i = 0; i < tagSplit.length; i++) {
            if (!tagSplit[i].matches("[a-zA-Z0-9]*")) {
                return TAG_ERROR;
            }
        }
        return VALIDATION_SUCCESS;
    }
    private void setSelectedIndex(int i) {
        selectedIndex = i;
    }

    private int getSelectedIndex() {
        return selectedIndex;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        int baseOneIndex = event.getSelectionIndex() + 1;
        setSelectedIndex(baseOneIndex);
    }
}
```
###### /java/seedu/address/ui/EmailButton.java
``` java
/**
 * The UI component that is responsible for emailing the selected person.
 */
public class EmailButton extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EmailButton.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private String selectedEmail  = "";
    private LoggingCommand lg = new LoggingCommand();

    @FXML
    private Button emailButton;
    public EmailButton() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Handles the Email button pressed event.
     */
    @FXML
    private void handleEmailButtonPressed() throws CommandException, ParseException, IOException {
        OpenEmailClient emailClient = new OpenEmailClient(this.selectedEmail);
        emailClient.sendMail();
        lg.keepLog("Email client opened!", "Email");
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        this.selectedEmail = event.getNewSelection().person.emailProperty().getValue().toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }



}
```
###### /java/seedu/address/ui/EmailTextField.java
``` java
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's email.
 */
public class EmailTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EmailTextField.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private TextField emailTextField;

    public EmailTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    public String getEmailTextField() {
        return emailTextField.getText();
    }

    public void setEmailTextField(String text) {
        emailTextField.setText(text);
    }

    public TextField getObject() {
        return emailTextField;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setEmailTextField(event.getNewSelection().person.getEmail().toString());
    }


}

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;
    private DeleteButton deleteButton;
    private EmailButton emailButton;
    private EditButton editButton;
    private NameTextField nameTextField;
    private PhoneTextField phoneTextField;
    private EmailTextField emailTextField;
    private AddressTextField addressTextField;
    private TagTextField tagTextField;
    private ClearLogButton clearLogButton;
    private QrButton qrButton;
    private QrSaveButton qrSaveButton;
    private QrSmsButton qrSmsButton;
    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem blackMenuItem;

    @FXML
    private MenuItem whiteMenuItem;

    @FXML
    private MenuItem greenMenuItem;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane deleteButtonPlaceholder;

    @FXML
    private StackPane emailButtonPlaceholder;
    @FXML
    private StackPane editButtonPlaceholder;
    @FXML
    private StackPane editNameTextfieldPlaceholder;

    @FXML
    private StackPane editPhoneTextfieldPlaceholder;

    @FXML
    private StackPane editEmailTextfieldPlaceholder;

    @FXML
    private StackPane editAddressTextfieldPlaceholder;

    @FXML
    private StackPane editTagTextfieldPlaceholder;
    @FXML
    private StackPane clearLogButtonPlaceholder;
    @FXML
    private StackPane qrButtonPlaceholder;
    @FXML
    private StackPane qrSmsButtonPlaceholder;
    @FXML
    private StackPane qrSaveButtonPlaceholder;
```
###### /java/seedu/address/ui/MainWindow.java
``` java

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        clearLogButton = new ClearLogButton();
        clearLogButtonPlaceholder.getChildren().add(clearLogButton.getRoot());

        nameTextField = new NameTextField();
        editNameTextfieldPlaceholder.getChildren().add(nameTextField.getRoot());

        phoneTextField = new PhoneTextField();
        editPhoneTextfieldPlaceholder.getChildren().add(phoneTextField.getRoot());

        emailTextField = new EmailTextField();
        editEmailTextfieldPlaceholder.getChildren().add(emailTextField.getRoot());

        addressTextField = new AddressTextField();
        editAddressTextfieldPlaceholder.getChildren().add(addressTextField.getRoot());

        tagTextField = new TagTextField();
        editTagTextfieldPlaceholder.getChildren().add(tagTextField.getRoot());
        editButton = new EditButton(logic, nameTextField, phoneTextField,
                emailTextField, addressTextField, tagTextField);
        editButtonPlaceholder.getChildren().add(editButton.getRoot());

        deleteButton = new DeleteButton(logic, 0);
        deleteButtonPlaceholder.getChildren().add(deleteButton.getRoot());
        emailButton = new EmailButton();
        emailButtonPlaceholder.getChildren().add(emailButton.getRoot());

        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        qrButton = new QrButton(browserPanel);
        qrButtonPlaceholder.getChildren().add(qrButton.getRoot());

        qrSaveButton = new QrSaveButton(browserPanel);
        qrSaveButtonPlaceholder.getChildren().add(qrSaveButton.getRoot());

        qrSmsButton = new QrSmsButton(browserPanel);
        qrSmsButtonPlaceholder.getChildren().add(qrSmsButton.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }
```
###### /java/seedu/address/ui/NameTextField.java
``` java
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's name.
 */
public class NameTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "NameTextField.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private TextField nameTextField;

    public NameTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }
    public String getNameTextField() {
        return nameTextField.getText();
    }

    public void setNameTextField(String text) {
        nameTextField.setText(text);
    }
    public TextField getObject() {
        return nameTextField;
    }
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setNameTextField(event.getNewSelection().person.getName().toString());
    }
}
```
###### /java/seedu/address/ui/OpenEmailClient.java
``` java
/**
 * Handles the opening of email client
 */
public class OpenEmailClient {
    private Desktop desktop = Desktop.getDesktop();
    private String mailTo;

    /**
     * Handles the opening of email client
     * @param mailTo the receiver's email address
     */
    public OpenEmailClient(String mailTo) {
        this.mailTo = mailTo.trim();
    }

    public void setMail (String m) {
        mailTo = m;
    }
    /**
     * Handles the sending mail
     */
    public void sendMail () throws IOException {

        URI uri = URI.create("mailto:" + this.mailTo);
        desktop.mail(uri);

    }
}
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            if (tag.tagName.equals("colleagues")) {
                tagLabel.setStyle("-fx-background-color: red;");
            } else if (tag.tagName.equals("friends")) {
                tagLabel.setStyle("-fx-background-color: greenyellow;");
            } else {
                tagLabel.setStyle("-fx-background-color: grey;");

            }
            tags.getChildren().add(tagLabel);
        });
    }

```
###### /java/seedu/address/ui/PersonListPanel.java
``` java
    private void setEventHandlerForSelectionChangeEvent() {
        ObservableList<Integer> index = personListView.getSelectionModel().getSelectedIndices();
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue, index.get(0)));
                    }
                });
    }
```
###### /java/seedu/address/ui/PhoneTextField.java
``` java
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's phone number.
 */
public class PhoneTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "PhoneTextField.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private int selectedIndex;

    @FXML
    private TextField phoneTextField;

    public PhoneTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    public String getPhoneTextField() {
        return phoneTextField.getText();
    }

    public void setPhoneTextField(String text) {
        phoneTextField.setText(text);
    }
    public TextField getObject() {
        return phoneTextField;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setPhoneTextField(event.getNewSelection().person.getPhone().toString());
    }


}
```
###### /java/seedu/address/ui/QrButton.java
``` java
/**
 * The UI component that is for generating a QR code which calls a contact on a smartphone.
 */
public class QrButton extends UiPart<Region> {
    public static final String MESSAGE_FAIL = "Please select someone";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "QrButton.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private LoggingCommand loggingCommand;
    private BrowserPanel bp;
    private ReadOnlyPerson person;

    public QrButton(BrowserPanel bp) {
        super(FXML);
        loggingCommand = new LoggingCommand();
        this.bp = bp;
        registerAsAnEventHandler(this);
    }
    /**
     * Handles the QR button pressed event.
     */
    @FXML
    private void handleQrButtonPressed() throws CommandException, ParseException, IOException {
        if (person != null) {
            bp.loadQrCode(person);
            logger.info("QR Code displayed");
        } else {
            logger.info(MESSAGE_FAIL);
        }
    }
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.person = event.getNewSelection().person;
    }
    @Subscribe
    private void clickButton(QrEvent event) {
        bp.loadQrCode((ReadOnlyPerson) event.getPerson());
    }
}
```
###### /java/seedu/address/ui/QrSaveButton.java
``` java
/**
 * The UI component that is for generating a QR code which exports contacts on a smartphone.
 */
public class QrSaveButton extends UiPart<Region> {
    public static final String MESSAGE_FAIL = "Please select someone";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "QrSaveButton.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private LoggingCommand loggingCommand;
    private BrowserPanel bp;
    private ReadOnlyPerson person;

    public QrSaveButton(BrowserPanel bp) {
        super(FXML);
        loggingCommand = new LoggingCommand();
        this.bp = bp;
        registerAsAnEventHandler(this);
    }
    /**
     * Handles the QR button pressed event.
     */
    @FXML
    private void handleQrSaveButtonPressed() throws CommandException, ParseException, IOException {
        if (person != null) {
            bp.loadSaveQrCode(person);
            logger.info("QR Code displayed");
        } else {
            logger.info(MESSAGE_FAIL);
        }
    }
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.person = event.getNewSelection().person;
    }

    @Subscribe
    private void clickButton(QrSaveEvent event) {
        bp.loadSaveQrCode((ReadOnlyPerson) event.getPerson());
    }

}
```
###### /java/seedu/address/ui/QrSmsButton.java
``` java
/**
 * The UI component that is for generating a QR code which opens up an SMS client on a smartphone.
 */
public class QrSmsButton extends UiPart<Region> {
    public static final String MESSAGE_FAIL = "Please select someone";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "QrSmsButton.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private LoggingCommand loggingCommand;
    private BrowserPanel bp;
    private ReadOnlyPerson person;

    public QrSmsButton(BrowserPanel bp) {
        super(FXML);
        loggingCommand = new LoggingCommand();
        this.bp = bp;
        registerAsAnEventHandler(this);
    }
    /**
     * Handles the QR button pressed event.
     */
    @FXML
    private void handleQrSmsButtonPressed() throws CommandException, ParseException, IOException {
        if (person != null) {
            bp.loadSmsQrCode(person);
            logger.info("QR Code displayed");
        } else {
            logger.info(MESSAGE_FAIL);
        }
    }
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.person = event.getNewSelection().person;
    }

    @Subscribe
    private void clickButton(QrSmsEvent event) {
        bp.loadSmsQrCode((ReadOnlyPerson) event.getPerson());
    }

}

```
###### /java/seedu/address/ui/TagTextField.java
``` java
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's tags.
 */
public class TagTextField extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "TagTextField.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private TextArea tagTextArea;
    private Set<Tag> tagSet;

    public TagTextField() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    public String getTagTextArea() {
        return tagTextArea.getText();
    }

    public void setTagTextArea(String text) {
        tagTextArea.setText(text);
    }

    public void setTagSet(Set<Tag> t) {
        tagSet = t;
    }

    public Set<Tag> getTagSet() {
        return tagSet;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        StringBuilder currTags = new StringBuilder();
        tagSet = event.getNewSelection().person.getTags();
        setTagSet(event.getNewSelection().person.getTags());
        for (Tag t : tagSet) {
            if (currTags.length() != 0) {
                currTags.append(",");
            }
            currTags.append(t.tagName);
        }
        setTagTextArea(currTags.toString());
    }
    public TextArea getObject() {
        return tagTextArea;
    }


}

```
###### /resources/view/AddressTextField.fxml
``` fxml
<?import javafx.scene.control.TextField?>


<TextField fx:id="addressTextField" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="27.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/DeleteButton.fxml
``` fxml
<?import javafx.scene.control.Button?>


<Button fx:id="deleteButton" mnemonicParsing="false" onAction="#handleDeleteButtonPressed" text="Delete" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/EditButton.fxml
``` fxml
<?import javafx.scene.control.Button?>


<Button fx:id="editButton" mnemonicParsing="false" onAction="#handleEditButtonPressed" text="Save" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/EmailButton.fxml
``` fxml
<?import javafx.scene.control.Button?>


<Button fx:id="emailButton" mnemonicParsing="false" onAction="#handleEmailButtonPressed" text="Email" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/EmailTextField.fxml
``` fxml
<?import javafx.scene.control.TextField?>


<TextField fx:id="emailTextField" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="27.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/MainWindow.fxml
``` fxml
  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4352226720647773, 0.569838056680162">
    <VBox fx:id="personList" minWidth="340" prefWidth="340.0" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
         <AnchorPane minHeight="-Infinity" minWidth="-Infinity" prefHeight="103.0" prefWidth="369.0">
            <children>
               <HBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="100.0" prefWidth="368.0" AnchorPane.bottomAnchor="150.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="1.0" AnchorPane.topAnchor="20.0">
                  <children>
                     <StackPane fx:id="deleteButtonPlaceholder" alignment="TOP_CENTER" prefHeight="100.0" prefWidth="168.0" />
                     <StackPane fx:id="emailButtonPlaceholder" alignment="TOP_CENTER" prefHeight="100.0" prefWidth="167.0" />
                  </children>
               </HBox>
            </children>
         </AnchorPane>
    </VBox>
      <VBox prefHeight="667.0" prefWidth="113.0">
         <children>
            <StackPane fx:id="editNamePlaceholder" prefHeight="10.0" prefWidth="64.0">
               <children>
                  <Text fill="darkgrey" strokeType="OUTSIDE" strokeWidth="0.0" text="Name:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
                     <font>
                        <Font name="Helvetica" size="14.0" />
                     </font>
                     <StackPane.margin>
                        <Insets top="3.0" />
                     </StackPane.margin>
                  </Text>
               </children>
            </StackPane>
            <StackPane fx:id="editNameTextfieldPlaceholder" alignment="CENTER_LEFT" prefHeight="55.0" prefWidth="64.0" />
            <StackPane fx:id="editPhonePlaceholder" layoutX="10.0" layoutY="10.0" prefHeight="10.0" prefWidth="64.0">
               <children>
                  <Text fill="darkgrey" strokeType="OUTSIDE" strokeWidth="0.0" text="Phone:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
                     <font>
                        <Font name="Helvetica" size="14.0" />
                     </font>
                  </Text>
               </children>
            </StackPane>
            <StackPane fx:id="editPhoneTextfieldPlaceholder" alignment="CENTER_LEFT" layoutX="10.0" layoutY="45.0" prefHeight="55.0" prefWidth="64.0" />
            <StackPane fx:id="editEmailPlaceholder" layoutX="10.0" layoutY="139.0" prefHeight="10.0" prefWidth="64.0">
               <children>
                  <Text fill="darkgrey" strokeType="OUTSIDE" strokeWidth="0.0" text="Email:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
                     <font>
                        <Font name="Helvetica" size="14.0" />
                     </font>
                  </Text>
               </children>
            </StackPane>
            <StackPane fx:id="editEmailTextfieldPlaceholder" alignment="CENTER_LEFT" layoutX="10.0" layoutY="174.0" prefHeight="55.0" prefWidth="64.0" />
            <StackPane fx:id="editAddressPlaceholder" layoutX="10.0" layoutY="268.0" prefHeight="10.0" prefWidth="64.0">
               <children>
                  <Text fill="darkgrey" strokeType="OUTSIDE" strokeWidth="0.0" text="Address:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
                     <font>
                        <Font name="Helvetica" size="14.0" />
                     </font>
                  </Text>
               </children>
            </StackPane>
            <StackPane fx:id="editAddressTextfieldPlaceholder" alignment="CENTER_LEFT" layoutX="10.0" layoutY="303.0" prefHeight="55.0" prefWidth="64.0" />
            <StackPane fx:id="editTagPlaceholder" layoutX="10.0" layoutY="397.0" prefHeight="10.0" prefWidth="64.0">
               <children>
                  <Text fill="darkgrey" strokeType="OUTSIDE" strokeWidth="0.0" text="Tags:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
                     <font>
                        <Font name="Helvetica" size="14.0" />
                     </font>
                     <StackPane.margin>
                        <Insets bottom="5.0" />
                     </StackPane.margin>
                  </Text>
               </children>
            </StackPane>
            <StackPane fx:id="editTagTextfieldPlaceholder" alignment="CENTER_LEFT" layoutX="10.0" layoutY="432.0" prefHeight="115.0" prefWidth="64.0" />
            <StackPane fx:id="editButtonPlaceholder" prefHeight="32.0" prefWidth="64.0">
               <padding>
                  <Insets top="15.0" />
               </padding>
            </StackPane>
         </children>
      </VBox>
```
###### /resources/view/NameTextField.fxml
``` fxml
<?import javafx.scene.control.TextField?>


<TextField fx:id="nameTextField" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="27.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/PhoneTextField.fxml
``` fxml
<?import javafx.scene.control.TextField?>


<TextField fx:id="phoneTextField" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="27.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/QrButton.fxml
``` fxml

<?import javafx.scene.control.Button?>


<Button fx:id="qrButton" mnemonicParsing="false" onAction="#handleQrButtonPressed" text="Call" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/TagTextField.fxml
``` fxml
<?import javafx.scene.control.TextArea?>


<TextArea fx:id="tagTextArea" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="170.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
