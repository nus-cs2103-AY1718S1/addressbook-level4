package seedu.address;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyRolodex;
import seedu.address.model.Rolodex;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.RolodexStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlRolodexStorage;
import seedu.address.storage.XmlSerializableRolodex;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.rldx");
    public static final String SECONDARY_SAVE_LOCATION = TestUtil.getFilePathInSandboxFolder("tempRolodex.rldx");
    public static final String APP_TITLE = "Test App";

    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected static final String ROLODEX_NAME = "Test";
    protected Supplier<ReadOnlyRolodex> initialDataSupplier = () -> null;
    protected String userPrefsLocation = DEFAULT_PREF_FILE_LOCATION_FOR_TESTING;
    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyRolodex> initialDataSupplier, String saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableRolodex(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
    }

    @Override
    protected Config initConfig(String configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(userPrefsLocation);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setRolodexFilePath(saveFileLocation);
        userPrefs.setRolodexName(ROLODEX_NAME);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the rolodex data stored inside the storage file.
     */
    public Rolodex readStorageRolodex() {
        try {
            return new Rolodex(storage.readRolodex().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the Rolodex format.");
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.");
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public String getStorageSaveLocation() {
        return storage.getRolodexFilePath();
    }

    /**
     * Returns a defensive copy of the UndoRedoStack.
     */
    public UndoRedoStack getUndoRedoStack() {
        return new UndoRedoStack(logic.getUndoRedoStack());
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getRolodex()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getLatestPersonList());
        return copy;
    }

    /**
     * Returns a defensive copy of the storage.
     */
    public Storage getStorage() {
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefsCopy = initPrefs(userPrefsStorage);
        RolodexStorage rolodexStorage = new XmlRolodexStorage(userPrefsCopy.getRolodexFilePath());

        return new StorageManager(rolodexStorage, userPrefsStorage);
    }

    /**
     * Returns a defensive copy of the user prefs.
     */
    public UserPrefs getUserPrefs() {
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs copy = initPrefs(userPrefsStorage);
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
