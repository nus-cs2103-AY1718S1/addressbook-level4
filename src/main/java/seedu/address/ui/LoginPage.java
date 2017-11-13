package seedu.address.ui;
//@@author yangminxingnus

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
