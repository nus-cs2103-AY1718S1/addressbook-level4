//@@author jshoung
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for bus page
 */
public class BusWindow extends UiPart<Region> {


    private static final Logger logger = LogsCenter.getLogger(BusWindow.class);
    private static final String ICON = "/images/bus_icon.png";
    private static final String FXML = "BusWindow.fxml";
    private static final String TITLE = "Bus Routes";

    private final Stage dialogStage;

    public BusWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(false);
        FxViewUtil.setStageIcon(dialogStage, ICON);
    }

    /**
     * Shows the bus window.
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
        logger.fine("Showing bus routes for NUS.");
        dialogStage.showAndWait();
    }
}
