package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.Status;

//@@author vicisapotato
/**
 * New window telling users of overdue parcels
 * Takes in uncompleted parcel list from logic to get number of overdue parcels
 * This window is set to hide after 7 seconds
 */
public class PopupOverdueParcelsWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(PopupOverdueParcelsWindow.class);
    private static final String FXML = "PopupOverdueParcelsWindow.fxml";
    private static final String TITLE = "Important";
    private static final String CONTENT_TEXT = "You have parcels whose status are overdue\n"
                                                 + "Number of overdue parcels: ";

    private final Stage dialogStage;

    @FXML
    private Label contentPlaceholder;

    public PopupOverdueParcelsWindow (ObservableList<ReadOnlyParcel> uncompletedParcels) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setResizable(false);
        dialogStage.setAlwaysOnTop(true);
        fillDialogPane(getNumOverdueParcels(uncompletedParcels));
    }

    private void fillDialogPane (int numOverdueParcels) {
        contentPlaceholder.setText(CONTENT_TEXT + numOverdueParcels);
    }

    /**
     * Gets the number of parcels with the overdue status from logic uncompleted parcel list
     * @return int numOverdueParcels
     */
    private int getNumOverdueParcels (ObservableList<ReadOnlyParcel> uncompletedParcels) {
        int numOverdueParcels = 0;

        for (int i = 0; i < uncompletedParcels.size(); i++) {
            if (uncompletedParcels.get(i).getStatus().equals(Status.OVERDUE)) {
                numOverdueParcels++;
            }
        }

        return numOverdueParcels;
    }

    /**
     * Shows the parcelsOverduePopup window, and hides the window after 7 seconds.
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
    public void showAndHide () {
        logger.fine("Showing popup window for overdue.");
        dialogStage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(7));
        pause.setOnFinished(e -> dialogStage.hide());
        pause.play();
    }
    //@@author
}
