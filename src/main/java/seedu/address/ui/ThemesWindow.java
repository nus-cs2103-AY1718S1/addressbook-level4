package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for a theme page
 */
public class ThemesWindow extends UiPart<Region> {

    public static final String THEMES_FILE_PATH = "/docs/Themes.html";

    private static final Logger logger = LogsCenter.getLogger(ThemesWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "ThemesWindow.fxml";
    private static final String TITLE = "Themes";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public ThemesWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        String userGuideUrl = getClass().getResource(THEMES_FILE_PATH).toString();
        browser.getEngine().load(userGuideUrl);
    }

    /**
     * Shows the themes window.
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
        logger.fine("Showing themes page for the application.");
        dialogStage.showAndWait();
    }
}
