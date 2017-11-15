package seedu.address.ui;

import java.io.File;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.HideCalendarEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowCalendarEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowPhotoSelectionEvent;
import seedu.address.commons.events.ui.ToggleTimetableEvent;

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

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    //@@author sebtsh
    private PersonPanel personPanel;
    private EventPanel eventPanel;
    //@@author
    private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private EventListPanel eventListPanel;
    //@@author reginleiff
    private EventPanel timetablePanel;
    private TimetableListPanel timetableListPanel;
    //@@author
    private CalendarView calendarView;
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

    //@@author reginleiff
    @FXML
    private StackPane eventListPanelPlaceholder;

    @FXML
    private StackPane timetableListPanelPlaceholder;

    @FXML
    private SplitPane timetable;
    //@@author

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab eventTab;

    @FXML
    private Tab contactTab;

    @FXML
    private AnchorPane notificationButton;

    @FXML
    private AnchorPane calendarButton;

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
        //@@author sebtsh
        eventPanel = new EventPanel(logic);

        personPanel = new PersonPanel(logic);
        //@@author

        timetablePanel = new EventPanel(logic);

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        timetable.managedProperty().bind(timetable.visibleProperty());

        eventListPanel = new EventListPanel(logic.getFilteredEventList());
        eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

        timetableListPanel = new TimetableListPanel(logic.getTimetable());
        timetableListPanelPlaceholder.getChildren().add(timetableListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs
                .getAddressBookFilePath(), logic.getFilteredPersonList().size(), logic.getFilteredEventList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        //@@author sebtsh
        personPanel.setDimensions(personListPanel.getRoot().getHeight(),
                primaryStage.getWidth() - personListPanel.getRoot().getWidth());
        //@@author

        //@@author shuang-yang
        //When calendar button is clicked, the browserPlaceHolder will switch
        // to the calendar view
        calendarView = new CalendarView(logic.getFilteredEventList(), logic);
        calendarButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!browserPlaceholder.getChildren().contains(calendarView
                        .getRoot())) {
                    browserPlaceholder.getChildren().add(calendarView.getRoot());
                    raise(new ShowCalendarEvent());
                } else {
                    browserPlaceholder.getChildren().remove(calendarView.getRoot());
                }
            }
        });
        //@@author
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


    //@@author shuang-yang
    /**
     * Opens the calendar view.
     */
    @FXML
    public void handleShowCalendar() {
        if (!browserPlaceholder.getChildren().contains(calendarView.getRoot()
        )) {
            browserPlaceholder.getChildren().add(calendarView
                    .getRoot());
        }
    }

    /**
     * Hides the calendar view.
     */
    @FXML
    public void handleHideCalendar() {
        if (browserPlaceholder.getChildren().contains(calendarView.getRoot())) {
            browserPlaceholder.getChildren().remove(calendarView
                    .getRoot());
        }
    }

    //@@author sebtsh
    /**
     * Called when a person panel is selected. Hides other panels and displays the person panel.
     */
    @FXML
    public void handlePersonPanelSelected() {
        if (browserPlaceholder.getChildren().contains(eventPanel.getRoot())) {
            browserPlaceholder.getChildren().remove(eventPanel.getRoot());
        }
        if (browserPlaceholder.getChildren().contains(calendarView.getRoot())) {
            browserPlaceholder.getChildren().remove(calendarView.getRoot());
        }
        browserPlaceholder.getChildren().add((personPanel.getRoot()));
    }

    /**
     * Called when an event panel is selected. Hides other panels and displays the event panel.
     */
    @FXML
    public void handleEventPanelSelected() {
        if (browserPlaceholder.getChildren().contains(personPanel.getRoot())) {
            browserPlaceholder.getChildren().remove(personPanel.getRoot());
        }
        if (browserPlaceholder.getChildren().contains(eventPanel.getRoot())) {
            browserPlaceholder.getChildren().remove(eventPanel.getRoot());
        }
        if (browserPlaceholder.getChildren().contains(calendarView.getRoot())) {
            browserPlaceholder.getChildren().remove(calendarView.getRoot());
        }
        browserPlaceholder.getChildren().add((eventPanel.getRoot()));
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

    public EventListPanel getEventListPanel() {
        return this.eventListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author shuang-yang
    @Subscribe
    private void handleShowCalendarEvent(ShowCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (browserPlaceholder.getChildren().contains(personPanel.getRoot())) {
            browserPlaceholder.getChildren().remove(personPanel.getRoot());
        }
        if (browserPlaceholder.getChildren().contains(eventPanel.getRoot())) {
            browserPlaceholder.getChildren().remove(eventPanel.getRoot());
        }
        handleShowCalendar();
    }

    @Subscribe
    private void handleHideCalendarEvent(HideCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHideCalendar();
    }

    /**
     * On receiving ShowPhotoSelectionEvent, display a file chooser window to choose photo from local file system and
     * update the photo of specified person.
     */
    @Subscribe
    private void handleShowPhotoSelectionEvent(ShowPhotoSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJpg = new FileChooser
                .ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterJpeg = new FileChooser
                .ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG");
        FileChooser.ExtensionFilter extFilterPng = new FileChooser
                .ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJpg,
                extFilterJpeg, extFilterPng);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(primaryStage.getScene().getWindow());

        try {
            logic.execute("edit " + event.index.getOneBased() + " ph/"
                    + file.getAbsolutePath());
        } catch (CommandException | ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }
    //@@author sebtsh
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handlePersonPanelSelected();
    }

    @Subscribe
    private void handleEventPanelSelectionChangedEvent(EventPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleEventPanelSelected();
    }
    //@@author

    //@@author reginleiff
    @Subscribe
    public void handleToggleTimetableEvent(ToggleTimetableEvent event) {
        boolean timetableIsVisible = timetable.visibleProperty().getValue();
        if (timetableIsVisible) {
            hideTimetable();
        } else {
            showTimeTable();
        }
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    /**
     * Hides the timetable view.
     */
    void hideTimetable() {
        timetable.setVisible(false);
    }

    /**
     * Shows the timetable view.
     */
    void showTimeTable() {
        timetable.setVisible(true);
    }
    //@@author
}
