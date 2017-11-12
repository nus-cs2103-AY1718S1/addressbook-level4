# Choony93
###### \java\seedu\address\commons\events\model\AddressBookImportEvent.java
``` java

/**
 * Indicates the AddressBook calling for import
 */
public class AddressBookImportEvent extends BaseEvent {

    public final String filePath;
    public final ReadOnlyAddressBook importedBook;

    public AddressBookImportEvent(String filePath, ReadOnlyAddressBook importedBook) {
        this.filePath = filePath;
        this.importedBook = importedBook;
    }

    @Override
    public String toString() {
        return "New Addressbook imported from file: " + filePath;
    }
}
```
###### \java\seedu\address\commons\events\ui\ChangeThemeEvent.java
``` java
/**
 * Indicates a request to change theme to targeted index
 */
public class ChangeThemeEvent extends BaseEvent {

    public final int targetIndex;

    public ChangeThemeEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\DisplayGmapEvent.java
``` java

/**
 * Indicates a request to display Google Map of targeted index
 */
public class DisplayGmapEvent extends BaseEvent {

    public final int targetIndex;

    public DisplayGmapEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\PersonPanelDeleteEvent.java
``` java

/**
 * Indicates a request to delete person of targeted index from UI menu
 */
public class PersonPanelDeleteEvent extends BaseEvent {

    public final int targetIndex;

    public PersonPanelDeleteEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getOneBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\GmapCommand.java
``` java

/**
 * Display google map of person identified using it's last displayed index or name from the address book.
 */
public class GmapCommand extends Command {

    public static final String COMMAND_WORD = "gmap";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Display google map data of person identified by the index number used in the last person listing.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1\n"
        + "OR\n"
        + ": Display google map data of person identified by the name used in the person listing.\n"
        + "Parameters: NAME \n"
        + "Example: " + COMMAND_WORD + " Bernice";

    private static final String MESSAGE_GMAP_PERSON_SUCCESS = "Displayed Google map of Person: %1$s";

    private final boolean usingIndex;
    private Index targetIndex = Index.fromOneBased(Integer.parseInt("1"));
    private NameConsistsKeywordsPredicate predicate = new NameConsistsKeywordsPredicate(new ArrayList<>());

    public GmapCommand(Index targetIndex) {
        this.usingIndex = true;
        this.targetIndex = targetIndex;
    }

    public GmapCommand(NameConsistsKeywordsPredicate predicate) {
        this.usingIndex = false;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (!this.usingIndex) {
            lastShownList = model.getPersonListByPredicate(predicate);
            try {
                this.targetIndex = ParserUtil.parseIndex("1");
            } catch (IllegalValueException ive) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
            }
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new DisplayGmapEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_GMAP_PERSON_SUCCESS,
            lastShownList.get(targetIndex.getZeroBased()).getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof GmapCommand // instanceof handles nulls
            && this.targetIndex.equals(((GmapCommand) other).targetIndex)) // state check
            || (other instanceof GmapCommand // instanceof handles nulls
            && this.predicate.equals(((GmapCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java

/**
 * Imports addressbook based on given file path
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Imports XML file into addressbook from given filepath.\n"
        + "Parameters: FILEPATH (must be absolute)\n"
        + "Example: " + COMMAND_WORD + " FILEPATH";

    public static final String MESSAGE_IMPORT_SUCCESS = "Addressbook successfully imported from: %1$s";
    public static final String MESSAGE_INVALID_IMPORT_FILE_ERROR = "Problem reading file: %1$s";
    public static final String MESSAGE_INVALID_XML_FORMAT_ERROR = "XML syntax not well-formed: %1$s";

    private final String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        ReadOnlyAddressBook addressBookOptional;
        try {
            addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        } catch (DataConversionException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_XML_FORMAT_ERROR, filePath));
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_IMPORT_FILE_ERROR, filePath));
        }

        EventsCenter.getInstance().post(new AddressBookImportEvent(filePath, addressBookOptional));
        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, this.filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ImportCommand // instanceof handles nulls
            && this.filePath.equals(((ImportCommand) other).filePath)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java
/**
 * Set theme of current addressbook based on index listed or theme name.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Changes current theme based on index.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1\n"
        + "OR\n"
        + ": Changes current theme based on theme name.\n"
        + "Parameters: NAME \n"
        + "Example: " + COMMAND_WORD + " modena";

    public static final String MESSAGE_THEME_CHANGE_SUCCESS = "Theme successfully changed to: %1$s";
    public static final String MESSAGE_THEME_INVALID_NAME = "Could not find theme Name or Index.\n"
        + "Available themes:\n%1$s";

    private final boolean usingName;
    private String themeName = "";
    private Index targetIndex = Index.fromOneBased(Integer.parseInt("1"));

    public ThemeCommand(Index targetIndex) {
        this.usingName = false;
        this.targetIndex = targetIndex;
    }

    public ThemeCommand(String themeName) {
        this.usingName = true;
        this.themeName = themeName;
    }

    @Override
    public CommandResult execute() throws CommandException {

        boolean themeNameNotFound = false;
        if (this.usingName) {
            themeNameNotFound = true;
            for (int i = 0; i < UiPart.THEME_LIST_DIR.size(); i++) {
                String tempName = UiPart.getThemeNameByIndex(i);
                if (tempName.contains(")")) {
                    tempName = tempName.substring(tempName.lastIndexOf(")") + 2);
                }
                if (tempName.equalsIgnoreCase(this.themeName)) {
                    this.targetIndex = Index.fromOneBased(i + 1);
                    themeNameNotFound = false;
                }
            }
        }

        if (targetIndex.getZeroBased() >= UiPart.THEME_LIST_DIR.size() || themeNameNotFound) {
            String themeList = "";
            for (int i = 0; i < UiPart.THEME_LIST_DIR.size(); i++) {
                themeList += (Integer.toString(i + 1) + ". " + UiPart.getThemeNameByIndex(i) + "\n");
            }
            throw new CommandException(String.format(MESSAGE_THEME_INVALID_NAME, themeList));
        }

        EventsCenter.getInstance().post(new ChangeThemeEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_THEME_CHANGE_SUCCESS,
            UiPart.getThemeNameByIndex(this.targetIndex.getZeroBased())));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ThemeCommand // instanceof handles nulls
            && this.targetIndex.equals(((ThemeCommand) other).targetIndex)) // state check
            || (other instanceof ThemeCommand // instanceof handles nulls
            && this.themeName.equals(((ThemeCommand) other).themeName)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case GmapCommand.COMMAND_WORD:
            return new GmapCommandParser().parse(arguments);

        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\GmapCommandParser.java
``` java
/**
 * Parses input arguments and creates a new GmaptCommand object
 */
public class GmapCommandParser implements Parser<GmapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GmapCommand
     * and returns an GmapCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GmapCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
        } else {
            if (Character.isDigit(trimmedArgs.charAt(0))) {
                try {
                    Index index = ParserUtil.parseIndex(args);
                    return new GmapCommand(index);
                } catch (IllegalValueException ive) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
                }
            } else {
                String[] nameKeywords = trimmedArgs.split("\\s+");
                return new GmapCommand(new NameConsistsKeywordsPredicate(Arrays.asList(nameKeywords)));
            }
        }
    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        return new ImportCommand(trimmedArgs);
    }
}
```
###### \java\seedu\address\logic\parser\ThemeCommandParser.java
``` java

/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns an ThemeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }

        if (Character.isDigit(trimmedArgs.charAt(0))) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new ThemeCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
            }
        }
        return new ThemeCommand(trimmedArgs);
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    void handleAddressBookImportEvent(AddressBookImportEvent abce);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * @param predicate
     * @return the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public List<ReadOnlyPerson> getPersonListByPredicate(Predicate<ReadOnlyPerson> predicate) {
        FilteredList<ReadOnlyPerson> filteredList = new FilteredList<>(filteredPersons);
        filteredList.setPredicate(predicate);
        return FXCollections.unmodifiableObservableList(filteredList);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    @Subscribe
    public void handleAddressBookImportEvent(AddressBookImportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data added from file-> "
                + event.filePath));
        for (ReadOnlyPerson person : event.importedBook.getPersonList()) {
            if (!addressBook.getPersonList().contains(person)) {
                try {
                    if (addressBook.getPersonList().stream().noneMatch(p -> p.getName().equals(person.getName()))) {
                        addPerson(person);
                    } else {
                        updatePerson(addressBook.getPersonList().stream()
                                .filter(p -> p.getName().equals(person.getName())).findFirst().get(), person);
                    }
                } catch (DuplicatePersonException dpe) {
                    logger.info(LogsCenter.getEventHandlingLogMessage(event, "Person name [" + person.getName()
                            + "] already, not added."));
                } catch (PersonNotFoundException pnfe) {
                    logger.info(LogsCenter.getEventHandlingLogMessage(event, "Person name [" + person.getName()
                            + "] not found, not modified."));
                }
            }
        }
    }
```
###### \java\seedu\address\model\person\NameConsistsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} contains all of the keywords given.
 */
public class NameConsistsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameConsistsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameConsistsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameConsistsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java

    /**
     * @param person Loads a Google Map page based on index of {@param person}
     */
    private void loadMapPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_GMAP_URL_PREFIX + person.getAddress().value
            .replaceAll(" ", "+").replaceAll(",", ""));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    public void loadDefaultPage(String theme) {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm() + "?theme=" + theme);
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleDisplayGmapEvent(DisplayGmapEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadMapPage(personList.get(event.targetIndex));
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Sets the current theme based on given css.
     *
     * @param themeUrl e.g. {@code "/darktheme/DarkTheme.css"}
     */
    private void setTheme(String themeUrl) {
        this.getPrimaryStage().getScene().getStylesheets().clear();
        this.getPrimaryStage().getScene().getStylesheets().add(MainApp.class
                .getResource("/view/" + themeUrl).toExternalForm());
        this.currentTheme = themeUrl;
        browserPanel.loadDefaultPage(themeUrl);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java

    /**
     * Changes the current theme
     */
    @FXML
    public void handleThemeBootstrap3() {
        setTheme(THEME_FILE_FOLDER + THEME_CSS_BOOTSTRAP3);
    }

    @FXML
    public void handleThemeDark() {
        setTheme(THEME_FILE_FOLDER + THEME_CSS_DARKTHEME);
    }

    @FXML
    public void handleThemeCaspian() {
        setTheme(THEME_FILE_FOLDER + THEME_CSS_CASPIAN);
    }

    @FXML
    public void handleThemeModena() {
        setTheme(THEME_FILE_FOLDER + THEME_CSS_MODENA);
    }

    @FXML
    public void handleThemeModenaBoW() {
        setTheme(THEME_FILE_FOLDER + THEME_CSS_MODENA_BLACKONWHITE);
    }

    @FXML
    public void handleThemeModenaWoB() {
        setTheme(THEME_FILE_FOLDER + THEME_CSS_MODENA_WHITEONBLACK);
    }

    @FXML
    public void handleThemeModenaYoB() {
        setTheme(THEME_FILE_FOLDER + THEME_CSS_MODENA_YELLOWONBLACK);
    }

    /**
     * Displays a file chooser to extract url
     */
    @FXML
    public void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import From...");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        ReadOnlyAddressBook addressBookOptional;
        CommandResult cmdResult;
        try {
            addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(selectedFile.getPath()));
            raise(new AddressBookImportEvent(selectedFile.getPath(), addressBookOptional));

            cmdResult = new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, selectedFile.getPath()));
            raise(new NewResultAvailableEvent(cmdResult.feedbackToUser, true));
        } catch (DataConversionException e) {
            cmdResult = new CommandResult(String.format(MESSAGE_INVALID_XML_FORMAT_ERROR, selectedFile.getPath()));
            raise(new NewResultAvailableEvent(cmdResult.feedbackToUser, true));
        } catch (IOException e) {
            cmdResult = new CommandResult(String.format(MESSAGE_INVALID_IMPORT_FILE_ERROR, selectedFile.getPath()));
            raise(new NewResultAvailableEvent(cmdResult.feedbackToUser, true));
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setTheme(THEME_FILE_FOLDER + THEME_LIST_DIR.get(event.targetIndex));
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String getColorForTag(String tagValue) {

        if (!tagColors.containsKey(tagValue)) {
            int multiplier = 1;
            int asciiSum = (tagValue.hashCode() > 1) ? tagValue.hashCode() : tagValue.hashCode() * -1;

            int colorRed = asciiSum % 256;
            int colorGreen = (asciiSum / 2) % 256;
            int colorBlue = (asciiSum / 3) % 256;
            while ((colorRed + colorGreen + colorBlue) > 700) {
                asciiSum = (asciiSum / multiplier) * ++multiplier;
                colorRed = asciiSum % 256;
                colorGreen = (asciiSum / 2) % 256;
                colorBlue = (asciiSum / 3) % 256;
            }
            String colorString = String.format("#%02x%02x%02x", colorRed, colorGreen, colorBlue);
            tagColors.put(tagValue, colorString);
        }

        return tagColors.get(tagValue);
    }

    /**
     * Menu list option: Delete
     * Raises PersonPanelOptionsDelete, handled by UIManager
     * Handle Delete user
     */
    @FXML
    public void handleDelete() throws CommandException, ParseException {
        raise(new PersonPanelDeleteEvent(Index.fromOneBased(this.displayedIndex)));
    }

    /**
     * Menu list option: GoogleMap
     * Raises DisplayGmapEvent, handled by BrowserPanel
     * Display google map on main viewport
     */
    @FXML
    public void handleGoogleMap() {
        raise(new DisplayGmapEvent(Index.fromOneBased(this.displayedIndex)));
    }
```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Subscribe
    private void handleOptionsDeleteEvent(PersonPanelDeleteEvent event) throws CommandException, ParseException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.logic.execute("delete " + event.targetIndex);
    }
```
###### \java\seedu\address\ui\UiPart.java
``` java
    public static final String THEME_CSS_DARKTHEME = "/darktheme/DarkTheme.css";
    public static final String THEME_CSS_BOOTSTRAP3 = "/bootstrap3/bootstrap3.css";
    public static final String THEME_CSS_CASPIAN = "/caspian/caspian.css";
    public static final String THEME_CSS_MODENA = "/modena/modena.css";
    public static final String THEME_CSS_MODENA_BLACKONWHITE = "/modena/blackOnWhite.css";
    public static final String THEME_CSS_MODENA_WHITEONBLACK = "/modena/whiteOnBlack.css";
    public static final String THEME_CSS_MODENA_YELLOWONBLACK = "/modena/yellowOnBlack.css";

    public static final List<String> THEME_LIST_DIR = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add(THEME_CSS_DARKTHEME);
                add(THEME_CSS_BOOTSTRAP3);
                add(THEME_CSS_CASPIAN);
                add(THEME_CSS_MODENA);
                add(THEME_CSS_MODENA_BLACKONWHITE);
                add(THEME_CSS_MODENA_WHITEONBLACK);
                add(THEME_CSS_MODENA_YELLOWONBLACK);
            }});
```
###### \java\seedu\address\ui\UiPart.java
``` java
    public static String getThemeNameByIndex(int index) {
        String themeName = THEME_LIST_DIR.get(index);
        themeName = themeName.replaceAll(".css", "");
        themeName = themeName.substring(themeName.lastIndexOf("/") + 1);
        themeName = themeName.substring(0, 1).toUpperCase() + themeName.substring(1);

        if (THEME_LIST_DIR.get(index).contains("modena") && !THEME_LIST_DIR.get(index).contains("modena.css")) {
            themeName = "(Modena) " + themeName;
        }
        return themeName;
    }
```
###### \resources\view\Extensions.css
``` css
.menu-personcard {
    -fx-border-color: transparent;
    -fx-border-width: 0;
    -fx-background-radius: 0;
    -fx-background-color: transparent;
	-fx-mark-color: grey;
	-fx-pref-width: 120px;
	-fx-text-alignment: right;
}

.menu-personcard:showing {
    -fx-mark-color: #00FFFF;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
        <MenuItem mnemonicParsing="false" text="Import Addressbook" onAction="#handleImport"/>
        <MenuItem mnemonicParsing="false" text="Export Addressbook (TDB)"/>
        <Menu text="Themes">
            <MenuItem text="Bootstrap3" onAction="#handleThemeBootstrap3" userData="0"/>
            <MenuItem text="Dark" onAction="#handleThemeDark" userData="1"/>
            <MenuItem text="Caspian" onAction="#handleThemeCaspian" userData="2"/>
            <Menu text="Modena">
                <MenuItem text="Default" onAction="#handleThemeModena" userData="3"/>
                <MenuItem text="Black On White" onAction="#handleThemeModenaBoW" userData="4"/>
                <MenuItem text="White On Black" onAction="#handleThemeModenaWoB" userData="5"/>
                <MenuItem text="Yellow On Black" onAction="#handleThemeModenaYoB" userData="6"/>
            </Menu>
        </Menu>
```
###### \resources\view\PersonListCard.fxml
``` fxml
                <Pane HBox.hgrow="ALWAYS"/>
                <MenuButton fx:id="options_button" text="" alignment="CENTER_RIGHT" styleClass="menu-personcard">
                    <items>
                        <MenuItem text="Delete" onAction="#handleDelete"/>
                        <MenuItem text="Google Map" onAction="#handleGoogleMap"/>
                        <MenuItem text="Add photo" onAction="#handleAddImage"/>
                    </items>
                </MenuButton>
```
