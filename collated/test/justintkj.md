# justintkj
###### \java\seedu\address\logic\commands\FavouriteCommandTest.java
``` java
/**
 * Test FavouriteCommand
 */
public class FavouriteCommandTest {

    public static final int NUMBER_ONE = 1;
    public static final String DUPLICATE_EXCEPTION = "duplicate";
    public static final String MISSING_EXCEPTION = "Missing";
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FavouriteCommand favouriteCommand = prepareCommand(model, INDEX_FIRST_PERSON, new Favourite(COLOR_SWITCH));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person editedPerson = getEditedPerson(personToEdit);
        expectedModel.updatePerson(personToEdit, editedPerson);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITE_SUCCESS, editedPerson);

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Creates a new {@code Person} with a valid favourite
     *
     * @param personToEdit The Person to edit
     * @return The edited person
     * @throws IllegalValueException Invalid value when generating the Person
     */
    private Person getEditedPerson(ReadOnlyPerson personToEdit) throws IllegalValueException {
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                personToEdit.getTags(), personToEdit.getPicture(), new Favourite(COLOR_SWITCH));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws IllegalValueException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + NUMBER_ONE);
        FavouriteCommand favouriteCommand = prepareCommand(model, outOfBoundIndex, new Favourite(COLOR_SWITCH));

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicatePerson_failure() throws Exception {
        execute_personError_failure(DUPLICATE_EXCEPTION, MESSAGE_DUPLICATE_PERSON, CommandException.class);
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        execute_personError_failure(MISSING_EXCEPTION, MESSAGE_MISSING_PERSON, AssertionError.class);
    }

    /**
     * Generates a person error when invalid person type occurs
     *
     * @param exceptionType Duplicate or Missing person error
     * @param exceptionMessage Exception message for errors
     * @param exceptionClass Exception class
     * @throws IllegalValueException Duplicate person error
     * @throws CommandException Missing person error
     */
    private void execute_personError_failure(String exceptionType, String exceptionMessage, Class exceptionClass)
            throws IllegalValueException, CommandException {
        FavouriteCommand favouriteCommand = prepareCommand (
                new ModelStubThrowingPersonException(exceptionType), INDEX_FIRST_PERSON,
                new Favourite(COLOR_SWITCH));

        thrown.expect(exceptionClass);
        thrown.expectMessage(exceptionMessage);

        favouriteCommand.execute();
    }

    @Test
    public void equals() throws IllegalValueException {
        final FavouriteCommand standardCommand = new FavouriteCommand(INDEX_FIRST_PERSON, new Favourite (COLOR_SWITCH));

        // same values -> returns true
        FavouriteCommand commandWithSameValues = new FavouriteCommand(INDEX_FIRST_PERSON, new Favourite (COLOR_SWITCH));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new FavouriteCommand(INDEX_SECOND_PERSON, new Favourite (COLOR_SWITCH))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new FavouriteCommand(INDEX_FIRST_PERSON, new Favourite (COLOR_OFF))));
    }

    /**
     * Returns an {@code FavouriteCommand} with parameters {@code index} and {@code favourite}
     *
     * @param model model to run the tet on
     * @param index index of the person to tet
     * @param favourite favourite state of the person
     * @return a new FavouriteCommand
     */
    private FavouriteCommand prepareCommand(Model model, Index index, Favourite favourite) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index, favourite);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }

    /**
     * A Model stub that always throw a DuplicatePersonException or PersonNotFoundException
     * when trying to edit favourite.
     */
    private class ModelStubThrowingPersonException extends ModelStub {
        private static final String EXCEPTION_DUPLICATE = "duplicate";
        private String exceptiontype;

        public ModelStubThrowingPersonException (String exceptionType) {
            this.exceptiontype = exceptionType;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException, PersonNotFoundException {
            if (exceptiontype.trim().toLowerCase().equals(EXCEPTION_DUPLICATE)) {
                throw new DuplicatePersonException();
            } else {
                throw new PersonNotFoundException();
            }
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\RedoCommandTest.java
``` java
    @Test
    public void alternative() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandOne, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand(2);
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
public class RemarkCommandTest {

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, VALID_REMARK_AMY);
        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, VALID_REMARK_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, VALID_REMARK_AMY)));
        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, VALID_REMARK_BOB)));
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    public static final String SORT_NAME_ARG = "name";
    public static final String SORT_EMAIL_ARG = "email";
    public static final String SORT_ADDRESS_ARG = "address";
    public static final String SORT_NUM_ARG = "number";
    public static final String SORT_NUM_ARG_CAMELCASE = "NuMBeR";
    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }
    @Test
    public void execute_showsSameList() {
        //Sort name -> command parsed successful
        assertSuccessSortWithParameter(SORT_NAME_ARG, model, sortCommand, SortCommand.MESSAGE_SORT_SUCCESS
                + SORT_NAME_ARG, expectedModel);

        //Sort email -> command parsed successful
        expectedModel = new ModelManager(getSortedEmailAddressBook(), new UserPrefs());
        assertSuccessSortWithParameter(SORT_EMAIL_ARG, model, sortCommand,
                SortCommand.MESSAGE_SORT_SUCCESS + SORT_EMAIL_ARG, expectedModel);

        //Sort address -> command parsed successful
        expectedModel = new ModelManager(getSortedAddressAddressBook(), new UserPrefs());
        assertSuccessSortWithParameter(SORT_ADDRESS_ARG, model, sortCommand,
                SortCommand.MESSAGE_SORT_SUCCESS + SORT_ADDRESS_ARG, expectedModel);

        //Sort number -> command parsed successful
        expectedModel = new ModelManager(getSortedNumAddressBook(), new UserPrefs());
        assertSuccessSortWithParameter(SORT_NUM_ARG, model, sortCommand,
                SortCommand.MESSAGE_SORT_SUCCESS + SORT_NUM_ARG, expectedModel);

        //Sort number CamelCase -> command parsed successful
        expectedModel = new ModelManager(getSortedNumAddressBook(), new UserPrefs());
        assertSuccessSortWithParameter(SORT_NUM_ARG, model, sortCommand,
                SortCommand.MESSAGE_SORT_SUCCESS + SORT_NUM_ARG, expectedModel);

    }

    /**
     * Validates the model addressbook is the same as expected when sort with valid parameter is given
     *
     * @param sortParameterArg the type of sort
     * @param model sample addressbook model
     * @param sortCommand SortCommand object
     * @param expectedMessage Expected message to produce after sort
     * @param expectedModel Expected addressbook after the sort
     */
    private void assertSuccessSortWithParameter(String sortParameterArg, Model model, SortCommand sortCommand,
                                                String expectedMessage, Model expectedModel) {
        sortCommand = new SortCommand(sortParameterArg);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }
}

```
###### \java\seedu\address\logic\commands\UndoCommandTest.java
``` java
    @Test
    public void alternative() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand(2);
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());;
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne), Collections.emptyList());
        undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_allFieldsPresentAlternative_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRemark("")
                .withTags().withBirthday(VALID_BIRTHDAY_BOB).build();

        //Valid input format - Accepted
        assertParseSuccess(parser, VALID_NAME_BOB + COMMA_STRING + VALID_PHONE_BOB + SPACE_STRING
                + VALID_ADDRESS_BOB + SPACE_STRING + VALID_EMAIL_BOB + SPACE_STRING
                + VALID_BIRTHDAY_BOB, new AddCommand(expectedPerson));

        //Multiple phone, 1st Phone accepted - Accepted
        assertParseSuccess(parser, VALID_NAME_BOB + COMMA_STRING + VALID_PHONE_BOB + SPACE_STRING
                + VALID_PHONE_AMY + SPACE_STRING + VALID_ADDRESS_BOB + SPACE_STRING + VALID_EMAIL_BOB + SPACE_STRING
                + VALID_BIRTHDAY_BOB, new AddCommand(expectedPerson));

        //Multiple Email, 1st Email accepted - Accepted
        assertParseSuccess(parser, VALID_NAME_BOB + COMMA_STRING + VALID_PHONE_BOB + SPACE_STRING
                + VALID_ADDRESS_BOB + SPACE_STRING + VALID_EMAIL_BOB + SPACE_STRING + VALID_EMAIL_AMY + SPACE_STRING
                + VALID_BIRTHDAY_BOB, new AddCommand(expectedPerson));

        //Multiple Addresses, 1st Address accepted - Accepted
        assertParseSuccess(parser, VALID_NAME_BOB + COMMA_STRING + VALID_PHONE_BOB + SPACE_STRING
                        + VALID_ADDRESS_BOB + SPACE_STRING + VALID_ADDRESS_AMY + SPACE_STRING + VALID_EMAIL_BOB
                        + SPACE_STRING + VALID_BIRTHDAY_BOB, new AddCommand(expectedPerson));
        //Multiple Birthdays, 1st Birthday accepted - Accepted
        assertParseSuccess(parser, VALID_NAME_BOB + COMMA_STRING + VALID_PHONE_BOB + SPACE_STRING
                + VALID_ADDRESS_BOB + SPACE_STRING + VALID_EMAIL_BOB + SPACE_STRING
                + VALID_BIRTHDAY_BOB + SPACE_STRING + VALID_BIRTHDAY_AMY, new AddCommand(expectedPerson));
    }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_altcompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name field
        assertParseFailure(parser, VALID_PHONE_BOB + SPACE_STRING + VALID_ADDRESS_BOB_NO_COMMA
                + SPACE_STRING + VALID_EMAIL_BOB + SPACE_STRING + VALID_BIRTHDAY_BOB, NAME_EXCEPTION_MESSAGE);

        // missing phone field
        assertParseFailure(parser, VALID_NAME_BOB + COMMA_STRING + VALID_ADDRESS_BOB + SPACE_STRING
                + VALID_EMAIL_BOB + SPACE_STRING + VALID_BIRTHDAY_BOB, PHONE_EXCEPTION_MESSAGE);

        // missing email field
        assertParseFailure(parser, VALID_NAME_BOB + COMMA_STRING + VALID_PHONE_BOB + SPACE_STRING
                + VALID_ADDRESS_BOB + SPACE_STRING + VALID_BIRTHDAY_BOB, EMAIL_EXCEPTION_MESSAGE);


        // missing Address/Block field
        assertParseFailure(parser, VALID_NAME_BOB + COMMA_STRING + INVALID_BLOCK_ADDRESS_BOB + SPACE_STRING
                + VALID_PHONE_BOB + SPACE_STRING + VALID_EMAIL_BOB + SPACE_STRING + VALID_BIRTHDAY_BOB,
                BLOCK_EXCEPTION_MESSAGE);

        // missing Address/Street field
        assertParseFailure(parser, VALID_NAME_BOB + COMMA_STRING + INVALID_STREET_ADDRESS_BOB + SPACE_STRING
                + VALID_PHONE_BOB + SPACE_STRING + VALID_EMAIL_BOB + SPACE_STRING + VALID_BIRTHDAY_BOB,
                STREET_EXCEPTION_MESSAGE);

        // missing Address/Unit field
        assertParseFailure(parser, VALID_NAME_BOB + COMMA_STRING + INVALID_UNIT_ADDRESS_BOB + SPACE_STRING
                + VALID_PHONE_BOB + SPACE_STRING + VALID_EMAIL_BOB + SPACE_STRING + VALID_BIRTHDAY_BOB,
                UNIT_EXCEPTION_MESSAGE);
    }
```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
/**
 * Tests for all valid Email types of command.
 */
public class EmailCommandParserTest {

    public static final String SUBJECT_MESSAGE = "subject";
    public static final String BODY_MESSAGE = "body";
    public static final String ALPHABET_INDEX = "a";
    public static final String EXTRA_STRING = "extra";
    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_validArgs_returnsEmailCommand() {
        //VALID EMAIL FORMAT
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + COMMA_STRING + SUBJECT_MESSAGE + COMMA_STRING + BODY_MESSAGE;
        EmailCommand expectedCommand = new EmailCommand(INDEX_FIRST_PERSON, SUBJECT_MESSAGE, BODY_MESSAGE);
        assertParseSuccess(parser, userInput, expectedCommand);

        //VALID EMAIL FORMAT - extra whitespaces
        targetIndex = INDEX_FIRST_PERSON;
        userInput = SPACE_STRING + targetIndex.getOneBased() + SPACE_COMMMA_STRING + SUBJECT_MESSAGE + COMMA_STRING
                + BODY_MESSAGE;
        expectedCommand = new EmailCommand(INDEX_FIRST_PERSON, SUBJECT_MESSAGE, BODY_MESSAGE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //No Index selected
        assertParseFailure(parser, ALPHABET_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Too many inputs
        assertParseFailure(parser, INDEX_ONE + COMMA_STRING + SUBJECT_MESSAGE + COMMA_STRING + BODY_MESSAGE
                + COMMA_STRING + EXTRA_STRING, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));

        //Too little input
        assertParseFailure(parser, INDEX_ONE + COMMA_STRING + SUBJECT_MESSAGE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\FavouriteCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FavouriteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FavouriteCommandParserTest {

    public static final String FIRST_PERSON = INDEX_ONE;
    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsFavouriteCommand() throws IllegalValueException {
        assertParseSuccess(parser, FIRST_PERSON, new FavouriteCommand(INDEX_FIRST_PERSON,
                new Favourite(Favourite.COLOR_SWITCH)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, ALPHABET_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FavouriteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RedoCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MapCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MapCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RedoCommandParserTest {

    public static final int NUMBER_ONE = 1;
    private RedoCommandParser parser = new RedoCommandParser();

    @Test
    public void parse_validArgs_returnsRedoCommand() {
        assertParseSuccess(parser, INDEX_ONE, new RedoCommand(NUMBER_ONE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, ALPHABET_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RedoCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String remark = "Some remark";

        //Have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + SPACE_STRING + PREFIX_REMARK + SPACE_STRING + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);

        //No remarks
        userInput = targetIndex.getOneBased() + SPACE_STRING + PREFIX_REMARK;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, EMPTY_STRING);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }

}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
/**
 * Tests for all possible type of arguments possible for sortCommand.
 */
public class SortCommandParserTest {
    public static final String PARAM_NUMBER = "number";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_ADDRESS = "address";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_BIRTHDAY = "birthday";
    public static final String PARAM_REMARK = "remark";
    public static final String PARAM_CAMEL_CASE = "NuMbEr";
    public static final String INVALID_ARG = "x";
    public static final String MULTIPLE_VALID_PARAM = "number name";
    private SortCommandParser parser = new SortCommandParser();

    //Tests for valid argument, sort number
    @Test
    public void parseNumber_returnsSortCommand() {
        assertParseSuccess(PARAM_NUMBER);
    }
    //Tests for valid argument, sort name
    @Test
    public void parseName_returnsSortCommand() {
        assertParseSuccess(PARAM_NAME);
    }
    //Tests for valid argument, sort address
    @Test
    public void parseAddress_returnsSortCommand() {
        assertParseSuccess(PARAM_ADDRESS);
    }
    //Tests for valid argument, sort email
    @Test
    public void parseEmail_returnsSortCommand() {
        assertParseSuccess(PARAM_EMAIL);
    }
    //Tests for valid argument, sort birthday
    @Test
    public void parseBirthday_returnsSortCommand() {
        assertParseSuccess(PARAM_BIRTHDAY);
    }
    //Tests for valid argument, sort remark
    @Test
    public void parseRemark_returnsSortCommand() {
        assertParseSuccess(PARAM_REMARK);
    }
    //Tests for valid argument, sort number with CamelCase
    @Test
    public void parseCamelCase_returnsSortCommand() {
        assertParseSuccess(PARAM_CAMEL_CASE);
    }

    //Tests for invalid Argument
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_ARG, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    //Tests for invalid Argument , Empty
    @Test
    public void parse_emptyInvalidArgs_throwsParseException() {
        assertParseFailure(parser, ParserUtil.EMPTY_STRING, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    //Tests for invalid Argument , multiple valid argument
    @Test
    public void parse_multipleInvalidArgs_throwsParseException() {
        assertParseFailure(parser, MULTIPLE_VALID_PARAM, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }
    /**
     * Validates correct parsing
     *
     * @param param user input parameter
     */
    private void assertParseSuccess(String param) {
        try {
            SortCommand newCommand = parser.parse(param);
            assert true;
        } catch (ParseException pe) {
            assert false;
        }
    }
}
```
###### \java\seedu\address\logic\parser\UndoCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MapCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MapCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UndoCommandParserTest {

    private UndoCommandParser parser = new UndoCommandParser();

    @Test
    public void parse_validArgs_returnsUndoCommand() {
        assertParseSuccess(parser, INDEX_ONE, new UndoCommand(1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, ALPHABET_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UndoCommand.MESSAGE_USAGE));
    }
}
```
###### \java\systemtests\SortCommandSystemTest.java
``` java
public class SortCommandSystemTest extends AddressBookSystemTest {
    public static final String PARAM_NAME_CAMELCASE = " NaMe";
    public static final String PARAM_NAME = " name";
    public static final String PARAM_ADDRESS = " address";
    public static final String PARAM_REMARK = " remark";
    public static final String PARAM_BIRTHDAY = " birthday";
    public static final String PARAM_INVALID = " xxx";
    public static final String PARAM_NUMBER = " number";
    public static final String PARAM_FAVOURITE = " favourite";

    @Test
    public void sort() throws Exception {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        /* Case: Sort all persons by name, CamelCase */
        sortExecuteSuccess(model, PARAM_NAME_CAMELCASE);

        /* Case: Sort all persons by null*/
        sortExecuteFail(SPACE_STRING + SPACE_STRING);

        /* Case: Sort all persons by name */
        sortExecuteSuccess(model, PARAM_NAME);

        /* Case: Sort all persons by address */
        sortExecuteSuccess(model, PARAM_ADDRESS);

        /* Case: Sort all persons by number */
        sortExecuteSuccess(model, PARAM_NUMBER);

        /* Case: Sort all persons by remark */
        sortExecuteSuccess(model, PARAM_REMARK);

        /* Case: Sort all persons by birthday */
        sortExecuteSuccess(model, PARAM_BIRTHDAY);

        /* Case: Sort all persons by invalid argument */
        sortExecuteFail(PARAM_INVALID);

        /* Adds a person AMY to the addressBook */
        addAmyToModel(model);

        /* Case: Sort all persons by number */
        sortExecuteSuccess(model, PARAM_NUMBER);

        /* Case: Sort all persons by favourite */
        sortExecuteSuccess(model, PARAM_FAVOURITE);
    }

    /**
     * Adds amy into the addressbook model
     *
     * @param model the addressbook mdodel currently in use
     * @throws DuplicatePersonException Amy already exist in model
     */
    private void addAmyToModel(Model model) throws DuplicatePersonException {
        String command;
        ReadOnlyPerson toAdd = AMY;
        command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + REMARK_DESC_AMY + " "
                + BIRTHDAY_DESC_AMY + " " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);
        model.addPerson(toAdd);
    }

    /**
     * Fails to sort given invalid argument
     *
     * @param message invalid argument
     */
    private void sortExecuteFail(String message) {
        String command;
        String expectedResultMessage;
        command = SortCommand.COMMAND_WORD + message;
        expectedResultMessage = SortCommand.MESSAGE_SORT_FAILURE + " \n" + SortCommand.MESSAGE_USAGE;
        assertCommandFailure(command, expectedResultMessage);
    }

    /**
     * Succeeds in sorting given valid argument
     *
     * @param model Addressbook currently being used
     * @param message Valid argument used
     */
    private void sortExecuteSuccess(Model model, String message) {
        String command;
        String expectedResultMessage;
        command = SortCommand.COMMAND_WORD + message;
        expectedResultMessage = SortCommand.MESSAGE_SORT_SUCCESS + message.toLowerCase().trim();
        model.sortPerson(message.toLowerCase().trim());
        assertCommandSuccess(command, model, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }
    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, ReadOnlyPerson toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
```
