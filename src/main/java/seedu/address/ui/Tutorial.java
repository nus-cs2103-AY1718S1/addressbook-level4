package seedu.address.ui;

import java.util.ArrayList;

import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private ResultDisplay resultDisplay;
    private MenuButton sortMenu;
    private TextField searchField;
    private TextArea tutorialText;
    private ArrayList<TutSteps> tutorialSteps = new ArrayList<>();
    private Logic logic;

    public Tutorial(CommandBox commandBox, PersonListPanel personListPanel, ResultDisplay resultDisplay,
                    MenuButton sortMenu, TextField searchField, TextArea tutorialText, Logic logic) {

        this.commandBox = commandBox;
        this.personListPanel = personListPanel;
        this.tutorialText = tutorialText;
        this.logic = logic;
        this.resultDisplay = resultDisplay;
        this.sortMenu = sortMenu;
        this.searchField = searchField;

        setUpTutorial();
    }

    private void setUpTutorial() {
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_ONE, false));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_TWO, false));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_THREE, false));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_FOUR, false));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_FIVE, false));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_SIX, false));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_SEVEN, false, TutorialMessages.COMMAND_ADD_USAGE));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_EIGHT, false, "delete 6"));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_NINE, true, "delete 6"));
        tutorialSteps.add(new TutSteps(TutorialMessages.STEP_CONCLUSION, false));
        tutorialSteps.add(new TutSteps("Last step", false));
    }

    /**
     * Executes the current tutorial's step.
     */
    public void executeStep(TutSteps currentStep) throws CommandException, ParseException {
        commandBox.setPromptText(currentStep.getCommandInput());
        switch (currentStep.getStepNumber()) {
        case 0:
            commandBox.tutorialHighlight();
            break;
        case 1:
            unhighlightAll();
            resultDisplay.tutorialHighlight();
            break;
        case 2:
            unhighlightAll();
            sortMenu.setStyle("-fx-border-color: green; -fx-border-width: 2");
            break;
        case 3:
            unhighlightAll();
            searchField.setStyle("-fx-border-color: green; -fx-border-width: 2");
            break;
        case 4:
            unhighlightAll();
            personListPanel.tutorialHighlight();
            break;
        default:
            unhighlightAll();
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

    /**
     * Unhighlights all the UIs during tutorial.
     */
    private void unhighlightAll() {
        personListPanel.tutorialUnhighlight();
        commandBox.tutorialUnhighlight();
        resultDisplay.tutorialUnhighlight();
        sortMenu.setStyle("-fx-border-color: null; -fx-border-width: 1");
        searchField.setStyle("-fx-border-color: null; -fx-border-width: 1");
    }

    public ArrayList<TutSteps> getTutorialSteps() {
        return tutorialSteps;
    }

    /**
     * Ends the tutorial.
     */
    public void endTutorial() {
        commandBox.setInputText("");
        unhighlightAll();
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
