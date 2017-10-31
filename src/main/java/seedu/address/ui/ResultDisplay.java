package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FontSizeChangeRequestEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private static final int DEFAULT_FONT_SIZE = 17;
    private int fontSizeChange = 0;

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

    @Subscribe
    private void handleFontSizeChangeEvent(FontSizeChangeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        fontSizeChange = event.sizeChange;
        refreshFontSizes();
    }

    /**
     * Updates the font sizes of all components of this component with the {@code fontSizeChange} given.
     */
    private void refreshFontSizes() {
        resultDisplay.setStyle("-fx-font-size: " + (DEFAULT_FONT_SIZE + fontSizeChange));
    }
}
