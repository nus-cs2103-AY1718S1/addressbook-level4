package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
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
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ToggleListAllStyleEvent;
import seedu.address.commons.events.ui.ToggleListPinStyleEvent;
import seedu.address.commons.events.ui.ToggleParentChildModeEvent;
import seedu.address.commons.events.ui.ToggleSortByLabelEvent;
import seedu.address.commons.events.ui.ToggleToAliasViewEvent;
import seedu.address.commons.events.ui.ToggleToAllPersonViewEvent;
import seedu.address.commons.events.ui.ToggleToTaskViewEvent;
import seedu.address.commons.events.ui.UpdatePinnedPanelEvent;
import seedu.address.commons.events.ui.ValidResultDisplayEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EnablePersonCommand;
import seedu.address.logic.commands.EnableTaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.ListAliasCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.person.ListPinCommand;
import seedu.address.logic.commands.person.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/blue_bird_logo.png";
    private static final String FXML = "MainWindow.fxml";
    private static final String DIM_LABEL = "-fx-text-fill: #555555";
    private static final String BRIGHT_LABEL = "-fx-text-fill: white";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 800;
    private String lastSorted = "Name";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;
    private Model model;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private SortFindPanel sortFindPanel;
    private AliasListPanel aliasListPanel;
    private TaskListPanel taskListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane tutorialPlaceholder;

    @FXML
    private Label organizedByLabel;

    @FXML
    private Label organizerLabel;

    @FXML
    private Label personViewLabel;

    @FXML
    private Label taskViewLabel;

    @FXML
    private Label aliasViewLabel;

    @FXML
    private Label listPinLabel;

    @FXML
    private Label listAllLabel;

    @FXML
    private ScrollPane helpOverlay;

    @FXML
    private Menu helpMenu;

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

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Model model) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.model = model;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
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

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        aliasListPanel = new AliasListPanel(logic.getFilteredAliasTokenList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
                logic.getFilteredPersonList().size(), logic.getFilteredTaskList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        sortFindPanel = new SortFindPanel(logic);
        sortFindPanelPlaceholder.getChildren().add(sortFindPanel.getRoot());

        if (MainApp.isIsFirstTimeOpen()) {
            TutorialPanel tutorialPanel = new TutorialPanel(this, tutorialPlaceholder);
            tutorialPlaceholder.getChildren().add(tutorialPanel.getRoot());
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
     * Opens the help overlay for parent commands
     */
    @FXML
    private void handleOverlay() {
        helpOverlay.setVisible(true);
    }

    /**
     * Closes the help overlay for parent commands
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
            CommandResult result = logic.execute(ListCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(ListCommand.COMMAND_WORD));
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
            CommandResult result = logic.execute(ListPinCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(ListPinCommand.COMMAND_WORD));
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
            CommandResult result = logic.execute(EnableTaskCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(EnableTaskCommand.COMMAND_WORD));
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
            CommandResult result = logic.execute(EnablePersonCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(EnablePersonCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to toggle to person view using label");
        }
    }

    /**
     * Toggles to alias view.
     */
    @FXML
    private void handleAliasViewClicked() {
        try {
            CommandResult result = logic.execute(ListAliasCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(ListAliasCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to toggle to alias view using label");
        }
    }
    //@@author

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author deep4k
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        if (event.getOldSelection() != null) {
            try {
                model.deselectPerson(event.getOldSelection().person);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
                logger.warning("Failed to DESELECT person card based on clicks");
            }
        }
        try {
            model.selectPerson(event.getNewSelection().person);
            raise(new NewResultAvailableEvent(String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS,
                    event.getSelectedIndex())));
            raise(new ValidResultDisplayEvent(SelectCommand.COMMAND_WORD));
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
            logger.warning("Failed to SELECT person card based on clicks");
        }
    }
    //author

    //@@author Alim95

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
        organizedByLabel.setText(event.toString());
    }

    @Subscribe
    private void handleToggleParentChildModeEvent(ToggleParentChildModeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        helpMenu.setVisible(event.isSetToParentMode);
        aliasViewLabel.setVisible(event.isSetToParentMode);
    }

    @Subscribe
    private void handleToggleToTaskViewEvent(ToggleToTaskViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToTaskView();
    }

    @Subscribe
    private void handleToggleToAliasViewEvent(ToggleToAliasViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToAliasView();
    }

    @Subscribe
    private void handleToggleToAllPersonViewEvent(ToggleToAllPersonViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToPersonView();
    }

    @Subscribe
    private void handleUpdatePinnedPanelEvent(UpdatePinnedPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (listPinLabel.getStyle().equals(BRIGHT_LABEL)) {
            try {
                logic.execute(ListPinCommand.COMMAND_WORD);
            } catch (CommandException | ParseException e) {
                logger.warning("Unable to list pin when unpinning");
            }
        }
    }

    /**
     * Switches style to person view.
     */
    private void switchToPersonView() {
        addSelectedPanel(personListPanel.getRoot());
        setListLabelsVisible(true);
        dimAllViewLabels();
        personViewLabel.setStyle(BRIGHT_LABEL);
        organizerLabel.setText("Sorted By:");
        organizedByLabel.setText(lastSorted);
        lastSorted = organizedByLabel.getText();
        setOrganizerLabelsVisible(true);
    }

    /**
     * Switches style to task view.
     */
    private void switchToTaskView() {
        addSelectedPanel(taskListPanel.getRoot());
        setListLabelsVisible(false);
        dimAllViewLabels();
        taskViewLabel.setStyle(BRIGHT_LABEL);
        organizerLabel.setText("Showing:");
        organizedByLabel.setText("All");
        setOrganizerLabelsVisible(true);
    }

    /**
     * Switches style to alias view.
     */
    private void switchToAliasView() {
        addSelectedPanel(aliasListPanel.getRoot());
        setListLabelsVisible(false);
        dimAllViewLabels();
        aliasViewLabel.setStyle(BRIGHT_LABEL);
        setOrganizerLabelsVisible(false);
    }

    private void setOrganizerLabelsVisible(boolean isVisible) {
        organizerLabel.setVisible(isVisible);
        organizedByLabel.setVisible(isVisible);
    }

    private void setListLabelsVisible(boolean isVisible) {
        listAllLabel.setVisible(isVisible);
        listPinLabel.setVisible(isVisible);
    }

    private void dimAllViewLabels() {
        personViewLabel.setStyle(DIM_LABEL);
        aliasViewLabel.setStyle(DIM_LABEL);
        taskViewLabel.setStyle(DIM_LABEL);
    }

    /**
     * Removes current panel in personListPanelPlaceHolder and adds {@code toAdd} into it.
     */
    private void addSelectedPanel(Region toAdd) {
        personListPanelPlaceholder.getChildren()
                .removeAll(personListPanel.getRoot(), aliasListPanel.getRoot(), taskListPanel.getRoot());
        personListPanelPlaceholder.getChildren().add(toAdd);
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
        listPinLabel.setStyle(DIM_LABEL);
        listAllLabel.setStyle(BRIGHT_LABEL);
    }

    private void listPinToggleStyle() {
        listPinLabel.setStyle(BRIGHT_LABEL);
        listAllLabel.setStyle(DIM_LABEL);
    }
}
