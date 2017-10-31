# hanselblack
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EmailCommand}.
 */
public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(emailCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EmailCommand emailFirstCommand = new EmailCommand(INDEX_FIRST_PERSON, "unifycs2103@gmail.com");
        EmailCommand emailSecondCommand = new EmailCommand(INDEX_SECOND_PERSON, "unifycs2103@gmail.com");

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = new EmailCommand(INDEX_FIRST_PERSON, "unifycs2103@gmail.com");
        assertTrue(emailFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(emailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(emailFirstCommand.equals(emailSecondCommand));
    }


    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index index) {
        EmailCommand emailCommand = new EmailCommand(index, "unifycs2103@gmail.com");
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
public class RemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same value -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    @Test
    public void generate_successMessage() {
        RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        RemarkCommand deleteCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        try {
            Method m = standardCommand.getClass().getDeclaredMethod("generateSuccessMessage", ReadOnlyPerson.class);
            m.setAccessible(true);

            //has remark
            String addSuccessMessage = (String) m.invoke(standardCommand, AMY);
            Field field = RemarkCommand.class.getDeclaredField("MESSAGE_ADD_REMARK_SUCCESS");
            field.setAccessible(true);
            String messageAddRemarkSuccess = (String) field.get(standardCommand);
            assertEquals(String.format(messageAddRemarkSuccess, AMY), addSuccessMessage);

            //no remark
            String deleteSuccessMessage = (String) m.invoke(deleteCommand, AMY);
            field = RemarkCommand.class.getDeclaredField("MESSAGE_DELETE_REMARK_SUCCESS");
            field.setAccessible(true);
            String messageDeleteRemarkSuccess = (String) field.get(deleteCommand);
            assertEquals(String.format(messageDeleteRemarkSuccess, AMY), deleteSuccessMessage);


        } catch (Exception e) {
            assert false;
        }
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
/**
 * tests different boundary values for RemarkCommandParser.
 */
public class RemarkCommandParserTest {

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure()throws Exception {
        final Remark remark = new Remark("Some remark.");

        //have remarks

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);

        //no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        //nothing at all
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        //same object -> returns true
        assertTrue(remark.equals(remark));

        //same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        //different types -> return false
        assertFalse(remark.equals(1));

        //null -> returns false
        assertFalse(remark.equals(null));

        //different person -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }

    @Test
    public void hashCodeTest() throws IllegalValueException {
        Remark remarkStub = new Remark("remark stub");
        assertEquals("remark stub".hashCode(), remarkStub.hashCode());

        Remark remarkStub2 = new Remark("remark stub 2");
        assertEquals("remark stub 2".hashCode(), remarkStub2.hashCode());
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_AVATAR = "";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Remark defaultRemark = new Remark(DEFAULT_REMARK);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            Avatar defaultAvatar = new Avatar(DEFAULT_AVATAR);
            this.person = new Person(defaultName, defaultPhone, defaultEmail,
                    defaultAddress, defaultRemark, defaultAvatar, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default person's values are invalid.");
        }
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code Avatar} of the {@code Person} that we are building.
     */
    public PersonBuilder withAvatar(String filePath) {
        try {
            this.person.setAvatar(Avatar.readAndCreateAvatar(filePath));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("avatar file is not valid");
        }
        return this;
    }

    /**
     * saves {@code avatar} to data folder and returns {@code Person}
     */
    public Person build() {
        this.person.saveAvatar();
        return this.person;
    }

}
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withRemark("Likes to swim").build();
```
