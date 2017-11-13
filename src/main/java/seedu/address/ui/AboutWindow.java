package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

//@@author yewshengkai
/**
 * Controller for a about page
 */
public class AboutWindow extends UiPart<Region> {

    public static final String USERGUIDE_FILE_PATH = "/docs/AboutUs.html";

    private static final Logger logger = LogsCenter.getLogger(AboutWindow.class);
    private static final String ICON = "/images/info_icon.png";
    private static final String FXML = "AboutWindow.fxml";
    private static final String TITLE = "About";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 650;

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public AboutWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        setAboutWindowSize();
        String userGuideUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
        browser.getEngine().load(userGuideUrl);
    }

    private void setAboutWindowSize() {
        dialogStage.setMinHeight(MIN_HEIGHT);
        dialogStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Shows the about window.
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
        logger.fine("Showing about page.");
        //@@author yewshengkai-reused
        dialogStage.show();
        //@@author
    }
}
