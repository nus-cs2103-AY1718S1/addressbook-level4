//@@author danielbrzn
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a progress window
 */
public class ProgressWindow extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ProgressWindow.class);
    private static final String FXML = "ProgressWindow.fxml";
    private static final String TITLE = "Import Progress";

    private final Stage dialogStage;

    @FXML
    private ProgressBar progBar;

    private ReadOnlyDoubleProperty progress;

    public ProgressWindow(ReadOnlyDoubleProperty progress) {
        super(FXML);
        this.progress = progress;
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setAlwaysOnTop(true);
        progBar = (ProgressBar) scene.lookup("#progressBar");
        bindListeners(progress);
    }

    /**
     * Binds the progress bar to a provided {@code ReadOnlyDoubleProperty}
     * so that it will be notified of any changes.
     */
    private void bindListeners(ReadOnlyDoubleProperty progress) {
        progBar.progressProperty().bind(progress);
    }

    /**
     * Shows a progress window.
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
        logger.fine("Showing progress window.");
        dialogStage.show();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public ReadOnlyDoubleProperty getProgress() {
        return progress;
    }

}
