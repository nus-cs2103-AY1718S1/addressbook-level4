package seedu.address.ui;

import static seedu.address.logic.commands.ImportCommand.MESSAGE_IMPORT_SUCCESS;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_IMPORT_FILE_ERROR;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_XML_FORMAT_ERROR;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookImportEvent;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.XmlFileStorage;

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
    private String currentTheme;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

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

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
        this.currentTheme = prefs.getGuiSettings().getWindowTheme();

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
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
        browserPanel = new BrowserPanel(logic.getFilteredPersonList(), prefs.getGuiSettings().getWindowTheme());
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
                logic.getFilteredPersonList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        this.setTheme(this.currentTheme);
    }

    void hide() {
        primaryStage.hide();
    }

    //@@author Choony93
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
    //@@author

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
                (int) primaryStage.getX(), (int) primaryStage.getY(), currentTheme);
    }

    //@@author Choony93

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
    //@@author

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

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author Choony93
    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setTheme(THEME_FILE_FOLDER + THEME_LIST_DIR.get(event.targetIndex));
    }
    //@@author
}
