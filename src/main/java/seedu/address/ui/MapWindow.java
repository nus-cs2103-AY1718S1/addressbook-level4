//@@author jshoung
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for map page
 */
public class MapWindow extends UiPart<Region> {


    private static final Logger logger = LogsCenter.getLogger(MapWindow.class);
    private static final String ICON = "/images/map_icon.png";
    private static final String FXML = "MapWindow.fxml";
    private static final String TITLE = "NUS Map";

    private final Stage dialogStage;

    MapWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true);
        FxViewUtil.setStageIcon(dialogStage, ICON);
    }

    /**
     * Shows the map window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing map for NUS.");
        dialogStage.showAndWait();
    }
}
