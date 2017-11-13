# zacharytang
###### \java\seedu\address\commons\util\TimetableParserUtilTest.java
``` java
public class TimetableParserUtilTest {

    private static final String INVALID_URL = "https://www.google.com";
    private static final String INVALID_URL_NOT_URL = "hello123";
    private static final String INVALID_SHORT_URL = "http://modsn.us/abc";

    private static final String VALID_URL_EMPTY = "http://modsn.us/5tN3z";
    private static final String VALID_URL_ALL_TYPES = "http://modsn.us/BkDgl";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseUrl_invalidUrl_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_TIMETABLE_URL_CONSTRAINTS);
        parseUrl(INVALID_URL);
    }

    @Test
    public void parseUrl_notUrl_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_TIMETABLE_URL_CONSTRAINTS);
        parseUrl(INVALID_URL_NOT_URL);
    }

    @Test
    public void parseUrl_invalidShortUrl_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_SHORT_URL);
        parseUrl(INVALID_SHORT_URL);
    }

    @Test
    public void parseUrl_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        parseUrl(null);
    }

    @Test
    public void parseUrl_validUrlEmptyTimetable_success() throws Exception {
        TimetableInfo emptyTimetable = new TimetableInfo();
        assertEquals(emptyTimetable, parseUrl(VALID_URL_EMPTY));
    }

    @Test
    public void parseUrl_validUrl_success() throws Exception {
        TimetableInfo emptyTimetable = new TimetableInfo();
        assertNotEquals(emptyTimetable, parseUrl(VALID_URL_ALL_TYPES));
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // missing timetable prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + GENDER_DESC_BOB
                + MATRIC_NO_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BIRTHDAY_DESC_BOB + VALID_TIMETABLE_BOB, expectedMessage);

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid timetable
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + GENDER_DESC_BOB
                        + MATRIC_NO_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + BIRTHDAY_DESC_BOB + INVALID_TIMETABLE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Timetable.MESSAGE_TIMETABLE_URL_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseTimetable_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTimetable(null);
    }

    @Test
    public void parseTimetable_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTimetable(Optional.of(INVALID_TIMETABLE));
    }

    @Test
    public void parseTimetable_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTimetable(Optional.empty()).isPresent());
    }

    @Test
    public void parseTimetable_validValue_returnsTimetable() throws Exception {
        Timetable expectedTimetable = new Timetable(VALID_TIMETABLE);
        Optional<Timetable> actualTimetable = ParserUtil.parseTimetable(Optional.of(VALID_TIMETABLE));

        assertEquals(expectedTimetable, actualTimetable.get());
    }

```
###### \java\seedu\address\model\person\TimetableTest.java
``` java
public class TimetableTest {

    private static final String INVALID_DAY = "Dayday";
    private static final String INVALID_WEEK_TYPE = "No Week";
    private static final String INVALID_TIME = "080q";

    private static final String VALID_URL_EMPTY = "http://modsn.us/5tN3z";
    private static final String VALID_URL = "http://modsn.us/TDSEt";
    private static final String VALID_WEEK_TYPE = "Odd Week";
    private static final String VALID_DAY = "Monday";
    private static final String VALID_TIME = "0800";
    private static final String VALID_TIME_CHECK = "0900";
    private static final String VALID_DAY_CHECK = "Tuesday";
    private static final String VALID_WEEK_TYPE_CHECK = "Even Week";
    private static final String VALID_WEEK_EVERY = "Every Week";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidUrl() throws Exception {
        // invalid urls
        assertFalse(Timetable.isValidUrl("")); // empty string
        assertFalse(Timetable.isValidUrl(" ")); // spaces only
        assertFalse(Timetable.isValidUrl("abcde12345")); // string
        assertFalse(Timetable.isValidUrl("https://www.youtube.com/")); // other url
        assertFalse(Timetable.isValidUrl("http://modsn.us")); // invalid request
        assertFalse(Timetable.isValidUrl("http://mods.us/TDSEt")); // invalid domain name

        // valid urls
        assertTrue(Timetable.isValidUrl("http://modsn.us/TEt")); // bad short url, but still valid
        assertTrue(Timetable.isValidUrl(VALID_URL)); // valid url
        assertTrue(Timetable.isValidUrl(VALID_URL_EMPTY)); // valid url, empty timetable
    }

    @Test
    public void updateSlot() throws Exception {
        TimetableSlot slotToUpdate = new TimetableSlot();
        slotToUpdate.setLesson();
        assertNotEquals(slotToUpdate, new TimetableSlot());
    }

    @Test
    public void timetableDay() throws Exception {
        TimetableDay dayToUpdate = new TimetableDay();

        assertEquals(dayToUpdate, dayToUpdate);
        assertNotEquals("Hello", dayToUpdate);

        dayToUpdate.updateSlotsWithLesson(VALID_TIME, VALID_TIME_CHECK);

        assertNotEquals(dayToUpdate, new TimetableDay());
        assertTrue(dayToUpdate.doesSlotHaveLesson(VALID_TIME));
        assertFalse(dayToUpdate.doesSlotHaveLesson(VALID_TIME_CHECK));
    }

    @Test
    public void timetableWeek() throws Exception {
        TimetableWeek weekToUpdate = new TimetableWeek();

        assertEquals(weekToUpdate, weekToUpdate);
        assertNotEquals("Hello", weekToUpdate);

        weekToUpdate.updateSlotsWithLesson(VALID_DAY, VALID_TIME, VALID_TIME_CHECK);

        assertNotEquals(weekToUpdate, new TimetableWeek());
        assertTrue(weekToUpdate.doesSlotHaveLesson(VALID_DAY, VALID_TIME));
        assertFalse(weekToUpdate.doesSlotHaveLesson(VALID_DAY_CHECK, VALID_TIME));
        assertFalse(weekToUpdate.doesSlotHaveLesson(VALID_DAY, VALID_TIME_CHECK));
    }

    @Test
    public void timetableInfo() throws Exception {
        TimetableInfo timetableToUpdate = new TimetableInfo();

        assertEquals(timetableToUpdate, timetableToUpdate);
        assertNotEquals("Hello", timetableToUpdate);

        timetableToUpdate.updateSlotsWithLesson(VALID_WEEK_TYPE, VALID_DAY, VALID_TIME, VALID_TIME_CHECK);
        assertNotEquals(timetableToUpdate, new TimetableInfo());
        assertTrue(timetableToUpdate.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY, VALID_TIME));
        assertFalse(timetableToUpdate.doesSlotHaveLesson(VALID_WEEK_TYPE_CHECK, VALID_DAY, VALID_TIME));
        assertFalse(timetableToUpdate.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY_CHECK, VALID_TIME));
        assertFalse(timetableToUpdate.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY, VALID_TIME_CHECK));
    }

    @Test
    public void timetableQueries_invalidWeekType_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_WEEK_TYPE);
        Timetable timetable = new Timetable(VALID_URL_EMPTY);
        timetable.doesSlotHaveLesson(INVALID_WEEK_TYPE, VALID_DAY, VALID_TIME);
    }

    @Test
    public void timetableQueries_validEveryWeek_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage("Please specify a week type!");
        Timetable timetable = new Timetable(VALID_URL_EMPTY);
        timetable.doesSlotHaveLesson(VALID_WEEK_EVERY, VALID_DAY, VALID_TIME);
    }

    @Test
    public void timetableQueries_invalidDay_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_DAY);
        Timetable timetable = new Timetable(VALID_URL_EMPTY);
        timetable.doesSlotHaveLesson(VALID_WEEK_TYPE, INVALID_DAY, VALID_TIME);
    }

    @Test
    public void timetableQueries_invalidTime_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_TIME);
        Timetable timetable = new Timetable(VALID_URL_EMPTY);
        timetable.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY, INVALID_TIME);
    }

    @Test
    public void timetableQueries_validQuery_success() throws Exception {
        Timetable timetable = new Timetable(VALID_URL);
        timetable.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY, VALID_TIME);
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Timetable} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withTimetable(String url) {
        try {
            ParserUtil.parseTimetable(Optional.of(url)).ifPresent(descriptor::setTimetable);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withTimetable(String url) {
        try {
            this.person.setTimetable(new Timetable(url));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("timetable is expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\ui\StatusBarFooterTest.java
``` java
    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION, SYNC_STATUS_INITIAL,
                STUB_PERSON_TOTAL + TOTAL_PERSONS_TEXT);

        // after address book is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()),
                STUB_PERSON_TOTAL + TOTAL_PERSONS_TEXT);
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus,
                                        String expectedTotalPersons) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertEquals(expectedTotalPersons, statusBarFooterHandle.getTotalPersons());
        guiRobot.pauseForHuman();
    }

}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except timetable -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withGender(VALID_GENDER_AMY)
                .withMatricNo(VALID_MATRIC_NO_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withTimetable(VALID_TIMETABLE_BOB)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + MATRIC_NO_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_AMY + TIMETABLE_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing timetable -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + MATRIC_NO_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid timetable -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + MATRIC_NO_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + INVALID_TIMETABLE_DESC;
        assertCommandFailure(command, Timetable.MESSAGE_TIMETABLE_URL_CONSTRAINTS);

        /* Case: invalid short url -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + MATRIC_NO_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + INVALID_SHORT_URL_DESC;
        assertCommandFailure(command, Timetable.MESSAGE_INVALID_SHORT_URL);

```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: invalid timetable -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_TIMETABLE_DESC,
                Timetable.MESSAGE_TIMETABLE_URL_CONSTRAINTS);

        /* Case: invalid short url -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_SHORT_URL_DESC,
                Timetable.MESSAGE_INVALID_SHORT_URL);

```
