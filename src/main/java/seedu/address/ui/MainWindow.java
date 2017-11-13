package seedu.address.ui;

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
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.commons.events.ui.BrowserPanelNavigateEvent;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.OpenRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.SaveAsRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;
import seedu.address.storage.XmlFileStorage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/address_book_32_alternative.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;
    private static final String VIEW_PATH = "/view/";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final FileChooser fileChooser = new FileChooser();

    private MainApp mainApp;
    private Storage storage;
    private Model model;
    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private TaskListPanel taskListPanel;
    private Config config;
    private UserPrefs prefs;
    private ViewTaskPanel viewTaskPanel;
    private ViewPersonPanel viewPersonPanel;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem increaseSizeMenuItem;

    @FXML
    private MenuItem decreaseSizeMenuItem;

    @FXML
    private MenuItem resetSizeMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane taskListPanelPlaceholder;

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

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        setWindowDefaultTheme(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    //@@author jeffreygohkw
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(openMenuItem, KeyCombination.valueOf("SHORTCUT+O"));
        setAccelerator(saveMenuItem, KeyCombination.valueOf("SHORTCUT+S"));
        setAccelerator(exitMenuItem, KeyCombination.valueOf("ALT+F4"));
        setAccelerator(increaseSizeMenuItem, KeyCombination.valueOf("SHORTCUT+W"));
        setAccelerator(decreaseSizeMenuItem, KeyCombination.valueOf("SHORTCUT+E"));
        setAccelerator(resetSizeMenuItem, KeyCombination.valueOf("SHORTCUT+R"));
    }

    /**
     * Sets the accelerator of a MenuItem.
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
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp the MainApp itself
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Is called by the main application to provide MainWindow with Storage
     *
     * @param s the Storage used by MainApp
     */
    public void setStorage(Storage s) {
        this.storage = s;
    }

    /**
     * Is called by the main application to  provide MainWindow with Model
     *
     * @param m the Model used by MainApp
     */
    public void setModel(Model m) {
        this.model = m;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel();
        viewTaskPanel = new ViewTaskPanel();
        viewPersonPanel = new ViewPersonPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        String preferredFilePath = this.prefs.getAddressBookFilePath();
        StatusBarFooter statusBarFooter = new StatusBarFooter(preferredFilePath, logic.getFilteredPersonList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
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

    /**
     * Sets the default theme based on user preferences.
     */
    private void setWindowDefaultTheme(UserPrefs prefs) {
        getRoot().getStylesheets().add(prefs.getTheme());
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    public String getTheme() {
        return getRoot().getStylesheets().get(1);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    //@@author charlesgoh
    /**
     * Handle increase font size command for menu item
     */
    @FXML
    public void handleIncreaseFontSize() {
        logger.info("Handling increase in font size");
        raise(new ChangeFontSizeEvent(ChangeFontSizeEvent.getIncreaseSizeEventIndex()));
    }

    /**
     * Handle decrease font size command for menu item
     */
    @FXML
    public void handleDecreaseFontSize() {
        logger.info("Handling decrease in font size");
        raise(new ChangeFontSizeEvent(ChangeFontSizeEvent.getDecreaseSizeEventIndex()));
    }

    /**
     * Handle reset font size command
     */
    @FXML
    public void handleResetFontSize() {
        logger.info("Handling reset in font size");
        raise(new ChangeFontSizeEvent(ChangeFontSizeEvent.getResetSizeEventIndex()));
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

    //@@author jeffreygohkw
    /**
     * Opens the data from a desired location
     */
    @FXML
    private void handleOpen() throws IOException, DataConversionException {
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            // Change file path to the opened file
            storage.changeFilePath(file.getPath(), prefs);
            // Reset data in the model to the data from the opened file
            model.resetData(XmlFileStorage.loadDataFromSaveFile(file));
            // Update the UI
            fillInnerParts();
        }
    }

    @Subscribe
    private void handleOpenRequestEvent(OpenRequestEvent event) throws IOException, DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleOpen();
    }

    /**
     * Saves the data at a desired location
     */
    @FXML
    private void handleSaveAs() throws IOException {
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            // Change file path to the new save file
            storage.changeFilePath(file.getPath(), prefs);
            // Save the address book data and the user preferences
            storage.saveAddressBook(model.getAddressBook());
            storage.saveUserPrefs(prefs);
            // Update the UI
            fillInnerParts();
        }
    }

    @Subscribe
    private void handleSaveAsRequestEvent(SaveAsRequestEvent event) throws IOException, DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSaveAs();
    }

    /**
     * Changes the existing theme to the input theme
     */
    public void handleChangeTheme(String theme) {
        if (getRoot().getStylesheets().size() > 1) {
            getRoot().getStylesheets().remove(1);
        }
        getRoot().getStylesheets().add(VIEW_PATH + theme);
    }

    //@@author
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    public TaskListPanel getTaskListPanel() {
        return this.taskListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author Esilocke
    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(viewTaskPanel.getRoot());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(viewPersonPanel.getRoot());
    }
    //@@author

    @Subscribe
    private void handleBrowserPanelLocateEvent(BrowserPanelLocateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());
    }

    @Subscribe
    private void handleBrowserPanelNavigateEvent(BrowserPanelNavigateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());
    }

    //@@author jeffreygohkw
    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleChangeTheme(event.getStyleSheet());
    }
}
