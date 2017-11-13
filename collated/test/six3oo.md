# six3oo
###### /java/seedu/address/logic/parser/FavCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FavCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the FavCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FavCommandParserTest {

    private FavCommandParser parser = new FavCommandParser();
    /*
    @Test
    public void parse_validArgs_returnsFavCommand() {
        assertParseSuccess(parser, "1 true", new FavCommand(INDEX_FIRST_PERSON, "true"));
    }

    @Test
    public void parse_validArgs_removeReturnsFavCommand() {
        assertParseSuccess(parser, "1 false", new FavCommand(INDEX_FIRST_PERSON, "false"));
    }
    */
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavCommand.MESSAGE_USAGE));
    }
}
```
