package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

//@@author fongwz
/**
 * A ui component that displays commands in the command helper box
 */
public class HelperCard extends UiPart<Region> {

    private static final String FXML = "HelperCard.fxml";

    public final String commandString;

    @FXML
    private HBox commandCardPane;

    @FXML
    private Label command;

    public HelperCard(String commandString) {
        super(FXML);
        this.commandString = commandString;
        command.textProperty().setValue(commandString);
    }

    public String getText() {
        return this.commandString;
    }

}
