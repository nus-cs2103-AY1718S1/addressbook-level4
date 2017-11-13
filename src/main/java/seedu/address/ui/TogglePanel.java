package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TogglePanelEvent;
import seedu.address.commons.events.ui.ToggleSelectEvent;

// @@author LeonChowWenHao
/**
 * A ui containing browserpanel, informationboard and eventlistpanel.
 * This UI is also responsible for handling toggle request events, switching between displaying details or the browser.
 */
public class TogglePanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(TogglePanel.class);
    private static final String FXML = "TogglePanel.fxml";

    private boolean browserIsFront;

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
        browserIsFront = false;
        registerAsAnEventHandler(this);
    }

    @Subscribe
    public void handleToggleSelectEvent(ToggleSelectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserToFront();
    }

    @Subscribe
    public void handleTogglePanelEvent(TogglePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        triggerToggle();
    }

    /**
    * Triggers the toggling mechanism to switch the browserPlaceHolder and the splitPane.
    */
    private void triggerToggle() {
        if (browserIsFront) {
            browserToBack();
        } else {
            browserToFront();
        }
    }

    /**
     * Sets the browserPlaceHolder to be visible and in front
     * while setting the splitPane to be invisible and at the back.
     */
    private void browserToFront() {
        browserPlaceHolder.setVisible(true);
        browserPlaceHolder.toFront();
        toggleSplitPane.setVisible(false);
        toggleSplitPane.toBack();
        browserIsFront = true;
    }

    /**
     * Sets the browserPlaceHolder to be invisible and at the back
     * while setting the splitPane to be visible and in front.
     */
    private void browserToBack() {
        browserPlaceHolder.setVisible(false);
        browserPlaceHolder.toBack();
        toggleSplitPane.setVisible(true);
        toggleSplitPane.toFront();
        browserIsFront = false;
    }
}
