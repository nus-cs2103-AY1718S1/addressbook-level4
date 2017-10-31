package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.meeting.ReadOnlyMeeting;

/**
 * A utility class to help with building EditMeetingDescriptor objects.
 */
public class EditMeetingDescriptorBuilder {

    private EditMeetingDescriptor descriptor;

    public EditMeetingDescriptorBuilder() {
        descriptor = new EditMeetingDescriptor();
    }

    public EditMeetingDescriptorBuilder(EditMeetingDescriptor descriptor) {
        this.descriptor = new EditMeetingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMeetingDescriptor} with fields containing {@code meeting}'s details
     */
    public EditMeetingDescriptorBuilder(ReadOnlyMeeting meeting) {
        descriptor = new EditMeetingDescriptor();
        descriptor.setNameMeeting(meeting.getName());
        descriptor.setDate(meeting.getDate());
        descriptor.setPlace(meeting.getPlace());
        descriptor.setPhoneNum(meeting.getPersonPhone());
        descriptor.setPersonToMeet(meeting.getPersonName());
    }

    /**
     * Sets the {@code MeetingName} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withMeetingName(String name) {
        try {
            ParserUtil.parseNameMeeting(Optional.of(name)).ifPresent(descriptor::setNameMeeting);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withDate(String date) {
        try {
            ParserUtil.parseDate(Optional.of(date)).ifPresent(descriptor::setDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code PersonToMeet} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withPersonToMeet(String personToMeet) {
        try {
            ParserUtil.parsePersonToMeet(Optional.of(personToMeet)).ifPresent(descriptor::setPersonToMeet);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("person name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Place} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withPlace(String place) {
        try {
            ParserUtil.parsePlace(Optional.of(place)).ifPresent(descriptor::setPlace);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("place is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code PhoneNum} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withPhoneNum(String phoneNum) {
        try {
            ParserUtil.parsePhoneNum(Optional.of(phoneNum)).ifPresent(descriptor::setPhoneNum);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone number is expected to be unique.");
        }
        return this;
    }



    public EditMeetingDescriptor build() {
        return descriptor;
    }
}

