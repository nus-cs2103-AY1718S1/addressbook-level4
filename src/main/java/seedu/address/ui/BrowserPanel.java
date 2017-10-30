package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ParcelPanelSelectionChangedEvent;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_MAP_URL_PREFIX = "https://www.google.com.sg/maps/search/";
    public static final int POSTAL_CODE_LENGTH = 6;

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadParcelLocationPage(ReadOnlyParcel parcel) {
        loadPage(GOOGLE_MAP_URL_PREFIX + parcel.getAddress().postalCode.value.substring(1));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleParcelPanelSelectionChangedEvent(ParcelPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadParcelLocationPage(event.getNewSelection().parcel);
    }

    //@@author kennard123661
    public static String getMapQueryStringFromPostalString(String postalCode) {
        int firstDigitIndex = 1;
        int lastDigitIndex = 7;

        return "Singapore+" + postalCode.substring(firstDigitIndex, lastDigitIndex);
    }

}
