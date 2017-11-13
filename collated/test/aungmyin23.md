# aungmyin23
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortPlaces() {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\MrtCommandTest.java
``` java
public class MrtCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_mrt_success() {
        CommandResult result = new MrtCommand().execute();
        assertEquals(SHOWING_MRT_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowMrtRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\commands\PsiCommandTest.java
``` java
public class PsiCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_psi_success() {
        CommandResult result = new PsiCommand().execute();
        assertEquals(SHOWING_PSI_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowPsiRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
public class SortCommandTest {

    private Model compareModel;
    private Model expectedModel;
    private SortCommand executeSort;

    /*
    * Setting up the model to compare with for the testing.
     */
    @Before
    public void setUp() {
        compareModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(compareModel.getAddressBook(), new UserPrefs());
        executeSort = new SortCommand();
        executeSort.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
    }

    /*
    * Comparing the two model to check whether the sort command give expected outcome
     */
    @Test
    public void execute() {
        assertCommandSuccess(executeSort, compareModel, SortCommand.SHOWING_SORT_MESSAGE, expectedModel);
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple postalcode - last postalcode accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_AMY
                        + POSTAL_CODE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPlace));

        // multiple website - last website accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + WEBSITE_DESC_AMY
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPlace));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // missing postalcode prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + VALID_POSTAL_CODE_BOB, expectedMessage);
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid postalcode
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + INVALID_POSTALCODE_DESC + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                PostalCode.MESSAGE_POSTAL_CODE_CONSTRAINTS);

        // invalid website
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_WEBSITE_DESC + ADDRESS_DESC_BOB + POSTAL_CODE_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND, Website.MESSAGE_WEBSITE_CONSTRAINTS);
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseWebsite_validValue_returnsWebsite() throws Exception {
        Website expectedWebsite = new Website(VALID_WEBSITE);
        Optional<Website> actualWebsite = ParserUtil.parseWebsite(Optional.of(VALID_WEBSITE));

        assertEquals(expectedWebsite, actualWebsite.get());
    }
```
###### \java\seedu\address\model\place\PostalCodeTest.java
``` java
public class PostalCodeTest {

    @Test
    public void isValidPostalCode() {
        // invalid postal code
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("91")); // less than 3 numbers
        assertFalse(PostalCode.isValidPostalCode("phone")); // non-numeric
        assertFalse(PostalCode.isValidPostalCode("9011p041")); // alphabets within digits
        assertFalse(PostalCode.isValidPostalCode("9312 1534")); // spaces within digits
        assertFalse(PostalCode.isValidPostalCode("911")); // exactly 3 number

        // valid postal code
        assertTrue(PostalCode.isValidPostalCode("931215"));
    }
}
```
###### \java\seedu\address\model\place\WebsiteTest.java
``` java
public class WebsiteTest {

    @Test
    public void isValidWebsite() {
        // blank website
        assertFalse(Website.isValidWebsite("")); // empty string
        assertFalse(Website.isValidWebsite(" ")); // spaces only

        // invalid website
        assertFalse(Website.isValidWebsite("example.com")); // missing http part
        assertFalse(Website.isValidWebsite("http://peterjackexample")); // missing .com part
        assertFalse(Website.isValidWebsite("http://.com")); // domain name missing
        assertFalse(Website.isValidWebsite("1232430982")); // no digits

        // valid website
        assertTrue(Website.isValidWebsite("http://www.example.com")); //full address with http://
        assertTrue(Website.isValidWebsite("https://www.example.com")); //full address with https://
    }
}
```
###### \java\seedu\address\testutil\PlaceBuilder.java
``` java
    /**
     * Sets the {@code PostalCode} of the {@code PostalCode} that we are building.
     */
    public PlaceBuilder withPostalCode(String postalcode) {
        try {
            this.place.setPostalcode(new PostalCode(postalcode));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phostalcode is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code Place} that we are building.
     */
    public PlaceBuilder withWebsite(String website) {
        try {
            this.place.setWebsite(new Website(website));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique.");
        }
        return this;
    }
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid WEBSITE -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_WEBSITE_DESC
                + ADDRESS_DESC_AMY + POSTAL_CODE_DESC_AMY;
        assertCommandFailure(command, Website.MESSAGE_WEBSITE_CONSTRAINTS);
```
