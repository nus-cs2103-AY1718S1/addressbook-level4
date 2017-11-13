# Sri-vatsa
###### /IllegalTimeException.java
``` java
//No longer required as DatetimeException handles both exceptions in date and time

public class IllegalTimeException extends IllegalValueException {

    public IllegalTimeException(String message) {super (message);}
}
```
###### /DateTimeConverter.java
``` java
//this code is no longer used because it has been condensed into parseUtil & revised to use java lcoaldatetime class
/**
 * converts date and time in string into datetime
 */
public class DateTimeConverter{

    private LocalDateTime localDateTime;
    private final Logger logger = LogsCenter.getLogger(DateTimeConverter.class);

    public DateTimeConverter(String date, String time) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");
            String dateTime = date + " " + time;
            localDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException dtpe) {
            logger.info("Invalid date/time format!");
        }
        /*
        try {
            //determine date
            LocalDate parsedDate = dateConverter(date);

            //determine time
            LocalTime parsedTime = timeConverter(time);
            localDateTime = LocalDateTime.of(parsedDate, parsedTime);
        }
        catch (IllegalDateException ide) {
            logger.info("Invalid Date Format");
        } catch (IllegalTimeException ite) {
            logger.info("Invalid Time Format");
        }
        */
    }

    /**
     * Helper function to convert date in String to the right format
     * @param date
     * @return LocalDate date in the right format
     */
    private LocalDate dateConverter (String date) throws IllegalValueException {
        String [] dateArray = date.split("/", 3);

        if(dateArray.length != 3) {
            throw new IllegalValueException("Date should be in format: DD/MM/YYYY");
        }

        int day = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int year = Integer.parseInt(dateArray[2]);

        if(day < 0 || day > 31) {
            throw new IllegalValueException("Invalid day");
        } else if (month < 0 || month > 12) {
            throw new IllegalValueException("Invalid month");
        } else if ( year < LocalDateTime.now().getYear() || year > LocalDateTime.now().getYear() + 100) {
            throw new IllegalValueException("Invalid year");
        }

        return  LocalDate.of(year, month, day);
    }

    /**
     * Helper function to convert time in String to 24 hour clock
     * @param time
     * @return LocalTime time in the right format
     */
    private LocalTime timeConverter (String time) throws IllegalTimeException {

            int timeInt = Integer.parseInt(time);

            if(timeInt > 2400 || timeInt < 0) {
                throw new IllegalTimeException("Time should be in format: HHMM");
            }

            int hour = timeInt / 100;
            int min = timeInt % 100;
            return of(hour, min ,0);
    }

    /**
     * Getter method for local date time
     * @return LocalDateTime
     */
    public LocalDateTime getLocalDateTime () {
        return localDateTime;
    }

}
```
