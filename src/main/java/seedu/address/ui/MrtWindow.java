package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

//@@author aungmyin23
/**
 * Controller for a help page
 */
public class MrtWindow extends UiPart<Region> {

    public static final String MRT_FILE_PATH = "/images/MrtMap.jpg";

    private static final Logger logger = LogsCenter.getLogger(MrtWindow.class);
    private static final String ICON = "/images/mrt_logo.png";
    private static final String FXML = "MrtMapWindow.fxml";
    private static final String TITLE = "MRT Map";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public MrtWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        String mrtMapUrl = getClass().getResource(MRT_FILE_PATH).toString();
        browser.getEngine().load(mrtMapUrl);
    }

    /**
     * Shows the help window.
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
        logger.fine("Showing MRT map of Singapore.");
        dialogStage.showAndWait();
    }
}
