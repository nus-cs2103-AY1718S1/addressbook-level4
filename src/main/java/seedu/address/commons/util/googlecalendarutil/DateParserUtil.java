package seedu.address.commons.util.googlecalendarutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import seedu.address.logic.parser.exceptions.ParseException;

/** Helper functions to parse dateTime strings */
public class DateParserUtil {
    private static final String DAY = "Day";
    private static final String MONTH = "Month";
    private static final String DATE = "Date";
    private static final String TIME = "Time";
    private static final String YEAR = "Year";


    /** Convert Google's dateTime string to valid dateTime string */
    public static String convertDateTime(String dateTime) {
        String parsedDateTime = dateTime.replaceFirst("(.*):(..)", "$1$2")
                .replace("T", " ");
        return parsedDateTime;
    }

    /** Parse Google's dateTime and return a hashmap */
    public static HashMap<String, String> parseDateTime(String dateTime) throws ParseException {
        HashMap<String, String> dateTimeMap = new HashMap<>();
        DateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String [] tokens;

        try {
            tokens = dateInput.parse(dateTime).toString().split("\\s+");
        } catch (java.text.ParseException pe) {
            throw new ParseException("Error parsing datetime.");

        }

        dateTimeMap.put(DAY, tokens[0]);
        dateTimeMap.put(MONTH, tokens[1]);
        dateTimeMap.put(DATE, tokens[2]);
        dateTimeMap.put(TIME, tokens[3].substring(0, tokens[3].length() - 3));
        dateTimeMap.put(YEAR, tokens[5]);


        return dateTimeMap;
    }

    /** Ensures event start date is before event end date
     * @param startDateTime
     * @param endDateTime*/
    public static boolean isValidEventDuration(Date startDateTime, Date endDateTime) {
        if (startDateTime.compareTo(endDateTime) > 0) {
            return false;
        }
        return true;
    }

    /** Ensures event start date is after current time
     * @param dateTime*/
    public static boolean isAfterCurrentTime(Date dateTime) {
        Date currentDateTime = new Date();
        if (dateTime.compareTo(currentDateTime) < 0) {
            return false;
        }
        return true;
    }


    /** Sanity checks date input **/
    public static boolean isValidTime(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        sdf.setLenient(false);
        try {
            sdf.parse(dateTime);
        } catch (java.text.ParseException e) {
            return false;
        }

        return true;
    }



    /** Returns yyyy-MM-dd hh:mm representation of current time */
    public static String getCurrentTime() {
        Date currentTime = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return df.format(currentTime).toString();
    }



    /** Returns a Google Calendar-like representation of the duration of an event
     * Examples:
     * Events that span more than a day:
     * Saturday, 21 October, 10:30 -
     * Sunday, 22 October, 11:30
     *
     * Events that finish within a day:
     * Sunday, 22 October
     * 08:30 - 12:30
     *
     */
    public static String getDurationOfEvent(String startDateTime, String endDateTime) throws ParseException {
        HashMap<String, String> sDateTimeMap = parseDateTime(startDateTime);
        HashMap<String, String> eDateTimeMap = parseDateTime(endDateTime);
        StringBuilder durationString = new StringBuilder();
        durationString.append(sDateTimeMap.get(DAY) + ", ");
        durationString.append(sDateTimeMap.get(DATE) + " ");
        durationString.append(sDateTimeMap.get(MONTH) + " ");
        durationString.append(sDateTimeMap.get(YEAR));
        if (sDateTimeMap.get(DATE).equals(eDateTimeMap.get(DATE))
                && sDateTimeMap.get(MONTH).equals(eDateTimeMap.get(MONTH))
                && sDateTimeMap.get(YEAR).equals(eDateTimeMap.get(YEAR))) {
            durationString.append("\n" + sDateTimeMap.get(TIME) + " - " + eDateTimeMap.get(TIME));
        } else {
            durationString.append(", " + sDateTimeMap.get(TIME) + " - \n");
            durationString.append(eDateTimeMap.get(DAY) + ", ");
            durationString.append(eDateTimeMap.get(DATE) + " ");
            durationString.append(eDateTimeMap.get(MONTH) + " ");
            durationString.append(eDateTimeMap.get(YEAR) + ", ");
            durationString.append(eDateTimeMap.get(TIME));
        }

        return durationString.toString();
    }

}
