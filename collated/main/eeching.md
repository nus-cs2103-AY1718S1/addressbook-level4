# eeching
###### \java\seedu\address\logic\commands\PhoneCommand.java
``` java
/**
 * Adds or updates a custom field of a person identified using it's last displayed index from the address book.
 */
public class PhoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "updatePhone";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates one person's additional phone identified by the index"
            + " number used in the last person listing or the name of the person.\n"
            + "if index is used to identify the person\n"
            + "Parameters: \n"
            + "INDEX (must be a positive integer) "
            + "ACTION (either add or remove) "
            + "PHONE (must be at least 3 positive digits.)\n"
            + "Example: " + COMMAND_WORD + " 1" + " add" + " 6583609887\n"
            + "if name is used to identify the person\n"
            + "Parameters: \n"
            + "byName "
            + "ACTION "
            + "PHONE "
            + "NAME (must be the full name saved in the Contact Book)\n"
            + "Example: " + COMMAND_WORD + " byName" + " add" + " 6583609887 " + "Alex Yeoh";
    public static final String PERSON_NOT_FOUND_EXCEPTION_MESSAGE = "The target person cannot be missing.\n";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.\n";
    public static final String PHONE_NOT_FOUND_EXCEPTION_MESSAGE = "Phone number to be removed is not found in"
            + " the list.\n";
    public static final String DUPLICATE_PHONE_EXCEPTION_MESSAGE = "Phone number to be added already exists in"
            + " the list.\n";
    public static final String INVALID_COMMAND_MESSAGE = "Command is invalid, please check again.\n";
    public static final String PRIMARY_PHONE_MESSAGE = "The primary phone number is %s.\n";
    public static final String ADD_PHONE_SUCCESS_MESSAGE = "Phone number %s has been added.\n";
    public static final String REMOVE_PHONE_SUCCESS_MESSAGE = "Phone number %s has been removed.\n";
    public static final String TOTAL_NUMBER_OF_PHONES = "The updated phone list now has %s phone numbers.\n";
    public static final String UNSPECIFIED_NAME = "unspecified";

    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_REMOVE = "remove";

    private final Logger logger = LogsCenter.getLogger(PhoneCommand.class);

    private final Index targetIndex;

    private final Phone phone;

    private final String action;

    private final String name;


    public PhoneCommand(Index targetIndex, String action,  Phone phone) {
        this.targetIndex = targetIndex;
        this.action = action;
        this.phone = phone;
        this.name = UNSPECIFIED_NAME;
    }

    public PhoneCommand(String name, String action, Phone phone) {
        this.name = name;
        this.action = action;
        this.phone = phone;
        this.targetIndex = new Index(999999999); //indicating invalid index
    }

    /**
     * Adds or Updates a Person's phoneNumber list
     */
    private Person updatePersonPhoneList(ReadOnlyPerson personToUpdatePhoneList, String action, Phone phone)
            throws PhoneNotFoundException, DuplicatePhoneException {
        Name name = personToUpdatePhoneList.getName();
        Phone primaryPhone = personToUpdatePhoneList.getPhone();
        UniquePhoneList uniquePhoneList = personToUpdatePhoneList.getPhoneList();
        Email email = personToUpdatePhoneList.getEmail();
        Address address = personToUpdatePhoneList.getAddress();
        Photo photo = personToUpdatePhoneList.getPhoto();
        Set<Tag> tags = personToUpdatePhoneList.getTags();
        UniqueCustomFieldList customFields = personToUpdatePhoneList.getCustomFieldList();

        if (action.equals(COMMAND_REMOVE)) {
            uniquePhoneList.remove(phone);
        } else if (action.equals(COMMAND_ADD)) {
            if (!phone.equals(primaryPhone)) {
                uniquePhoneList.add(phone);
            } else {
                throw new DuplicatePhoneException();
            }
        }

        Person personUpdated = new Person(name, primaryPhone, email, address,
                photo, uniquePhoneList, tags, customFields.toSet());

        return personUpdated;
    }

    public Index getIndex() {
        return targetIndex;
    }

    public String getAction() {
        return action;
    }

    public Phone getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneCommand // instanceof handles nulls
                && (this.targetIndex.equals(((PhoneCommand) other).getIndex())
                || this.name.equals(((PhoneCommand) other).getName()))
                && this.action.equals(((PhoneCommand) other).getAction())
                && this.phone.equals(((PhoneCommand) other).getPhone()));
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson personToUpdatePhoneList = null;

        logger.info("Get the person of the specified index.");
        if (name.equals(UNSPECIFIED_NAME)) {

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                logger.warning(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            personToUpdatePhoneList = lastShownList.get(targetIndex.getZeroBased());
        } else {

            for (int i = 0; i < lastShownList.size(); i++) {
                if (lastShownList.get(i).getName().toString().equals(name)) {
                    personToUpdatePhoneList = lastShownList.get(i);
                    break;
                }
            }
            if (personToUpdatePhoneList == null) {
                throw new CommandException(Messages.MESSAGE_UNFOUND_PERSON_NAME);
            }

        }

        try {
            Person personUpdated = updatePersonPhoneList(personToUpdatePhoneList, action, phone);
            UniquePhoneList uniquePhoneList = personUpdated.getPhoneList();
            Phone primaryPhone = personUpdated.getPhone();
            try {
                model.updatePerson(personToUpdatePhoneList, personUpdated);
            } catch (DuplicatePersonException dpe) {
                logger.warning("Invalid person " + MESSAGE_DUPLICATE_PERSON);
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                logger.warning("Invalid person " + PERSON_NOT_FOUND_EXCEPTION_MESSAGE);
                throw new CommandException(PERSON_NOT_FOUND_EXCEPTION_MESSAGE);
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            logger.info("Execute update phone command");
            CommandResult commandResult;
            switch (action) {

            case COMMAND_ADD:
                String successAdditionMessage = String.format(ADD_PHONE_SUCCESS_MESSAGE, phone.number);
                String infoAddition = String.format(TOTAL_NUMBER_OF_PHONES, uniquePhoneList.getSize() + 1)
                        + String.format(PRIMARY_PHONE_MESSAGE, primaryPhone);
                commandResult = new CommandResult(successAdditionMessage + infoAddition);
                break;
            case COMMAND_REMOVE:
                String successRemovalMessage = String.format(REMOVE_PHONE_SUCCESS_MESSAGE, phone.number);
                String infoRemoval = String.format(TOTAL_NUMBER_OF_PHONES, uniquePhoneList.getSize() + 1)
                        + String.format(PRIMARY_PHONE_MESSAGE, primaryPhone);
                commandResult = new CommandResult(successRemovalMessage + infoRemoval);
                break;
            default :
                commandResult = new CommandResult(INVALID_COMMAND_MESSAGE);
            }
            logger.info("Result: " + commandResult.feedbackToUser);
            return commandResult;
        } catch (PhoneNotFoundException e) {
            logger.warning(PHONE_NOT_FOUND_EXCEPTION_MESSAGE);
            return new CommandResult(PHONE_NOT_FOUND_EXCEPTION_MESSAGE);
        } catch (DuplicatePhoneException e) {
            logger.warning(DUPLICATE_PHONE_EXCEPTION_MESSAGE);
            return new CommandResult(DUPLICATE_PHONE_EXCEPTION_MESSAGE);
        }
    }
}
```
###### \java\seedu\address\logic\parser\PhoneCommandParser.java
``` java
public class PhoneCommandParser implements Parser<PhoneCommand> {

    private static final String BY_NAME_IDENTIFIER = "byName";
    /**
     * Parses the given {@code String} of arguments in the context of the PhoneCommand
     * and returns a PhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public PhoneCommand parse(String args) throws ParseException {

        try {
            StringTokenizer st = new StringTokenizer(args);
            String personIdentifier = st.nextToken();
            String action = st.nextToken();
            String value = st.nextToken();
            Phone phone = new Phone(value);


            //if the index of the target is passed to identify the person
            try {
                Integer.parseInt(personIdentifier);
                Index index = ParserUtil.parseIndex(personIdentifier);
                return new PhoneCommand(index, action, phone);
            } catch (Exception e) { //name is passed to identify the person
                if (personIdentifier.equals(BY_NAME_IDENTIFIER)) {
                    String rawName = "";
                    while (st.hasMoreTokens()) {
                        rawName = rawName + " " + st.nextToken();
                    }
                    try {
                        String name = ParserUtil.parseNameString(rawName.trim());
                        return new PhoneCommand(name, action, phone);
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
                    }
                } else {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
                }
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
        } catch (NoSuchElementException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\person\exceptions\DuplicatePhoneException.java
``` java
/**
 * Signals that the operation will result in duplicate Phone objects.
 */
public class DuplicatePhoneException extends DuplicateDataException {
    public DuplicatePhoneException() {
        super("Operation would result in duplicate phones");
    }
}
```
###### \java\seedu\address\model\person\exceptions\PhoneNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified phone.
 */
public class PhoneNotFoundException extends Exception {}
```
###### \java\seedu\address\model\person\phone\UniquePhoneList.java
``` java
/**
 * A list of phones that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Phone#equals(Object)
 */
public class UniquePhoneList implements Iterable<Phone> {

    private final ObservableList<Phone> internalList = FXCollections.observableArrayList();

    /**
     * Constructs phoneList with a number.
     */

    public UniquePhoneList() {}

    public UniquePhoneList(Phone phone) {

        requireNonNull(phone);
        internalList.add(phone);
    }

    /**
     * Returns all phones in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Phone> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns true if the list contains an equivalent phone as the given argument.
     */
    public boolean contains(Phone toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePhoneException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Phone toAdd) throws DuplicatePhoneException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePhoneException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PhoneNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Phone toRemove) throws PhoneNotFoundException {
        requireNonNull(toRemove);
        final boolean phoneFoundAndDeleted = internalList.remove(toRemove);
        if (!phoneFoundAndDeleted) {
            throw new PhoneNotFoundException();
        }
        return phoneFoundAndDeleted;
    }


    public int getSize() {
        return internalList.size();
    }



    public String getAllPhone() {

        if (internalList.size() > 1) {
            String rest = "The additional phone number(s) are/is \n";
            int index = 1;
            for (Phone phone: internalList) {
                rest = rest + index + "/ " + phone.number + "\n";
                index++;
            }
            return rest;
        } else {
            return "";
        }
    }


    @Override
    public Iterator<Phone> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Phone> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.person.phone.UniquePhoneList // instanceof handles nulls
                && this.internalList.equals(((seedu.address.model.person.phone.UniquePhoneList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFilteredPersonList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    public StatusBarFooter(int totalPersons) throws JAXBException, IOException {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setTotalPersons(totalPersons);
        setSaveWeather(getWeather());
        registerAsAnEventHandler(this);
    }

    /**
     * Set the total number of person in the current address Book
     */
    private void setTotalPersons(int totalPersons) {
        this.totalPersons.setText(totalPersons + " person(s) in total");
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    private void setSaveWeather(String weather) {
        Platform.runLater(() -> this.weatherReport.setText(weather));
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    private String getWeather() throws JAXBException {
        try {
            WeatherRequest request = new WeatherRequest();
            return request.getSgWeather();
        } catch (IOException e) {
            logger.warning("no internet connection");
            return NO_INTERNET_CONNECTION_WARNING;
        }

    }
```
###### \java\seedu\address\ui\WeatherRequest.java
``` java
public class WeatherRequest {

    private static final String WHERE_ON_EARTH_IDENTIFIER = "1062617"; //this is Yahoo's woeid for Singapore
    private static final int WEATHER_INDEX_FROM_CHANNEL = 0;
    private static final int TEMPERATURE_INDEX_FROM_CHANNEL = 2;
    private static final String LOCATION_INFORMATION = "Singapore GMT +0800";
    private static final String DEGREE_CELSIUS = "â„?, ";



    public String getSgWeather() throws JAXBException, IOException {

        YahooWeatherService service = new YahooWeatherService();
        Channel channel = service.getForecast(WHERE_ON_EARTH_IDENTIFIER, DegreeUnit.CELSIUS);

        return weatherParser(channel.getItem().getCondition().toString());
    }

    /**
     * Extract the weather information from channel
     */
    private String weatherParser (String rawString) {
        ArrayList<String> information = new ArrayList<>();
        String infoString = rawString.substring(10, rawString.length() - 1);


        for (String piece : infoString.split(",")) {
            information.add(piece);
        }

        String weather = formatInfo(information.get(WEATHER_INDEX_FROM_CHANNEL)) + ", ";
        String temperature = formatInfo(information.get(TEMPERATURE_INDEX_FROM_CHANNEL)) + DEGREE_CELSIUS;
        String location = LOCATION_INFORMATION;

        StringBuilder builder = new StringBuilder();

        builder.append(weather)
               .append(temperature)
               .append(location);

        return builder.toString();

    }

    private String formatInfo (String rawInfo) {
        return Arrays.stream(rawInfo.split("="))
                .reduce((first, second) -> second).get();

    }
}
```
###### \resources\view\MainWindow.fxml
``` fxml

<VBox fx:id="topContainer" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
  <stylesheets>
    <URL value="@DarkTheme.css" />
    <URL value="@Extensions.css" />
  </stylesheets>
    <SplitPane VBox.vgrow="ALWAYS">

<AnchorPane fx:id="topBar" styleClass="menu-bar-container">
    <MenuBar fx:id="menuBar" styleClass="menu-bar" VBox.vgrow="NEVER">
        <Menu mnemonicParsing="false" text="File">
            <MenuItem mnemonicParsing="false" onAction="#handleExit" styleClass="menu-bar-item" text="Exit" />
        </Menu>
        <Menu mnemonicParsing="false" text="Help">
           <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" styleClass="menu-bar-item" text="Help" />
        </Menu>
    </MenuBar>
</AnchorPane>
    </SplitPane>


   <SplitPane dividerPositions="0.7698529411764706" VBox.vgrow="NEVER">
        <StackPane fx:id="commandBoxPlaceholder" prefHeight="35.0" prefWidth="744.0" styleClass="pane-with-border">
          <padding>
            <Insets bottom="5" left="10" right="10" top="5" />
          </padding>
        </StackPane>
         <StackPane fx:id="searchBoxPlaceholder" prefHeight="35.0" prefWidth="608.0" styleClass="pane-with-border">
             <padding>
                 <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
             </padding>
         </StackPane>
   </SplitPane>

  <StackPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100" prefHeight="100" styleClass="pane-with-border" VBox.vgrow="NEVER">
      <padding>
          <Insets bottom="5" left="10" right="10" top="5" />
      </padding>
  </StackPane>

  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.5" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList" minWidth="340" prefWidth="340" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS">
            <VBox.margin>
               <Insets />
            </VBox.margin></StackPane>
    </VBox>
      <GridPane minWidth="1000" prefHeight="650">
      <StackPane fx:id="personInformationPanelPlaceholder" GridPane.columnIndex="1" GridPane.rowIndex="0" />
         <columnConstraints>
            <ColumnConstraints />
            <ColumnConstraints />
         </columnConstraints>
         <rowConstraints>
            <RowConstraints />
         </rowConstraints>

      </GridPane>
<!-- Commented out to remove browser panel
    <StackPane fx:id="browserPlaceholder" prefWidth="340">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
    </StackPane>-->
  </SplitPane>

    <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />

   <stylesheets>
      <URL value="@DarkTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
</VBox>
```
###### \resources\view\StatusBarFooter.fxml
``` fxml
<GridPane styleClass="grid-pane" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <columnConstraints>
        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="300.0" />
        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="400.0" />
        <ColumnConstraints />
    </columnConstraints>
    <StatusBar fx:id="syncStatus" styleClass="anchor-pane" />
    <StatusBar fx:id="totalPersons" styleClass="anchor-pane" GridPane.columnIndex="1" />
    <StatusBar fx:id="weatherReport" nodeOrientation="RIGHT_TO_LEFT" styleClass="anchor-pane" GridPane.columnIndex="2" />
    <rowConstraints>
        <RowConstraints />
    </rowConstraints>
</GridPane>
```
