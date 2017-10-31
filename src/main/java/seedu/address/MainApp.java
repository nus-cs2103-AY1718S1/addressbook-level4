package seedu.address;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.storage.OpenRolodexRequestEvent;
import seedu.address.commons.events.storage.RolodexChangedDirectoryEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyRolodex;
import seedu.address.model.Rolodex;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.RolodexStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlRolodexStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 1, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Rolodex ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        RolodexStorage rolodexStorage = new XmlRolodexStorage(userPrefs.getRolodexFilePath());
        storage = new StorageManager(rolodexStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Reloads the current Rolodex application with new data at the specified filePath.
     * @param newRolodexPath string path of the new Rolodex to be loaded.
     * @throws IOException if unable to save the new filePath into the user preferences
     */
    public void loadNewRolodexPath(String newRolodexPath) throws IOException {
        userPrefs.setRolodexFilePath(newRolodexPath);
        storage.saveUserPrefs(userPrefs);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        RolodexStorage rolodexStorage = new XmlRolodexStorage(userPrefs.getRolodexFilePath());

        storage.setNewRolodexStorage(rolodexStorage);
        Model modelToBeLoaded = initModelManager(storage, userPrefs);
        model.resetData(modelToBeLoaded.getRolodex());
        logic.clearUndoRedoStack();
        EventsCenter.getInstance().post(new RolodexChangedDirectoryEvent(newRolodexPath));
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s rolodex and {@code userPrefs}. <br>
     * The data from the sample rolodex will be used instead if {@code storage}'s rolodex is not found,
     * or an empty rolodex will be used instead if errors occur when reading {@code storage}'s rolodex.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyRolodex> rolodexOptional;
        ReadOnlyRolodex initialData;
        try {
            rolodexOptional = storage.readRolodex();
            if (!rolodexOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Rolodex");
            }
            initialData = rolodexOptional.orElseGet(SampleDataUtil::getSampleRolodex);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Rolodex");
            initialData = new Rolodex();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Rolodex");
            initialData = new Rolodex();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
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
            logger.warning("Problem while reading from the file. Will be starting with an empty Rolodex");
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

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Rolodex " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Rolodex ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleOpenNewRolodexRequestEvent(OpenRolodexRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String savedRolodexFilePath = userPrefs.getRolodexFilePath();
        try {
            loadNewRolodexPath(event.getFilePath());
        } catch (IOException e) {
            logger.severe("Failed to save preferences, reverting to previous Rolodex " + StringUtil.getDetails(e));
            loadPreviousRolodex(savedRolodexFilePath);
        }
    }

    /**
     * Loads the previous Rolodex into the current application and updates the userprefs.
     */
    private void loadPreviousRolodex(String savedFilePath) {
        try {
            loadNewRolodexPath(savedFilePath);
        } catch (Exception e) {
            logger.severe("Failed to initialize previous Rolodex, stopping program " + StringUtil.getDetails(e));
            this.stop();
        }
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
