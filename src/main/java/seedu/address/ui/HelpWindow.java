package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Region> {

    public static final String HELP_FILE_PATH = "/docs/Help.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";

    @FXML
    private WebView browser;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    @FXML
    private HBox controls;

    private final Stage dialogStage;

    public HelpWindow() {
        super(FXML);

        txtSearch.setOnAction(event -> {
            if (browser.getEngine().getDocument() != null) {
                highlight(browser.getEngine(), txtSearch.getText());
            }
        });

        btnSearch.setDefaultButton(true);
        btnSearch.setOnAction(actionEvent -> txtSearch.fireEvent(new ActionEvent()));

        btnClear.setOnAction(actionEvent -> clearHighlights(browser.getEngine()));
        btnClear.setCancelButton(true);

        controls.disableProperty().bind(browser.getEngine().getLoadWorker().runningProperty());
        txtSearch.disableProperty().bind(browser.getEngine().getLoadWorker().runningProperty());

        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        String userGuideUrl = getClass().getResource(HELP_FILE_PATH).toString();
        browser.getEngine().load(userGuideUrl);
    }

    private void highlight(WebEngine engine, String text) {
        engine.executeScript("$('body').removeHighlight().highlight('" + text + "')");
    }

    private void clearHighlights(WebEngine engine) {
        engine.executeScript("$('body').removeHighlight()");
        txtSearch.clear();
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
        logger.fine("Showing help page about the application.");
        dialogStage.showAndWait();
    }
}
