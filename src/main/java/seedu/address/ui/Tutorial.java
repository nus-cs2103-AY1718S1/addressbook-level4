package seedu.address.ui;

import java.util.ArrayList;

import javafx.scene.control.TextArea;
import seedu.address.commons.core.TutorialMessages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

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
            tutorialSteps.add(new TutSteps(introMessages, TutorialMessages.PROMPT_DEFAULT));
        }

        /* Steps for commands usage */
        for (int i = 0; i < TutorialMessages.PROMPT_NUM_STEPS; i++) {
            tutorialSteps.add(new TutSteps(TutorialMessages.COMMAND_USAGE_LIST.get(i),
                    TutorialMessages.COMMAND_PROMPT_LIST.get(i)));
        }

        /* Steps for conclusion */
        tutorialSteps.add(new TutSteps(TutorialMessages.CONCLUSION, TutorialMessages.PROMPT_DEFAULT));
    }

    /**
     * Executes the next tutorial step.
     */
    public void executeNextStep() {
        TutSteps stepToExecute = tutorialSteps.get(currentStepNum);
        switch (currentStepNum++) {
        case 0:
            mainWindow.unhighlightAll();
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
        case 5:
            mainWindow.unhighlightAll();
            mainWindow.highlightPinnedPanel();
            break;
        default:
            mainWindow.unhighlightAll();
        }
        mainWindow.setCommandPrompt(stepToExecute.getCommandPrompt());
        mainWindow.setCommandText("");
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
    private String commandPrompt = "";

    public TutSteps(String textDisplay, String commandPrompt) {
        this.textDisplay = textDisplay;
        this.commandPrompt = commandPrompt;
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public String getCommandPrompt() {
        return commandPrompt;
    }
}
