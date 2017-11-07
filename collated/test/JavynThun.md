# JavynThun
###### /java/guitests/guihandles/PersonCardHandle.java
``` java
    public String getOccupation() {
        return occupationLabel.getText();
    }
```
###### /java/guitests/guihandles/PersonCardHandle.java
``` java
    public String getRemark() {
        return remarkLabel.getText();
    }

    public String getWebsite() {
        return websiteLabel.getText();
    }
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public Boolean sortPersonList(ArrayList<ReadOnlyPerson> personlist) {
            fail("This method should not be called.");
            return null;
        }
```
###### /java/seedu/address/logic/commands/AddMultipleByTsvCommandTest.java
``` java
        @Override
        public Boolean sortPersonList(ArrayList<ReadOnlyPerson> personList) {
            fail("This method should not be called.");
            return null;
        }
```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return remarkCommand;
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseOccupation_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseOccupation(null);
    }

    @Test
    public void parseOccupation_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseOccupation(Optional.of(INVALID_OCCUPATION));
    }

    @Test
    public void parseOccupation_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseOccupation(Optional.empty()).isPresent());
    }

    @Test
    public void parseOccupation_validValue_returnsOccupation() throws Exception {
        Occupation expectedOccupation = new Occupation(VALID_OCCUPATION);
        Optional<Occupation> actualOccupation = ParserUtil.parseOccupation(Optional.of(VALID_OCCUPATION));
        assertEquals(expectedOccupation, actualOccupation.get());
    }
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseWebsite_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseWebsite(null);
    }

    @Test
    public void parseWebsite_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseWebsite(Optional.of(INVALID_WEBSITE));
    }

    @Test
    public void parseWebsite_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseWebsite(Optional.empty()).isPresent());
    }

    @Test
    public void parseWebsite_validValue_returnsWebsite() throws Exception {
        Website expectedWebsite = new Website(VALID_WEBSITE);
        Optional<Website> actualWebsite = ParserUtil.parseWebsite(Optional.of(VALID_WEBSITE));

        assertEquals(expectedWebsite, actualWebsite.get());
    }
```
###### /java/seedu/address/logic/parser/RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final  Remark remark = new Remark("Some remark.");

        // have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
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
###### /java/seedu/address/model/person/OccupationTest.java
``` java
public class OccupationTest {

    @Test
    public void isValidOccupation() {
        // blank email
        assertFalse(Occupation.isValidOccupation("")); // empty string
        assertFalse(Occupation.isValidOccupation(" ")); // spaces only

        // invalid email
        assertFalse(Occupation.isValidOccupation("@pple, CEO")); // special character in the middle of two strings

        // missing parts
        assertFalse(Occupation.isValidOccupation("Google,Software Engineer")); // missing ' ' after ','
        assertFalse(Occupation.isValidOccupation("Microsoft CEO")); // missing ',' in the middle of two strings
        assertFalse(Occupation.isValidOccupation("Apple")); // missing second part (position)
        assertFalse(Occupation.isValidOccupation("Software Engineer")); // missing first part (company name)

        // valid email
        assertTrue(Occupation.isValidOccupation("Tan Tock Seng Hospital, Nurse"));
        assertTrue(Occupation.isValidOccupation("SMRT, Bus Driver"));
        assertTrue(Occupation.isValidOccupation("NUS, Student"));
    }


}
```
###### /java/seedu/address/model/person/RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        //different types -> returns false
        assertFalse(remark.equals(1));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different person -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }
}
```
###### /java/seedu/address/model/person/WebsiteTest.java
``` java
public class WebsiteTest {

    @Test
    public void isValidWebsite() {
        // blank email
        assertFalse(Website.isValidWebsite("")); // empty string
        assertFalse(Website.isValidWebsite(" ")); // spaces only

        // invalid email
        assertFalse(Website.isValidWebsite("https://example@example.com")); // special character in the middle

        // missing parts
        assertFalse(Website.isValidWebsite("https://example")); // missing top-level domain
        assertFalse(Website.isValidWebsite("http://example.com")); // missing 's' after http
        assertFalse(Website.isValidWebsite("https://.com")); // missing domain name
        assertFalse(Website.isValidWebsite("https//example.com")); // missing ':' after https

        // valid email
        assertTrue(Website.isValidWebsite("https://example.com"));
        assertTrue(Website.isValidWebsite("https://example.com.net"));  // multiple top-level domains
        assertTrue(Website.isValidWebsite("pexample.com"));
        assertTrue(Website.isValidWebsite("https://example.org"));
        assertTrue(Website.isValidWebsite("https://www.example.com"));
        assertTrue(Website.isValidWebsite("https://example.com/abcd"));
    }


}
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Website} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withWebsite(String website) {
        try {
            ParserUtil.parseWebsite(Optional.of(website)).ifPresent(descriptor::setWebsite);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Occupation} of the {@code Person} that we are building.
     */
    public PersonBuilder withOccupation(String occupation) {
        try {
            this.person.setOccupation(new Occupation(occupation));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("occupation is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     *  Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }

    /**
     *  Sets the {@code Website} of the {@code Person} that we are building.
     */
    public PersonBuilder withWebsite(String website) {
        try {
            this.person.setWebsite(new Website(website));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique");
        }
        return this;
    }
```
