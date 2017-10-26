package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.Meeting;
import seedu.address.model.ReadOnlyMeetingList;
import seedu.address.model.UniqueMeetingList;
import seedu.address.model.person.InternalId;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalMeetings {

    public static final Meeting M1= new MeetingBuilder().build();

    private TypicalMeetings() {} // prevents instantiation


    /**
     * Returns a {@code UniqueMeetingList} with all the typical meetings
     * @return
     */
    public static UniqueMeetingList getTypicalMeetingList() {
        try {
            UniqueMeetingList meetings = new UniqueMeetingList();
            for (Meeting m : getTypicalMeetings()) {
                meetings.add(m);
            }
            return meetings;
        } catch (UniqueMeetingList.DuplicateMeetingException e) {
            throw new AssertionError("sample data cannot contain duplicate meetings", e);
        }
    }

    public static List<Meeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(M1));
    }


}
