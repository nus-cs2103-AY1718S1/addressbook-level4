# cqhchan
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.Ui;

```
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
/**
 *
 */
public class LoginCommand extends Command {


    public static final String COMMAND_WORD = "login";
    private static String MESSAGE_LOGIN_ACKNOWLEDGEMENT;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    public LoginCommand(String userName, String userPassword) {
        if (userName.equals("private") && userPassword.equals("password")) {
            MESSAGE_LOGIN_ACKNOWLEDGEMENT = "Login Successful";
            Ui ui = MainApp.getUi();
            ui.restart(userName);
        } else {
            MESSAGE_LOGIN_ACKNOWLEDGEMENT = "Login Failed: " + "username " + userName
                    + " and password " + userPassword + " incorrect";
        }
    }

    @Override
    public CommandResult execute() {

        return new CommandResult(MESSAGE_LOGIN_ACKNOWLEDGEMENT);
    }
}
```
###### \java\seedu\address\logic\commands\LogoutCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.MainApp;
import seedu.address.ui.Ui;

/**
 * The LOgout Function
 **/
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logout as requested ...";

    @Override
    public CommandResult execute() {
        Ui ui = MainApp.getUi();
        ui.restart("address");

        return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
    }

}
```
###### \java\seedu\address\logic\parser\LoginCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 *
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {

            String userPassword = ParserUtil.parseName(argMultimap.getValue(PREFIX_PASSWORD)).get().toString();
            String userName = ParserUtil.parseName(argMultimap.getValue(PREFIX_USERNAME)).get().toString();

            return new LoginCommand(userName, userPassword);
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\ui\Browser.java
``` java
package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * The Browser of the App.
 */
public class Browser extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";

    private static final String FXML = "Browser.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;


    public Browser() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        loadDefaultPage();

    }


    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     *  Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        // loadPage("https://i.pinimg.com/736x/25/9e/ab/
        // 259eab749e20a2594e83025c5cf9c79c--being-a-gentleman-gentleman-rules.jpg");

        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @FXML
    private StackPane browserPanel;

    public BrowserPanel() {
        super(FXML);
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
        browser = new Browser();
        browserPanel.getChildren().add(browser.getRoot());
    }

    /**
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        try {
            browserPanel.getChildren().remove(displayPanel.getRoot());
        } catch (Exception e) {
            logger.info("DisplayPanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(browser.getRoot());
        } catch (Exception e) {
            logger.info("BrowserPanel does not exist");
        }

        browserPanel.getChildren().add(browser.getRoot());
        String personUrl = GOOGLE_SEARCH_URL_PREFIX + person.getAddress().toString() + GOOGLE_SEARCH_URL_SUFFIX;
        browser.loadPage(personUrl);
    }

    /**
     * @param reminder
     */
    private void displayReminder(ReadOnlyReminder reminder) {
        try {
            browserPanel.getChildren().remove(displayPanel.getRoot());
        } catch (Exception e) {
            logger.info("DisplayPanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(browser.getRoot());
        } catch (Exception e) {
            logger.info("BrowserPanel does not exist");
        }
        displayPanel = new DisplayPanel(reminder);
        browserPanel.getChildren().add(displayPanel.getRoot());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser.freeResources();
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java


    @Subscribe
    private void handleReminderPanelSelectionChangedEvent(ReminderPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayReminder(event.getNewSelection().reminder);
    }
```
###### \java\seedu\address\ui\DisplayPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * The Display Panel of the App.
 */
public class DisplayPanel  extends UiPart<Region> {


    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String FXML = "ReminderDisplay.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyReminder reminder;


    @FXML
    private Label task;
    @FXML
    private Label priority;
    @FXML
    private Label datentime;
    @FXML
    private TextArea message;
    @FXML
    private FlowPane tags;

    public DisplayPanel(ReadOnlyReminder reminder) {
        super(FXML);
        this.reminder = reminder;
        initTags(reminder);
        setDisplay(reminder);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void setDisplay(ReadOnlyReminder reminder) {
        task.textProperty().bind(Bindings.convert(reminder.taskProperty()));
        priority.textProperty().bind(Bindings.convert(reminder.priorityProperty()));
        datentime.textProperty().bind(Bindings.convert(reminder.dateProperty()));
        message.textProperty().bind(Bindings.convert(reminder.messageProperty()));
        reminder.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            reminder.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    private void initTags(ReadOnlyReminder reminder) {
        reminder.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
```
###### \java\seedu\address\ui\ReminderCard.java
``` java
package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";

    private static String[] colors = { "red", "gold", "blue", "purple", "orange", "brown",
        "green", "magenta", "black", "grey" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyReminder reminder;

    @javafx.fxml.FXML
    private HBox remindercardPane;
    @FXML
    private Label task;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label datentime;
    @FXML
    private Label message;
    @FXML
    private FlowPane tags;

    public ReminderCard(ReadOnlyReminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        initTags(reminder);
        bindListeners(reminder);
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyReminder reminder) {
        task.textProperty().bind(Bindings.convert(reminder.taskProperty()));
        priority.textProperty().bind(Bindings.convert(reminder.priorityProperty()));
        datentime.textProperty().bind(Bindings.convert(reminder.dateProperty()));
        message.textProperty().bind(Bindings.convert(reminder.messageProperty()));
        reminder.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(reminder);
        });
    }

    /**
     * @param reminder
     */
    private void initTags(ReadOnlyReminder reminder) {
        reminder.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
}

```
###### \java\seedu\address\ui\ReminderListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ReminderPanelSelectionChangedEvent;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Panel containing the list of reminders.
 */
public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);

    @javafx.fxml.FXML
    private ListView<ReminderCard> reminderListView;

    public ReminderListPanel(ObservableList<ReadOnlyReminder> reminderList) {
        super(FXML);
        setConnections(reminderList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyReminder> reminderList) {
        ObservableList<ReminderCard> mappedList = EasyBind.map(
                reminderList, (reminder) -> new ReminderCard(reminder, reminderList.indexOf(reminder) + 1));
        reminderListView.setItems(mappedList);
        reminderListView.setCellFactory(listView -> new ReminderListPanel.ReminderListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        reminderListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new ReminderPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            reminderListView.scrollTo(index);
            reminderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class ReminderListViewCell extends ListCell<ReminderCard> {

        @Override
        protected void updateItem(ReminderCard reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(reminder.getRoot());
            }
        }
    }

}
```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Override
    public void restart(String userName) {
        stop();
        primaryStage = new Stage();

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(userPrefs.getAddressBookFilePath(userName));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        prefs = userPrefs;


        primaryStage.setTitle(config.getAppTitle() + " " + userName);


        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            logger.info("Fatal error during initializing" + e);
        }
    }

    /**
     * get new user prefs
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {

        String prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;

        try {

            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();

            initializedPrefs = prefsOptional.orElse(new UserPrefs());

        } catch (DataConversionException e) {

            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {

            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {

            storage.saveUserPrefs(initializedPrefs);

        } catch (IOException e) {

            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }
    /**
     *
     * @param storage
     * @param userPrefs
     * @return
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }

        return new ModelManager(initialData, userPrefs);
    }
```
