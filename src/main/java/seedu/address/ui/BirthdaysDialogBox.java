package seedu.address.ui;

import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;

public class BirthdaysDialogBox extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(BirthdaysDialogBox.class);
    //private static String message_true = "The following people have birthdays today. Wish them a Happy Birthday!";
    //private static String message_false = "No birthdays today.";
    private static final String FXML = "BirthdayDialog.fxml";
    private static final String TITLE = "Birthdays Today";

    private final Stage dialogStage;


    public BirthdaysDialogBox (String[] birthdays){
        super(FXML);
        Scene scene = new Scene(getRoot());
        dialogStage = createDialogStage(TITLE,null, scene);
        registerAsAnEventHandler(this);
    }

    public void show() {
        logger.fine("Showing birthday dialog box.");
        dialogStage.showAndWait();
    }

}
