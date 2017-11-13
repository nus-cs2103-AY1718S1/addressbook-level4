//@@author keloysiusmak
package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;

/**
 * Controller for a help page
 */
public class ViewAliasWindow extends UiPart<Region> {

    public static final String VIEWALIAS_FILE_PATH = "/docs/ViewAliasGuide.html";

    private static final Logger logger = LogsCenter.getLogger(ViewAliasWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "ViewAliasWindow.fxml";
    private static final String TITLE = "View Aliases";
    @FXML
    private final Stage dialogStage;
    private ViewAliasListPanel viewAliasListPanel;
    @FXML
    private StackPane viewAliasListPanelPlaceholder;


    public ViewAliasWindow(ArrayList<ArrayList<String>> c, Logic logic, Config config) {
        super(FXML);

        viewAliasListPanel = new ViewAliasListPanel(c, logic);
        viewAliasListPanelPlaceholder.getChildren().add(viewAliasListPanel.getRoot());

        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMinHeight(600);
        dialogStage.setMinWidth(400);
        String image = MainWindow.class.getResource("/images/" + config.getTheme() + ".jpg").toExternalForm();
        viewAliasListPanelPlaceholder.setStyle("-fx-background-image: url('" + image + "'); ");

        FxViewUtil.setStageIcon(dialogStage, ICON);

        registerAsAnEventHandler(this);
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing aliases for the application.");
        dialogStage.showAndWait();
    }

}
