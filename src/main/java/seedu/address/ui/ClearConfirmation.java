package seedu.address.ui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Displays a popup for confirmation for clearing the address book
 */
public class ClearConfirmation {

    public ClearConfirmation() {
    }

    /**
     * Checks whether the user wants to clear the address book.
     * Clicking OK or pressing ENTER button will return true.
     * Click Cancel/Closing the dialog will return false.
     * Pressing SPACE confirms the choice.
     */
    public boolean isClearCommand() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to clear Weaver entirely?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            return true;
        } else {
            // ... user chose CANCEL or closed the dialog
            return false;
        }
    }
}
