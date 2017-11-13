package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.ChangeFontSizeCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;
import seedu.address.model.font.FontSize;
import seedu.address.model.theme.Theme;
import seedu.address.storage.AccountsStorage;
import seedu.address.storage.StorageManager;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private ReminderListPanel reminderListPanel;
    private Config config;
    private UserPrefs prefs;
    private StorageManager storage;
    //@@author yangminxingnus
    private AccountsStorage accPrefs;
    private UiManager uiManager;
    //@@author
    private CommandBox commandBox;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane reminderListPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, StorageManager storage, UserPrefs prefs, Logic logic,
                      AccountsStorage accPrefs, UiManager uiManager) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
        this.storage = storage;
        //@@author yangminxingnus
        this.accPrefs = accPrefs;
        this.uiManager = uiManager;
        uiManager.setMainWindow(this);
        //@@author

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        initTheme();
        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BrowserPanel getBrowserPanel() {
        return browserPanel;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    private void initTheme() {
        Theme.changeTheme(primaryStage, Theme.getCurrentTheme());
    }
    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        reminderListPanel = new ReminderListPanel(logic.getFilteredReminderList());
        reminderListPlaceholder.getChildren().add(reminderListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
                logic.getFilteredPersonList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        this.commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
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

    /**
     * Sets the default size based on user preferences.
     */
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

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), FontSize.getCurrentFontSizeLabel(),
                Theme.getCurrentTheme());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }
    //@@author yangminxingnus
    /**
    * logout
    */
    public void logout() {
        logger.info("Trying to logout");
        prefs.updateLastUsedGuiSetting(this.getCurrentGuiSetting());
        this.releaseResources();
    }
    //@@author

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }
    //@@author yangminxingnus
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
