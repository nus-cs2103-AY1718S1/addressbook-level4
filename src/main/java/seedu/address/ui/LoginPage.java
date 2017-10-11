package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.loginLogic;
import seedu.address.model.UserPrefs;

import javax.swing.*;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class LoginPage extends UiPart<Region> {


    private static final String FXML = "Login.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private loginLogic logic;
    private static boolean session;

    // Independent Ui parts residing in this Ui container


    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;





    public LoginPage(Stage primaryStage, loginLogic logic) {


        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        logger.info("login finished");

        // Configure the UI
        setWindowMinSize();
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);


        registerAsAnEventHandler(this);
    }

    @FXML
    private  void handleCommandInputChanged() {
            logger.info("enter registered");
            boolean result = logic.execute(usernameTextField.getText(), passwordTextField.getText());
            session = result;
            // process result of the command
            logger.info(usernameTextField.getText());
            logger.info(passwordTextField.getText());
            logger.info("Result: " + result);


    }

    public boolean getSession(){
        return session;

    }

    public Stage getPrimaryStage() {
        return primaryStage;
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


    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
}
