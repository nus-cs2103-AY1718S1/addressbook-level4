package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Meeting;
import seedu.address.model.ReadOnlyMeetingList;

//@@author liuhang0213
/**
 * An Immutable MeetingList that is serializable to XML format
 */
@XmlRootElement(name = "meetingList")
public class XmlSerializableMeetingList extends XmlSerializableData implements ReadOnlyMeetingList {

    @XmlElement
    private List<XmlAdaptedMeeting> meetings;

    /**
     * Creates an empty XmlSerializableMeetingList
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableMeetingList() {
        meetings = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableMeetingList(ReadOnlyMeetingList src) {
        this();
        meetings.addAll(src.getMeetingList().stream().map(XmlAdaptedMeeting::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<Meeting> getMeetingList() {
        final ObservableList<Meeting> meetings = this.meetings.stream().map(m -> {
            try {
                return m.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(meetings);
    }

    /**
     * Should not be reached since the list should be immutable and cannot be sorted
     * @return
     */
    @Override
    public Meeting getUpcomingMeeting() {
        return null;
    }
}
