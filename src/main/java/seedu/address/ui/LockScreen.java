package seedu.address.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import seedu.address.commons.core.Config;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
//@@author moomeowroar
/**
 * The Lock Screen Window.
 * Provides a password field to enter the password.
 */
public class LockScreen extends UiPart<Region> {

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "LockScreen.fxml";
    private static final int MIN_HEIGHT = 100;
    private static final int MIN_WIDTH = 300;
    private int x = 0;
    private int y = 0;
    private String theme = "";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private Stage primaryStage;
    private MainWindow mainWindow;

    @FXML
    private StackPane pwdBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private VBox vBox;

    public LockScreen(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle("Login");
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize();
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        initThemeFromSettings();
    }

    /**
     * Loads the passwordField into the stage.
     */
    void fillInnerParts() {
        PasswordField pwBox = new PasswordField();
        pwBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if (logic.isPassword(pwBox.getText())) {
                    loadMainWindow();
                } else {
                    setTitle("Wrong Password!");
                    pwBox.setStyle("-fx-text-box-border: #f44b42; -fx-focus-color: #f44b42;");
                    shakeStage();
                }
                pwBox.clear();
            }
        });
        pwdBoxPlaceholder.getChildren().add(pwBox);
    }

    private void initThemeFromSettings() {
        theme = prefs.getGuiSettings().getTheme();
        vBox.getStylesheets().remove(0);
        vBox.getStylesheets().add("/view/" + theme + ".css");
    }

    void loadMainWindow() {
        mainWindow = new MainWindow(primaryStage, config, prefs, logic);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
    }

    /**
     * Show the stage.
     */
    void show() {
        primaryStage.show();
    }

    /**
     * Hide the stage.
     */
    void hide() {
        if (mainWindow != null) {
            prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
            mainWindow.releaseResources();
        }
        primaryStage.hide();
    }

    public Stage getMainWindowPrimaryStage() {
        return mainWindow.getPrimaryStage();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
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
    private void setWindowDefaultSize() {
        primaryStage.setHeight(MIN_HEIGHT);
        primaryStage.setWidth(MIN_WIDTH);
    }

    /**
     * Sets the window minimum size.
     */
    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Function to animate the stage for wrong password.
     */
    private void shakeStage() {
        Timeline timelineX = new Timeline(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (x == 0) {
                    primaryStage.setX(primaryStage.getX() + 10);
                    x = 1;
                } else {
                    primaryStage.setX(primaryStage.getX() - 10);
                    x = 0;
                }
            }
        }));

        timelineX.setCycleCount(4);
        timelineX.setAutoReverse(false);
        timelineX.play();

        Timeline timelineY = new Timeline(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (y == 0) {
                    primaryStage.setY(primaryStage.getY() + 10);
                    y = 1;
                } else {
                    primaryStage.setY(primaryStage.getY() - 10);
                    y = 0;
                }
            }
        }));

        timelineY.setCycleCount(4);
        timelineY.setAutoReverse(false);
        timelineY.play();
    }
}
