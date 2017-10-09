package seedu.address.ui;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The tutorial of the address book.
 */
public class Tutorial {

    private CommandBox commandBox;
    private StackPane browserPlaceholder;
    private ImageView tutorialImage;
    private ArrayList<TutSteps> tutorialSteps = new ArrayList<>();
    private Logic logic;

    public Tutorial(CommandBox commandBox, StackPane browserPlaceholder, ImageView tutorialImage, Logic logic) {
        this.commandBox = commandBox;
        this.browserPlaceholder = browserPlaceholder;
        this.tutorialImage = tutorialImage;
        this.logic = logic;
        tutorialSteps.add(new TutSteps("/images/step1.png", false));
        tutorialSteps.add(new TutSteps("/images/step2.png", true, "delete 1"));
        tutorialSteps.add(new TutSteps(true, "delete 1", "/images/step3.png"));
        tutorialSteps.add(new TutSteps("LAST", false));
    }

    /**
     * Executes the current tutorial's step.
     * @param currentStep
     * @throws CommandException
     * @throws ParseException
     */
    public void executeStep(TutSteps currentStep) throws CommandException, ParseException {
        commandBox.setInputText(currentStep.getCommandInput());
        if (!currentStep.isLastStep() && !currentStep.isToExecute()) {
            tutorialImage.setImage(new Image(currentStep.getImageUrl()));
        } else if (!currentStep.isLastStep() && currentStep.isToExecute()) {
            logic.execute(currentStep.getCommandInput());
            commandBox.setInputText("");
            tutorialImage.setImage(new Image(currentStep.getImageUrl()));
        } else {
            tutorialImage.setImage(null);
        }
    }

    public ArrayList<TutSteps> getTutorialSteps() {
        return tutorialSteps;
    }

    /**
     * Ends the tutorial.
     */
    public void endTutorial() {
        tutorialImage.setImage(null);
        commandBox.setInputText("");
    }
}

/**
 * The steps of the tutorial.
 */
class TutSteps {

    private static int numSteps = 0;
    private String imageUrl;
    private boolean hasCommandInput;
    private boolean toExecute = false;
    private boolean isLastStep = false;
    private String commandInput = null;


    public TutSteps(boolean toExecute, String commandInput, String imageUrl) {
        this.toExecute = toExecute;
        this.commandInput = commandInput;
        this.imageUrl = imageUrl;
        numSteps++;
    }

    public TutSteps(String imageUrl, boolean hasCommandInput, String commandInput) {
        this.imageUrl = imageUrl;
        this.hasCommandInput = hasCommandInput;
        this.commandInput = commandInput;
        if (numSteps++ == 3) {
            this.isLastStep = true;
        }
    }

    public TutSteps(String imageUrl, boolean hasCommandInput) {
        this.imageUrl = imageUrl;
        this.hasCommandInput = hasCommandInput;
        if (numSteps++ == 3) {
            this.isLastStep = true;
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isLastStep() {
        return isLastStep;
    }

    public String getCommandInput() {
        if (hasCommandInput || toExecute) {
            return this.commandInput;
        }
        return "";
    }

    public boolean isToExecute() {
        return toExecute;
    }
}
