package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.WebsiteSelectionRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author hansiang93
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
        registerAsAnEventHandler(this);
        setEventHandlerForButtonClick();
        buttonBar.getButtons().setAll();
    }

    private void setEventHandlerForButtonClick() {
        mapsButton.setOnMouseClicked(e -> {
            logger.info("Maps button clicked");
            raise(new WebsiteSelectionRequestEvent("mapsView"));
        });
        searchButton.setOnMouseClicked(e -> {
            logger.info("Search button clicked");
            raise(new WebsiteSelectionRequestEvent("searchView"));
        });
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson selectedPerson = event.getNewSelection().person;
        ArrayList<Button> buttonList = new ArrayList<>();
        selectedPerson.getWebLinks().forEach(webLink -> {
            Button newbutton = new Button(webLink.toStringWebLinkTag());
            newbutton.setOnMouseClicked(e -> {
                logger.info(webLink.toStringWebLinkTag() + " button clicked");
                raise(new WebsiteSelectionRequestEvent(webLink.toStringWebLinkTag()));
            });
            buttonList.add(newbutton);
        });
        buttonList.add(searchButton);
        if (!selectedPerson.addressProperty().get().toString().equals("-")) {
            buttonList.add(mapsButton);
        }
        buttonBar.getButtons().setAll(buttonList);
    }
}
