package seedu.address.ui;

import java.util.ArrayList;

import javafx.scene.control.TextArea;
import seedu.address.commons.core.TutorialMessages;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The tutorial of the address book.
 */
public class Tutorial {

    private CommandBox commandBox;
    private PersonListPanel personListPanel;
    private TextArea tutorialText;
    private ArrayList<TutSteps> tutorialSteps = new ArrayList<>();
    private Logic logic;

    public Tutorial(CommandBox commandBox, PersonListPanel personListPanel, TextArea tutorialText, Logic logic) {
        this.commandBox = commandBox;
        this.personListPanel = personListPanel;
        this.tutorialText = tutorialText;
        this.logic = logic;
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_ONE, false));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_TWO, false));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_THREE, false));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_FOUR, false, "delete 6"));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_FIVE, true, "delete 6"));
        tutorialSteps.add(new TutSteps("Last step", false));
    }

    /**
     * Executes the current tutorial's step.
     *
     * @param currentStep
     * @throws CommandException
     * @throws ParseException
     */
    public void executeStep(TutSteps currentStep) throws CommandException, ParseException {
        commandBox.setInputText(currentStep.getCommandInput());
        switch (currentStep.getStepNumber()) {
        case 0:
            commandBox.tutorialHighlight();
            break;
        case 1:
            commandBox.tutorialUnhighlight();
            personListPanel.tutorialHighlight();
            break;
        default:
            personListPanel.tutorialUnhighlight();
        }
        if (currentStep.isLastStep()) {
            tutorialText.setVisible(false);
        } else if (!currentStep.isToExecute()) {
            tutorialText.setText(currentStep.getTextDisplay());
        } else if (currentStep.isToExecute()) {
            CommandResult result = logic.execute(currentStep.getCommandInput());
            commandBox.setInputText("");
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
        commandBox.setInputText("");
    }
}

/**
 * The steps of the tutorial.
 */
class TutSteps {

    private static int totalNumSteps = 0;
    private String textDisplay;
    private boolean toExecute = false;
    private boolean isLastStep = false;
    private int stepNumber;
    private String commandInput = "";

    public TutSteps(String textDisplay, boolean toExecute) {
        this.textDisplay = textDisplay;
        this.toExecute = toExecute;
        stepNumber = totalNumSteps;
        if (totalNumSteps++ == TutorialMessages.NUM_STEPS) {
            this.isLastStep = true;
        }
    }

    public TutSteps(String textDisplay, boolean toExecute, String commandInput) {
        this.textDisplay = textDisplay;
        this.toExecute = toExecute;
        this.commandInput = commandInput;
        stepNumber = totalNumSteps;
        if (totalNumSteps++ == TutorialMessages.NUM_STEPS) {
            this.isLastStep = true;
        }
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public boolean isLastStep() {
        return isLastStep;
    }

    public String getCommandInput() {
        return commandInput;
    }

    public boolean isToExecute() {
        return toExecute;
    }

    public int getStepNumber() {
        return stepNumber;
    }
}
