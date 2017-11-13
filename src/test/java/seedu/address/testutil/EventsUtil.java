package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;

import guitests.GuiRobot;
import javafx.application.Platform;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * Helper methods related to events.
 */
public class EventsUtil {
    /**
     * Posts {@code event} to all registered subscribers. This method will return successfully after the {@code event}
     * has been posted to all subscribers.
     */
    public static void postNow(BaseEvent event) {
        new GuiRobot().interact(() -> EventsCenter.getInstance().post(event));
    }

    /**
     * Posts {@code event} to all registered subscribers at some unspecified time in the future.
     */
    public static void postLater(BaseEvent event) {
        Platform.runLater(() -> EventsCenter.getInstance().post(event));
    }

    public static String getEventDetails(ReadOnlyEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EVENT_NAME + event.getEventName().fullEventName + " ");
        sb.append(PREFIX_EVENT_DESCRIPTION + event.getDescription().eventDesc + " ");
        sb.append(PREFIX_EVENT_TIME + event.getEventTime().eventTime);

        return sb.toString();
    }
}
