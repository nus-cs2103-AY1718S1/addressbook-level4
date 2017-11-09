package seedu.room.model.person;

import java.time.LocalDateTime;

import seedu.room.commons.exceptions.IllegalValueException;

//@@author Haozhe321
/**
 Create a timestamp in each person to record the time created and time that this temporary person will expire
 */
public class Timestamp {

    public static final String MESSAGE_TIMESTAMP_CONSTRAINTS =
            "Days to expire cannot be negative";

    private LocalDateTime creationTime = null;
    private LocalDateTime expiryTime = null; //after construction, a null expiryTime means this person will not expire
    private long daysToLive;

    public Timestamp(long day) throws IllegalValueException {
        creationTime = LocalDateTime.now().withNano(0).withSecond(0).withMinute(0);
        if (!isValidTimestamp(day)) {
            throw new IllegalValueException(MESSAGE_TIMESTAMP_CONSTRAINTS);
        }
        if (day > 0) {
            expiryTime = creationTime.plusDays(day).withNano(0).withSecond(0).withMinute(0);
        }
        daysToLive = day;
    }

    public Timestamp(String expiry) {
        expiryTime = LocalDateTime.parse(expiry);
        expiryTime = expiryTime.withNano(0).withSecond(0).withMinute(0);
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public long getDaysToLive() {
        return daysToLive;
    }

    /**
     * following method returns null if this person does not expiry
     * @return time of expiry
     */
    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    /**
     *
     * @return the expiry time of the timestamp in String
     */
    public String toString() {
        if (expiryTime == null) {
            return "null";
        } else {
            return expiryTime.toString();
        }
    }


    /**
     * Returns true if a given long is a valid timestamp.
     */
    public static boolean isValidTimestamp(long test) {
        return (test >= 0);
    }

}
