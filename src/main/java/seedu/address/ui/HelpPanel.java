package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * The Help Panel of the App.
 */
public class HelpPanel extends UiPart<Region> {

    public static final String USERGUIDE_FILE_PATH = "/docs/UserGuide.html";

    private static final String FXML = "HelpPanel.fxml";

    public HelpPanel() {
        super(FXML);
    }

}
