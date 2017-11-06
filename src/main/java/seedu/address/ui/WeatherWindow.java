package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * A controller for Yahoo weather forecast window
 * */
public class WeatherWindow extends UiPart<Region> {

    public static final String USERGUIDE_FILE_PATH = "https://www.yahoo.com/news/weather";

    private static final Logger logger = LogsCenter.getLogger(WeatherWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Weather";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public WeatherWindow() throws JAXBException, IOException {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size

        browser.getEngine().load(USERGUIDE_FILE_PATH);
    }

    /**
     * Shows the Yahoo weather window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing Yahoo weather window.");
        dialogStage.showAndWait();
    }


}
