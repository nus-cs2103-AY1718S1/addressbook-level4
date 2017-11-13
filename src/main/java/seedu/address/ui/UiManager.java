package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AccountsStorage;
import seedu.address.storage.StorageManager;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    public static final String FILE_OPS_ERROR_DIALOG_STAGE_TITLE = "File Op Error";
    public static final String FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE = "Could not save data";
    public static final String FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE = "Could not save data to file";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    //@@author yangminxingnus
    private Logic logic;
    private StorageManager storage;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;
    private LoginPage loginPage;
    private AccountsStorage accPrefs;
    private RegisterPage registerPage;

    private int test;
    public UiManager(Logic logic, Config config, StorageManager storage, UserPrefs prefs, AccountsStorage accPrefs) {
        super();
        this.logic = logic;
        this.storage = storage;
        this.config = config;
        this.prefs = prefs;
        this.accPrefs = accPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));


        try {
            loginPage = new LoginPage(primaryStage, config, storage, prefs, logic, accPrefs, this);
            loginPage.show();
        } catch (Throwable e) {
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void start(Stage primaryStage, int test) {
        this.test = 1;
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, storage, prefs, logic, accPrefs, this);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
        } catch (Throwable e) {
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        if (test == 1) {
            prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
            mainWindow.releaseResources();
            mainWindow.hide();
        } else {
            prefs.updateLastUsedGuiSetting(loginPage.getCurrentGuiSetting());
            loginPage.releaseResources();
            loginPage.hide();

        }
    }
    //@@author

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, FILE_OPS_ERROR_DIALOG_STAGE_TITLE, description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }
    //@@author yangminxingnus
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setRegisterPage(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }
    //@@author
    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code ===============================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait(FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE, FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE,
                event.exception);
    }
}
