package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

import java.util.logging.Logger;


public class CommandModeStatusBarFooter extends UiPart<Region> {

    private static final String FXML = "CommandModeStatusBarFooter.fxml";
    private static final Logger logger = LogsCenter.getLogger(CommandModeStatusBarFooter.class);

    private Model model;
    @FXML
    private Label commandMode;

    public CommandModeStatusBarFooter(Model model) {
        super(FXML);
        this.model = model;
        commandMode.textProperty().bind(model.getCommandModeProperty());
        registerAsAnEventHandler(this);
    }
}
