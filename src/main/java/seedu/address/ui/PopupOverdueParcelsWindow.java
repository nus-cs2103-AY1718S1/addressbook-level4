package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.Status;

import java.util.logging.Logger;

/**
 * New window telling users of overdue parcels
 */
//@@author vicisapotato
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

    private int getNumOverdueParcels (ObservableList<ReadOnlyParcel> uncompletedParcels) {
        int numOverdueParcels = 0;

        for (int i = 0 ; i < uncompletedParcels.size() ; i++) {
            if (uncompletedParcels.get(i).getStatus().equals(Status.OVERDUE)) {
                numOverdueParcels++;
            }
        }

        return numOverdueParcels;
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
    public void show () {
        logger.fine("Showing popup window for overdue.");
        dialogStage.show();
    }
    //@@author
}
