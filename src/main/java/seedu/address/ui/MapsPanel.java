package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Maps Panel of the App.
 */
public class MapsPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String MAPS_DIR_URL_PREFIX = "https://www.google.com/maps/dir/?api=1";
    public static final String MAPS_DEFAULT_ORIGIN = "&origin=My+Location";
    public static final String MAPS_DEST_PREFIX = "&destination=";
    public static final String MAPS_SEARCH_ORIGIN = "&query=My+Location";
    public static final String MAPS_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/?api=1";
    public static final String MAPS_SEARCH_URL_SUFFIX = "&dg=dbrw&newdg=1";

    private static final String FXML = "MapsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView maps;

    public MapsPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }


    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(MAPS_DIR_URL_PREFIX + MAPS_DEFAULT_ORIGIN + MAPS_DEST_PREFIX
                + person.getAddress().value.replaceAll(" ", "+") + MAPS_SEARCH_URL_SUFFIX);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> maps.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(MAPS_SEARCH_URL_PREFIX + MAPS_SEARCH_ORIGIN);
    }

    /**
     * Frees resources allocated to the maps.
     */
    public void freeResources() {
        maps = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
