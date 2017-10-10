package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

//@@author jelneo
/**
 * The Start Up Panel will be loaded in place of the Browser Panel
 */
public class StartUpPanel extends UiPart<Region> {

    private static final String FXML = "StartUpPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());


    public StartUpPanel() {
        super(FXML);
        logger.info("Loading start up panel...");
    }

}
