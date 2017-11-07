package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

//@@author huyuanrong
/**
 * Controller for a Useful Contacts page
 */
public class ContactWindow extends UiPart<Region> {

    public static final String USERGUIDE_FILE_PATH = "/docs/UsefulContacts.html";

    private static final Logger logger = LogsCenter.getLogger(ContactWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "ContactWindow.fxml";
    private static final String TITLE = "Useful Numbers";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public ContactWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        String usefulNumbersUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
        browser.getEngine().load(usefulNumbersUrl);
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
        logger.fine("Showing useful numbers to note in Singapore.");
        dialogStage.showAndWait();
    }
}
