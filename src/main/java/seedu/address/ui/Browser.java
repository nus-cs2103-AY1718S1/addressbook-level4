package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * The Browser of the App.
 */
public class Browser extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";

    private static final String FXML = "Browser.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;


    public Browser() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        loadDefaultPage();

    }


    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     *  Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        // loadPage("https://i.pinimg.com/736x/25/9e/ab/
        // 259eab749e20a2594e83025c5cf9c79c--being-a-gentleman-gentleman-rules.jpg");

        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
