# zhoukai07
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_ADD_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid add tag
        assertParseFailure(parser, "1" + INVALID_REM_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid remove tag
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // add tags
        userInput = targetIndex.getOneBased() + TAG_ADD_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withToAddTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remove tags
        userInput = targetIndex.getOneBased() + TAG_REM_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withToRemoveTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // clear tags
        userInput = targetIndex.getOneBased() + " " + PREFIX_CLEAR_TAG.getPrefix();
        descriptor = new EditPersonDescriptorBuilder().clearTags(true).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY, VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withToAddTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ArrayList<String> expectedEmail = new ArrayList<>();
        expectedEmail.add(INVALID_EMAIL);
        ParserUtil.parseEmail(expectedEmail);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseEmail_validValue_returnsEmail() throws Exception {
        ArrayList<Email> expectedEmail = new ArrayList<>();
        expectedEmail.add(new Email(VALID_EMAIL));
        expectedEmail.add(new Email(VALID_EMAIL_2));
        ArrayList<Email> actualEmail = ParserUtil.parseEmail(Arrays.asList(VALID_EMAIL, VALID_EMAIL_2));

        assertEquals(expectedEmail, actualEmail);
    }
```
