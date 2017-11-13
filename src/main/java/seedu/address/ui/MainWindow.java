package seedu.address.ui;

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
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.NearbyPersonNotInCurrentListEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.BlacklistCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.OverdueListCommand;
import seedu.address.logic.commands.WhitelistCommand;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/sharkie.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private InfoPanel infoPanel;
    private StartUpPanel startUpPanel;
    private PersonListPanel personListPanel;
    private PersonListStartUpPanel personListStartUpPanel;
    private Config config;
    private UserPrefs prefs;
    private CommandBox commandBox = null;
    private HelpWindow helpWindow;

    @FXML
    private StackPane infoPanelPlaceholder;

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

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        helpWindow = new HelpWindow();

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
        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), ListCommand.COMMAND_WORD);
        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        infoPanel = new InfoPanel(logic);
        infoPanelPlaceholder.getChildren().clear();
        infoPanelPlaceholder.getChildren().add(infoPanel.getRoot());
        fillInnerPartsForCommandBox();
    }

    //@@author jelneo
    /**
     * Fills up all the placeholders of window once the app starts up.
     * Should only display welcome page without contacts.
     */
    void fillInnerPartsForStartUp() {
        startUpPanel = new StartUpPanel(primaryStage);
        infoPanelPlaceholder.getChildren().clear();
        infoPanelPlaceholder.getChildren().add(startUpPanel.getRoot());

        personListStartUpPanel = new PersonListStartUpPanel();
        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListStartUpPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up the placeholder of command box.
     */
    void fillInnerPartsForCommandBox() {
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Changes from command box to login view with text fields for username and password.
     */
    public void fillCommandBoxWithLoginFields() {
        LoginView loginView = new LoginView(logic);
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(loginView.getRoot());
    }

    //@@author jaivigneshvenugopal
    /**
     * Fills up the placeholders of PersonListPanel with the given list name.
     * Should only display welcome page without contacts.
     */
    void fillInnerPartsWithIndicatedList(String listName) {

        switch(listName) {

        case BlacklistCommand.COMMAND_WORD:
            personListPanel = new PersonListPanel(logic.getFilteredBlacklistedPersonList(), listName);
            break;
        case WhitelistCommand.COMMAND_WORD:
            personListPanel = new PersonListPanel(logic.getFilteredWhitelistedPersonList(), listName);
            break;
        case OverdueListCommand.COMMAND_WORD:
            personListPanel = new PersonListPanel(logic.getFilteredOverduePersonList(), listName);
            break;
        default:
            personListPanel = new PersonListPanel(logic.getFilteredPersonList(), ListCommand.COMMAND_WORD);
        }

        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        infoPanel = new InfoPanel(logic);
        infoPanelPlaceholder.getChildren().clear();
        infoPanelPlaceholder.getChildren().add(infoPanel.getRoot());
    }

    //@@author khooroko
    /**
     * Changes the current theme.
     */
    private void changeTheme() {
        for (String stylesheet : getRoot().getStylesheets()) {
            if (stylesheet.endsWith("DarkTheme.css")) {
                getRoot().getStylesheets().remove(stylesheet);
                getRoot().getStylesheets().add("/view/BrightTheme.css");
                break;
            } else if (stylesheet.endsWith("BrightTheme.css")) {
                getRoot().getStylesheets().remove(stylesheet);
                getRoot().getStylesheets().add("/view/DarkTheme.css");
                break;
            }
        }
    }

    //@@author
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

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
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

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author khooroko
    /**
     * Sets the person panel to display the unfiltered masterlist and raises a {@code JumpToListRequestEvent}.
     */
    @Subscribe
    private void handleNearbyPersonNotInCurrentListEvent(NearbyPersonNotInCurrentListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        logic.resetFilteredPersonList();
        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), ListCommand.COMMAND_WORD);
        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        raise(new JumpToListRequestEvent(Index.fromZeroBased(logic.getFilteredPersonList()
                .indexOf(event.getNewSelection().person))));
    }

    /**
     * Handles a request to change theme.
     */
    @Subscribe
    private void handleChangeThemeRequestEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeTheme();
    }
}
