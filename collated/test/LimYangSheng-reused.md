# LimYangSheng-reused
###### \java\guitests\guihandles\MeetingListPanelHandle.java
``` java
/**
 * Provides a handle for {@code MeetingListPanel} containing the list of {@code MeetingCard}.
 */
public class MeetingListPanelHandle extends NodeHandle<ListView<MeetingCard>> {
    public static final String MEETING_LIST_VIEW_ID = "#meetingListView";

    public MeetingListPanelHandle(ListView<MeetingCard> meetingListPanelNode) {
        super(meetingListPanelNode);
    }

}
```
