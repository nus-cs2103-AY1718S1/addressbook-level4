# YuchenHe98
###### /java/seedu/address/logic/parser/AddScheduleCommandParserTest.java
``` java
public class AddScheduleCommandParserTest {
    private AddScheduleCommandParser parser = new AddScheduleCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() throws IllegalValueException {

        Index exampleIndex = Index.fromOneBased(1);
        Day exampleDay = new Day("Friday");
        Time exampleStartTime = new Time("0730");
        Time exampleEndTime = new Time("1000");
        String input =  "1" + DAY_DESC_AMY
                + START_TIME_DESC_AMY
                + END_TIME_DESC_AMY;
        assertParseSuccess(parser, input, new AddScheduleCommand(exampleIndex,
                exampleDay, exampleStartTime, exampleEndTime));
    }
}
```
###### /java/seedu/address/logic/parser/ClearScheduleCommandParserTest.java
``` java
public class ClearScheduleCommandParserTest {
    private ClearScheduleCommandParser parser = new ClearScheduleCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() throws IllegalValueException {

        Index exampleIndex = Index.fromOneBased(1);
        Day exampleDay = new Day("Friday");
        Time exampleStartTime = new Time("0730");
        Time exampleEndTime = new Time("1000");
        String input =  "1" + DAY_DESC_AMY
                + START_TIME_DESC_AMY
                + END_TIME_DESC_AMY;
        assertParseSuccess(parser, input, new ClearScheduleCommand(exampleIndex,
                exampleDay, exampleStartTime, exampleEndTime));
    }
}

```
###### /java/seedu/address/logic/parser/FindByAddressCommandParserTest.java
``` java
public class FindByAddressCommandParserTest {

    private FindByAddressCommandParser parser = new FindByAddressCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindByAddressCommand expectedCommand =
                new FindByAddressCommand(new AddressContainsKeywordsPredicate(Arrays.asList("street", "raffles")));
        assertParseSuccess(parser, "street raffles", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n street \n \t raffles  \t", expectedCommand);
    }

}
```
###### /java/seedu/address/logic/parser/LocateCommandParserTest.java
``` java
public class LocateCommandParserTest {

    private LocateCommandParser parser = new LocateCommandParser();

    @Test
    public void parseValidArgsReturnsDeleteCommand() {
        assertParseSuccess(parser, "1", new LocateCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE));
    }
}
```
