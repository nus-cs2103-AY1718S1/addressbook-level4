package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;

public class WelcomeScreen extends UiPart<Region> {
    private static final String ICON = "/images/contag_logo.png";
    private static final String LOGO = "/images/contag_logo_high_res.png";
    private static final String FXML = "WelcomeScreen.fxml";
    private static final int FIXED_HEIGHT = 450;
    private static final int FIXED_WIDTH = 450;

    private Stage primaryStage;
    private Config config;
    private UserPrefs prefs;
    private Logic logic;
    private Model model;

    private MainWindow mainWindow;

    @FXML
    private VBox welcomeWindow;

    @FXML
    private ImageView logo;

    @FXML
    private Button closeButton;

    public WelcomeScreen(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Model model) {
        super(FXML);

        // main window dependencies
        this.primaryStage = primaryStage;
        this.config = config;
        this.prefs = prefs;
        this.logic = logic;
        this.model = model;

        primaryStage.initStyle(StageStyle.UNDECORATED);
        setWindowFixedSize();
        setIcon(ICON);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
    }

    private void setWindowFixedSize() {
        primaryStage.setHeight(FIXED_HEIGHT);
        primaryStage.setWidth(FIXED_WIDTH);
        primaryStage.setResizable(false);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        logo = new ImageView(new Image(LOGO));
        welcomeWindow.getChildren().add(logo);

        closeButton = new Button("Close");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                welcomeWindow.getScene().getWindow().hide();
                loadMainWindow();
            }
        });
        welcomeWindow.getChildren().add(closeButton);
    }

    private void loadMainWindow() {
        Stage newStage = new Stage();
        mainWindow = new MainWindow(newStage, config, prefs, logic, model);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
    }

    void show() {
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets the given image as the icon of the main window.
     *
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }
}
