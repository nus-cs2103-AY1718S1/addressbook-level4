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
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowContactsEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowMrtRequestEvent;
import seedu.address.commons.events.ui.ShowPsiRequestEvent;
import seedu.address.commons.events.ui.ShowWeatherRequestEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/address_book_32.png";

    private static final String FXML = "MainWindow.fxml";

    private static BrowserPanel browserPanel = new BrowserPanel();
    //Randomize the theme color
    //private static Random random = new Random();
    //private static String[] themeColors = {"MainWindow_Black.fxml", "MainWindow_White.fxml"};
    //private static final String FXML = themeColors[random.nextInt(themeColors.length)];

    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PlaceListPanel placeListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem weatherItem;

    @FXML
    private MenuItem helpMenuItem;
    //@@author huyuanrong
    @FXML
    private MenuItem contactsMenuItem;
    //@@author
    //@@author aungmyin23
    @FXML
    private MenuItem mrtMapItem;
    @FXML
    private MenuItem psiItem;
    //@@author
    @FXML
    private StackPane placeListPanelPlaceholder;

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

        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        //@@author huyuanrong
        setAccelerator(contactsMenuItem, KeyCombination.valueOf("F2"));
        //@@author
        setAccelerator(mrtMapItem, KeyCombination.valueOf("F3"));
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
        //browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        //browserPanel.loadPage("http://www.nea.gov.sg/weather-climate/forecasts/24-hour-forecast");

        placeListPanel = new PlaceListPanel(logic.getFilteredPlaceList());
        placeListPanelPlaceholder.getChildren().add(placeListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        //StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
            logic.getFilteredPlaceList().size());
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

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    //@@author thanhson16198
    public static void loadUrl(String url) {
        browserPanel.loadPage(url);
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
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    //@@author thanhson16198
    /**
     * Opens the Weather on browser.
     */
    @FXML
    public void handleWeather() {
        logger.info("Open a weather forecast for today on BrowerPanel.");
        browserPanel.loadPage("https://www.accuweather.com/en/sg/singapore/300597/hourly-weather-forecast/300597");
    }
    //@@author
    //@@author huyuanrong
    /**
     * Opens the useful contacts window.
     */
    @FXML
    public void showNumbers() {
        ContactWindow contactWindow = new ContactWindow();
        contactWindow.show();
    }
    //@@author
    //@aurhor aungmyin23
    /**
     * Opens the mrt map window.
     */
    @FXML
    public void handleMrtMap() {
        MrtWindow mrtWindow = new MrtWindow();
        mrtWindow.show();
    }
    /**
     * Opens the have webstite at Browser window.
     */
    @FXML
    public void handlePsi() {
        logger.info("Open the PSI value around Singapore for today on BrowerPanel.");
        browserPanel.loadPage("http://www.haze.gov.sg/air-quality-information");
    }
    //@@author

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

    public PlaceListPanel getPlaceListPanel() {
        return this.placeListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author thanhson16198
    @Subscribe
    private void handleShowWeatherEvent(ShowWeatherRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleWeather();
    }
    //@@author
    //@@author huyuanrong
    @Subscribe
    private void handleShowContactsEvent(ShowContactsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showNumbers();
    }
    //@@author
    //@@author aungmyin23
    @Subscribe
    private void handleShowMrtEvent(ShowMrtRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleMrtMap();

    }

    @Subscribe
    private void handleShowPsiEvent(ShowPsiRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handlePsi();

    }
    //@@author aungmyin23
}
