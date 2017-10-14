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

    private CommandBox commandBox;
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private SortFindPanel sortFindPanel;
    private TextArea tutorialText;
    private ArrayList<TutSteps> tutorialSteps = new ArrayList<>();
    private Logic logic;

    public Tutorial(CommandBox commandBox, PersonListPanel personListPanel, ResultDisplay resultDisplay,
                    SortFindPanel sortFindPanel, TextArea tutorialText, Logic logic) {

        this.commandBox = commandBox;
        this.personListPanel = personListPanel;
        this.tutorialText = tutorialText;
        this.logic = logic;
        this.resultDisplay = resultDisplay;
        this.sortFindPanel = sortFindPanel;

        setUpTutorial();
    }

    private void setUpTutorial() {
        tutorialSteps.add(new TutSteps(TutorialMessages.INTRO_TWO));
        tutorialSteps.add(new TutSteps(TutorialMessages.INTRO_THREE));
        tutorialSteps.add(new TutSteps(TutorialMessages.INTRO_FOUR));
        tutorialSteps.add(new TutSteps(TutorialMessages.INTRO_FIVE));
        tutorialSteps.add(new TutSteps(TutorialMessages.INTRO_SIX));
        tutorialSteps.add(new TutSteps(TutorialMessages.INTRO_END));
        tutorialSteps.add(new TutSteps(TutorialMessages.PROMPT_BEGIN));
        tutorialSteps.add(new TutSteps(TutorialMessages.PROMPT_TWO, TutorialMessages.COMMAND_ADD_USAGE));
        tutorialSteps.add(new TutSteps(TutorialMessages.PROMPT_THREE, TutorialMessages.COMMAND_EDIT_USAGE));
        tutorialSteps.add(new TutSteps(TutorialMessages.PROMPT_FOUR, TutorialMessages.COMMAND_DELETE_USAGE));
        tutorialSteps.add(new TutSteps(TutorialMessages.CONCLUSION, TutorialMessages.DEFAULT_PROMPT));
        tutorialSteps.add(new TutSteps("Last step"));
    }

    /**
     * Executes the current tutorial's step.
     */
    public void executeStep(TutSteps currentStep) throws CommandException, ParseException {
        switch (currentStep.getStepNumber()) {
        case 0:
            commandBox.highlightCommandBox();
            break;
        case 1:
            unhighlightAll();
            resultDisplay.highlightResultDisplay();
            break;
        case 2:
            unhighlightAll();
            sortFindPanel.highlightSortMenu();
            break;
        case 3:
            unhighlightAll();
            sortFindPanel.highlightSearchField();
            break;
        case 4:
            unhighlightAll();
            personListPanel.highlightPersonListPanel();
            break;
        default:
            unhighlightAll();
        }
        if (currentStep.isLastStep()) {
            endTutorial();
        } else if (currentStep.isPrompt()) {
            commandBox.setPromptText(currentStep.getCommandPrompt());
            tutorialText.setText(currentStep.getTextDisplay());
        } else {
            tutorialText.setText(currentStep.getTextDisplay());
        }
    }

    /**
     * Unhighlights all the UIs during tutorial.
     */
    private void unhighlightAll() {
        personListPanel.unhighlightPersonListPanel();
        commandBox.unhighlightCommandBox();
        resultDisplay.unhighlightResultDisplay();
        sortFindPanel.unhighlightAll();
    }

    public ArrayList<TutSteps> getTutorialSteps() {
        return tutorialSteps;
    }

    /**
     * Ends the tutorial.
     */
    public void endTutorial() {
        unhighlightAll();
        tutorialText.setVisible(false);
        commandBox.setPromptText(TutorialMessages.DEFAULT_PROMPT);
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
        if (totalNumSteps++ == TutorialMessages.NUM_STEPS) {
            this.isLastStep = true;
        }
    }

    public TutSteps(String textDisplay, String commandPrompt) {
        this.textDisplay = textDisplay;
        this.commandPrompt = commandPrompt;
        stepNumber = totalNumSteps;
        if (totalNumSteps++ == TutorialMessages.NUM_STEPS) {
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
