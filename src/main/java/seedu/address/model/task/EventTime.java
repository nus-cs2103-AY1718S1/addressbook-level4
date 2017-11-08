package seedu.address.model.task;

public class EventTime {

    private final String startTime;
    private final String endTime;

    public EventTime(String startTime, String endTime) {
        String trimmedStartTime = startTime.trim();
        String trimmedEndTime = endTime.trim();
        this.startTime = trimmedStartTime;
        this.endTime = trimmedEndTime;
    }

    //public boolean areTimesValid

    //publ
}
