# lincredibleJC
###### \java\guitests\guihandles\StatisticsPanelHandle.java
``` java
/**
 * Provides a handle to the Statistics panel.
 */
public class StatisticsPanelHandle extends NodeHandle<Node> {

    public static final String STATISTICS_PANEL_ID = "#statisticsPanelPlaceholder";
    public static final String MEAN_FIELD_ID = "#mean";
    private static final String MEDIAN_FIELD_ID = "#median";
    private static final String MODE_FIELD_ID = "#mode";
    private static final String VARIANCE_FIELD_ID = "#variance";
    private static final String STANDARD_DEVIATION_FIELD_ID = "#standardDeviation";
    private static final String QUARTILE3_FIELD_ID = "#quartile3";
    private static final String QUARTILE1_FIELD_ID = "#quartile1";
    private static final String INTERQUARTILERANGE_FIELD_ID = "#interQuartileRange";


    private final Label meanLabel;
    private final Label medianLabel;
    private final Label modeLabel;
    private final Label varianceLabel;
    private final Label standardDeviationLabel;
    private final Label quartile3Label;
    private final Label quartile1Label;
    private final Label interquartileLabel;

    public StatisticsPanelHandle(Node node) {
        super(node);

        this.meanLabel = getChildNode(MEAN_FIELD_ID);
        this.medianLabel = getChildNode(MEDIAN_FIELD_ID);
        this.modeLabel = getChildNode(MODE_FIELD_ID);
        this.varianceLabel = getChildNode(VARIANCE_FIELD_ID);
        this.standardDeviationLabel = getChildNode(STANDARD_DEVIATION_FIELD_ID);
        this.quartile3Label = getChildNode(QUARTILE3_FIELD_ID);
        this.quartile1Label = getChildNode(QUARTILE1_FIELD_ID);
        this.interquartileLabel = getChildNode(INTERQUARTILERANGE_FIELD_ID);
    }

    public String getMeanLabel() {
        return meanLabel.getText();
    }

    public String getMedianLabel() {
        return medianLabel.getText();
    }

    public String getModeLabel() {
        return modeLabel.getText();
    }

    public String getVarianceLabel() {
        return varianceLabel.getText();
    }

    public String getStandardDeviationLabel() {
        return standardDeviationLabel.getText();
    }

    public String getQuartile3Label() {
        return quartile3Label.getText();
    }

    public String getQuartile1Label() {
        return quartile1Label.getText();
    }

    public String getInterquartileLabel() {
        return interquartileLabel.getText();
    }
}
```
###### \java\seedu\address\logic\commands\FindTagsCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindTagsCommand}.
 */
public class FindTagsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_singleKeyword_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindTagsCommand command = prepareCommand("owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindTagsCommand command = prepareCommand("friends");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Returns an {@code FindTagsCommand}.
     */
    private FindTagsCommand prepareCommand(String userInput) {
        FindTagsCommand command =
                new FindTagsCommand(new TagsContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindTagsCommand command,
                                      String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }


}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddAlias(person));
        assertEquals(new AddCommand(person), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_findtags() throws Exception {
        List<String> keywords = Arrays.asList("tag1", "tag2", "tag3");
        FindTagsCommand command = (FindTagsCommand) parser.parseCommand(
                FindTagsCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTagsCommand(new TagsContainsKeywordsPredicate(keywords)), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias_findtags() throws Exception {
        List<String> keywords = Arrays.asList("tag1", "tag2", "tag3");
        FindTagsCommand command = (FindTagsCommand) parser.parseCommand(
                FindTagsCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTagsCommand(new TagsContainsKeywordsPredicate(keywords)), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_alias_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("r 1") instanceof RedoCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("u 3") instanceof UndoCommand);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
        // invalid phone
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);
        // invalid email
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS);
        // invalid address
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS);
        // invalid formclass
        assertParseFailure(parser, "1" + INVALID_FORMCLASS_DESC, FormClass.MESSAGE_FORMCLASS_CONSTRAINTS);
        // invalid Grades
        assertParseFailure(parser, "1" + INVALID_GRADES_DESC, Grades.MESSAGE_GRADES_CONSTRAINTS);
        // invalid postal code
        assertParseFailure(parser, "1" + INVALID_POSTALCODE_DESC, PostalCode.MESSAGE_POSTALCODE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag
        //invalid remarks field
        assertParseFailure(parser, "1" + REMARK_DESC_BOB, MESSAGE_ADDEDITCOMMANDREMARK_INVALID);

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid postalcode followed by valid email
        assertParseFailure(parser, "1" + INVALID_POSTALCODE_DESC + EMAIL_DESC_AMY,
                PostalCode.MESSAGE_POSTALCODE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY
                + VALID_POSTALCODE_AMY, Name.MESSAGE_NAME_CONSTRAINTS);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + POSTALCODE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + FORMCLASS_DESC_AMY + GRADES_DESC_AMY + NAME_DESC_AMY
                + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withFormClass(VALID_FORMCLASS_AMY).withGrades(VALID_GRADES_AMY).withPostalCode(VALID_POSTALCODE_AMY)
                .withTags(VALID_TAG_FRIEND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
```
###### \java\seedu\address\logic\parser\FindTagsCommandParserTest.java
``` java
public class FindTagsCommandParserTest {
    private FindTagsCommandParser parser = new FindTagsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTagsCommand() {
        // no leading and trailing whitespaces
        FindTagsCommand expectedFindTagsCommand =
                new FindTagsCommand(new TagsContainsKeywordsPredicate(Arrays.asList("friends", "owesMoney")));
        assertParseSuccess(parser, "friends owesMoney", expectedFindTagsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t      owesMoney  \t", expectedFindTagsCommand);
    }


}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseGrades_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseGrades(null);
    }

    @Test
    public void parseGrades_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseGrades(Optional.of(INVALID_GRADES));
    }

    @Test
    public void parseGrades_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseGrades(Optional.empty()).isPresent());
    }

    @Test
    public void parseGrades_validValue_returnsGrade() throws Exception {
        Grades expectedGrades = new Grades(VALID_GRADES);
        Optional<Grades> actualGrades = ParserUtil.parseGrades(Optional.of(VALID_GRADES));

        assertEquals(expectedGrades, actualGrades.get());
    }
```
###### \java\seedu\address\logic\statistics\StatisticsTest.java
``` java
public class StatisticsTest {

    private Statistics statistics1 = new Statistics(new double[]{64630, 11735, 14216, 99233, 14470, 4978, 73429, 38120,
        51135, 67060}); // Small data set
    private Statistics statistics2 = new Statistics(new double[]{6392, 51608, 71247, 14271, 48327, 50618, 67435, 47029,
        61857, 22987, 64858, 99745, 75504, 85464, 60482, 30320, 11342, 48808, 66882, 40522}); // Medium data set
    private Statistics statistics3 = new Statistics(new double[]{33664, 35702, 7049, 74334, 5725, 12090, 62774, 1149,
        81848, 84123, 17698, 42269, 42457, 80934, 83409, 19075, 87353, 63407, 20669, 36812, 44473, 46723, 41091, 54903,
        1749, 88514, 65760, 47322, 17365, 24779, 20301, 97839, 69612, 13975, 89694, 14585, 37259, 38361, 13720, 18729,
        8283, 3886, 26681, 8005, 48460, 13101, 52287, 44583, 878, 16133, 6334, 21592, 87253, 34537, 10974, 87484, 36937,
        79336, 78434, 76977, 75040, 77796, 62173, 63217, 68712, 60934, 88017, 3811, 41145, 57471, 6887, 74612, 78798,
        7308, 88094, 43245, 57157, 86406, 2922, 42919, 74375, 40076, 26030, 65306, 94610, 11923, 90367, 5603, 52189,
        45765, 44982, 27234, 77150, 75570, 40800, 22550, 64134, 4029, 13841, 91097}); // Large data set
    private Statistics statistics4 = new Statistics((new double[]{3, 7, 8, 5, 12, 14, 21, 13, 18})); //odd number
    private Statistics statistics5 = new Statistics((new double[]{3, 7, 8, 5, 12, 14, 21, 13, 18, 14})); // even number
    private Statistics statistics6 = new Statistics((new double[]{})); // null set
    private Statistics statistics7 = new Statistics((new double[]{1})); // single item set

    @Test
    public void getMeanString() throws Exception {
        assertEquals(statistics1.getMeanString(), "43900.6");
        assertEquals(statistics2.getMeanString(), "51284.9");
        assertEquals(statistics3.getMeanString(), "45357.45");
        assertEquals(statistics6.getMeanString(), NO_PERSONS_MESSAGE);
    }

    @Test
    public void getMedianString() throws Exception {
        assertEquals(statistics1.getMedianString(), "44627.5");
        assertEquals(statistics2.getMedianString(), "51113.0");
        assertEquals(statistics3.getMedianString(), "43082.0");
        assertEquals(statistics6.getMedianString(), NO_PERSONS_MESSAGE);

    }

    @Test
    public void getModeString() throws Exception {
        assertEquals(statistics1.getModeString(), "4978.0");
        assertEquals(statistics2.getModeString(), "6392.0");
        assertEquals(statistics3.getModeString(), "878.0");
        assertEquals(statistics6.getModeString(), NO_PERSONS_MESSAGE);

    }

    @Test
    public void getQuartile1String() throws Exception {
        assertEquals(statistics4.getQuartile1String(), "6.0");
        assertEquals(statistics5.getQuartile1String(), "7.0");
        assertEquals(statistics6.getQuartile1String(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getQuartile1String(), INSUFFICIENT_DATA_MESSAGE);

    }

    @Test
    public void getQuartile2String() throws Exception {
        assertEquals(statistics4.getQuartile2String(), "12.0");
        assertEquals(statistics5.getQuartile2String(), "12.5");
        assertEquals(statistics6.getQuartile2String(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getQuartile2String(), INSUFFICIENT_DATA_MESSAGE);
    }

    @Test
    public void getQuartile3String() throws Exception {
        assertEquals(statistics4.getQuartile3String(), "16.0");
        assertEquals(statistics5.getQuartile3String(), "14.0");
        assertEquals(statistics6.getQuartile3String(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getQuartile3String(), INSUFFICIENT_DATA_MESSAGE);
    }

    @Test
    public void getInterquartileRangeString() throws Exception {
        assertEquals(statistics4.getInterquartileRangeString(), "10.0");
        assertEquals(statistics5.getInterquartileRangeString(), "7.0");
        assertEquals(statistics6.getInterquartileRangeString(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getInterquartileRangeString(), INSUFFICIENT_DATA_MESSAGE);
    }

    //TODO:Fix value problems
    @Test
    public void getVarianceString() throws Exception {
        assertEquals(statistics1.getVarianceString(), "1.03137210182E9");
        assertEquals(statistics2.getVarianceString(), "6.1878123241E8");
        assertEquals(statistics3.getVarianceString(), "8.6507074558E8");
        assertEquals(statistics6.getVarianceString(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getVarianceString(), INSUFFICIENT_DATA_MESSAGE);
    }

    @Test
    public void getStdDevString() throws Exception {
        assertEquals(statistics1.getStdDevString(), "32114.98");
        assertEquals(statistics2.getStdDevString(), "24875.31");
        assertEquals(statistics3.getStdDevString(), "29412.09");
        assertEquals(statistics6.getStdDevString(), NO_PERSONS_MESSAGE);
        assertEquals(statistics7.getStdDevString(), INSUFFICIENT_DATA_MESSAGE);
    }

}
```
###### \java\seedu\address\model\person\FormClassTest.java
``` java
public class FormClassTest {
    @Test
    public void isValidFormClass() throws Exception {
        // invalid name
        assertFalse(FormClass.isValidFormClass("")); // empty string
        assertFalse(FormClass.isValidFormClass(" ")); // spaces only
        assertFalse(FormClass.isValidFormClass("^")); // only non-alphanumeric characters
        assertFalse(FormClass.isValidFormClass("ClassA*")); // contains non-alphanumeric characters
        assertFalse(FormClass.isValidFormClass("Ability 5")); // multiple words
        // valid name
        assertTrue(FormClass.isValidFormClass("ALevel")); // alphabets only
        assertTrue(FormClass.isValidFormClass("48")); // numbers only Class 48
        assertTrue(FormClass.isValidFormClass("4.8")); // with a '.'
        assertTrue(FormClass.isValidFormClass("4-8")); // with a '-'
        assertTrue(FormClass.isValidFormClass("12s23")); // alphanumeric characters
        assertTrue(FormClass.isValidFormClass("12S23")); // with capital letters
        assertTrue(FormClass.isValidFormClass("Class2F1Ability")); // long names
    }

}
```
###### \java\seedu\address\model\person\GradesTest.java
``` java
public class GradesTest {
    @Test
    public void isValidGrades() throws Exception {

        //invalid grades
        assertFalse(Grades.isValidGrades("")); // empty string
        assertFalse(Grades.isValidGrades(" ")); // spaces only
        assertFalse(Grades.isValidGrades("^")); // contains a symbol
        assertFalse(Grades.isValidGrades("A")); // contains alphabets
        assertFalse(Grades.isValidGrades("A+")); //contains alphabets and symbols
        assertFalse(Grades.isValidGrades("-100")); // negative number
        assertFalse(Grades.isValidGrades("150.")); // does not end after the decimal point
        assertFalse(Grades.isValidGrades("0.00001")); // more than 2 decimal place
        assertFalse(Grades.isValidGrades("01")); // first digit is 0
        assertFalse(Grades.isValidGrades("120, 150")); //contains two numbers

        //valid grades
        assertTrue(Grades.isValidGrades("0")); // lowest number
        assertTrue(Grades.isValidGrades("80.0")); // 1 decimal point
        assertTrue(Grades.isValidGrades("120.92")); // 2 decimal point
        assertTrue(Grades.isValidGrades("22023232323232")); // very large number
    }
}
```
###### \java\seedu\address\model\person\TagsContainsKeywordsPredicateTest.java
``` java
public class TagsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagsContainsKeywordsPredicate firstPredicate = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        TagsContainsKeywordsPredicate secondPredicate = new TagsContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainsKeywordsPredicate firstPredicateCopy = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainsKeywords_returnsTrue() {
        // One keyword
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Collections.singletonList("tag1"));
        assertTrue(predicate.test(new PersonBuilder().withName("personName").withTags("tag1").build()));

        // Multiple keywords
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("tag1", "tag2"));
        assertTrue(predicate.test(new PersonBuilder().withName("personName").withTags("tag1", "tag2").build()));

        // Mixed-case keywords
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("tAg1", "TaG2"));
        assertTrue(predicate.test(new PersonBuilder().withName("personName").withTags("tag1", "tag2").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Arrays.asList("tag10"));
        assertFalse(predicate.test(new PersonBuilder().withName("personName").withTags("tag1").build()));

        // Only one matching keyword
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("tag1", "tag2"));
        assertFalse(predicate.test(new PersonBuilder().withName("personName").withTags("tag1").build()));

        // Keywords match phone, email and address, but does not match tags
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("12345", "person@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("personName")
                .withPhone("student/97272031 parent/97979797")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("tag1", "tag2", "tag3").build()));
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code FormClass} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFormClass(String formClass) {
        try {
            ParserUtil.parseFormClass(Optional.of(formClass)).ifPresent(descriptor::setFormClass);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("formClass is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Grades} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGrades(String grades) {
        try {
            ParserUtil.parseGrades(Optional.of(grades)).ifPresent(descriptor::setGrades);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("grades is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code FormClass} of the {@code Person} that we are building.
     */
    public PersonBuilder withFormClass(String formClass) {
        try {
            this.person.setFormClass(new FormClass(formClass));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("formClass is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Grades} of the {@code Person} that we are building.
     */
    public PersonBuilder withGrades(String grades) {
        try {
            this.person.setGrades(new Grades(grades));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("grades are expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonUtil.java
``` java
    /**
     * Returns an add command alias string for adding the {@code person}.
     */
    public static String getAddAlias(ReadOnlyPerson person) {
        return AddCommand.COMMAND_ALIAS + " " + getPersonDetails(person);
    }
```
###### \java\seedu\address\ui\StatisticsPanelTest.java
``` java
public class StatisticsPanelTest extends GuiUnitTest {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private StatisticsPanel statisticsPanel;
    private StatisticsPanelHandle statisticsPanelHandle;

    @Before
    public void setUp() throws Exception {
        try {
            guiRobot.interact(() -> statisticsPanel = new StatisticsPanel(TYPICAL_PERSONS));
            uiPartRule.setUiPart(statisticsPanel);
            statisticsPanelHandle = new StatisticsPanelHandle(statisticsPanel.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Test
    public void display() throws Exception {
        guiRobot.pauseForHuman();
        postNow(new FilteredListChangedEvent(TYPICAL_PERSONS));
        assertPanelDisplaysStatistics(TYPICAL_PERSONS, statisticsPanelHandle);
    }

    //TODO: Add GUI tests for all commands

}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code statisticsPanelHandle} displays the statistics correctly
     */
    public static void assertPanelDisplaysStatistics(ObservableList<ReadOnlyPerson> actualList,
                                               StatisticsPanelHandle actualPanelHandle) {
        Statistics expectedStatistics = new Statistics(actualList);

        assertEquals(expectedStatistics.getMeanString(), actualPanelHandle.getMeanLabel());
        assertEquals(expectedStatistics.getMedianString(), actualPanelHandle.getMedianLabel());
        assertEquals(expectedStatistics.getModeString(), actualPanelHandle.getModeLabel());
        assertEquals(expectedStatistics.getVarianceString(), actualPanelHandle.getVarianceLabel());
        assertEquals(expectedStatistics.getStdDevString(), actualPanelHandle.getStandardDeviationLabel());
        assertEquals(expectedStatistics.getQuartile3String(), actualPanelHandle.getQuartile3Label());
        assertEquals(expectedStatistics.getQuartile1String(), actualPanelHandle.getQuartile1Label());
        assertEquals(expectedStatistics.getInterquartileRangeString(), actualPanelHandle.getInterquartileLabel());
    }
```
