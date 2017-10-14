package seedu.address.ui.util;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * The Person Detail Panel of the App.
 */
public class PersonDetailPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private HBox personCard;

    public PersonDetailPanel() {
        super(FXML);
        logger.info("creating panel");
    }
}
