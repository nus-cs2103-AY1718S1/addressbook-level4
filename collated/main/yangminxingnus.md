# yangminxingnus
###### /java/seedu/address/commons/core/CipherUnit.java
``` java
/**
 * method for encrypt / decrypt the files
 */
public class CipherUnit {
    private static String key = "squirrel123"; // needs to be at least 8 characters for DES
    private static String dest;

    /**
    * class for encrypting files
    */
    public static void encrypt(String path) {
        try {
            dest = path;
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream("data/temp.xml");
            encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, fis, fos);
            swapName("data/temp.xml");
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    /**
    * class for decrypting files
    */
    public static void decrypt(String path) {
        try {
            dest = path;
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream("data/temp.xml");
            encryptOrDecrypt(key, Cipher.DECRYPT_MODE, fis, fos);
            swapName("data/temp.xml");
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    /**
    * translating method
    */
    public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Throwable {

        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            doCopy(cis, os);
        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            doCopy(is, cos);
        }
    }

    /**
    * do a copy
    */
    public static void doCopy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            os.write(bytes, 0, numBytes);
        }
        os.flush();
        os.close();
        is.close();
    }

    /**
    * used to replace files encypted/decrypted with the dectypted/encrypted one.
    */
    public static boolean swapName(String source) {
        File tmp = new File(source);

        File swapFile1 = new File(dest);

        return tmp.renameTo(swapFile1);

    }

}
```
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
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
            + PREFIX_REMARK + "CS2101/SEC/1, CS2104/LEC/1";

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

        return new CommandResult(String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (this.remark.equals(((RemarkCommand) other).remark)
                && this.index == ((RemarkCommand) other).index);
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();

            FileImage image = ParserUtil.parseImage(argMultimap.getValue(PREFIX_IMAGE))
                    .orElse(new FileImage(""));
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Address address;
            Remark remark;
            DateOfBirth date;
            FacebookUsername username;
            if (ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).isPresent()) {
                address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            } else {
                address = new Address("");
            }

            if (ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB)).isPresent()) {
                date = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB)).get();
            } else {
                date = new DateOfBirth("");;
            }
            if (ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).isPresent()) {
                remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
            } else {
                remark = new Remark("");
            }
            if (ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).isPresent()) {
                username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            } else {
                username = new FacebookUsername("");
            }


            ReadOnlyPerson person = new Person(name, phone, email, address, date, remark, image, username, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
```
###### /java/seedu/address/logic/parser/RemarkCommandParser.java
``` java
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
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String[] argus = trimmedArgs.split("r/");

        try {
            Integer index = Integer.parseInt(argus[0].trim());
            Remark remark;
            if (argus.length > 1) {
                remark = new Remark(argus[1].trim());
            } else {
                remark = new Remark("");
            }
            return new RemarkCommand(index, remark);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/model/person/Address.java
``` java
        return test.matches(ADDRESS_VALIDATION_REGEX) || test.equals("");
```
###### /java/seedu/address/model/person/DateOfBirth.java
``` java
        String trimmedBirthday = birthday.trim();

        if (trimmedBirthday.isEmpty()) {
            return true;
        }
        if (!trimmedBirthday.matches(BIRTHDAY_VALIDATION_REGEX)) {
            return false;
        }

        return true;
```
###### /java/seedu/address/model/person/Person.java
``` java
    @Override
    public  DateOfBirth getDateOfBirth() {
        return date.get();
    }

    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }
```
###### /java/seedu/address/model/person/Remark.java
``` java
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
            if (!isValidRemark(t)) {
                System.out.println(t);
                throw new IllegalValueException(REMARK_CONSTRAINTS);
            }
        }
        this.moduleLists = remark;
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
            String mod = helper[0];
            String kind = helper[1];
            String num = helper[2];
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
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.moduleLists.equals(((Remark) other).moduleLists)); // state check
    }

    @Override
    public int hashCode() {
        return moduleLists.hashCode();
    }
}
```
###### /java/seedu/address/storage/AccountsStorage.java
``` java
/**
 * Represents a storage for account information.
 */

public class AccountsStorage implements UserPrefsStorage {
    public static final String DEFAULT_ACCOUNTS_PATH = "accounts.json";

    private String accountsPath;
    private String usernames;
    private String passwords;
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

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return readUserPrefs(accountsPath);
    }

    /**
     * Returns UserPrefs data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    public Optional<UserPrefs> readUserPrefs(String prefsFilePath) throws DataConversionException, IOException {
        return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
    }

    public void saveAccountsPrefs(AccountsStorage accStorage, String filePath) throws IOException {
        JsonUtil.saveJsonFile(accStorage, filePath);
    }

    public AccountsStorage setAccountMsg() throws DataConversionException, IOException {
        String accountMsg = this.readAccountsPrefs(this.accountsPath).toString();
        this.hm = makeMap(accountMsg);
        return this;
    }

    /**
     * Returns the file path of the UserPrefs data file.
     */
    public String getUserPrefsFilePath() {
        return accountsPath;
    }

    public Optional<AccountsStorage> readAccountsPrefs(String prefsFilePath)
            throws DataConversionException, IOException {
        return JsonUtil.readJsonFile(prefsFilePath, AccountsStorage.class);
    }

    /**
     * Saves the given {@link seedu.address.model.UserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, accountsPath);
    }

    /**
     * Returns the map of username and password.
     */
    public HashMap<String, String> makeMap(String rawValue) {
        HashMap<String, String> hm = new HashMap<String, String>();
        rawValue = rawValue.substring(9, rawValue.length() - 1);
        String[] value = rawValue.split("/");
        String[] username = value[0].split(",");
        String[] password = value[1].split(",");
        for (int i = 0; i < username.length; i++) {
            hm.put(username[i].trim(), password[i].trim());
        }
        return hm;
    }

    public HashMap<String, String> getHm() {
        return hm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(usernames);
        sb.append("/");
        sb.append(passwords);
        return sb.toString();
    }
}
```
###### /java/seedu/address/ui/LoginPage.java
``` java
/**
 * The login page. Users need to key in their username and password to login the MainWindow.
 */
public class LoginPage extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public MainWindow getMainWindow() { return mainWindow; }

    /**
     * Method for handle login event
     */
    @FXML
    private void handleLoginEvent() throws IOException {
        logger.info("Trying to login");
        String uname = username.getText();
        String pword = password.getText();
        if (checkValid(uname, pword)) {

            String path = "data/" + uname + "addressbook.xml";
            String tempPath = "data/temp.xml";

            File addressBookFile = new File(path);
            if (addressBookFile.exists()) {
                decrypt(path);
                logger.info("File decypted");
            }

            UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
            AddressBookStorage addressBookStorage = new XmlAddressBookStorage(path);

            //storage.setUserPrefsStorage(userPrefsStorage);
            prefs.setAddressBookFilePath(path);
            storage.setAddressBookStorage(addressBookStorage);

            model = initModelManager(storage, prefs);
            logic = new LogicManager(model);

            mainWindow = new MainWindow(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
        } else {
            logger.info("Wrong name or password!");
        }
    }

    /**
     * Handles the register event.
     */
    @FXML
    private void handleRegisterEvent() {
        logger.info("Trying to register");
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
     *
     * @param username
     * @param password
     * @return validity of account
     */
    private boolean checkValid(String username, String password) {
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
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
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
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
    * logout
    */
    public void logout() {
        logger.info("Trying to logout");
        prefs.updateLastUsedGuiSetting(this.getCurrentGuiSetting());
        encrypt(storage.getAddressBookFilePath());
        logger.info("File encypted");
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Method for handle logout event.
     */
    @FXML
    private void handleLogoutEvent() throws IOException {
        logout();
        LoginPage loginPage = new LoginPage(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
        loginPage.show();
    }
```
###### /java/seedu/address/ui/RegisterPage.java
``` java
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

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        registerAsAnEventHandler(this);
        //loginPage = new LoginPage(primaryStage, config, storage, prefs, logic, accPrefs);
    }

    /**
     * @return validity of account
     */
    private boolean checkValid() {
        if (accPrefs.getHm().get(username.getText()) != null) {
            logger.info("Register faild");
            return false;
        } else {
            logger.info("Register successful");
            return password.getText().equals(password1.getText());
        }
    }

    /**
     * Method for handle register event
     */
    @FXML
    private void handleRegisterEvent() {
        try {
            logger.info("Trying to register");
            if (checkValid()) {
                accPrefs.getHm().put(username.getText(), password.getText());
                accPrefs.saveAccountsPrefs(accPrefs, accPrefs.getUserPrefsFilePath());
                loginPage = new LoginPage(primaryStage, config, storage, prefs, logic, accPrefs, uiManager);
                this.hide();
                loginPage.show();
            }
        } catch (IOException e) {
            logger.info("Invalid input");
        }
    }

    /**
     * Method for going back login page.
     */
    @FXML
    private void handleBackEvent() {
        logger.info("Going back to login page");
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

    void releaseResources() {
        this.hide();
    }
}
```
###### /java/seedu/address/ui/UiManager.java
``` java
        try {
            loginPage = new LoginPage(primaryStage, config, storage, prefs, logic, accPrefs, this);
            loginPage.show();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
```
###### /java/seedu/address/ui/UiManager.java
``` java
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
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }
```
###### /java/seedu/address/ui/UiManager.java
``` java
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

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, FILE_OPS_ERROR_DIALOG_STAGE_TITLE, description, content);
    }
```
###### /java/seedu/address/ui/UiManager.java
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
```
