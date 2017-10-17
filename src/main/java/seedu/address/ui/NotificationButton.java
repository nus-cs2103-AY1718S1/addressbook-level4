package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class NotificationButton extends UiPart<Region> {

    private static final String FXML = "NotificationButton.fxml";

    @FXML
    private AnchorPane notificationButton;

    public NotificationButton() {
        super(FXML);
    }
}
