# April0616
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void deletePersons(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addPhotoPath(PhotoPath photoPath) throws DuplicatePhotoPathException {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        ArrayList<ReadOnlyPerson> deletePersonList = new ArrayList<>();
        deletePersonList.add(personToDelete);

        String expectedMessage = deleteCommand.generateSuccessfulResultMsgForPerson(deletePersonList);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }
```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        ArrayList<ReadOnlyPerson> deletePersonList = new ArrayList<>();
        deletePersonList.add(personToDelete);

        String expectedMessage = deleteCommand.generateSuccessfulResultMsgForPerson(deletePersonList);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\PhotoCommandTest.java
``` java
public class PhotoCommandTest {

    @Test
    public void isValidLocalPhotoPath() {
        // blank photo path
        assertFalse(PhotoCommand.isValidLocalPhotoPath("")); // empty string
        assertFalse(PhotoCommand.isValidLocalPhotoPath(" ")); // spaces only

        // missing parts
        assertFalse(PhotoCommand.isValidLocalPhotoPath("photo.jpg")); // missing disk part
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:photo.jpg")); // missing backslash
        assertFalse(PhotoCommand.isValidLocalPhotoPath("d:photo.jpg")); // missing backslash

        // invalid parts
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:\\\\photo.jpg")); // too many backslashes
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:\\")); // no file name
        assertFalse(PhotoCommand.isValidLocalPhotoPath("c:\\")); // no file name

        // valid photo path
        assertTrue(PhotoCommand.isValidLocalPhotoPath("c:\\desktop\\baby.jpg"));
        assertTrue(PhotoCommand.isValidLocalPhotoPath("d:\\myself.jpg"));  //
        assertTrue(PhotoCommand.isValidLocalPhotoPath("d:\\my_photo.jpg"));  // underscore
    }



    /**
     * This test cannot run on Travis CI.
     */
    @Ignore
    private void equals() throws FileNotFoundException, IllegalValueException {
        //File amyFile = new File(VALID_PHOTONAME_AMY);
        File amyFile = new File("photo.jpg");
        //File bobFile = new File(VALID_PHOTONAME_BOB);
        File bobFile = new File("selfie.jpg");
        try {
            amyFile.createNewFile();
            bobFile.createNewFile();
        } catch (IOException ioe) {
            System.err.println("Cannot create temporary files!");
        }

        String amyPath = amyFile.getAbsolutePath();
        String bobPath = bobFile.getAbsolutePath();
        //TestUtil.createTempFile(VALID_PHOTOPATH_AMY);
        //TestUtil.createTempFile(VALID_PHOTOPATH_BOB);

        PhotoCommand addFirstPersonPhotoPath = new PhotoCommand(INDEX_FIRST_PERSON, amyPath);
        PhotoCommand addSecondPersonPhotoPath = new PhotoCommand(INDEX_SECOND_PERSON, bobPath);

        // same object -> returns true
        assertTrue(addFirstPersonPhotoPath.equals(addFirstPersonPhotoPath));

        // same value -> returns true
        PhotoCommand copy = new PhotoCommand(addFirstPersonPhotoPath.getIndex(),
                addFirstPersonPhotoPath.getLocalPhotoPath());
        assertTrue(addFirstPersonPhotoPath.equals(copy));

        // different types -> returns false
        assertFalse(addFirstPersonPhotoPath.equals(1));

        // null -> returns false
        assertFalse(addFirstPersonPhotoPath == null);

        // different objects -> returns false
        assertFalse(addFirstPersonPhotoPath.equals(addSecondPersonPhotoPath));

        //TestUtil.removeFileAndItsParentsTillRoot(VALID_PHOTOPATH_BOB);
        //TestUtil.removeFileAndItsParentsTillRoot(VALID_PHOTOPATH_AMY);
        amyFile.delete();
        bobFile.delete();
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("remark example").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
               .withRemark("Some remark").build();
        //RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark());
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark("Some remark"));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, REMARK_COFFEE);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edits filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, REMARK_COFFEE);
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RemarkCommand addFirstPersonCoffeeRemark = new RemarkCommand(INDEX_FIRST_PERSON, REMARK_COFFEE);
        RemarkCommand addFirstPersonCapRemark = new RemarkCommand(INDEX_FIRST_PERSON, REMARK_CAP);
        RemarkCommand addSecondPersonCoffeeRemark = new RemarkCommand(INDEX_SECOND_PERSON, REMARK_COFFEE);

        // same values -> returns true
        assertTrue(addFirstPersonCoffeeRemark.equals(addFirstPersonCoffeeRemark));

        // same object -> returns true
        assertTrue(addFirstPersonCapRemark.equals(addFirstPersonCapRemark));

        // null -> returns false
        assertFalse(addFirstPersonCapRemark == null);

        // different index -> returns false
        assertFalse(addFirstPersonCoffeeRemark.equals(addSecondPersonCoffeeRemark));

        // different types -> returns false
        assertFalse(addFirstPersonCoffeeRemark.equals(new ClearCommand()));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Likes to drink tea.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_photo() throws Exception {
        final String photoPath = createTempFile();

        PhotoCommand command = (PhotoCommand) parser.parseCommand(PhotoCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_PHOTO + " " + photoPath);
        String commandAppPath = command.getAppPhotoPath();

        PhotoCommand newCommand = new PhotoCommand(INDEX_FIRST_PERSON, photoPath);
        String newCommandAppPath = newCommand.getAppPhotoPath();

        assertEquals(newCommand, command);

        Path thisPhotoPath = Paths.get(photoPath);
        removeFileAndItsParentsTillRoot(thisPhotoPath);

        //remove the two temporary files saved in the app
        removeAppFile(commandAppPath);
        removeAppFile(newCommandAppPath);
    }
```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgsOnePerson_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", new DeleteCommand(deletePersonList));
    }

```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgsMultiplePersons_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_SECOND_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "1, 2, 3", new DeleteCommand(deletePersonList));
    }


```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgsMultiplePersonsNoWhiteSpace_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "1,3", new DeleteCommand(deletePersonList));
    }

```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgsMultiplePersonsDuplicatedIndexes_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "1, 1, 3, 3", new DeleteCommand(deletePersonList));
    }
```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgsMultiplePersonsManyWhiteSpaces_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_SECOND_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "  1  ,  2  , 3 ", new DeleteCommand(deletePersonList));
    }
```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgsMultiplePersonsSplitByWhiteSpace_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_SECOND_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);

        assertParseSuccess(parser, "  1 2 3 ", new DeleteCommand(deletePersonList));
    }
```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgsMultiplePersonsManyWhiteSpacesLessComma_returnsDeleteCommand() {
        ArrayList<Index> deletePersonList = new ArrayList<>();
        deletePersonList.add(INDEX_FIRST_PERSON);
        deletePersonList.add(INDEX_SECOND_PERSON);
        deletePersonList.add(INDEX_THIRD_PERSON);
        assertParseFailure(parser, "  1, 2 3 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseGender_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseGender(null);
    }

    @Test
    public void parseGender_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseGender(Optional.of(INVALID_GENDER));
    }

    @Test
    public void parseGender_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseGender(Optional.empty()).isPresent());
    }

    @Test
    public void parseGender_validValue_returnsGender() throws Exception {
        Gender expectedGender = new Gender(VALID_GENDER);
        Optional<Gender> actualGender = ParserUtil.parseGender(Optional.of(VALID_GENDER));

        assertEquals(expectedGender, actualGender.get());
    }

    @Test
    public void parseMatricNo_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseMatricNo(null);
    }

    @Test
    public void parseMatricNo_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseMatricNo(Optional.of(INVALID_MATRIC_NO));
    }

    @Test
    public void parseMatricNo_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseMatricNo(Optional.empty()).isPresent());
    }

    @Test
    public void parseMatricNo_validValue_returnsMatricNo() throws Exception {
        MatricNo expectedMatricNo = new MatricNo(VALID_MATRIC_NO);
        Optional<MatricNo> actualMatricNo = ParserUtil.parseMatricNo(Optional.of(VALID_MATRIC_NO));

        assertEquals(expectedMatricNo, actualMatricNo.get());
    }
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java

public class RemarkCommandParserTest {
    private static final String REMARK_EMPTY = " " + PREFIX_REMARK;
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_remarkSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;

        //have remarks
        String userInput = targetIndex.getOneBased() + REMARK_EMPTY + VALID_REMARK_COFFEE;
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, REMARK_COFFEE);
        assertParseSuccess(parser, userInput, expectedCommand);

        //no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;
        expectedCommand = new RemarkCommand(targetIndex, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<PhotoPath> photoPaths = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<PhotoPath> getPhotoPathList() {
            return photoPaths;
        }
    }

}
```
###### \java\seedu\address\model\person\GenderTest.java
``` java
public class GenderTest {

    @Test
    public void isValidGender() {

        // invalid Gender
        assertFalse(Gender.isValidInput(" ")); // spaces only
        assertFalse(Gender.isValidInput("^")); // only non-alphanumeric characters
        assertFalse(Gender.isValidInput("peter*")); // contains non-alphanumeric characters
        assertFalse(Gender.isValidInput("apple")); // unrelated description
        assertFalse(Gender.isValidInput("fmale")); // wrong input

        // valid Gender
        assertTrue(Gender.isValidInput("")); // empty string when unspecified
        assertTrue(Gender.isValidInput("m"));
        assertTrue(Gender.isValidInput("f"));
        assertTrue(Gender.isValidInput("male"));
        assertTrue(Gender.isValidInput("female"));
        assertTrue(Gender.isValidInput("MALE"));
        assertTrue(Gender.isValidInput("FEMALE"));
        assertTrue(Gender.isValidInput("mAlE"));
        assertTrue(Gender.isValidInput("FemALE"));
        assertTrue(Gender.isValidInput("Male"));
        assertTrue(Gender.isValidInput("Female"));
    }
}
```
###### \java\seedu\address\model\person\MatricNoTest.java
``` java
public class MatricNoTest {

    @Test
    public void isValidMatricNo() {
        // invalid MatricNo numbers
        assertFalse(MatricNo.isValidMatricNo(" ")); // spaces only
        assertFalse(MatricNo.isValidMatricNo("A016253K")); // not exactly 9-digit
        assertFalse(MatricNo.isValidMatricNo("40162533J")); // not start with 'A' but a number
        assertFalse(MatricNo.isValidMatricNo("B0162533J")); // not start with 'A' but a letter
        assertFalse(MatricNo.isValidMatricNo("A90p3041F")); // alphabets within 2nd-8th digits
        assertFalse(MatricNo.isValidMatricNo("A014 2333H")); // spaces within digits
        assertFalse(MatricNo.isValidMatricNo("A01423332")); // not end with a letter

        // valid MatricNo numbers/ empty when optional
        assertTrue(MatricNo.isValidMatricNo("")); // empty string
        assertTrue(MatricNo.isValidMatricNo("A0172631H"));
        assertTrue(MatricNo.isValidMatricNo("A0112331K"));
        assertTrue(MatricNo.isValidMatricNo("a0172631h")); //case-insensitive
        assertTrue(MatricNo.isValidMatricNo("A0172631h")); //case-insensitive
        assertTrue(MatricNo.isValidMatricNo("a0172631H")); //case-insensitive
    }
}
```
###### \java\seedu\address\model\person\PhotoPathTest.java
``` java
public class PhotoPathTest {

    @Test
    public void isValidPhotoPath() {

        // empty
        assertTrue(PhotoPath.isValidPhotoPath(""));
        assertFalse(PhotoPath.isValidPhotoPath(" "));

        // / missing partsï¼? not start with 'docs/images/contactPhotos/'
        assertFalse(PhotoPath.isValidPhotoPath("photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("c:photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("d:photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("c:\\\\photo.jpg"));
        assertFalse(PhotoPath.isValidPhotoPath("c:\\"));
        assertFalse(PhotoPath.isValidPhotoPath("c:\\"));

        // valid photo path
        //assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/help_icon.png"));  //default photo path
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contactPhotos/1234.jpg"));
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contactPhotos/12345678.jpg"));
        assertTrue(PhotoPath.isValidPhotoPath("src/main/resources/images/contactPhotos/1234_5678.jpg"));  // underscore
    }

    /*
    @Ignore
    @Test
    public void equals() throws IllegalValueException {
        String parentPath = PATH_FILE_SAVED_PARENT_DIRECTORY;
        //PhotoPath validPhotoPath_1 = new PhotoPath(parentPath + "1234.jpg");
        //PhotoPath validPhotoPath_2 = new PhotoPath(parentPath + "5678.jpg");

        // same object -> returns true
        assertTrue(validPhotoPath_1.equals(validPhotoPath_1));

        // same value -> returns true
        PhotoPath copy = new PhotoPath(validPhotoPath_1.value);
        assertTrue(validPhotoPath_1.equals(copy));

        // different types -> returns false
        assertFalse(validPhotoPath_1.equals(1));

        // null -> returns false
        assertFalse(validPhotoPath_1 == null);

        // different objects -> returns false
        assertFalse(validPhotoPath_1.equals(validPhotoPath_2));
    }
    */
}
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark likeCoffeeRemark = new Remark("Likes to drink coffee.");
        Remark capFiveRemark = new Remark("Got CAP 5.0.");

        // same object -> returns true
        assertTrue(likeCoffeeRemark.equals(likeCoffeeRemark));

        // same value -> returns true
        Remark copy = new Remark(capFiveRemark.value);
        assertTrue(capFiveRemark.equals(copy));

        // different types -> returns false
        assertFalse(capFiveRemark.equals(1));

        // null -> returns false
        assertFalse(capFiveRemark == null);

        // different objects -> returns false
        assertFalse(likeCoffeeRemark.equals(capFiveRemark));
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except gender -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withGender(VALID_GENDER_BOB)
                .withMatricNo(VALID_MATRIC_NO_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withTimetable(VALID_TIMETABLE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_BOB + MATRIC_NO_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + TIMETABLE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except matricNo -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withGender(VALID_GENDER_AMY)
                .withMatricNo(VALID_MATRIC_NO_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withTimetable(VALID_TIMETABLE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + MATRIC_NO_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + TIMETABLE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid gender -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_GENDER_DESC + MATRIC_NO_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + TIMETABLE_DESC_AMY;
        assertCommandFailure(command, Gender.MESSAGE_GENDER_CONSTRAINTS);

        /* Case: invalid matricNo -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY + INVALID_MATRIC_NO_DESC + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + TIMETABLE_DESC_AMY;
        assertCommandFailure(command, MatricNo.MESSAGE_MATRIC_NO_CONSTRAINTS);
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
    /**
     * Deletes the person at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ReadOnlyPerson deletedPerson = removePerson(expectedModel, toDelete);

        ArrayList<ReadOnlyPerson> deletePersonList = new ArrayList<>();
        deletePersonList.add(deletedPerson);

        String expectedResultMessage = DeleteCommand.generateSuccessfulResultMsgForPerson(deletePersonList);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }
```
