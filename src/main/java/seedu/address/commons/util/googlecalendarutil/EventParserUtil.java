package seedu.address.commons.util.googlecalendarutil;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ServiceHandlerUtil;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;
import seedu.address.model.schedule.ScheduleName;
import seedu.address.model.schedule.UniqueScheduleList;

//@@author cjianhui
/** Class to query Google's calendar API to obtain events */
public class EventParserUtil {

    private static final String API = "https://www.googleapis.com/calendar/v3/calendars/";

    /** Node names for Jackson JSON parser to traverse JSON response */
    private static final String EVENTS = "/items";
    private static final String EVENT_NAME = "/summary";
    private static final String EVENT_START = "/start";
    private static final String EVENT_DATE_TIME = "/dateTime";
    private static final String EVENT_END = "/end";
    private static final String EVENT_DETAILS = "/description";
    private static final String KEY = "AIzaSyB34cw8YT02y2qA8ElCddMLxNvS3o1_siI";

    /** Get events ordered by start time */
    private static final String QUERY = "/events?singleEvents=true&orderBy=startTime&key=";

    private static Schedule getSingleSchedule(JsonNode event) throws IllegalValueException {
        String name = event.at(EVENT_NAME).asText();
        String details = event.at(EVENT_DETAILS).asText();
        JsonNode sDateTime = event.at(EVENT_START);
        JsonNode eDateTime = event.at(EVENT_END);
        String startDateTime = DateParserUtil.convertDateTime(sDateTime.at(EVENT_DATE_TIME).asText());
        String endDateTime = DateParserUtil.convertDateTime(eDateTime.at(EVENT_DATE_TIME).asText());
        String duration = DateParserUtil.getDurationOfEvent(startDateTime, endDateTime);
        Schedule schedule = new Schedule(new ScheduleName(name), new ScheduleDate(startDateTime),
                new ScheduleDate(endDateTime), duration, details);
        return schedule;
    }


    public static UniqueScheduleList getScheduleList(String calendarId) throws IOException, IllegalValueException {
        UniqueScheduleList scheduleList = new UniqueScheduleList();
        String apiUrl = API + calendarId + QUERY + KEY;
        String response = ServiceHandlerUtil.makeCall(apiUrl);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode events = root.at(EVENTS);
        for (JsonNode event: events) {
            scheduleList.add(getSingleSchedule(event));
        }
        return scheduleList;

    }

}
