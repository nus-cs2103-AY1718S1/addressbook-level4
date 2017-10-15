package seedu.address.ui;

import java.util.ArrayList;

import javafx.scene.control.TextArea;
import seedu.address.commons.core.TutorialMessages;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The tutorial of the address book.
 */
public class Tutorial {

    private MainWindow mainWindow;
    private TextArea tutorialText;
    private ArrayList<TutSteps> tutorialSteps = new ArrayList<>();
    private Logic logic;

    public Tutorial(MainWindow mainWindow, TextArea tutorialText, Logic logic) {
        this.mainWindow = mainWindow;
        this.tutorialText = tutorialText;
        this.logic = logic;

        setUpTutorial();
    }

    private void setUpTutorial() {

        /* Steps for introduction to Bluebird */
        for (String introMessages : TutorialMessages.getIntroList()) {
            tutorialSteps.add(new TutSteps(introMessages));
        }

        /* Steps for commands usage */
        for (int i = 0; i < TutorialMessages.PROMPT_NUM_STEPS; i++) {
            tutorialSteps.add(new TutSteps(TutorialMessages.getPromptList().get(i),
                    TutorialMessages.getCommandUsageList().get(i)));
        }

        /* Steps for conclusion */
        tutorialSteps.add(new TutSteps(TutorialMessages.CONCLUSION, TutorialMessages.DEFAULT_PROMPT));
        tutorialSteps.add(new TutSteps("Last step"));
    }

    /**
     * Executes the current tutorial's step.
     */
    public void executeStep(TutSteps currentStep) throws CommandException, ParseException {
        switch (currentStep.getStepNumber()) {
        case 0:
            mainWindow.highlightCommandBox();
            break;
        case 1:
            mainWindow.unhighlightAll();
            mainWindow.highlightResultDisplay();
            break;
        case 2:
            mainWindow.unhighlightAll();
            mainWindow.highlightSortMenu();
            break;
        case 3:
            mainWindow.unhighlightAll();
            mainWindow.highlightSearchBox();
            break;
        case 4:
            mainWindow.unhighlightAll();
            mainWindow.highlightPersonListPanel();
            break;
        default:
            mainWindow.unhighlightAll();
        }
        if (currentStep.isLastStep()) {
            endTutorial();
        } else if (currentStep.isPrompt()) {
            mainWindow.setCommandPrompt(currentStep.getCommandPrompt());
            tutorialText.setText(currentStep.getTextDisplay());
        } else {
            tutorialText.setText(currentStep.getTextDisplay());
        }
    }

    public ArrayList<TutSteps> getTutorialSteps() {
        return tutorialSteps;
    }

    /**
     * Ends the tutorial.
     */
    public void endTutorial() {
        mainWindow.unhighlightAll();
        tutorialText.setVisible(false);
        mainWindow.setCommandPrompt(TutorialMessages.DEFAULT_PROMPT);
    }
}

/**
 * The steps of the tutorial.
 */
class TutSteps {

    private static int totalNumSteps = 0;
    private String textDisplay;
    private boolean isLastStep = false;
    private int stepNumber;
    private String commandPrompt = "";
    private boolean hasPrompt = false;

    public TutSteps(String textDisplay) {
        this.textDisplay = textDisplay;
        stepNumber = totalNumSteps;
        if (totalNumSteps++ == TutorialMessages.TOTAL_NUM_STEPS) {
            this.isLastStep = true;
        }
    }

    public TutSteps(String textDisplay, String commandPrompt) {
        this.textDisplay = textDisplay;
        this.commandPrompt = commandPrompt;
        stepNumber = totalNumSteps;
        if (totalNumSteps++ == TutorialMessages.TOTAL_NUM_STEPS) {
            this.isLastStep = true;
        }
        this.hasPrompt = true;
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public boolean isLastStep() {
        return isLastStep;
    }

    public String getCommandPrompt() {
        return commandPrompt;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public boolean isPrompt() {
        return hasPrompt;
    }
}
