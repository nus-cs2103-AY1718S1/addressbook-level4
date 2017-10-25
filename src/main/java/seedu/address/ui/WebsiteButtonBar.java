package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.WebsiteSelectionRequestEvent;

/**
 * The Button Bar above the browser of the App.
 */
public class WebsiteButtonBar extends UiPart<Region> {

    private static final String FXML = "WebsiteButtonbar.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private Button mapsButton;

    @FXML
    private Button searchButton;

    public WebsiteButtonBar() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
        setEventHandlerForButtonClick();
    }

    private void setEventHandlerForButtonClick() {
        mapsButton.setOnMouseClicked(e -> {
            logger.info("maps button clicked");
            raise(new WebsiteSelectionRequestEvent("mapsView"));
        });
        searchButton.setOnMouseClicked(e -> {
            logger.info("Search button clicked");
            raise(new WebsiteSelectionRequestEvent("searchView"));
        });
    }

    // To add dynamically added buttons in the future
}
