package seedu.address.ui;

import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_LARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_NORMAL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_SMALL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XLARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XSMALL;
import static seedu.address.model.font.FontSize.FONT_SIZE_LARGE_NUMBER;
import static seedu.address.model.font.FontSize.FONT_SIZE_NORMAL_NUMBER;
import static seedu.address.model.font.FontSize.FONT_SIZE_SMALL_NUMBER;
import static seedu.address.model.font.FontSize.FONT_SIZE_XLARGE_NUMBER;
import static seedu.address.model.font.FontSize.FONT_SIZE_XSMALL_NUMBER;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

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

    //@@author cctdaniel
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }

    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_XSMALL_NUMBER + ";");
            break;

        case FONT_SIZE_SMALL:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_SMALL_NUMBER + ";");
            break;

        case FONT_SIZE_NORMAL:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_NORMAL_NUMBER + ";");
            break;

        case FONT_SIZE_LARGE:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_LARGE_NUMBER + ";");
            break;

        case FONT_SIZE_XLARGE:
            resultDisplay.setStyle("-fx-font-size: " + FONT_SIZE_XLARGE_NUMBER + ";");
            break;

        default:
            break;
        }
    }

}
