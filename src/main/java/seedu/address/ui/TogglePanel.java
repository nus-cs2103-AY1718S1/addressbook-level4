package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

//import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TogglePanelEvent;

/**
 * A ui containing browserpanel, informationboard and eventlistpanel.
 * This UI is also responsible for handling toggle request events, switching between displaying details or the browser.
 */
public class TogglePanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(TogglePanel.class);
    private static final String FXML = "TogglePanel.fxml";

    Boolean browserInfront;

    @FXML
    private SplitPane toggleSplitPane;

    @FXML
    private StackPane browserPlaceHolder;

    @FXML
    private StackPane informationBoardPlaceHolder;

    @FXML
    private StackPane eventListPanelPlaceHolder;

    public TogglePanel(BrowserPanel browserPanel, InformationBoard informationBoard,
                       EventListPanel eventListPanel) {
        super(FXML);
        browserPlaceHolder.getChildren().add(browserPanel.getRoot());
        informationBoardPlaceHolder.getChildren().add(informationBoard.getRoot());
        eventListPanelPlaceHolder.getChildren().add(eventListPanel.getRoot());
        browserInfront = false;
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewTogglePanelEvent(TogglePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        //Platform.runLater(() -> displayed.setValue(event.message));
        triggerToggle();
    }

    public void triggerToggle() {
        if (browserInfront) {
            toggleSplitPane.toFront();
            toggleSplitPane.setVisible(true);
            browserPlaceHolder.toBack();
            browserPlaceHolder.setVisible(false);
            browserInfront = false;
        } else {
            toggleSplitPane.toBack();
            toggleSplitPane.setVisible(false);
            browserPlaceHolder.toFront();
            browserPlaceHolder.setVisible(true);
            browserInfront = true;
        }
    }

}
