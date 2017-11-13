# yangminxingnus
###### \java\seedu\address\commons\events\ui\LogoutEvent.java
``` java
/**
 * Indicates a request to logout
 */
public class LogoutEvent extends BaseEvent {
    @Override
    public String toString() {
        return "logout";
    }
}
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_DOB + "Date of Birth "
            + PREFIX_REMARK + "REMARK "
            + "[" + PREFIX_IMAGE + "IMAGE] "
            + "[" + PREFIX_USERNAME + "USERNAME]"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_DOB + "13.10.1997 "
            + PREFIX_REMARK + "CS2103T/LEC/1 "
            + PREFIX_IMAGE + " "
            + PREFIX_USERNAME + "john.doe "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DOB + "DATE_OF_BIRTH] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_IMAGE + "IMAGE] "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        private Remark remark;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            this.remark = toCopy.remark;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setRemark(Remark remark) {
            this.remark = remark;
        }
        public void setUsername(FacebookUsername username) {
            this.username = username;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getDateOfBirth().equals(e.getDateOfBirth())
                    && getRemark().equals(e.getRemark())
                    && getImage().equals(e.getImage())
                    && getUsername().equals(e.getUsername())
                    && getTags().equals(e.getTags());
```
###### \java\seedu\address\logic\commands\LogoutCommand.java
``` java
/**
 * Command of logout.
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Logout succeeded.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new LogoutEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Add a remark for a specified person
 */
public class RemarkCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remark the module information of the person identified by the index. "
            + "Existing modulelist will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "MODULENAME1/MODULETYPE1/NUM1,MODULENAME2/MODULETYPE2/NUM2\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "CS2101/SEC/1,CS2104/LEC/1,CS2105/LEC/1,CS2102/LEC/1";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Integer index;
    private final Remark remark;

    /**
          * @param index of the person in the filtered person list to edit the remark
          * @param remark of the person
          */
    public RemarkCommand(Integer index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);
        this.index = index - 1;
        this.remark = remark;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (index < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getDateOfBirth(),
                remark, personToEdit.getImage(), personToEdit.getUsername(), personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_ADD_REMARK_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (this.remark.equals(((RemarkCommand) other).remark)
                && this.index == ((RemarkCommand) other).index);
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.commands.RemarkCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.Remark;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String[] argus = trimmedArgs.split("r/");

        try {
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
            Integer index = Integer.parseInt(argus[0].trim());

            return new RemarkCommand(index, remark);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\MainApp.java
``` java
        AccountsStorage accountsPrefs = new AccountsStorage(config.getAccountsPath());

        accPrefs = initAccPrefs(accountsPrefs);
```
###### \java\seedu\address\MainApp.java
``` java
    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected AccountsStorage initAccPrefs(AccountsStorage storage) {
        String prefsFilePath = storage.getAccPrefsFilePath();
        logger.info("Using account prefs file : " + prefsFilePath);

        AccountsStorage initializedPrefs;
        try {
            Optional<AccountsStorage> prefsOptional = storage.readAccountsPrefs(prefsFilePath);
            initializedPrefs = prefsOptional.orElse(new AccountsStorage());
        } catch (DataConversionException e) {
            logger.warning("Account Prefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default account prefs");
            initializedPrefs = new AccountsStorage();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. "
                    + "Will be starting with default account information");
            initializedPrefs = new AccountsStorage();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveAccountsPrefs(initializedPrefs, prefsFilePath);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addRemarkPerson(ReadOnlyPerson person, String remark, Index targetIndex) {
        try {
            person.remarkProperty().setValue(new Remark(remark));
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            indicateAddressBookChanged();
        } catch (IllegalValueException ive) {
            throw new AssertionError("Invalid input");
        }
    }

```
###### \java\seedu\address\model\person\Remark.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's remark in the address book.
 */

public class Remark {
    public static final String REMARK_CONSTRAINTS =
            "Remark should be in the format MODULENAME/MODULETYPE/NUM. Example: CS2101/SEC/2";
    public static final String REMARK_VALIDATION_REGEX = "[\\w\\.]+/[\\w\\.]+/[\\d\\.]";
    private String moduleLists;

    public Remark(String remark) throws IllegalValueException {
        requireNonNull(remark);
        String[] test = remark.split(",");
        for (String t : test) {
            t = t.trim();
            if (!isValidRemark(t)) {
                System.out.println(t);
                throw new IllegalValueException(REMARK_CONSTRAINTS);
            }
        }
        this.moduleLists = remark;
    }

    public void setModuleLists(String mods) {
        this.moduleLists = mods;
    }

    /**
     * Returns true if a given string is a valid Remark.
     */
    public static boolean isValidRemark(String test) {
        return (test.matches(REMARK_VALIDATION_REGEX) || test.equals(""));
    }

    /**
     * Get the moduleLists.
     */
    public String getParsedModuleLists() {
        return parse(moduleLists);
    }

    /**
     * Get the moduleLists.
     */
    public String getModuleLists() {
        return moduleLists;
    }

    /**
     * Parse the modulelist to correct url format.
     */
    private String parse(String moduleLists) {
        String[] mods = moduleLists.split(",");
        String result = "";
        for (String m : mods) {
            String[] helper = m.split("/");
            String mod = helper[0].trim();
            String kind = helper[1].trim();
            String num = helper[2].trim();
            result = result + "&" + mod + "[" + kind + "]" + "=" + num;
        }
        return result;
    }

    @Override
    public String toString() {
        return moduleLists;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Remark // instanceof handles nulls
                && this.moduleLists.equals(((Remark) other).moduleLists)); // state check
    }

    @Override
    public int hashCode() {
        return moduleLists.hashCode();
    }
}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2105/LEC/1,CS2104/LEC/1"), new FileImage(""),
                    new FacebookUsername(""), getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2104/LEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""),    getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""),
                    getTagSet("colleagues")),
                new Person(new Name("Ronak Lakhotia"), new Phone("93911558"), new Email("email@gmail.com"),
                    new Address("Prince Georges Park"), new DateOfBirth(("13.10.1997")),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""),
                    getTagSet("colleagues"))
            };
```
###### \java\seedu\address\storage\AccountsStorage.java
``` java
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;

/**
 * Represents a storage for account information.
 */

public class AccountsStorage {
    public static final String DEFAULT_ACCOUNTS_PATH = "accounts.json";

    private String accountsPath;
    private HashMap<String, String> hm;

    public AccountsStorage() {
        this.accountsPath = DEFAULT_ACCOUNTS_PATH;
        this.hm = new HashMap<String, String> ();
        hm.put("admin", "admin");
        hm.put("user", "user");
    }

    public AccountsStorage(String accountsPath) {
        this.accountsPath = accountsPath;
    }

    public String getAccPrefsFilePath() {
        return accountsPath;
    }

    public void saveAccountsPrefs(AccountsStorage accStorage, String filePath) throws IOException {
        JsonUtil.saveJsonFile(accStorage, filePath);
    }

    public Optional<AccountsStorage> readAccountsPrefs(String prefsFilePath)
            throws DataConversionException, IOException {
        return JsonUtil.readJsonFile(prefsFilePath, AccountsStorage.class);
    }

    public HashMap<String, String> getHm() {
        return hm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getHm().keySet());
        sb.append("/");
        sb.append(getHm().values());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        return ((AccountsStorage) o).getHm().equals(this.getHm());
    }
}
```
###### \java\seedu\address\storage\JsonAccountsStorage.java
``` java
import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonAccountsStorage implements UserPrefsStorage {

    private String filePath;

    public JsonAccountsStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(String prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

    public Optional<AccountsStorage> readAccountsPrefs() throws DataConversionException, IOException {
        return readAccountsPrefs(filePath);
    }

    public Optional<AccountsStorage> readAccountsPrefs(String prefsFilePath)
            throws DataConversionException, IOException {
        return JsonUtil.readJsonFile(prefsFilePath, AccountsStorage.class);
    }

    public void saveAccountsPrefs(AccountsStorage accStorage) throws IOException {
        JsonUtil.saveJsonFile(accStorage, filePath);
    }

}
```
###### \java\seedu\address\ui\LoginPage.java
``` java

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.font.FontSize;
import seedu.address.model.theme.Theme;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AccountsStorage;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * The login page. Users need to key in their username and password to login the MainWindow.
 */
public class LoginPage extends UiPart<Region> {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "LoginPage.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private Stage primaryStage;
    private MainWindow mainWindow;

    private Config config;
    private StorageManager storage;
    private UserPrefs prefs;
    private Logic logic;
    private Model model;
    private AccountsStorage accPrefs;
    private UiManager uiManager;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    public LoginPage(Stage primaryStage, Config config, StorageManager storage, UserPrefs prefs,
                     Logic logic, AccountsStorage accPrefs, UiManager uiManager) {
        super(FXML);
        this.logic = logic;
        // Set dependencies
        this.primaryStage = primaryStage;
        this.config = config;
        this.prefs = prefs;
        this.accPrefs = accPrefs;
        this.storage = storage;
        this.uiManager = uiManager;
        uiManager.setLoginPage(this);

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        initTheme();
        registerAsAnEventHandler(this);
    }

    private void initTheme() {
        Theme.changeTheme(primaryStage, Theme.getCurrentTheme());
    }

    /**
     * Method for handle login event
     */
    @FXML
    private void handleLoginEvent() throws IOException {
        String uname = username.getText();
        String pword = password.getText();
        if (isValid(uname, pword)) {

            String path = "data/" + uname + ".xml";
            AddressBookStorage addressBookStorage = new XmlAddressBookStorage(path);

            prefs.setAddressBookFilePath(path);
            storage.setAddressBookStorage(addressBookStorage);

            model = initModelManager(storage, prefs);
            logic = new LogicManager(model);

            mainWindow = new MainWindow(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
            uiManager.setMainWindow(mainWindow);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Incorrect username or password.");
            alert.setContentText("Your username or password is not correct, please try again.");
            alert.showAndWait();
        }
    }

    /**
     * Handles the register event.
     */
    @FXML
    private void handleRegisterEvent() {
        RegisterPage registerPage = new RegisterPage(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
        this.hide();
        registerPage.show();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleLoginEvent();
        }
    }

    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), FontSize.getCurrentFontSizeLabel(),
                Theme.getCurrentTheme());
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     *
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        FontSize.setCurrentFontSizeLabel(prefs.getGuiSettings().getFontSize());
        Theme.setCurrentTheme(prefs.getGuiSettings().getTheme());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    void hide() {
        primaryStage.hide();
    }

    /**
     * release the resources
     */
    void releaseResources() {
        if (mainWindow != null) {
            mainWindow.logout();
            mainWindow.getBrowserPanel().freeResources();
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Check whether the username and password matches in the account.json file
     * @param username
     * @param password
     * @return validity of account
     */
    private boolean isValid(String username, String password) {
        if (accPrefs.getHm().get(username) != null) {
            return accPrefs.getHm().get(username).equals(password);
        } else {
            return false;
        }
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            initialData = new AddressBook();
        } catch (IOException e) {
            initialData = new AddressBook();
        }
        return new ModelManager(initialData, userPrefs);
    }

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.getFontSize());
    }

    /**
     * Sets the command box style to user preferred font size.
     */
    private void setFontSize(String newFontSize) {
        String fxFormatFontSize = FontSize.getAssociateFxFontSizeString(newFontSize);
        username.setStyle(fxFormatFontSize);
        password.setStyle(fxFormatFontSize);
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private AccountsStorage accPrefs;
    private UiManager uiManager;
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        this.accPrefs = accPrefs;
        this.uiManager = uiManager;
        uiManager.setMainWindow(this);
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
    * logout
    */
    public void logout() {
        logger.info("Trying to logout");
        prefs.updateLastUsedGuiSetting(this.getCurrentGuiSetting());
        this.releaseResources();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Method for handle logout event.
     */
    @FXML
    private void handleLogoutEvent() throws IOException {
        this.logout();
        LoginPage loginPage = new LoginPage(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
        uiManager.setLoginPage(loginPage);
        loginPage.show();
    }

    /**
     * Logout from the current MainWindow.
     */
    @Subscribe
    public void handleLogoutEvent(LogoutEvent event) throws ParseException, IOException {

        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        this.handleLogoutEvent();

    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    public ReminderListPanel getReminderListPanel() {
        return this.reminderListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        handleHelp();
    }

    /**
     * Increase the font size.
     */
    @FXML
    private void handleIncreaseFontSize() throws CommandException, ParseException {
        commandBox.handleCommandInputChanged(ChangeFontSizeCommand.INCREASE_FONT_SIZE_COMMAND);
    }

    /**
     * Decrease the font size.
     */
    @FXML
    private void handleDecreaseFontSize() throws CommandException, ParseException {
        commandBox.handleCommandInputChanged(ChangeFontSizeCommand.DECREASE_FONT_SIZE_COMMAND);
    }

    /**
     * Change the theme to dark theme
     */
    @FXML
    private void handleChangeDarkTheme() {
        commandBox.handleCommandInputChanged(ChangeThemeCommand.CHENG_TO_DARK_THEME_COMMAND);
    }

    /**
     * Change the theme to bright theme
     */
    @FXML
    private void handleChangeBrightTheme() {
        commandBox.handleCommandInputChanged(ChangeThemeCommand.CHENG_TO_BRIGHT_THEME_COMMAND);
    }

    /**
     * Change the theme when a ChangeThemeEvent is raised
     * @param changeThemeEvent
     */
    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeEvent changeThemeEvent) {
        Theme.changeTheme(primaryStage, changeThemeEvent.getTheme());
    }
}
```
###### \java\seedu\address\ui\RegisterPage.java
``` java
import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.font.FontSize;
import seedu.address.model.theme.Theme;
import seedu.address.storage.AccountsStorage;
import seedu.address.storage.StorageManager;

/**
 * The register page.
 */
public class RegisterPage extends UiPart<Region> {

    private static final String FXML = "RegisterPage.fxml";
    private static final String ICON = "/images/address_book_32.png";

    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private Stage primaryStage;
    private AccountsStorage accPrefs;
    private UserPrefs prefs;
    private StorageManager storage;
    private Config config;
    private Logic logic;
    private LoginPage loginPage;
    private UiManager uiManager;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField password1;

    public RegisterPage(Stage primaryStage, Config config, StorageManager storage, UserPrefs prefs,
                        Logic logic, AccountsStorage accPrefs, UiManager uiManager) {
        super(FXML);
        // Set dependencies
        this.primaryStage = primaryStage;
        this.accPrefs = accPrefs;
        this.config = config;
        this.storage = storage;
        this.prefs = prefs;
        this.logic = logic;
        this.uiManager = uiManager;
        uiManager.setRegisterPage(this);

        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        initTheme();
        registerAsAnEventHandler(this);
    }

    private void initTheme() {
        Theme.changeTheme(primaryStage, Theme.getCurrentTheme());
    }

    /**
     * @return validity of account
     */
    private boolean checkValid() {
        if (accPrefs.getHm().get(username.getText()) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Username already exist.");
            alert.setContentText("This username is already taken, please choose another one.");
            alert.showAndWait();
            return false;
        } else {
            boolean result = password.getText().equals(password1.getText());

            if (!result) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Password inconformity.");
                alert.setContentText("You must key in indentical password twice");
                alert.showAndWait();
            }

            return result;
        }
    }

    /**
     * Method for handle register event
     */
    @FXML
    private void handleRegisterEvent() {
        try {
            if (checkValid()) {
                accPrefs.getHm().put(username.getText(), password.getText());
                accPrefs.saveAccountsPrefs(accPrefs, accPrefs.getAccPrefsFilePath());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Register Successful");
                alert.setContentText("You have registered a new account!");
                loginPage = new LoginPage(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
                uiManager.setLoginPage(loginPage);
                this.hide();
                loginPage.show();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Register failed");
            alert.setContentText("You need to use a unique username. "
                    + "And you need to key in indentical password twice!");
            logger.info("Invalid input");
        }
    }

    /**
     * Method for going back login page.
     */
    @FXML
    private void handleBackEvent() {
        loginPage = new LoginPage(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
        this.hide();
        loginPage.show();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleRegisterEvent();
        }
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        FontSize.setCurrentFontSizeLabel(prefs.getGuiSettings().getFontSize());
        Theme.setCurrentTheme(prefs.getGuiSettings().getTheme());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Sets the given image as the icon of the main window.
     *
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    void hide() {
        primaryStage.hide();
    }
}
```
###### \java\seedu\address\ui\UiManager.java
``` java
    private Logic logic;
    private StorageManager storage;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;
    private LoginPage loginPage;
    private AccountsStorage accPrefs;
    private RegisterPage registerPage;

    private int test;
    public UiManager(Logic logic, Config config, StorageManager storage, UserPrefs prefs, AccountsStorage accPrefs) {
        super();
        this.logic = logic;
        this.storage = storage;
        this.config = config;
        this.prefs = prefs;
        this.accPrefs = accPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));


        try {
            loginPage = new LoginPage(primaryStage, config, storage, prefs, logic, accPrefs, this);
            loginPage.show();
        } catch (Throwable e) {
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void start(Stage primaryStage, int test) {
        this.test = 1;
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, storage, prefs, logic, accPrefs, this);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
        } catch (Throwable e) {
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        if (test == 1) {
            prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
            mainWindow.releaseResources();
            mainWindow.hide();
        } else {
            prefs.updateLastUsedGuiSetting(loginPage.getCurrentGuiSetting());
            loginPage.releaseResources();
            loginPage.hide();

        }
    }
```
###### \java\seedu\address\ui\UiManager.java
``` java
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setRegisterPage(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }
```
###### \resources\view\LoginPage.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<VBox minHeight="400.0" minWidth="800.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">

      <StackPane VBox.vgrow="ALWAYS" styleClass="pane-with-border">

         <children>
            <TextField fx:id="username" alignment="CENTER" onKeyPressed="#handleKeyPress" promptText="username" StackPane.alignment="CENTER">
               <StackPane.margin>
                  <Insets bottom="100.0" left="300.0" right="300.0" />
               </StackPane.margin>
            </TextField>
            <PasswordField fx:id="password" alignment="CENTER" onKeyPressed="#handleKeyPress" prefHeight="33.0" prefWidth="300.0" promptText="password" StackPane.alignment="BOTTOM_CENTER">
               <StackPane.margin>
                  <Insets bottom="80.0" left="300.0" right="300.0" />
               </StackPane.margin>
            </PasswordField>
         </children>
      </StackPane>
      <StackPane styleClass="pane-with-border" VBox.vgrow="ALWAYS">
         <children>
            <Button fx:id="registerButton" alignment="CENTER" mnemonicParsing="false" onMouseClicked="#handleRegisterEvent" prefHeight="70.0" prefWidth="159.0" text="Register" textAlignment="CENTER">
            <StackPane.margin>
               <Insets left="200.0" />
            </StackPane.margin></Button>
         <Button fx:id="loginButton1" alignment="CENTER" layoutX="382.0" layoutY="60.0" mnemonicParsing="false" onMouseClicked="#handleLoginEvent" prefHeight="70.0" prefWidth="159.0" text="Login" textAlignment="CENTER">
            <StackPane.margin>
               <Insets right="200.0" />
            </StackPane.margin>
         </Button>
         </children>
      </StackPane>
</VBox>
```
###### \resources\view\RegisterPage.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<VBox minHeight="400.0" minWidth="800.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <StackPane styleClass="pane-with-border" VBox.vgrow="ALWAYS">
      <children>
         <TextField fx:id="username" alignment="CENTER" onKeyPressed="#handleKeyPress" promptText="username" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets bottom="140.0" left="300.0" right="300.0" />
            </StackPane.margin>
         </TextField>
         <PasswordField fx:id="password" alignment="CENTER" onKeyPressed="#handleKeyPress" prefHeight="33.0" prefWidth="300.0" promptText="password" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets bottom="20.0" left="300.0" right="300.0" />
            </StackPane.margin>
         </PasswordField>
         <PasswordField fx:id="password1" alignment="CENTER" layoutX="311.0" layoutY="127.0" onKeyPressed="#handleKeyPress" prefHeight="33.0" prefWidth="300.0" promptText="reenter password" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets bottom="-100.0" left="300.0" right="300.0" />
            </StackPane.margin>
         </PasswordField>
      </children>
   </StackPane>
   <StackPane styleClass="pane-with-border" VBox.vgrow="ALWAYS">
      <children>
         <Button fx:id="registerButton" alignment="CENTER" layoutX="344.0" layoutY="60.0" mnemonicParsing="false" onMouseClicked="#handleRegisterEvent" prefHeight="70.0" prefWidth="143.0" text="Register" textAlignment="CENTER" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets left="-200.0" />
            </StackPane.margin>
         </Button>
         <Button fx:id="backButton" alignment="CENTER" layoutX="390.0" layoutY="55.0" mnemonicParsing="false" onMouseClicked="#handleBackEvent" prefHeight="70.0" prefWidth="143.0" text="Back" textAlignment="CENTER" StackPane.alignment="CENTER">
            <StackPane.margin>
               <Insets right="-200.0" />
            </StackPane.margin>
         </Button>
      </children>
   </StackPane>
   <stylesheets>
      <URL value="@Extensions.css" />
   </stylesheets>
</VBox>
```
