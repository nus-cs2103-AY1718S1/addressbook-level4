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
import seedu.address.commons.events.ui.FontSizeRefreshRequestEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;

/**
 * A ui for the display of the results.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private static final int DEFAULT_FONT_SIZE = 17;

    private final StringProperty displayed = new SimpleStringProperty("");

    private final Logic logic;

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay(Logic logic) {
        super(FXML);
        this.logic = logic;
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

    // @@author donjar
    @Subscribe
    private void handleFontSizeChangeEvent(FontSizeRefreshRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        refreshFontSizes();
    }

    /**
     * Updates the font sizes of all components of this component with the {@code fontSizeChange} given.
     */
    private void refreshFontSizes() {
        resultDisplay.setStyle("-fx-font-size: " + (DEFAULT_FONT_SIZE + logic.getFontSizeChange()));
    }
    // @@author
}
