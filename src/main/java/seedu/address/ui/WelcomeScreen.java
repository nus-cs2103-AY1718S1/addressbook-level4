package seedu.address.ui;

import static seedu.address.logic.commands.HelpCommand.COMMAND_QUICK_HELP;

import java.util.Random;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;

//@@author CT15
/**
 * The Welcome Screen is opened when the application is run. Main Window will be
 * loaded when the welcome screen is closed.
 */
public class WelcomeScreen extends UiPart<Region> {
    private static final String ICON = "/images/contag_logo.png";
    private static final String LOGO = "/images/contag_logo_high_res.png";
    private static final String FXML = "WelcomeScreen.fxml";

    private static String[] quotes = {
        "The perfect is the enemy of the good. — Voltaire",
        "There is no substitute for hard work. — Thomas A. Edison",
        "If you spend too much time thinking about a thing, you’ll never get it done. — Bruce Lee",
        "Nothing is less productive than to make more efficient what should not be done at all. — Peter Drucker",
        "Amateurs sit and wait for inspiration, the rest of us just get up and go to work. — Stephen King",
        "My goal is no longer to get more done, but rather to have less to do. — Francine Jay",
        "The simple act of paying positive attention to people has a great deal to do with productivity."
                + " — Tom Peters",
        "It is not enough to be industrious; so are the ants. What are you industrious about? — Henry David Thoreau",
        "We need space to be productive, we need places to go to be free. — Laure Lacornette",
        "The society based on production is only productive, not creative. — Albert Camus",
        "Improved productivity means less human sweat, not more. — Henry Ford",
        "It’s not always that we need to do more but rather that we need to focus on less. — Nathan W. Morris",
        "No matter how great the talent or efforts, some things just take time. You can’t produce a baby "
                + "in one month by getting nine women pregnant. — Warren Buffett",
        "If you commit to giving more time than you have to spend, you will constantly be running from "
                + "time debt collectors. — Elizabeth Grace Saunders",
        "There is no waste in the world that equals the waste from needless, ill-directed, and ineffective "
                + "motions. — Frank Bunker Gilbreth, Sr."
    };

    private static final int FIXED_HEIGHT = 450;
    private static final int FIXED_WIDTH = 450;

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private Stage primaryStage;
    private Config config;
    private UserPrefs prefs;
    private Logic logic;
    private Model model;

    private MainWindow mainWindow;

    private ImageView logo;
    private Button continueButton;

    @FXML
    private BorderPane welcomeWindow;

    @FXML
    private StackPane logoPlaceHolder;

    @FXML
    private StackPane textPlaceHolder;

    @FXML
    private StackPane buttonPlaceHolder;

    public WelcomeScreen(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Model model) {
        super(FXML);

        // main window dependencies
        this.primaryStage = primaryStage;
        this.config = config;
        this.prefs = prefs;
        this.logic = logic;
        this.model = model;

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
        logo.setFitHeight(190);
        logo.setFitWidth(190);
        logoPlaceHolder.getChildren().add(logo);

        Text text = new Text(quotes[generateQuoteNumber()]);
        text.wrappingWidthProperty().set(345);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.valueOf("#f7d0af"));
        textPlaceHolder.getChildren().add(text);

        continueButton = new Button("Continue");
        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                welcomeWindow.getScene().getWindow().hide();
                loadMainWindow();
            }
        });
        buttonPlaceHolder.getChildren().add(continueButton);
    }

    /**
     * Generates random quote number to display a random quote every time the welcome screen is displayed.
     */
    private int generateQuoteNumber() {
        Random random = new Random();

        int randomInteger = random.nextInt(quotes.length);

        return randomInteger;
    }

    /**
     * Opens main window.
     */
    public void loadMainWindow() {
        mainWindow = new MainWindow(primaryStage, config, prefs, logic, model);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
        mainWindow.openReminderWindowIfRequired();
        //@@author icehawker
        raise(new NewResultAvailableEvent(COMMAND_QUICK_HELP, false));
        //@@author
    }

    void show() {
        primaryStage.show();
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

    /**
     * Stops the application.
     */
    public void stop() {
        if (mainWindow != null) {
            prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
            mainWindow.hide();
            mainWindow.releaseResources();
        }
    }
}
