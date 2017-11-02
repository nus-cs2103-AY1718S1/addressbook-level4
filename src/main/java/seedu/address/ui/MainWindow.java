package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.SwitchToBrowserEvent;
import seedu.address.commons.events.ui.ToggleListAllStyleEvent;
import seedu.address.commons.events.ui.ToggleListPinStyleEvent;
import seedu.address.commons.events.ui.ToggleSortByLabelEvent;
import seedu.address.commons.events.ui.ToggleToPersonViewEvent;
import seedu.address.commons.events.ui.ToggleToTaskViewEvent;
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

    private static final String ICON = "/images/blue_bird_logo.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;
    private String lastSorted = "Name";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private SortFindPanel sortFindPanel;

    private TaskListPanel taskListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private Label sortedByLabel;

    @FXML
    private Label organizerLabel;

    @FXML
    private Label personViewLabel;

    @FXML
    private Label taskViewLabel;

    @FXML
    private Label pinLabel;

    @FXML
    private Label allLabel;

    @FXML
    private ScrollPane helpOverlay;

    @FXML
    private MenuItem helpOverlayItem;

    @FXML
    private MenuItem helpOverlayExit;

    @FXML
    private StackPane taskListPlaceHolder;


    @FXML
    private HBox sortFindPanelPlaceholder;

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

        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(helpOverlayItem, KeyCombination.valueOf("F2"));
        setAccelerator(helpOverlayExit, KeyCombination.valueOf("ESC"));
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

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
                logic.getFilteredPersonList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        sortFindPanel = new SortFindPanel(logic);
        sortFindPanelPlaceholder.getChildren().add(sortFindPanel.getRoot());

        browserPanel = new BrowserPanel();
        if (MainApp.isFirstTimeOpen()) {
            TutorialPanel tutorialPanel = new TutorialPanel(this, browserPlaceholder);
            browserPlaceholder.getChildren().add(tutorialPanel.getRoot());
        } else {
            switchToBrowser();
        }
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

    //@@author Alim95

    /**
     * Opens the help overlay
     */
    @FXML
    private void handleOverlay() {
        helpOverlay.setVisible(true);
    }

    /**
     * Closes the help overlay
     */
    @FXML
    private void handleOverlayExit() {
        helpOverlay.setVisible(false);
    }

    /**
     * Lists all Person in Bluebird.
     */
    @FXML
    private void handleListAllClicked() {
        try {
            logic.execute("list");
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to list all using label");
        }
    }

    /**
     * Lists pinned Person in Bluebird.
     */
    @FXML
    private void handleListPinnedClicked() {
        try {
            logic.execute("listpin");
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to list pinned using label");
        }
    }

    /**
     * Toggles to task view.
     */
    @FXML
    private void handleTaskViewClicked() {
        try {
            logic.execute("task");
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to toggle to task view using label");
        }
    }

    /**
     * Toggles to person view.
     */
    @FXML
    private void handlePersonViewClicked() {
        try {
            logic.execute("person");
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to toggle to person view using label");
        }
    }
    //@@author

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

    //@@author Alim95
    @Subscribe
    private void handleSwitchToBrowserEvent(SwitchToBrowserEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToBrowser();
    }

    @Subscribe
    private void handleShowPinnedListEvent(ToggleListPinStyleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        listPinToggleStyle();
    }

    @Subscribe
    private void handleShowAllListEvent(ToggleListAllStyleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        listAllToggleStyle();
    }

    @Subscribe
    private void handleSortByLabelEvent(ToggleSortByLabelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        sortedByLabel.setText(event.toString());
    }

    @Subscribe
    private void handleToggleToTaskViewEvent(ToggleToTaskViewEvent event) {
        switchToTaskView();
    }


    @Subscribe
    private void handleToggleToPersonViewEvent(ToggleToPersonViewEvent event) {
        switchToPersonView();
    }

    /**
     * Switches style to person view.
     */
    private void switchToPersonView() {
        personListPanelPlaceholder.getChildren().removeAll(taskListPanel.getRoot());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        allLabel.setVisible(true);
        pinLabel.setVisible(true);
        organizerLabel.setText("Sorted By:");
        personViewLabel.setStyle("-fx-text-fill: white");
        taskViewLabel.setStyle("-fx-text-fill: #555555");
        sortedByLabel.setText(lastSorted);
    }

    /**
     * Switches style to task view.
     */
    private void switchToTaskView() {
        personListPanelPlaceholder.getChildren().removeAll(personListPanel.getRoot());
        personListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());
        allLabel.setVisible(false);
        pinLabel.setVisible(false);
        organizerLabel.setText("Showing:");
        personViewLabel.setStyle("-fx-text-fill: #555555");
        taskViewLabel.setStyle("-fx-text-fill: white");
        lastSorted = sortedByLabel.getText();
        sortedByLabel.setText("All");
    }

    private void switchToBrowser() {
        browserPlaceholder.getChildren().add(browserPanel.getRoot());
    }

    /**
     * Unhighlights all the UIs during tutorial.
     */
    public void unhighlightAll() {
        personListPanel.unhighlight();
        commandBox.unhighlight();
        resultDisplay.unhighlight();
        sortFindPanel.unhighlight();
    }

    public void highlightCommandBox() {
        commandBox.highlight();
    }

    public void highlightResultDisplay() {
        resultDisplay.highlight();
    }

    public void highlightSortMenu() {
        sortFindPanel.highlightSortMenu();
    }

    public void highlightSearchBox() {
        sortFindPanel.highlightSearchBox();
    }

    public void highlightPersonListPanel() {
        personListPanel.highlight();
    }

    private void listAllToggleStyle() {
        pinLabel.setStyle("-fx-text-fill: #555555");
        allLabel.setStyle("-fx-text-fill: white");
    }

    private void listPinToggleStyle() {
        pinLabel.setStyle("-fx-text-fill: white");
        allLabel.setStyle("-fx-text-fill: #555555");
    }
}
