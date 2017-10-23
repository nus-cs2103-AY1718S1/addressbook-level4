package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class FunctionButtons extends UiPart<Region> {
    private static final String FXML = "FunctionButtons.fxml";

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
    @FXML
    private StackPane CLIPane;
    @FXML
    private Button CLIButton;

    public FunctionButtons() {
        super(FXML);
    }
}
