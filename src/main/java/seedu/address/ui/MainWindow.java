package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.PrefDefaultProfilePhotoChangedEvent;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ProfilePhotoChangedEvent;
import seedu.address.commons.events.ui.ShowBrowserEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowMeetingEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.Storage;

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
    private Storage storage;
    private Scene scene;
    private String cssPath;
    private String style;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private MeetingPanel meetingPanel;
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane commandBoxHelperPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private SplitPane settingsPane;

    @FXML
    private StackPane settingsSelectorPlaceholder;

    @FXML
    private StackPane themeSelectorPlaceholder;

    @FXML
    private VBox personListBox;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Storage storage) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.storage = storage;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        style = prefs.getTheme();
        cssPath = "view/";

        switch (style) {
        case "Dark":
            cssPath += "DarkTheme.css";
            break;
        case "Blue":
            cssPath += "BlueTheme.css";
            break;
        default:
            cssPath += "LightTheme.css";
            break;
        }

        scene.getStylesheets().add(cssPath);

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
        browserPanel = new BrowserPanel();
        meetingPanel = new MeetingPanel(logic);
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        //@@author fongwz
        SettingsSelector settingsSelector = new SettingsSelector();
        settingsSelector.selectTheme(style);
        settingsSelectorPlaceholder.getChildren().add(settingsSelector.getRoot());
        //@@author

        setPersonListPanel();

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
                logic.getFilteredPersonList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        //@@author fongwz
        //Setting initial position of settings panel
        settingsPane.setTranslateX(160);

        CommandBox commandBox = new CommandBox(logic, commandBoxHelperPlaceholder, settingsPane);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        //@@author
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

    //@@author liuhang0213
    private void setPersonListPanel() {
        try {
            ObservableList<ReadOnlyPerson> persons = logic.getFilteredPersonList();
            personListPanel = new PersonListPanel(persons);
            personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        } catch (IllegalStateException e) {
            logger.info("Cannot update profile photo on a non-main thread. ¯\\_(ツ)_/¯ "
                    + "Type 'list' to see the new profile photos.");
        }
    }

    //@@author
    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author fongwz
    @Subscribe
    private void handleShowBrowserEvent(ShowBrowserEvent event) {
        browserPlaceholder.getChildren().remove(meetingPanel.getRoot());
        browserPlaceholder.getChildren().add(browserPanel.getRoot());
    }

    @Subscribe
    private void handleShowMeetingEvent(ShowMeetingEvent event) {
        try {
            browserPlaceholder.getChildren().remove(browserPanel.getRoot());
        } catch (IllegalArgumentException e) {
            logger.info("Error removing browser panel : " + e.getMessage());
        }

        try {
            browserPlaceholder.getChildren().add(meetingPanel.getRoot());
        } catch (IllegalArgumentException e) {
            logger.info("Meeting panel is already displayed!");
        }
    }

    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeEvent event) {
        scene.getStylesheets().remove(cssPath);
        cssPath = "";
        cssPath = "view/";

        switch (event.theme) {
        case "Light":
            cssPath += "LightTheme.css";
            break;
        case "Blue":
            cssPath += "BlueTheme.css";
            break;
        default:
            cssPath += "DarkTheme.css";
            break;
        }
        scene.getStylesheets().add(cssPath);
    }

    //@@author liuhang0213
    @Subscribe
    private void handleDefaultProfilePhotoChangedEvent(PrefDefaultProfilePhotoChangedEvent event) {
        ObservableList<ReadOnlyPerson> persons = logic.getFilteredPersonList();
        Task<Void> task = new Task<Void>() {
            @Override public Void call() {
                for (ReadOnlyPerson person : persons) {
                    storage.downloadProfilePhoto(person, prefs.getDefaultProfilePhoto());
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    @Subscribe
    private void handleProfilePhotoChangedEvent(ProfilePhotoChangedEvent event) {
        setPersonListPanel();
    }
}
