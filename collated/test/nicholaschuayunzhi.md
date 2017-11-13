# nicholaschuayunzhi
###### \java\guitests\guihandles\CommandBoxHintsHandle.java
``` java
public class CommandBoxHintsHandle extends NodeHandle<TextField> {
    public static final String COMMAND_BOX_HINTS_ID = "#commandBoxHints";
    private TextField commandBoxHints;

    public CommandBoxHintsHandle(TextField commandBoxHintsNode) {
        super(commandBoxHintsNode);
        this.commandBoxHints = getChildNode(COMMAND_BOX_HINTS_ID);
    }

    public String getText() {
        return commandBoxHints.getText();
    }

    public TextField getTextField() {
        return commandBoxHints;
    }
}
```
###### \java\guitests\guihandles\PersonPanelHandle.java
``` java
public class PersonPanelHandle extends NodeHandle<Node> {
    public static final String PERSON_PANEL_ID = "#personPanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String AVATAR_FIELD_ID = "#avatar";

    private Label name;
    private Label phone;
    private Label address;
    private Label email;
    private Label remark;
    private List<Label> tagLabels;
    private ImageView avatar;

    public PersonPanelHandle(Node personPanelNode) {
        super(personPanelNode);

        this.name = getChildNode(NAME_FIELD_ID);
        this.phone = getChildNode(PHONE_FIELD_ID);
        this.address = getChildNode(ADDRESS_FIELD_ID);
        this.email = getChildNode(EMAIL_FIELD_ID);
        this.remark = getChildNode(REMARK_FIELD_ID);
        this.avatar = getChildNode(AVATAR_FIELD_ID);

        updateTags();
    }

    public String getName() {
        return name.getText();
    }

    public String getPhone() {
        return phone.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public String getRemark() {
        return remark.getText();
    }

    public Image getAvatar() {
        return avatar.getImage();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Update tag information in handle
     */
    public void updateTags() {
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);

        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }
}
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        List<Predicate> firstPredicateList =
                Arrays.asList(
                        new NameContainsKeywordPredicate("name_first"),
                        new EmailContainsKeywordPredicate("email_first"),
                        new TagsContainKeywordPredicate("tag_first"),
                        new PhoneContainsKeywordPredicate("phone_first"),
                        new AddressContainsKeywordPredicate("address_first")
                );

        List<Predicate> secondPredicateList =
                Arrays.asList(
                        new NameContainsKeywordPredicate("name_second"),
                        new EmailContainsKeywordPredicate("email_second"),
                        new TagsContainKeywordPredicate("tag_second"),
                        new PhoneContainsKeywordPredicate("phone_second"),
                        new AddressContainsKeywordPredicate("address_second")
                );

        PersonContainsFieldsPredicate firstPredicate =
                new PersonContainsFieldsPredicate(firstPredicateList);

        PersonContainsFieldsPredicate secondPredicate =
                new PersonContainsFieldsPredicate(secondPredicateList);


        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleKeywords_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("n/Meier a/311, Clementi Ave 2, #02-25 t/friends p/98765432");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_shortenedKeyword_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("n/Ben");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleKeywordsCaseInsensitive_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("meieR a/clEmenti n/BeNsOn");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }


    @Test
    public void execute_multipleKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand("n/Kurz n/Elle n/Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList());
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {
        FindCommand command = new FindCommand((person) -> true);
        try {
            command = new FindCommandParser().parse(userInput);
        } catch (ParseException pe) {
            assert false;
        }

        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsFieldsPredicate(Arrays.asList(
                                        new NameContainsKeywordPredicate("Alice"),
                                        new TagsContainKeywordPredicate("friend"),
                                        new PhoneContainsKeywordPredicate("111"),
                                        new AddressContainsKeywordPredicate("Big Road"),
                                        new EmailContainsKeywordPredicate("email@email.com"),
                                        new RemarkContainsKeywordPredicate("likes"))));

        assertParseSuccess(parser, "n/Alice t/friend a/Big Road e/email@email.com p/111 r/likes", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " \n n/Alice a/Big Road\n \r\nr/likes \ne/email@email.com\t t/friend   p/111\t", expectedFindCommand);
    }

    @Test
    public void unknown_prefix() {
        try {
            Method m = parser.getClass().getDeclaredMethod("valueAndPrefixIntoPredicate", String.class, Prefix.class);
            m.setAccessible(true);
            Predicate p =
                    (Predicate) m.invoke(parser, "UNKNOWN_PREFIX", new Prefix("UNKNOWN"));
            assertEquals(p, new NameContainsKeywordPredicate("UNKNOWN_PREFIX"));
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

}
```
###### \java\seedu\address\logic\parser\HintParserTest.java
``` java
    @Test
    public void generate_add_hint() {
        assertHintEquals("add", " n/NAME");
        assertHintEquals("add ", "n/NAME");
        assertHintEquals("add n", "/NAME");
        assertHintEquals("add n/", "NAME");
        assertHintEquals("add n/name", " p/PHONE");
        assertHintEquals("add n/name ", "p/PHONE");
        assertHintEquals("add n/name p", "/PHONE");
        assertHintEquals("add n/name p/", "PHONE");
        assertHintEquals("add n/name p/123", " e/EMAIL");
        assertHintEquals("add n/name p/notValid", " e/EMAIL");
        assertHintEquals("add n/name p/123 ", "e/EMAIL");
        assertHintEquals("add n/name p/123 e", "/EMAIL");
        assertHintEquals("add n/name p/123 e/", "EMAIL");
        assertHintEquals("add n/name p/123 e/e@e.com", " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/notValid", " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com" , " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a" , "/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a/" , "ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a/address" , " t/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address " , "t/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t" , "/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/" , "TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag" , " ");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag    " , "");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag    bla bla" , " ");

        assertHintEquals("add p/phone", " n/NAME");
        assertHintEquals("add p/phone n", "/NAME");
        assertHintEquals("add p/phone t", "/TAG");

        //hints repeated args
        assertHintEquals("add t/tag t", "/TAG");

        //TODO: remove repeated args for unrepeatbles
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag p" , "/PHONE");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag p/" , "PHONE");
    }

    @Test
    public void generate_edit_hint() {
        assertHintEquals("edit", " index");
        assertHintEquals("edit ", "index");
        assertHintEquals("edit 12", " n/NAME");
        assertHintEquals("edit 12 ", "n/NAME");

        assertHintEquals("edit 12 p", "/PHONE");
        assertHintEquals("edit 12 p/", "PHONE");

        assertHintEquals("edit 12 n", "/NAME");
        assertHintEquals("edit 12 n/", "NAME");

        assertHintEquals("edit 12 e", "/EMAIL");
        assertHintEquals("edit 12 e/", "EMAIL");

        assertHintEquals("edit 12 a", "/ADDRESS");
        assertHintEquals("edit 12 a/", "ADDRESS");

        assertHintEquals("edit 12 t", "/TAG");
        assertHintEquals("edit 12 t/", "TAG");

        assertHintEquals("edit 12 p/123", " n/NAME");
        assertHintEquals("edit 12 p/123 ", "n/NAME");

        //TODO: change this functionality
        assertHintEquals("edit p/123", " index");
        assertHintEquals("edit p/123 ", "index");
        assertHintEquals("edit p/123 1", " index");
        assertHintEquals("edit p/123 1 ", "index");
    }

    @Test
    public void generate_find_hint() {
        assertHintEquals("find", " n/NAME");
        assertHintEquals("find ", "n/NAME");
        assertHintEquals("find", " n/NAME");

        assertHintEquals("find n", "/NAME");
        assertHintEquals("find n/", "NAME");
        assertHintEquals("find n/1", " p/PHONE");

        assertHintEquals("find p", "/PHONE");
        assertHintEquals("find p/", "PHONE");
        assertHintEquals("find p/1", " n/NAME");

        assertHintEquals("find e", "/EMAIL");
        assertHintEquals("find e/", "EMAIL");
        assertHintEquals("find e/1", " n/NAME");

        assertHintEquals("find a", "/ADDRESS");
        assertHintEquals("find a/", "ADDRESS");
        assertHintEquals("find a/1", " n/NAME");

        assertHintEquals("find t", "/TAG");
        assertHintEquals("find t/", "TAG");
        assertHintEquals("find t/1", " n/NAME");

        assertHintEquals("find r", "/REMARK");
        assertHintEquals("find r/", "REMARK");
        assertHintEquals("find r/a", " n/NAME");


    }

    @Test
    public void generate_select_hint() {
        assertHintEquals("select", " index");
        assertHintEquals("select ", "index");
        assertHintEquals("select 1", "");
        assertHintEquals("select bla 1", " index");
    }

    @Test
    public void generate_delete_hint() {
        assertHintEquals("delete", " index");
        assertHintEquals("delete ", "index");
        assertHintEquals("delete 1", "");
        assertHintEquals("delete bla 1", " index");
    }

    @Test
    public void generate_standard_hint() {
        assertHintEquals("history", " show command history");
        assertHintEquals("exit", " exits the app");
        assertHintEquals("clear", " clears address book");
        assertHintEquals("help", " shows user guide");
        assertHintEquals("undo", " undo command");
        assertHintEquals("redo", " redo command");
        assertHintEquals("unknown", " type help for guide");

        //TODO: to change
        assertHintEquals("alias", " creates an alias");
    }

    public void assertHintEquals(String userInput, String expected) {
        assertEquals(expected, generateHint(userInput));
    }

}
```
###### \java\seedu\address\model\person\AddressContainsKeywordPredicateTest.java
``` java
public class AddressContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        AddressContainsKeywordPredicate firstPredicate = new AddressContainsKeywordPredicate(firstPredicateKeyword);
        AddressContainsKeywordPredicate secondPredicate = new AddressContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordPredicate firstPredicateCopy = new AddressContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordPredicate predicate = new AddressContainsKeywordPredicate("Blk 101 Address");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));

        // sub-string
        predicate = new AddressContainsKeywordPredicate("101");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordPredicate("bLk 101 addrESs");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {

        AddressContainsKeywordPredicate predicate = new AddressContainsKeywordPredicate("Different Address");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));

        predicate = new AddressContainsKeywordPredicate("Blk 101 Address Ave 6");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));
    }
}
```
###### \java\seedu\address\model\person\AvatarTest.java
``` java
public class AvatarTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void validFilePath_for_readAndCreateAvatar() {

        try {
            Avatar png = Avatar.readAndCreateAvatar("src/test/data/images/avatars/test.png");
            Avatar jpg = Avatar.readAndCreateAvatar("src/test/data/images/avatars/test.jpg");
            Avatar nullPath = Avatar.readAndCreateAvatar(null);

            assertEquals("src/test/data/images/avatars/test.png", png.getOriginalFilePath());
            assertEquals("src/test/data/images/avatars/test.jpg", jpg.getOriginalFilePath());
            assertNotEquals(UUID.randomUUID().toString() + ".png", png.value);
            assertNotEquals(UUID.randomUUID().toString() + ".jpg", jpg.value);
            assertEquals(new Avatar(png.value), png);
            assertEquals(new Avatar(jpg.value), jpg);
            assertEquals(new Avatar(""), nullPath);


        } catch (IllegalValueException e) {
            assert false;
        }
    }

    @Test
    public void toStringTest() {
        Avatar stub = new Avatar("stub");
        assertEquals("stub", stub.value);

        Avatar stub2 = new Avatar("stub2");
        assertEquals("stub2", stub2.value);
    }

    @Test
    public void hashCodeTest() {
        Avatar stub = new Avatar("stub");
        assertEquals("stub".hashCode(), stub.hashCode());

        Avatar stub2 = new Avatar("stub2");
        assertEquals("stub2".hashCode(), stub2.hashCode());
    }

    @Test
    public void invalidPngFilePath_for_readAndCreateAvatar()  throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        Avatar.readAndCreateAvatar("src/test/data/images/avatars/fake.png");
    }

    @Test
    public void invalidJpgFilePath_for_readAndCreateAvatar()  throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        Avatar.readAndCreateAvatar("src/test/data/images/avatars/fake.jpg");
    }

    @Test
    public void notImage_for_readAndCreateAvatar()  throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        Avatar.readAndCreateAvatar("src/test/data/images/avatars/fake");
    }

}
```
###### \java\seedu\address\model\person\EmailContainsKeywordPredicateTest.java
``` java
public class EmailContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        EmailContainsKeywordPredicate firstPredicate = new EmailContainsKeywordPredicate(firstPredicateKeyword);
        EmailContainsKeywordPredicate secondPredicate = new EmailContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordPredicate firstPredicateCopy = new EmailContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordPredicate predicate = new EmailContainsKeywordPredicate("email@address.com");
        assertTrue(predicate.test(new PersonBuilder().withEmail("email@address.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordPredicate("eMaiL@aDdress.cOM");
        assertTrue(predicate.test(new PersonBuilder().withEmail("email@address.com").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {
        EmailContainsKeywordPredicate predicate = new EmailContainsKeywordPredicate("address@email.com");
        assertFalse(predicate.test(new PersonBuilder().withEmail("email@address.com").build()));

        //space between email arguments (ie e/email address vs e/email e/address which will be 2 different predicates)
        predicate = new EmailContainsKeywordPredicate("address email");
        assertFalse(predicate.test(new PersonBuilder().withEmail("email@address.com").build()));

    }
}
```
###### \java\seedu\address\model\person\NameContainsKeywordPredicateTest.java
``` java
public class NameContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        NameContainsKeywordPredicate firstPredicate = new NameContainsKeywordPredicate(firstPredicateKeyword);
        NameContainsKeywordPredicate secondPredicate = new NameContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordPredicate firstPredicateCopy = new NameContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordPredicate("Alice Bob");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordPredicate("aLIce bOB");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        //sub-string keywords
        predicate = new NameContainsKeywordPredicate("aLI");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
```
###### \java\seedu\address\model\person\PersonContainsFieldsPredicateTest.java
``` java
public class PersonContainsFieldsPredicateTest {

    @Test
    public void equals() {
        PersonContainsFieldsPredicate firstPredicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate("name"),
                        new AddressContainsKeywordPredicate("address"),
                        new PhoneContainsKeywordPredicate("123")));

        PersonContainsFieldsPredicate secondPredicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new PhoneContainsKeywordPredicate("123"),
                        new AddressContainsKeywordPredicate("address"),
                        new NameContainsKeywordPredicate("name")));

        PersonContainsFieldsPredicate thirdPredicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new PhoneContainsKeywordPredicate("234"),
                        new AddressContainsKeywordPredicate("address"),
                        new NameContainsKeywordPredicate("name")));

        PersonContainsFieldsPredicate fourthPredicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new PhoneContainsKeywordPredicate("234"),
                        new PhoneContainsKeywordPredicate("234"),
                        new AddressContainsKeywordPredicate("address"),
                        new NameContainsKeywordPredicate("name")));


        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));
        assertTrue(secondPredicate.equals(firstPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different filter -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));

        //same function
        assertTrue(thirdPredicate.equals(fourthPredicate));
        assertTrue(fourthPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        //One keyword
        PersonContainsFieldsPredicate predicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate(ALICE.getName().fullName)));

        assertTrue(predicate.test(ALICE));

        //Multiple keywords
        predicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate(ALICE.getName().fullName),
                        new AddressContainsKeywordPredicate(ALICE.getAddress().value),
                        new EmailContainsKeywordPredicate(ALICE.getEmail().value)));

        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        PersonContainsFieldsPredicate predicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate(ALICE.getName().fullName + " Alice")));

        assertFalse(predicate.test(ALICE));

        //one wrong predicate
        predicate  =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate(ALICE.getName().fullName),
                        new AddressContainsKeywordPredicate(ALICE.getAddress().value),
                        new EmailContainsKeywordPredicate(BOB.getEmail().value)));
        assertFalse(predicate.test(ALICE));
    }

}
```
###### \java\seedu\address\model\person\RemarkContainsKeywordPredicateTest.java
``` java
public class RemarkContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        RemarkContainsKeywordPredicate firstPredicate = new  RemarkContainsKeywordPredicate(firstPredicateKeyword);
        RemarkContainsKeywordPredicate secondPredicate = new  RemarkContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RemarkContainsKeywordPredicate firstPredicateCopy = new  RemarkContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        // One keyword
        RemarkContainsKeywordPredicate predicate = new  RemarkContainsKeywordPredicate("likes");
        assertTrue(predicate.test(new PersonBuilder().withRemark("hates work but likes food").build()));
        //ignore case
        assertTrue(predicate.test(new PersonBuilder().withRemark("Likes to swim").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {
        RemarkContainsKeywordPredicate predicate = new RemarkContainsKeywordPredicate("hates");
        assertFalse(predicate.test(new PersonBuilder().withRemark("likes food").build()));

        //super-string
        predicate = new RemarkContainsKeywordPredicate("hates");
        assertFalse(predicate.test(new PersonBuilder().withRemark("I hate this").build()));

        // sub-string
        predicate = new RemarkContainsKeywordPredicate("like");
        assertFalse(predicate.test(new PersonBuilder().withRemark("hates work but likes food").build()));
    }
}
```
###### \java\seedu\address\model\person\TagsContainKeywordPredicateTest.java
``` java
public class TagsContainKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        TagsContainKeywordPredicate firstPredicate = new  TagsContainKeywordPredicate(firstPredicateKeyword);
        TagsContainKeywordPredicate secondPredicate = new  TagsContainKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainKeywordPredicate firstPredicateCopy = new  TagsContainKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        // One keyword
        TagsContainKeywordPredicate predicate = new  TagsContainKeywordPredicate("friends");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        //multiple tags
        predicate = new  TagsContainKeywordPredicate("friends");
        assertTrue(predicate.test(new PersonBuilder().withTags("owesMoney", "friends").build()));

        //multiple similar tags, one match only
        predicate = new  TagsContainKeywordPredicate("friends");
        assertTrue(predicate.test(
                new PersonBuilder().withTags("bestFriends", "friends", "friendsAndPals").build()));

        // sub-string
        predicate = new TagsContainKeywordPredicate("fri");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Mixed-case
        predicate = new TagsContainKeywordPredicate("FriEndS");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {

        TagsContainKeywordPredicate predicate = new TagsContainKeywordPredicate("enemies");
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        predicate = new TagsContainKeywordPredicate("friendsForLife");
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        //multiple similar tags, no match only
        predicate = new  TagsContainKeywordPredicate("bestFriends");
        assertFalse(predicate.test(
                new PersonBuilder().withTags("friendsForLife", "friends", "friendsAndPals").build()));
    }
}
```
###### \java\seedu\address\storage\ImageStorageTest.java
``` java
public class ImageStorageTest extends AddressBookGuiTest {

    private static final String PNG_STUB = "png";
    private static final String JPG_STUB = "jpg";


    @Before
    public void createTestPng() {
        assert ImageStorage.saveAvatar("src/test/data/images/avatars/test.png", "test.png");
        assert ImageStorage.saveAvatar("src/test/data/images/avatars/test.jpg", "test.jpg");
    }


    @Test
    public void getAvatarTest() throws FileNotFoundException {

        Image image1 = ImageStorage.getAvatar("test.png");
        Image image2 = new Image(new FileInputStream(new File("src/test/data/images/avatars/test.png")));

        assertImageAreEqual(image1, image2);

        Image image3 = ImageStorage.getAvatar("doestNotExist.png");
        Image image4 = AppUtil.getImage("/images/avatars/default.png");

        assertImageAreEqual(image3, image4);
    }

    @Test
    public void saveAvatarTest() {
        assertFalse(ImageStorage.saveAvatar("does_not_exist_file_path", "fakeimage"));
        assertFalse(ImageStorage.saveAvatar("does_not_exist_file_path.png", "fakeimage"));
    }

    @Test
    public void isValidImagePathTest() {

        assertTrue(ImageStorage.isValidImagePath("src/test/data/images/avatars/test.png"));
        assertTrue(ImageStorage.isValidImagePath("src/test/data/images/avatars/test.jpg"));

        //not jpg or png
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/test.pg"));
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/test.ng"));
        //gets caught in not jpg or png
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/"));

        //does not exist
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/fake.png"));
        assertFalse(ImageStorage.isValidImagePath("src/test/data/images/avatars/fake.jpg"));
    }

    @Test
    public void isJpgOrPngTest() {
        assertTrue(ImageStorage.isJpgOrPng("test.png"));
        assertTrue(ImageStorage.isJpgOrPng("test.jpg"));
        assertTrue(ImageStorage.isJpgOrPng(".png"));
        assertTrue(ImageStorage.isJpgOrPng(".jpg"));


        assertFalse(ImageStorage.isJpgOrPng("test.ng"));
        assertFalse(ImageStorage.isJpgOrPng("test.jng"));
        assertFalse(ImageStorage.isJpgOrPng("testpng"));
        assertFalse(ImageStorage.isJpgOrPng("testjng"));
        assertFalse(ImageStorage.isJpgOrPng(".pngtest"));
    }


    @Test
    public void getFormat() {
        assertEquals(PNG_STUB, ImageStorage.getFormat("test.png"));
        assertEquals(JPG_STUB, ImageStorage.getFormat("test.jpg"));

        assertEquals(PNG_STUB, ImageStorage.getFormat(".png"));
        assertEquals(JPG_STUB, ImageStorage.getFormat(".jpg"));

        //assumption
        assertEquals(PNG_STUB, ImageStorage.getFormat("fake.gif"));
        assertEquals(PNG_STUB, ImageStorage.getFormat("fake"));
    }

    /**
     * asserts that both image are equal by checking at each rendered pixel
     */

    public static void assertImageAreEqual(Image image1, Image image2) {

        assertEquals(image1.getHeight(), image2.getHeight(), 0);
        assertEquals(image1.getWidth(), image2.getWidth(), 0);


        for (int i = 0; i < image1.getWidth(); i++) {
            for (int j = 0; j < image1.getHeight(); j++) {
                int image1Argb = image1.getPixelReader().getArgb(i, j);
                int image2Argb  = image2.getPixelReader().getArgb(i, j);
                if (image1Argb != image2Argb) {
                    assert false;
                }
            }
        }
    }

}
```
###### \java\seedu\address\ui\CommandBoxHintsTest.java
``` java
public class CommandBoxHintsTest extends GuiUnitTest {

    private CommandBoxHints commandBoxHints;
    private CommandBoxHintsHandle commandBoxHintsHandle;
    private TextField commandTextField;
    @Before
    public void setUp() {
        commandTextField = new TextField();
        guiRobot.interact(() -> commandBoxHints = new CommandBoxHints(commandTextField));
        uiPartRule.setUiPart(commandBoxHints);
        commandBoxHintsHandle = new CommandBoxHintsHandle(commandBoxHints.getRoot());
    }

    @Test
    public void display() {
        postNow(new CommandInputChangedEvent("add"));
        assertEquals(" n/NAME", commandBoxHintsHandle.getText());

        postNow(new CommandInputChangedEvent("add "));
        assertEquals("n/NAME", commandBoxHintsHandle.getText());

        postNow(new CommandInputChangedEvent("add n"));
        assertEquals("/NAME", commandBoxHintsHandle.getText());

        postNow(new CommandInputChangedEvent("add n/"));
        assertEquals("NAME", commandBoxHintsHandle.getText());

        postNow(new CommandInputChangedEvent(""));
        assertEquals("Enter Command Here", commandBoxHintsHandle.getText());
    }

    @Test
    public void set_prefWidth() {
        postNow(new CommandInputChangedEvent("add"));
        String hint = commandBoxHintsHandle.getText();
        TextField textField = commandBoxHintsHandle.getTextField();
        double width = TextUtil.computeTextWidth(textField.getFont(), hint, 0.0D) + 1;

        assertEquals(width, textField.getPrefWidth(), 0);

        postNow(new CommandInputChangedEvent("select"));
        String hint2 = commandBoxHintsHandle.getText();
        TextField textField2 = commandBoxHintsHandle.getTextField();
        double width2 = TextUtil.computeTextWidth(textField.getFont(), hint2, 0.0D) + 1;

        assertEquals(width2, textField.getPrefWidth(), 0);
    }

    @Test public void clickSetsCorrectcaretPosition() {
        inputTextAndClickHints("add");
        assertEquals(3, commandTextField.getCaretPosition());

        inputTextAndClickHints("test");
        assertEquals(4, commandTextField.getCaretPosition());
    }

    /**
     * set {@code input} on commandTextField, posts change event and calls handleOnClick of commandBoxHints
     */
    private void inputTextAndClickHints(String input) {
        commandTextField.setText(input);
        postNow(new CommandInputChangedEvent(input));

        try {
            Method handleOnClick = commandBoxHints.getClass().getDeclaredMethod("handleOnClick");
            handleOnClick.setAccessible(true);
            handleOnClick.invoke(commandBoxHints);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }
}
```
###### \java\seedu\address\ui\PersonPanelTest.java
``` java
public class PersonPanelTest extends GuiUnitTest {

    private PersonPanel personPanel;
    private PersonPanelHandle personPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> personPanel = new PersonPanel());
        uiPartRule.setUiPart(personPanel);
        personPanelHandle = new PersonPanelHandle(personPanel.getRoot());
    }
    @Test
    public void display() throws Exception {
        //select ALICE
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertPersonIsDisplayed(ALICE, personPanelHandle);
        //select BOB
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(FIONA, 1)));
        assertPersonIsDisplayed(FIONA, personPanelHandle);
    }
    /**
     * Asserts that {@code personPanelHandle} displays the details of {@code expectedPerson} correctly
     */
    private void assertPersonIsDisplayed(ReadOnlyPerson expectedPerson, PersonPanelHandle personPanelHandle) {
        guiRobot.pauseForHuman();
        assertEquals(expectedPerson.getName().toString(), personPanelHandle.getName());
        assertEquals(expectedPerson.getPhone().toString(), personPanelHandle.getPhone());
        assertEquals(expectedPerson.getEmail().toString(), personPanelHandle.getEmail());
        assertEquals(expectedPerson.getAddress().toString(), personPanelHandle.getAddress());
        assertEquals(expectedPerson.getRemark().toString(), personPanelHandle.getRemark());
        assertImageDisplayed(expectedPerson.getAvatar().getOriginalFilePath(), personPanelHandle.getAvatar());

        //update tag information displayed
        personPanelHandle.updateTags();
        assertTagsAreDisplayed(expectedPerson, personPanelHandle);
    }

    /**
     * Asserts that {@code personPanelHandle} displays the tags of {@code expectedPerson} correctly
     */

    private void assertTagsAreDisplayed(ReadOnlyPerson expectedPerson, PersonPanelHandle personPanelHandle) {
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                personPanelHandle.getTags());
    }

    /**
     * Asserts that {@code personPanelHandle} displays the image of {@code expectedPerson} correctly
     */
    private void assertImageDisplayed(String originalFilePath, Image displayedImage) {

        Image image;
        try {
            if (originalFilePath == null || originalFilePath.isEmpty()) {
                image = AppUtil.getImage("/images/avatars/default.png");
            } else {
                image = new Image(new FileInputStream(new File(originalFilePath)));
            }

            ImageStorageTest.assertImageAreEqual(image, displayedImage);
        } catch (FileNotFoundException e) {
            assert false;
        }




    }
}
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " n/Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/Benson n/Daniel";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: unable to find multiple persons in address book, 2 keywords in reversed order -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/Daniel n/Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, 2 keywords with 1 repeat -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " n/Daniel n/Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: cannot find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 0 persons found
         */
        command = FindCommand.COMMAND_WORD + " n/Daniel n/Benson t/NonMatchingKeyWord";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find multiple persons with same name in address book, 1 matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " n/Meier";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " n/MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: first argument with no prefix searches for name*/
        command = FindCommand.COMMAND_WORD + " Meier";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " n/Mei";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: wrong format -> error */
        command = FindCommand.COMMAND_WORD + "";
        expectedResultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find phone number of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " p/" + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find  substring of phone number of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " p/"
                + DANIEL.getPhone().value
                .substring(DANIEL.getPhone().value.length() * 1 / 4, DANIEL.getPhone().value.length() / 2);
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " a/" + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find  substring of address of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " a/"
                + DANIEL.getAddress().value.substring(0, DANIEL.getAddress().value.length() * 3 / 4);
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " e/" + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find substring of email number of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " e/"
                + DANIEL.getEmail().value
                .substring(DANIEL.getEmail().value.length() * 1 / 4, DANIEL.getEmail().value.length() * 3 / 4);
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 7 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " t/" + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_WORD + " n/Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd n/Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }
```
