package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class helperCard extends UiPart<Region> {

    private static final String FXML = "HelperCard.fxml";

    public final String commandString;

    @FXML
    private HBox commandCardPane;

    @FXML
    private Label command;

    public helperCard(String commandString) {
        super(FXML);
        this.commandString = commandString;
        command.textProperty().setValue(commandString);
    }

    public String getText() {
        return this.commandString;
    }

}
