package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class PersonInformationPanel extends UiPart<Region> {

    private static final String FXML = "PersonInformationPanel.fxml";

    @FXML
    private Rectangle testRectangle;

    public PersonInformationPanel() {
        super(FXML);
    }
}
