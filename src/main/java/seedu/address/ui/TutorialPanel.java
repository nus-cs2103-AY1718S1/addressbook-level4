package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.TutorialMessages;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The panel for tutorial of the App.
 */
public class TutorialPanel extends UiPart<Region> {

    private static final String FXML = "TutorialPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(TutorialPanel.class);

    private ArrayList<TutSteps> tutStepsList = new ArrayList<>();
    private Tutorial newTutorial;
    private Logic logic;
    private MainWindow mainWindow;
    private BrowserPanel browserPanel;
    private StackPane browserPlaceHolder;
    private boolean isSkip = true;

    @FXML
    private Button rightButton;

    @FXML
    private Button leftButton;

    @FXML
    private Button skipButton;

    @FXML
    private ImageView tutorialImage;

    @FXML
    private TextArea tutorialText;

    public TutorialPanel(MainWindow mainWindow, Logic logic,
                         BrowserPanel browserPanel, StackPane browserPlaceHolder) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.logic = logic;
        this.browserPanel = browserPanel;
        this.browserPlaceHolder = browserPlaceHolder;
        tutorialText.setText(TutorialMessages.INTRO_BEGIN);
        initTutorial();
    }

    private void initTutorial() {
        newTutorial = new Tutorial(mainWindow, tutorialText, logic);
    }

    /**
     * Handles the left button pressed event.
     */
    @FXML
    private void handleLeftButtonPressed() throws CommandException, ParseException {
        if (isSkip) {
            isSkip = false;
            leftButton.setText("Back");
            rightButton.setText("Next");
            skipButton.setVisible(true);
            newTutorial.executeNextStep();
        } else {
            newTutorial.executePreviousStep();
        }
    }

    /**
     * Handles the right button pressed event.
     */
    @FXML
    private void handleRightButtonPressed() {
        if (isSkip) {
            newTutorial.revertToDefault();
            removeTutorialUi();
            browserPlaceHolder.getChildren().add(browserPanel.getRoot());
        } else {
            try {
                newTutorial.executeNextStep();
            } catch (CommandException e1) {
                logger.warning("Can't execute command in tutorial.");
            } catch (ParseException e1) {
                logger.warning("Wrong command input in tutorial.");
            }
            if (newTutorial.isLastStep()) {
                removeTutorialUi();
                browserPlaceHolder.getChildren().add(browserPanel.getRoot());
            }
        }
    }

    /**
     * Handles the skip button pressed event.
     */
    @FXML
    private void handleSkipButtonPressed() {
            newTutorial.revertToDefault();
            removeTutorialUi();
            browserPlaceHolder.getChildren().add(browserPanel.getRoot());
    }

    private void removeTutorialUi() {
        leftButton = null;
        rightButton = null;
        tutorialText = null;
        tutorialImage = null;
        skipButton = null;
    }
}
