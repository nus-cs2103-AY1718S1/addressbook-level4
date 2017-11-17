# Jacob
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parse_emptyScore_failure() {
        //an empty group score alone is not valid, as group score cannot be reset
        String userInput = "1 s/";
        assertParseFailure(parser, userInput, EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_emptyScore_success() {
        //an empty score alongside another change will process the other change, and not affect group score
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " s/" + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
}
```
