# bokwoon95
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_remarkAndBirthdayFieldPresent() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRemark("Likes Trains")
                .withTags().withBirthday("11-11-2010").build();
        //test if adding remarks and birthday via 'r/' and 'b/' respectively works
        assertParseSuccess(parser, "add n/Bob Choo p/22222222 a/Block 123, Bobby Street 3, #01-123 "
                + "e/bob@example.com " + "r/Likes Trains " + "b/11-11-2010", new AddCommand(expectedPerson));
    }
```
###### \java\seedu\address\logic\parser\FuzzyfindCommandParserTest.java
``` java
public class FuzzyfindCommandParserTest {

    private FuzzyfindCommandParser parser = new FuzzyfindCommandParser();

    @Test
    //empty input should throw error
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FuzzyfindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FuzzyfindCommand expectedFuzzyfindCommand =
                new FuzzyfindCommand(new NameContainsSubstringsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFuzzyfindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFuzzyfindCommand);
    }

}
```
