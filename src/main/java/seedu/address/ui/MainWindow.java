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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/AddressBookIcon.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 768;
    private static final int MIN_WIDTH = 1366;
    private static String currentTheme;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonPanel personPanel;
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane personPanelPlaceholder;

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

    @FXML
    private VBox mainWindow;

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
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        mainWindow.getStylesheets().add("view/Extensions.css");

        personPanel = new PersonPanel(logic);
        personPanelPlaceholder.getChildren().add(personPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(
                prefs.getAddressBookFilePath(), logic.getFilteredPersonList().size()
        );
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
     * @param iconSource e.g. {@code "/images/HelpIcon.png"}
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
        primaryStage.setResizable(false);
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    //@@author teclu
    @Subscribe
    public void handleChangeThemeRequestEvent(ChangeThemeRequestEvent event) throws CommandException, ParseException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme(event.theme + "Theme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
    }

    @FXML
    private void setToLightTheme() {
        if (checkSameTheme("Light")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("LightTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Light"));
        raise(new NewResultAvailableEvent("Theme updated to: Light", false));
    }

    @FXML
    private void setToDarkTheme() {
        if (checkSameTheme("Dark")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("DarkTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Dark"));
        raise(new NewResultAvailableEvent("Theme updated to: Dark", false));
    }

    @FXML
    private void setToRedTheme() {
        if (checkSameTheme("Red")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("RedTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Red"));
        raise(new NewResultAvailableEvent("Theme updated to: Red", false));
    }

    @FXML
    private void setToBlueTheme() {
        if (checkSameTheme("Blue")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("BlueTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Blue"));
        raise(new NewResultAvailableEvent("Theme updated to: Blue", false));
    }

    @FXML
    private void setToGreenTheme() {
        if (checkSameTheme("Green")) {
            return;
        }
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme("GreenTheme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent("Green"));
        raise(new NewResultAvailableEvent("Theme updated to: Green", false));
    }

    /**
     * Returns true if the theme to be set is already in place and raises an event to the user.
     */
    private boolean checkSameTheme(String theme) {
        if (currentTheme.contains(theme)) {
            raise(new NewResultAvailableEvent("Theme is already set to " + theme + "!", true));
            return true;
        }
        return false;
    }
    //@@author

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Returns the current theme.
     */
    public static String getCurrentTheme() {
        return currentTheme;
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

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
}
