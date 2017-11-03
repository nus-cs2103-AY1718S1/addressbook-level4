# Choony93
###### /java/seedu/address/commons/events/model/AddressBookImportEvent.java
``` java
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
###### /java/seedu/address/commons/events/ui/ChangeThemeEvent.java
``` java
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
###### /java/seedu/address/commons/events/ui/DisplayGmapEvent.java
``` java
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
###### /java/seedu/address/commons/events/ui/PersonPanelDeleteEvent.java
``` java
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
###### /java/seedu/address/logic/commands/GmapCommand.java
``` java
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

    public static final String MESSAGE_GMAP_PERSON_SUCCESS = "Displayed Google map of Person: %1$s";

    private final boolean usingIndex;
    private Index targetIndex = Index.fromOneBased(Integer.parseInt("1"));
    private NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(new ArrayList<>());

    public GmapCommand(Index targetIndex) {
        this.usingIndex = true;
        this.targetIndex = targetIndex;
    }

    public GmapCommand(NameContainsKeywordsPredicate predicate) {
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
###### /java/seedu/address/logic/commands/ImportCommand.java
``` java
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports XML file into addressbook from given filepath.\n"
            + "Parameters: FILEPATH (must be absolute)\n"
            + "Example: " + COMMAND_WORD + " FILEPATH";

    private static final String MESSAGE_IMPORT_SUCCESS = "Addressbook successfully imported from: %1$s";
    private static final String MESSAGE_INVALID_IMPORT_FILE_ERROR = "Problem reading file: %1$s";
    private static final String MESSAGE_INVALID_XML_FORMAT_ERROR = "XML syntax not well-formed: %1$s";

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
###### /java/seedu/address/logic/commands/ThemeCommand.java
``` java
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
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case GmapCommand.COMMAND_WORD:
            return new GmapCommandParser().parse(arguments);

        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/GmapCommandParser.java
``` java
        String trimmedArgs = args.trim();
        if (Character.isDigit(trimmedArgs.charAt(0))) {
            try {
                Index index = ParserUtil.parseIndex(args);
                return new GmapCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
            }
        } else {
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
            }
            String[] nameKeywords = trimmedArgs.split("\\s+");
            return new GmapCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
```
###### /java/seedu/address/logic/parser/ThemeCommandParser.java
``` java
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
```
###### /java/seedu/address/model/Model.java
``` java
    void handleAddressBookImportEvent(AddressBookImportEvent abce);
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public List<ReadOnlyPerson> getPersonListByPredicate(Predicate<ReadOnlyPerson> predicate) {
        FilteredList<ReadOnlyPerson> filteredList = new FilteredList<ReadOnlyPerson>(filteredPersons);
        filteredList.setPredicate(predicate);
        return FXCollections.unmodifiableObservableList(filteredList);
    }
```
###### /java/seedu/address/model/ModelManager.java
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
###### /java/seedu/address/ui/PersonCard.java
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
```
###### /java/seedu/address/ui/UiManager.java
``` java
    @Subscribe
    private void handleOptionsDeleteEvent(PersonPanelDeleteEvent event) throws CommandException, ParseException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.logic.execute("delete " + event.targetIndex);
    }
```
###### /java/seedu/address/ui/UiPart.java
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
###### /java/seedu/address/ui/UiPart.java
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
###### /resources/view/Extensions.css
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
###### /resources/view/MainWindow.fxml
``` fxml
        <MenuItem mnemonicParsing="false" text="Import Addressbook (TDB)"/>
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
###### /resources/view/PersonListCard.fxml
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
