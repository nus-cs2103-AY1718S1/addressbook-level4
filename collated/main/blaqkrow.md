# blaqkrow
###### /resources/view/MainWindow.fxml
``` fxml
  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4352226720647773, 0.569838056680162" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList" minWidth="340" prefWidth="340.0" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
         <HBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="100.0" prefWidth="323.0">
            <children>
               <StackPane fx:id="deleteButtonPlaceholder" alignment="TOP_CENTER" prefHeight="100.0" prefWidth="168.0" />
               <StackPane fx:id="emailButtonPlaceholder" alignment="TOP_CENTER" prefHeight="100.0" prefWidth="167.0" />
            </children>
         </HBox>
    </VBox>
      <VBox prefHeight="667.0" prefWidth="113.0">
         <children>
            <StackPane fx:id="editNamePlaceholder" prefHeight="10.0" prefWidth="64.0">
               <children>
                  <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Name:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
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
                  <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Phone:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
                     <font>
                        <Font name="Helvetica" size="14.0" />
                     </font>
                  </Text>
               </children>
            </StackPane>
            <StackPane fx:id="editPhoneTextfieldPlaceholder" alignment="CENTER_LEFT" layoutX="10.0" layoutY="45.0" prefHeight="55.0" prefWidth="64.0" />
            <StackPane fx:id="editEmailPlaceholder" layoutX="10.0" layoutY="139.0" prefHeight="10.0" prefWidth="64.0">
               <children>
                  <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Email:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
                     <font>
                        <Font name="Helvetica" size="14.0" />
                     </font>
                  </Text>
               </children>
            </StackPane>
            <StackPane fx:id="editEmailTextfieldPlaceholder" alignment="CENTER_LEFT" layoutX="10.0" layoutY="174.0" prefHeight="55.0" prefWidth="64.0" />
            <StackPane fx:id="editAddressPlaceholder" layoutX="10.0" layoutY="268.0" prefHeight="10.0" prefWidth="64.0">
               <children>
                  <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Address:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
                     <font>
                        <Font name="Helvetica" size="14.0" />
                     </font>
                  </Text>
               </children>
            </StackPane>
            <StackPane fx:id="editAddressTextfieldPlaceholder" alignment="CENTER_LEFT" layoutX="10.0" layoutY="303.0" prefHeight="55.0" prefWidth="64.0" />
            <StackPane fx:id="editTagPlaceholder" layoutX="10.0" layoutY="397.0" prefHeight="10.0" prefWidth="64.0">
               <children>
                  <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Tags:" wrappingWidth="56.21875" StackPane.alignment="TOP_LEFT">
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
               </padding></StackPane>
         </children></VBox>
```
###### /resources/view/EmailButton.fxml
``` fxml
<?import javafx.scene.control.Button?>


<Button fx:id="emailButton" mnemonicParsing="false" onAction="#handleEmailButtonPressed" text="Email" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/TagTextField.fxml
``` fxml
<?import javafx.scene.control.TextArea?>


<TextArea fx:id="tagTextArea" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="170.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/NameTextField.fxml
``` fxml
<?import javafx.scene.control.TextField?>


<TextField fx:id="nameTextField" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="27.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/EmailTextField.fxml
``` fxml
<?import javafx.scene.control.TextField?>


<TextField fx:id="emailTextField" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="27.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/EditButton.fxml
``` fxml
<?import javafx.scene.control.Button?>


<Button fx:id="editButton" mnemonicParsing="false" onAction="#handleEditButtonPressed" text="Save" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/PhoneTextField.fxml
``` fxml
<?import javafx.scene.control.TextField?>


<TextField fx:id="phoneTextField" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="27.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/DeleteButton.fxml
``` fxml
<?import javafx.scene.control.Button?>


<Button fx:id="deleteButton" mnemonicParsing="false" onAction="#handleDeleteButtonPressed" text="Delete" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### /resources/view/AddressTextField.fxml
``` fxml
<?import javafx.scene.control.TextField?>


<TextField fx:id="addressTextField" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="27.0" prefWidth="210.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
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
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    public static final String DEFAULT_PAGE = "default.html";
    //public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/place/";
    //public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    private void loadPersonPage(ReadOnlyPerson person) {
        browser.getEngine().setUserAgent("Mozilla/5.0 "
                + "(Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0");
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+"));
    }
```
###### /java/seedu/address/ui/EditButton.java
``` java
/**
 * The UI component that is responsible for editing selected contacts in the PersonListPanel.
 */
public class EditButton extends UiPart<Region> {
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
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleEditButtonPressed() throws CommandException, ParseException, IOException {
        StringBuilder command = new StringBuilder();
        command.append("edit " + getSelectedIndex() + " n/"
                + nameTextField.getNameTextField() + " p/" + phoneTextField.getPhoneTextField() + " e/"
                + emailTextField.getEmailTextField()
                + " a/" + addressTextFieldTextField.getAddressTextField());
        String tagTextArea = tagTextField.getTagTextArea();
        String[] tagSplit = tagTextArea.split(",");
        for ( String s : tagSplit) {
            command.append(" t/" + s.trim());
        }
        CommandResult commandResult = logic.execute(command.toString());
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
###### /java/seedu/address/ui/PhoneTextField.java
``` java
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's name.
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

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setPhoneTextField(event.getNewSelection().person.getPhone().toString());
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
###### /java/seedu/address/ui/AddressTextField.java
``` java
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's name.
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
###### /java/seedu/address/ui/TagTextField.java
``` java
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's name.
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

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setNameTextField(event.getNewSelection().person.getName().toString());
    }
}
```
###### /java/seedu/address/ui/EmailTextField.java
``` java
/**
 * The UI component that is responsible for saving and displaying the currently selected contact's name.
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
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

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
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        this.selectedEmail = event.getNewSelection().person.emailProperty().getValue().toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }



}
```
###### /java/seedu/address/commons/events/ui/PersonPanelSelectionChangedEvent.java
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
###### /java/seedu/address/logic/parser/EmailCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
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
###### /java/seedu/address/logic/commands/EmailCommand.java
``` java
/**
 * The UI component that is responsible for emailing the selected person.
 */
public class EmailCommand extends Command {
    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";
    public static final String MESSAGE_SUCCESS = "Email opened!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Emails the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index emailIndex;
    public EmailCommand(Index emailIndex) {
        this.emailIndex = emailIndex;
    }
    public void openEmail() {}
    @Override
    public CommandResult execute() throws CommandException, IOException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (emailIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEmail = lastShownList.get(emailIndex.getZeroBased());

        OpenEmailClient emailClient = new OpenEmailClient(personToEmail.getEmail().toString());
        emailClient.sendMail();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
