package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

//@@author yewshengkai-unused
/*Since having buttons to open windows form and
 executing different command is not graded, these features are removed.*/
/**
 * Panel containing all the buttons.
 */
public class ButtonsPanel extends UiPart<Region> {
    private static final String FXML = "ButtonsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ButtonsPanel.class);

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private Button refreshButton;

    public ButtonsPanel() {
        super(FXML);
        setButtonsImage();
        registerAsAnEventHandler(this);
    }

    public void setButtonsImage() {
        addButton.setGraphic(new ImageView("/docs/images/addIcon.png"));
        deleteButton.setGraphic(new ImageView("/docs/images/deleteIcon.png"));
        editButton.setGraphic(new ImageView("/docs/images/editIcon.png"));
        clearButton.setGraphic(new ImageView("/docs/images/clearIcon.png"));
        redoButton.setGraphic(new ImageView("/docs/images/redoIcon.png"));
        undoButton.setGraphic(new ImageView("/docs/images/undoIcon.png"));
        refreshButton.setGraphic(new ImageView("/docs/images/refreshIcon.png"));
    }

}
