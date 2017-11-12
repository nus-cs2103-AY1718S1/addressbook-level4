package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

//@@author jelneo
/**
 * The {@code StartUpPanel} will be loaded in place of the {@code InfoPanel} on start up.
 */
public class StartUpPanel extends UiPart<Region> {
    private static final String FXML = "StartUpPanel.fxml";

    @FXML
    private ImageView welcome;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public StartUpPanel(Stage stage) {
        super(FXML);
        logger.info("Loading welcome page...");
        welcome.fitHeightProperty().bind(stage.heightProperty());
    }

}
