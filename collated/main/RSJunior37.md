# RSJunior37
###### \java\seedu\address\commons\events\ui\InsurancePanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Person List Panel
 */
public class InsurancePanelSelectionChangedEvent extends BaseEvent {

    private final ReadOnlyInsurance insurance;

    public InsurancePanelSelectionChangedEvent(InsuranceCard newSelection) {
        insurance = newSelection.getInsurance();
    }

    public InsurancePanelSelectionChangedEvent(ReadOnlyInsurance insurance) {
        this.insurance = insurance;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyInsurance getInsurance() {
        return insurance;
    }
}
```
###### \java\seedu\address\commons\events\ui\PersonNameClickedEvent.java
``` java
/**
 * Represents a click on one of the names in Insurance Profile
 */
public class PersonNameClickedEvent extends BaseEvent {


    private final InsurancePerson target;

    public PersonNameClickedEvent(InsurancePerson target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getName() {
        return target.getName();
    }

```
###### \java\seedu\address\commons\events\ui\SwitchToInsurancePanelRequestEvent.java
``` java
/**
 * Request MainApp to middle panel to Insurance
 */
public class SwitchToInsurancePanelRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\SwitchToProfilePanelRequestEvent.java
``` java
/**
 * Request MainApp to switch middle panel to Profile
 */
public class SwitchToProfilePanelRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

```
###### \java\seedu\address\logic\commands\PartialFindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name starts with any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class PartialFindCommand extends Command {

    public static final String[] COMMAND_WORDS = {"pfind", "pf", "plook", "plookup"};
    public static final String COMMAND_WORD = "pfind";

    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Finds all persons whose names starts with "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Ali Bo Ch";

    private final NameStartsWithKeywordsPredicate predicate;

    public PartialFindCommand (NameStartsWithKeywordsPredicate predicate) {
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
                || (other instanceof PartialFindCommand // instanceof handles nulls
                && this.predicate.equals(((PartialFindCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\PartialFindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new PartialFindCommand object
 */
public class PartialFindCommandParser implements Parser<PartialFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PartialFindCommand
     * and returns an PartialFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PartialFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PartialFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new PartialFindCommand(new NameStartsWithKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public ObservableList<ReadOnlyInsurance> getInsuranceList() {
        return lifeInsuranceMap.asObservableList();
    }
```
###### \java\seedu\address\model\insurance\UniqueLifeInsuranceMap.java : Obsolete in final version
``` java
    /**
     * Accessor to insurance list
     * @return all existing insurances as ReadOnly, ObservableList
     */
    public ObservableList<ReadOnlyInsurance> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalMap.values());
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(internalMap.values()));
    }
```
###### \java\seedu\address\model\insurance\UniqueLifeInsuranceMap.java
``` java
    /**
     * Accessor to insurance list
     * @return all existing insurances as ReadOnly, ObservableList
     */
    public ObservableList<ReadOnlyInsurance> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalMap.values());
        return FXCollections.unmodifiableObservableList(internalList);
    }
```
###### \java\seedu\address\model\person\NameStartsWithKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches the start of any of the keywords given.
 */
public class NameStartsWithKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameStartsWithKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getName().fullName.toLowerCase().startsWith(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameStartsWithKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameStartsWithKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\ui\InsuranceCard.java
``` java

    public InsuranceCard(ReadOnlyInsurance insurance, int displayIndex) {
        super(FXML);
        this.insurance = insurance;
        index.setText(displayIndex + ".");
        enableNameToProfileLink(insurance);

        bindListeners(insurance);
        setPremiumLevel(insurance.getPremium().toDouble());
    }

    public ReadOnlyInsurance getInsurance() {
        return insurance;
    }


    /**
     * Listen for click event on person names to be displayed as profile
     * @param insurance
     */
    private void enableNameToProfileLink(ReadOnlyInsurance insurance) {
        owner.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getOwner())));
        insured.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getInsured())));
        beneficiary.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getBeneficiary())));
    }

    /**
     * Binds the individual UI elements to observe their respective {@code ReadOnlyInsurance} properties
     * so that they will be notified of any changes.
     * @param insurance
     */
    private void bindListeners(ReadOnlyInsurance insurance) {
        insuranceName.textProperty().bind(Bindings.convert(insurance.insuranceNameProperty()));
        owner.textProperty().bind(Bindings.convert(insurance.getOwner().nameProperty()));
        insured.textProperty().bind(Bindings.convert(insurance.getInsured().nameProperty()));
        beneficiary.textProperty().bind(Bindings.convert(insurance.getBeneficiary().nameProperty()));
        premium.textProperty().bind(Bindings.convert(insurance.premiumProperty()));
    }

```
###### \java\seedu\address\ui\InsuranceCard.java
``` java
    @Subscribe
    private void handleInsurancePanelSelectionChangedEvent(InsurancePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        insurance = event.getInsurance();
        enableNameToProfileLink(insurance);
        bindListeners(insurance);
        raise(new SwitchToInsurancePanelRequestEvent());
    }
```
###### \java\seedu\address\ui\InsuranceIdLabel.java
``` java
/**
 * To be used in ProfilePanel ListView, displaying list of associated insurance
 */
public class InsuranceIdLabel extends UiPart<Region> {

    private static final String FXML = "InsuranceIdLabel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());


    @FXML
    private Label insuranceId;

    public InsuranceIdLabel(ReadOnlyInsurance insurance) {
        super(FXML);
        insuranceId.textProperty().bind(Bindings.convert(insurance.insuranceNameProperty()));
        setPremiumLevel(insurance.getPremium().toDouble());
        insuranceId.setOnMouseClicked(e -> raise(new InsuranceClickedEvent(insurance)));
    }

```
###### \java\seedu\address\ui\InsuranceListPanel.java
``` java
    private void setEventHandlerForSelectionChangeEvent() {
        insuranceListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in insurance list panel changed to : '" + newValue + "'");
                        raise(new InsurancePanelSelectionChangedEvent(newValue));
                    }
                });
    }
```
###### \java\seedu\address\ui\InsuranceProfilePanel.java
``` java

    public InsuranceProfilePanel(ReadOnlyInsurance insurance) {
        super(FXML);
        this.insurance = insurance;

        initializeContractFile(insurance);
        enableNameToProfileLink(insurance);
        bindListeners(insurance);
        setPremiumLevel(insurance.getPremium().toDouble());
    }

    public ReadOnlyInsurance getInsurance() {
        return insurance;
    }

    /**
     * Listen for click event on person names to be displayed as profile
     * @param insurance
     */
    private void enableNameToProfileLink(ReadOnlyInsurance insurance) {
        owner.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getOwner())));
        insured.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getInsured())));
        beneficiary.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getBeneficiary())));
    }

    private void setAllToNull() {
        owner.setText(null);
        insured.setText(null);
        beneficiary.setText(null);
        contractName.setText(null);
        premium.setText(null);
        signingDate.setText(null);
        expiryDate.setText(null);
    }

    /**
     * Checks if pdf file exist in project, if not add click event on contract field to add file with filechooser
     * Then add click event on contract field to open up the file
     * @param insurance
     */
    private void initializeContractFile(ReadOnlyInsurance insurance) {
        insuranceFile =  new File(PDFFOLDERPATH + insurance.getContractFileName());
        if (isFileExists(insuranceFile)) {
            activateLinkToInsuranceFile();
        } else {
            contractName.getStyleClass().clear();
            contractName.getStyleClass().add("missing-file");
            contractName.setOnMouseClicked(event -> {
                FileChooser.ExtensionFilter extensionFilter =
                        new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().add(extensionFilter);
                File openedFile = chooser.showOpenDialog(null);

                if (isFileExists(openedFile)) {
                    try {
                        Files.copy(openedFile.toPath(), insuranceFile.toPath());
                        if (isFileExists(insuranceFile)) {
                            activateLinkToInsuranceFile();
                        }
                    } catch (IOException ex) {
                        logger.info("Unable to open file at path: " + openedFile.getAbsolutePath());
                    }
                }
            });
        }
    }

    /**
     *  Enable the link to open contract pdf file and adjusting the text hover highlight
     */
    private void activateLinkToInsuranceFile() {
        contractName.getStyleClass().clear();
        contractName.getStyleClass().add("valid-file");
        contractName.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().open(insuranceFile);
            } catch (IOException ee) {
                logger.info("File do not exist: " + PDFFOLDERPATH + insurance.getContractFileName());
            }
        });
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleSwitchToProfilePanelRequestEvent(SwitchToProfilePanelRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        middlePanelPlaceholder.getChildren().clear();
        middlePanelPlaceholder.getChildren().add(profilePanel.getRoot());
    }

    @Subscribe
    private void handleSwitchToInsurancePanelRequestEvent(SwitchToInsurancePanelRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        middlePanelPlaceholder.getChildren().clear();
        middlePanelPlaceholder.getChildren().add(insuranceProfilePanel.getRoot());
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Generate random colour with slight dark tint
     * and return it as hexadecimal String
     */
    private String generateRandomColor() {
        // Factor to divide down the random color to better contrast white text
        final double darkColorBase = 1.2;

        final int red = (int) Math.round((random.nextInt(256)) / darkColorBase);
        final int green = (int) Math.round((random.nextInt(256)) / darkColorBase);
        final int blue = (int) Math.round((random.nextInt(256)) / darkColorBase);

        // Convert RBG to Hex String
        return String.format("#%02x%02x%02x", red, blue, green);
    }

    /**
     * Store initialized tag into HashMap to remember the colour already given
     * so that subsequent occurence will use the same colour
     */
    private String getTagColor(String tagName) {
        if (!tagToColor.containsKey(tagName)) {
            tagToColor.put(tagName, generateRandomColor());
        }
        return tagToColor.get(tagName);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Retrieve all tags from a person and initialize them
     * with a unique tag colour
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label uniqueTagLabel = new Label(tag.tagName);
            uniqueTagLabel.setStyle("-fx-background-color: " + getTagColor(tag.tagName));
            tags.getChildren().add(uniqueTagLabel);
        });
    }
```
###### \java\seedu\address\ui\ProfilePanel.java
``` java
/**
 * The Profile Panel of the App.
 */
public class ProfilePanel extends UiPart<Region> {

    public static final String DEFAULT_MESSAGE = "Welcome to LISA!";
    public static final String INSURANCE_LIST_HEADER = "List of Insurance Contracts Involved: ";
    public static final String NO_INSURANCE_MESSAGE = "This person is not related to any Insurance Contracts";
    public static final String PERSON_DOES_NOT_EXIST_IN_LISA_MESSAGE = "This person does not exist in Lisa.";

    private static final String FXML = "ProfilePanel.fxml";

    public final ObservableList<InsuranceIdLabel> insurance = FXCollections.observableArrayList();

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane profilePanel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
```
###### \java\seedu\address\ui\SearchBox.java
``` java
/**
 * * The UI component that is responsible for real-time partial searching of contact name
 */
public class SearchBox extends UiPart<Region> {

    private static final String ERROR_STYLE_CLASS = "error";

    private static final Logger logger = LogsCenter.getLogger(SearchBox.class);
    private static final String FXML = "SearchBox.fxml";

    @FXML
    private TextField searchTextField;

    public SearchBox (Logic logic) {
        super(FXML);
        // track all changes in the searchTextField and call for partial search real-time.
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // show list of all contact when this textfield is empty
            if (newValue.equals("")) {
                try {
                    CommandResult commandResult = logic.execute("list");
                    logger.info("Result: " + commandResult.feedbackToUser);
                    raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));

                } catch (CommandException | ParseException e) {
                    // handle command failure
                    setStyleToIndicateCommandFailure();
                    logger.info("Invalid command: list");
                    raise(new NewResultAvailableEvent(e.getMessage(), true));
                }
            } else {
                try {
                    CommandResult commandResult = logic.execute("pfind " + newValue);
                    logger.info("Result: " + commandResult.feedbackToUser);
                    raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));

                } catch (CommandException | ParseException e) {
                    // handle command failure
                    setStyleToIndicateCommandFailure();
                    logger.info("Invalid command: pfind " + newValue);
                    raise(new NewResultAvailableEvent(e.getMessage(), true));
                }
            }

        });
    }

    /**
     * Sets the search box style to use the default style.
     */
    private void setStyleToDefault() {
        searchTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the search box style to indicate a failed command.
     */

    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = searchTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
```
###### \resources\view\DarkTheme.css
``` css
.particular-field {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.particular-link {
    -fx-font-size: 13pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

```
###### \resources\view\DarkTheme.css
``` css
.missing-file:hover {
    -fx-text-fill: #696969
}
```
###### \resources\view\DarkTheme.css
``` css
.list-cell:filled #insuranceCardPane .particular-person:hover {
    -fx-font-size: 13pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #d06651;
    -fx-opacity: 1;
}
```
###### \resources\view\InsuranceIdLabel.fxml
``` fxml
<Label fx:id="insuranceId" text="\$id" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
###### \resources\view\MainWindow.fxml
``` fxml
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
         <StackPane fx:id="searchBoxPlaceholder" prefHeight="0.0" prefWidth="320.0" />
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
    </VBox>
    <StackPane fx:id="middlePanelPlaceholder" prefWidth="101.0">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
    </StackPane>
      <VBox fx:id="insuranceList" minWidth="300" prefWidth="300" SplitPane.resizableWithParent="false">
         <padding>
            <Insets bottom="10" left="10" right="10" top="10" />
         </padding>
         <children>
            <StackPane fx:id="insuranceListPanelPlaceholder" VBox.vgrow="ALWAYS" />
         </children>
      </VBox>
```
###### \resources\view\ProfilePanel.fxml
``` fxml
<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <ScrollPane fx:id="scrollPane" prefHeight="600.0" prefWidth="800.0" styleClass="anchor-pane">
            <content>
                <AnchorPane fx:id="profilePanel" prefHeight="1000.0" prefWidth="1200" styleClass="anchor-pane">
                    <children>
                        <Label fx:id="name" layoutX="2.0" styleClass="profile-header" text="\$name" />
                        <VBox layoutX="14.0" layoutY="72.0" spacing="3.0">
                            <Label fx:id="phone" styleClass="profile-field" text="\$phone" />
                            <Label fx:id="address" styleClass="profile-field" text="\$address" />
                            <Label fx:id="dob" styleClass="profile-field" text="\$dob" />
                            <Label fx:id="gender" styleClass="profile-field" text="\$gender" />
                            <Label fx:id="email" styleClass="profile-field" text="\$email" />
                            <Label fx:id="insuranceHeader" styleClass="profile-field" text="\$insuranceHeader" />
                     <ListView fx:id="insuranceListView" prefHeight="200.0" prefWidth="200.0">
                        <VBox.margin>
                           <Insets />
                        </VBox.margin>
                        <padding>
                           <Insets top="20.0" />
                        </padding></ListView>
                        </VBox>
                    </children>
                </AnchorPane>
            </content>
        </ScrollPane>
    </children>
</StackPane>
```
###### \resources\view\SearchBox.fxml
``` fxml
<StackPane styleClass="anchor-pane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <TextField fx:id="searchTextField"  promptText="Search contact here..."/>
</StackPane>

```
