package seedu.address.ui;

import java.awt.Label;
import javax.swing.plaf.synth.Region;

import javafx.fxml.FXML;

/**
 * The Contact Details Panel of the App.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    @FXML
    private Label nameLable;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    public PersonDetailsPanel() {
        super(FXML);
    }
}
