package seedu.address.ui;

import java.util.ArrayList;

import javafx.scene.control.TextArea;
import seedu.address.commons.core.TutorialMessages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Alim95

/**
 * The tutorial of the address book.
 */
public class Tutorial {

    private MainWindow mainWindow;
    private TextArea tutorialText;
    private ArrayList<TutSteps> tutorialSteps = new ArrayList<>();
    private int currentStepNum = 0;

    public Tutorial(MainWindow mainWindow, TextArea tutorialText) {
        this.mainWindow = mainWindow;
        this.tutorialText = tutorialText;

        setUpTutorial();
    }

    private void setUpTutorial() {

        /* Steps for introduction to Bluebird */
        for (String introMessages : TutorialMessages.INTRO_LIST) {
            tutorialSteps.add(new TutSteps(introMessages));
        }

        /* Steps for conclusion */
        tutorialSteps.add(new TutSteps(TutorialMessages.CONCLUSION));
    }

    /**
     * Executes the next tutorial step.
     */
    public void executeNextStep() {
        TutSteps stepToExecute = tutorialSteps.get(currentStepNum);
        mainWindow.unhighlightAll();
        switch (currentStepNum++) {
        case 0:
            mainWindow.highlightCommandBox();
            break;
        case 1:
            mainWindow.highlightResultDisplay();
            break;
        case 2:
            mainWindow.highlightSortMenu();
            break;
        case 3:
            mainWindow.highlightSearchBox();
            break;
        case 4:
            mainWindow.highlightPersonListPanel();
            break;
        default:
            break;
        }
        tutorialText.setText(stepToExecute.getTextDisplay());
    }

    /**
     * Executes the previous tutorial step.
     */
    public void executePreviousStep() throws CommandException, ParseException {
        if (currentStepNum - 2 >= 0) {
            currentStepNum -= 2;
            executeNextStep();
        }
    }

    public boolean isLastStep() {
        return currentStepNum == TutorialMessages.TOTAL_NUM_STEPS;
    }
}

/**
 * The steps of the tutorial.
 */
class TutSteps {

    private String textDisplay;

    public TutSteps(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public String getTextDisplay() {
        return textDisplay;
    }
}
