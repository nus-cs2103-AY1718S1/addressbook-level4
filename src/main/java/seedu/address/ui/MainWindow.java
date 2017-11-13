package seedu.address.ui;

import java.time.YearMonth;
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
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.commons.events.ui.EventPanelUnselectEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.PersonPanelUnselectEvent;
import seedu.address.commons.events.ui.PopulateMonthEvent;
import seedu.address.commons.events.ui.PopulateRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowThemeRequestEvent;
import seedu.address.commons.events.ui.TogglePanelEvent;

import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.EventsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/address_icon_32.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;
    private static final int CURRENT_THEME_INDEX = 1;
    private static final String VIEW_PATH = "/view/";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private DetailsPanel detailsPanel;
    private EventsDetailsPanel eventsDetailsPanel;
    private PersonListPanel personListPanel;
    private EventListPanel eventListPanel;
    private Config config;
    private UserPrefs prefs;
    private Calendar calendar;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane detailsPanelPlaceholder;

    @FXML
    private StackPane eventsDetailsPanelPlaceholder;

    @FXML
    private StackPane personAndEventDetailsPanelPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane eventListPanelPlaceholder;

    @FXML
    private StackPane personAndEventListPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane calendarPanel;

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
        browserPlaceholder.getChildren().add(browserPanel.getRoot());
        browserPanel.setDefaultPage(prefs.getTheme());

        detailsPanel = new DetailsPanel();
        detailsPanelPlaceholder.getChildren().add(detailsPanel.getRoot());

        eventsDetailsPanel = new EventsDetailsPanel();
        eventsDetailsPanelPlaceholder.getChildren().add(eventsDetailsPanel.getRoot());

        eventListPanel = new EventListPanel(logic.getFilteredEventList());
        eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        personAndEventListPlaceholder.getChildren().add(personListPanelPlaceholder);
        personAndEventListPlaceholder.getChildren().add(eventListPanelPlaceholder);

        personListPanelPlaceholder.toFront();

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        calendar = new Calendar(YearMonth.now(), logic.getFilteredEventList());
        calendarPanel.getChildren().add(calendar.getView());
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

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    //@@author itsdickson
    /**
     * Sets the default theme based on user preferences.
     */
    private void setWindowDefaultTheme(UserPrefs prefs) {
        getRoot().getStylesheets().add(prefs.getTheme());
    }

    /**
     * Returns the current theme of the main Window.
     */
    String getCurrentTheme() {
        return getRoot().getStylesheets().get(CURRENT_THEME_INDEX);
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

    //@@author itsdickson
    /**
     * Opens the theme window.
     */
    @FXML
    public void handleThemes() {
        ThemesWindow themesWindow = new ThemesWindow();
        themesWindow.show();
    }

    /**
     * Changes the theme based on the input theme.
     */
    public void handleChangeTheme(String theme) {
        if (getRoot().getStylesheets().size() > 1) {
            getRoot().getStylesheets().remove(CURRENT_THEME_INDEX);
        }
        getRoot().getStylesheets().add(VIEW_PATH + theme);
    }

    /**
     * Toggles the list panel based on the input panel.
     */
    public void handleToggle(String selectedPanel) {
        if (selectedPanel.equals(EventsCommand.COMMAND_WORD)) {
            eventListPanelPlaceholder.toFront();
        } else if (selectedPanel.equals(ListCommand.COMMAND_WORD)) {
            personListPanelPlaceholder.toFront();
        }
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

    //@@author itsdickson
    @Subscribe
    private void handleShowThemesEvent(ShowThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleThemes();
    }

    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleChangeTheme(event.theme);
        browserPanel.setDefaultPage(event.theme);
        logic.setCurrentTheme(getCurrentTheme());
    }
    //@@author

    //@@author DarrenCzen
    @Subscribe
    private void handleToggleEvent(TogglePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleToggle(event.selectedPanel);
    }
    //@@author

    //@@author archthegit
    @Subscribe
    private void handleUnselectOfPersonCardEvent(PersonPanelUnselectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        detailsPanel = new DetailsPanel();
        detailsPanelPlaceholder.getChildren().clear();
        detailsPanelPlaceholder.getChildren().add(detailsPanel.getRoot());
    }
    //@@author

    //@@author DarrenCzen
    @Subscribe
    private void handleUnselectOfEventCardEvent(EventPanelUnselectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        eventsDetailsPanel = new EventsDetailsPanel();
        eventsDetailsPanelPlaceholder.getChildren().clear();
        eventsDetailsPanelPlaceholder.getChildren().add(eventsDetailsPanel.getRoot());
    }
    //@@author

    //@@author chernghann
    /**
     * this method is to populate the calendar when there is an add event.
     * @param request
     */
    @Subscribe
    private void handlePopulateEvent(PopulateRequestEvent request) {
        logger.info(LogsCenter.getEventHandlingLogMessage(request));
        calendar.populateUpdatedCalendar(request.eventList);
    }

    /**
     * For populating the calendar when starting the application and changing the months
     * @param request
     */
    @Subscribe
    private void handlePopulateMonthEvent(PopulateMonthEvent request) {
        logger.info(LogsCenter.getEventHandlingLogMessage(request));
        calendar.populateCalendar(request.yearMonth, logic.getFilteredEventList());
    }
    //@@author

}
