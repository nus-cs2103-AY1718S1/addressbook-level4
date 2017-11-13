package seedu.address.external.addevent;

import java.io.IOException;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.external.AddEventRequestEvent;
import seedu.address.model.calendarevent.CalendarEvent;

//@@author yilun-zhu
/**
 * Follows Singleton and Facade design pattern,
 * for other parts of the app to interact with the google calendar API service
 */
public class AddEventManager {

    private static AddEventManager instance = null;

    protected AddEventManager() {
        registerAsAnEventHandler(this);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Creates an instance of the CalendarApi and registers it as an event handler
     * @return The Singleton instance of the CalendarApi
     */
    public static AddEventManager init() {
        if (instance == null) {
            instance = new AddEventManager();
        }
        return instance;
    }

    @Subscribe
    public static void handleAddEventRequestEvent(AddEventRequestEvent addEventRequestEvent) throws IOException {
        CalendarEvent eventSent = addEventRequestEvent.getCalendarEvent();
        CalendarApi.createEvent(eventSent);
    }


}
