package seedu.address.ui;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

/**
 * Panel that displays the additional details of a Person
 */
public class CommandBoxIcon extends UiPart<Region> {

    private static final String FXML = "CommandBoxIcon.fxml";

    private Ikon iconCode = Feather.FTH_PLUS;

    @FXML
    private FontIcon icon;

    public CommandBoxIcon() {
        super(FXML);
        icon.iconCodeProperty().set(iconCode);
    }

}
