package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarPanelSelectionEvent;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e ->
                handleCalendarEvent(date.toString()));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private void handleCalendarEvent(String event){
        EventsCenter.getInstance().post(new CalendarPanelSelectionEvent(date.toString()));
        logger.info(LogsCenter.getEventHandlingLogMessage(new CalendarPanelSelectionEvent(event)));
    }
}
