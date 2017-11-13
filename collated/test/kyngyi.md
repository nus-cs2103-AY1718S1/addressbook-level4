# kyngyi
###### \java\seedu\address\logic\commands\EditMeetingCommandTest.java
``` java
public class EditMeetingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Meeting editedMeeting = new MeetingBuilder().build();
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(editedMeeting).build();
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_FIRST_MEETING, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMeeting(model.getFilteredMeetingList().get(0), editedMeeting);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastMeeting = Index.fromOneBased(model.getFilteredMeetingList().size());
        ReadOnlyMeeting lastMeeting = model.getFilteredMeetingList().get(indexLastMeeting.getZeroBased());

        MeetingBuilder meetingInList = new MeetingBuilder(lastMeeting);
        Meeting editedMeeting = meetingInList.withNameMeeting(VALID_NAME_BIKING).withPhoneNum(VALID_PHONENUM_BIKING)
                .withDateTime(VALID_DATE_BIKING).withPersonToMeet(VALID_PERSONTOMEET_BIKING)
                .withPlace(VALID_PLACE_BIKING)
                .build();

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withMeetingName(VALID_NAME_BIKING)
                .withPhoneNum(VALID_PHONENUM_BIKING).withPlace(VALID_PLACE_BIKING).withDate(VALID_DATE_BIKING)
                .build();

        EditMeetingCommand editMeetingCommand = prepareCommand(indexLastMeeting, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMeeting(lastMeeting, editedMeeting);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_FIRST_MEETING, new EditMeetingDescriptor());
        ReadOnlyMeeting editedMeeting = model.getFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstMeetingOnly(model);

        ReadOnlyMeeting meetingInFilteredList = model.getFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());
        Meeting editedMeeting = new MeetingBuilder(meetingInFilteredList)
                .withNameMeeting(VALID_NAME_BIKING).withDateTime(VALID_DATE_BIKING).build();
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_FIRST_MEETING, new EditMeetingDescriptorBuilder()
                .withMeetingName(VALID_NAME_BIKING).withDate(VALID_DATE_BIKING).build());

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateMeeting(model.getFilteredMeetingList().get(0), editedMeeting);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    /**
    @Test
    public void execute_duplicateMeetingUnfilteredList_failure() {
        Meeting firstMeeting = new Meeting(model.getFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased()));
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(firstMeeting).build();
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_SECOND_MEETING, descriptor);

        assertCommandFailure(editMeetingCommand, model, EditMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    @Test
    public void execute_duplicateMeetingFilteredList_failure() {
        showFirstMeetingOnly(model);

        // edit meeting in filtered list into a duplicate in address book
        ReadOnlyMeeting meetingInList = model.getAddressBook().getMeetingList()
                .get(INDEX_SECOND_MEETING.getZeroBased());
        EditMeetingCommand editMeetingCommand = prepareCommand(INDEX_FIRST_MEETING,
                new EditMeetingDescriptorBuilder(meetingInList).build());

        assertCommandFailure(editMeetingCommand, model, EditMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    @Test
    public void execute_invalidMeetingIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMeetingList().size() + 1);
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withMeetingName(VALID_NAME_BIKING).build();
        EditMeetingCommand editMeetingCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editMeetingCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }
    */

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidMeetingIndexFilteredList_failure() {
        showFirstMeetingOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_MEETING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getMeetingList().size());

        EditMeetingCommand editMeetingCommand = prepareCommand(outOfBoundIndex,
                new EditMeetingDescriptorBuilder().withMeetingName(VALID_NAME_BIKING).build());

        assertCommandFailure(editMeetingCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditMeetingCommand standardCommand = new EditMeetingCommand(INDEX_FIRST_MEETING, DESC_ACTIVITY);

        // same values -> returns true
        EditMeetingDescriptor copyDescriptor = new EditMeetingDescriptor(DESC_ACTIVITY);
        EditMeetingCommand commandWithSameValues = new EditMeetingCommand(INDEX_FIRST_MEETING, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMeetingCommand(INDEX_SECOND_MEETING, DESC_ACTIVITY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMeetingCommand(INDEX_FIRST_MEETING, DESC_BIKING)));
    }

    /**
     * Returns an {@code EditMeetingCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditMeetingCommand prepareCommand(Index index, EditMeetingDescriptor descriptor) {
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(index, descriptor);
        editMeetingCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editMeetingCommand;
    }
}
```
###### \java\seedu\address\testutil\EditMeetingDescriptorBuilder.java
``` java
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
        descriptor.setPersonsMeet(meeting.getPersonsMeet());
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
     * Sets the {@code Place} of the {@code EditMeetingDescriptor} that we are building.
     */
    /**
    public EditMeetingDescriptorBuilder withPersons(String index) {
        try {
            ParserUtil.parse(Optional.of(index)).ifPresent(descriptor::setPersonsMeet);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("place is expected to be unique.");
        }
        return this;
    }
     */




    public EditMeetingDescriptor build() {
        return descriptor;
    }
}

```
###### \java\seedu\address\testutil\MeetingBuilder.java
``` java
    /**
     * Sets the {@code NameMeeting} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withNameMeeting(String nameMeeting) {
        try {
            this.meeting.setName(new NameMeeting(nameMeeting));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name of meeting is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withDateTime(String dateTime) {
        try {
            this.meeting.setDateTime(new DateTime(dateTime));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Place} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withPlace(String location) {
        try {
            this.meeting.setPlace(new Place(location));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("location is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Place} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withIndex(Index index) {
        try {
            List<ReadOnlyPerson> persons = new ArrayList<>();
            ReadOnlyPerson person = getTypicalPersons().get(0);
            persons.add(person);
            this.meeting.setPersonsMeet(persons);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Index must be smaller than the size of person list");
        }
        return this;
    }

    public Meeting build() {
        return this.meeting;
    }

}
```
###### \java\seedu\address\testutil\TypicalMeetings.java
``` java
/**
 * A utility class containing a list of {@code Meeting} objects to be used in tests.
 */
public class TypicalMeetings {
    public static final ReadOnlyMeeting AGEING = new MeetingBuilder().withNameMeeting("Ageing")
            .withDateTime("31-01-2019 00:00").withIndex(Index.fromOneBased(1)).withPlace("Vivocity").build();
    public static final ReadOnlyMeeting BREEDING = new MeetingBuilder().withNameMeeting("Breeding")
            .withDateTime("31-01-2019 00:01").withIndex(Index.fromOneBased(2)).withPlace("West Coast Park").build();
    public static final ReadOnlyMeeting CYCLING = new MeetingBuilder().withNameMeeting("Cycling")
            .withDateTime("01-01-2019 00:02").withIndex(Index.fromOneBased(3)).withPlace("East Coast Park").build();
    public static final ReadOnlyMeeting DIVING = new MeetingBuilder().withNameMeeting("Diving Lesson")
            .withDateTime("01-01-2019 00:03").withIndex(Index.fromOneBased(4))
            .withPlace("Jurong Swimming Complex").build();
    public static final ReadOnlyMeeting EATING = new MeetingBuilder().withNameMeeting("Eating")
            .withDateTime("01-01-2019 00:04").withIndex(Index.fromOneBased(5)).withPlace("TechnoEdge").build();
    public static final ReadOnlyMeeting FENCING = new MeetingBuilder().withNameMeeting("Fencing Class")
            .withDateTime("01-01-2019 00:05").withIndex(Index.fromOneBased(6)).withPlace("NUS").build();
    public static final ReadOnlyMeeting GARDENING = new MeetingBuilder().withNameMeeting("Gardening")
            .withDateTime("01-01-2019 00:06").withIndex(Index.fromOneBased(7)).withPlace("My Backyard").build();

    // Manually added
    //    public static final ReadOnlyMeeting HIKING = new MeetingBuilder().withNameMeeting("Hiking")
    //            .withDateTime("01-01-2019 00:07").withPersonToMeet("Hiker Lo")
    //            .withPhoneNum("97745668").withPlace("Macritchie Reservoir").build();
    //    public static final ReadOnlyMeeting ICESKATING = new MeetingBuilder().withNameMeeting("Ice Skating")
    //            .withDateTime("01-01-2019 00:08").withPersonToMeet("Elsa Koh")
    //            .withPhoneNum("81234567").withPlace("JEM").build();
    //
    // Manually added - Meeting's details found in {@code CommandTestUtil}
    public static final ReadOnlyMeeting ACTIVITY = new MeetingBuilder().withNameMeeting(VALID_NAME_ACTIVITY)
            .withPlace(VALID_PLACE_ACTIVITY).withDateTime(VALID_DATE_ACTIVITY)
            .build();
    public static final ReadOnlyMeeting BIKING = new MeetingBuilder().withNameMeeting(VALID_NAME_BIKING)
            .withPlace(VALID_PLACE_BIKING).withDateTime(VALID_DATE_BIKING).build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMeetings() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical meetings.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        for (ReadOnlyMeeting meeting : getTypicalMeetings()) {
            try {
                ab.addMeeting(meeting);
            } catch (DuplicateMeetingException e) {
                assert false : "duplicate not possible";
            } catch (MeetingBeforeCurrDateException mbcde) {
                assert false : "mbcde not possible";
            } catch (MeetingClashException mce) {
                assert false : "mce not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyMeeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(AGEING, BREEDING, CYCLING, DIVING, EATING, FENCING, GARDENING));
    }
}
```
