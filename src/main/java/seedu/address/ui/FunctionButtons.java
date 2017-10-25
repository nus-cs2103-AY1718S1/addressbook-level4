package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.email.Email;
import seedu.address.logic.Logic;

import java.util.ArrayList;

public class FunctionButtons extends UiPart<Region> {
    private static final String FXML = "FunctionButtons.fxml";
    private Logic logic;
    private Stage stage;
    private MainWindow mainWindow;

    @FXML
    private StackPane loginPane;
    @FXML
    private Button loginButton;
    @FXML
    private StackPane sendPane;
    @FXML
    private Button sendButton;
    @FXML
    private StackPane checkPane;
    @FXML
    private Button checkButton;

    public FunctionButtons(Logic logic, Stage stage, MainWindow mainWindow) {
        super(FXML);
        this.logic = logic;
        this.stage = stage;
        this.mainWindow = mainWindow;
    }

    /**
     * Open the email login window
     */
    @FXML
    private void openEmailLoginWindow() {
        EmailLoginWindow emailLoginWindow = new EmailLoginWindow(logic, stage);
        emailLoginWindow.show();
    }

    /**
     * Open the email send window
     */
    @FXML
    private void openEmailSendWindow() {
        ArrayList<PersonCard> tickedPersons
                = mainWindow.getPersonListPanel().getTickedPersons();
        String recipients = new String();
        ArrayList<PersonCard> cardWithOutEmail = new ArrayList<PersonCard>();
        for (PersonCard card : tickedPersons) {
            if (card.isTicked()) {
                if (card.getEmail() != null) recipients += card.getEmail() + ";";
                else cardWithOutEmail.add(card);
            }
        }

        String feedbackPersonsWithoutEmail = "";

        if (cardWithOutEmail.size() != 0) {
            feedbackPersonsWithoutEmail = "The following person(s) do not have emails or have private emails:\n";

            for (PersonCard card : cardWithOutEmail) {
                feedbackPersonsWithoutEmail += card.getName() + "\n";
            }
        }

        EmailSendWindow emailSendWindow = new EmailSendWindow(logic, stage, recipients, feedbackPersonsWithoutEmail);
        emailSendWindow.show();
    }

    /**
     * Open the email check window
     */
    @FXML
    private void openEmailCheckWindow() {

    }
}
