package guitests.guihandles;

import javafx.scene.Node;


/**
 * Provides a handle for CalendarView
 */
public class CalendarViewHandle extends NodeHandle<Node> {

    public static final String CALENDAR_VIEW_ID = "#calendarPlaceholder";

    protected CalendarViewHandle(Node rootNode) {
        super(rootNode);
    }
}
