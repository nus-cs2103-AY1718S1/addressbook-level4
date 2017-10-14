package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

/**
 * Panel containing the todolist of a person.
 */
public class RemarkPanel extends UiPart<Region> {

    private final Logger logger = LogsCenter.getLogger(RemarkPanel.class);
    private static final String FXML = "RemarkPanel.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea remarkPanel;

    public RemarkPanel() {
        super(FXML);
        remarkPanel.textProperty().bind(displayed);

        registerAsAnEventHandler(this);
        remarkPanel.setStyle("-fx-text-fill:" + "black");
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue("Remarks regarding "
                + String.valueOf(event.getNewSelection().person.getName()) + ":\n"
                + String.valueOf(event.getNewSelection().person.getRemark())));
        if (String.valueOf(event.getNewSelection().person.getRemark()).equals(null)){
            Platform.runLater(() -> displayed.setValue("Remarks regarding "
                    + String.valueOf(event.getNewSelection().person.getName()) + ":\n"));
        }

    }

}
