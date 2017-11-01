package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.TutorialMessages;
import seedu.address.commons.events.ui.SwitchToBrowserEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Alim95
/**
 * The panel for tutorial of the App.
 */
public class TutorialPanel extends UiPart<Region> {

    private static final String FXML = "TutorialPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(TutorialPanel.class);

    private Tutorial newTutorial;
    private MainWindow mainWindow;
    private StackPane browserPlaceHolder;
    private boolean tutorialIntro = true;

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

    public TutorialPanel(MainWindow mainWindow, StackPane browserPlaceHolder) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.browserPlaceHolder = browserPlaceHolder;
        tutorialText.setText(TutorialMessages.INTRO_BEGIN);
        initTutorial();
    }

    private void initTutorial() {
        newTutorial = new Tutorial(mainWindow, tutorialText);
    }

    /**
     * Handles the left button pressed event.
     */
    @FXML
    private void handleLeftButtonPressed() throws CommandException, ParseException {
        if (tutorialIntro) {
            tutorialIntro = false;
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
        if (tutorialIntro) {
            endTutorial();
        } else if (!newTutorial.isLastStep()) {
            newTutorial.executeNextStep();
        } else if (newTutorial.isLastStep()) {
            endTutorial();
        }
    }

    /**
     * Handles the skip button pressed event.
     */
    @FXML
    private void handleSkipButtonPressed() {
        endTutorial();
    }

    /**
     * Removes tutorial panel and replace with browser panel
     */
    private void endTutorial() {
        mainWindow.unhighlightAll();
        browserPlaceHolder.getChildren().remove(this.getRoot());
        raise(new SwitchToBrowserEvent());
    }
}
