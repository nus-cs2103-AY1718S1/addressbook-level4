# Haozhe321
###### \java\seedu\room\logic\commands\AddCommandTest.java
``` java
        @Override
        public void deleteByTag(Tag tag) throws IllegalValueException, CommandException {
            fail("this method should not be called.");
        }
```
###### \java\seedu\room\logic\commands\AddEventCommandTest.java
``` java
        @Override
        public void deleteByTag(Tag tag) throws IllegalValueException, CommandException {
            fail("this method should not be called.");
        }
```
###### \java\seedu\room\logic\commands\DeleteByTagCommandTest.java
``` java
public class DeleteByTagCommandTest {
    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());


    @Test
    public void execute_validTag_success() throws IllegalValueException, CommandException {
        String tagToDelete = "friends";
        Tag tag = new Tag(tagToDelete);
        DeleteByTagCommand deleteByTagCommand = prepareCommand(tagToDelete);

        String expectedMessage = String.format(DeleteByTagCommand.MESSAGE_DELETE_PERSON_SUCCESS, tag);
        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());

        expectedModel.deleteByTag(tag);

        assertCommandSuccess(deleteByTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() throws IllegalValueException, CommandException {
        String tagToDelete = "thisTagDoesNotExist";
        Tag tag = new Tag(tagToDelete);
        DeleteByTagCommand deleteByTagCommand = prepareCommand(tagToDelete);

        assertCommandFailure(deleteByTagCommand, model, Messages.MESSAGE_INVALID_TAG_FOUND);
    }

    @Test
    public void equals() throws IllegalValueException {
        DeleteByTagCommand deleteByTagFirstCommand = new DeleteByTagCommand("friends");
        DeleteByTagCommand deleteByTagSecondCommand = new DeleteByTagCommand("friends");

        // same object -> returns true
        assertTrue(deleteByTagFirstCommand.equals(deleteByTagSecondCommand));

        // different types -> returns false
        assertFalse(deleteByTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteByTagFirstCommand.equals(null));

    }

    /**
     * Returns a {@code DeleteByTagCommand} with the parameter {@code index}.
     */
    private DeleteByTagCommand prepareCommand(String tagName) throws IllegalValueException {
        DeleteByTagCommand deleteByTagCommand = new DeleteByTagCommand(tagName);
        deleteByTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteByTagCommand;
    }
}
```
###### \java\seedu\room\logic\parser\DeleteByTagCommandParserTest.java
``` java
public class DeleteByTagCommandParserTest {
    private DeleteByTagCommandParser parser = new DeleteByTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByTagCommand() throws IllegalValueException {
        assertParseSuccess(parser, "friends", new DeleteByTagCommand("friends"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "friends forever", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteByTagCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\room\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseTimestamp_invalidInput_throwsNumberFormatException1() throws Exception {
        thrown.expect(NumberFormatException.class);
        ParserUtil.parseTimestamp(Optional.of(INVALID_TIMESTAMP_WITH_DECIMAL));
    }

    @Test
    public void parseTimestamp_invalidInput_throwsNumberFormatException2() throws Exception {
        thrown.expect(NumberFormatException.class);
        ParserUtil.parseTimestamp(Optional.of("-1.5"));
    }

    @Test
    public void parseTimestamp_invalidInput_throwsNumberFormatException3() throws Exception {
        thrown.expect(NumberFormatException.class);
        ParserUtil.parseTimestamp(Optional.of("2/3"));
    }

    @Test
    public void parseTimestamp_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTimestamp(Optional.of(INVALID_TIMESTAMP));
    }

    @Test
    public void parseTimestamp_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTimestamp(Optional.empty()).isPresent());
    }

    @Test
    public void parseTimestamp_validValue_returnsTimestamp() throws Exception {
        Timestamp expectedTimestamp = new Timestamp(1);
        Optional<Timestamp> actualTimestamp = ParserUtil.parseTimestamp(Optional.of(VALID_TIMESTAMP));

        assertEquals(expectedTimestamp.toString(), actualTimestamp.get().toString());
    }

```
###### \java\seedu\room\model\person\TimestampTest.java
``` java
public class TimestampTest {

    @Test
    public void isValidInputTimestamp() {
        // invalid Timestamp
        assertFalse(Timestamp.isValidTimestamp(-1));
        assertFalse(Timestamp.isValidTimestamp(-2));
        assertFalse(Timestamp.isValidTimestamp(-10000));

        //valid Timestamp
        assertTrue(Timestamp.isValidTimestamp(0));
        assertTrue(Timestamp.isValidTimestamp(1));
        assertTrue(Timestamp.isValidTimestamp(1000));
        assertTrue(Timestamp.isValidTimestamp(5));

    }
}
```
###### \java\seedu\room\model\ResidentBookTest.java
``` java
    @Test
    public void deleteTemporaryTest() {
        ResidentBook residentBookOne = new ResidentBookBuilder().withPerson(TEMPORARY_JOE).build();

        residentBookOne.deleteTemporary();
        //added temporary has argument of 0, so it stays permanently -> returns false
        assertFalse(residentBookOne.getPersonList().size() == 0);

        ResidentBook residentBookTwo = new ResidentBookBuilder().withPerson(TEMPORARY_JOE)
                .withPerson(TEMPORARY_DOE).build();

        residentBookTwo.deleteTemporary();
        //added new temporary contact has expiry day of 1000 years, so it won't be deleted
        assertTrue(residentBookTwo.getPersonList().size() == 2);

    }

    @Test
    public void removeByTagTest() throws IllegalValueException, CommandException {

        //initialise residentBook with one contact first, who has tag of "friends"
        ResidentBook residentBook = new ResidentBookBuilder().withPerson(ALICE).build();

        //remove residents who has tag "friends"
        residentBook.removeByTag(new Tag("friends"));

        //check that we have removed Alice, who has a tag called "friends"
        assertTrue(residentBook.getUniquePersonList().getInternalList().size() == 0);


        //build resident book with Alice and Benson
        residentBook = new ResidentBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        assertTrue(residentBook.getUniquePersonList().getInternalList().size() == 2);
        residentBook.removeByTag(new Tag("friends"));

        //both Alice and Benson have tags called "friends", so size of list after removing is 0
        assertTrue(residentBook.getUniquePersonList().getInternalList().size() == 0);


        residentBook = new ResidentBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        assertTrue(residentBook.getUniquePersonList().getInternalList().size() == 2);
        residentBook.removeByTag(new Tag("owesMoney"));
        //only Benson has tag "owesMoney", so the list is left with only 1 person, Alice
        assertTrue(residentBook.getUniquePersonList().getInternalList().size() == 1);
        assertTrue(residentBook.getUniquePersonList().contains(ALICE));
        assertFalse(residentBook.getUniquePersonList().contains(BENSON));
    }

    @Test
    public void removeByTag_nonExistentTag_throwsCommandException() throws IllegalValueException,
            CommandException {
        ResidentBook residentBook = new ResidentBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        assertTrue(residentBook.getUniquePersonList().getInternalList().size() == 2);

        /*since removeByTag is case sensitive, both Alice and Benson do not have tag "OWESMoney",
        so a CommandException is thrown */
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_TAG_FOUND);
        residentBook.removeByTag(new Tag("OWESMoney"));
    }
```
###### \java\seedu\room\model\UniquePersonListTest.java
``` java
    @Test
    public void removeByTagTest() throws IllegalValueException, CommandException {
        //build uniquePersonList with only Alice
        UniquePersonList uniquePersonList = new UniquePersonList();
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.getInternalList().size() == 1);
        uniquePersonList.removeByTag(new Tag("friends"));

        //check that we have removed Alice, who has a tag called "friends"
        assertTrue(uniquePersonList.getInternalList().size() == 0);


        uniquePersonList.add(ALICE);
        uniquePersonList.add(BENSON);
        uniquePersonList.removeByTag(new Tag("friends"));

        //both Alice and Benson have tags called "friends", so size of list after removing is 0
        assertTrue(uniquePersonList.getInternalList().size() == 0);


        //set up the list with Alice and Benson, where only Benson has the tag "owesMoney"
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BENSON);
        uniquePersonList.removeByTag(new Tag("owesMoney"));

        //check that only Alice is left in the list
        assertTrue(uniquePersonList.getInternalList().size() == 1);
        assertTrue(uniquePersonList.getInternalList().contains(ALICE));
        assertFalse(uniquePersonList.getInternalList().contains(BENSON));
    }

    @Test
    public void removeByTag_nonExistentTag_throwsException() throws IllegalValueException,
            CommandException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BENSON);

        /*since removeByTag is case sensitive, both Alice and Benson do not have tag "OWESMoney",
        so a CommandException is thrown */
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_TAG_FOUND);
        uniquePersonList.removeByTag(new Tag("OWESMoney"));
    }
```
###### \java\seedu\room\model\UniquePersonListTest.java
``` java
}
```
###### \java\seedu\room\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Temporary} of the {@code Person} that we are building.
     */
    public PersonBuilder withTemporary(long day)  {
        try {
            this.person.setTimestamp(new Timestamp(day));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Timestamp is expected to be unique");
        }
        return this;
    }
```
###### \java\seedu\room\testutil\TypicalPersons.java
``` java
    public static final ReadOnlyPerson TEMPORARY_JOE = new PersonBuilder().withName("Temporary Joe")
            .withPhone("9482442").withEmail("joe@example.com").withRoom("17-210").withTemporary(0)
            .withTags("friend").build();
    public static final ReadOnlyPerson TEMPORARY_DOE = new PersonBuilder().withName("Temporary Doe")
            .withPhone("9482442").withEmail("doe@example.com").withRoom("11-210").withTemporary(1000)
            .withTags("friend").build();
```
