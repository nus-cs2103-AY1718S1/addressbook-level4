package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

import seedu.address.commons.core.LogsCenter;

public class BirthdaysDialogBox extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(BirthdaysDialogBox.class);
    private static String message_true = "Wish your friends a Happy Birthday!";
    private static String message_false = "No birthdays today.";
    private static final String FXML = "BirthdayDialogBox.fxml";
    private static final String TITLE = "Birthdays Today";

    @FXML
    private Label messageLabel;

    private final Stage dialogStage;


    public BirthdaysDialogBox (String[] birthdays){
        super(FXML);
        if (birthdays.length>0){
            messageLabel.setText(message_true);
        }
        else{
            messageLabel.setText(message_false);
        }
        Scene scene = new Scene(getRoot());
        dialogStage = createDialogStage(TITLE,null, scene);
        registerAsAnEventHandler(this);
    }

    public void show() {
        logger.fine("Showing birthday dialog box.");
        dialogStage.showAndWait();
    }

    @FXML
    public void close() {
        messageLabel.getScene().getWindow().hide();
    }

}
