# coolpotato1
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortFilteredPersonListScore() {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        //multiple scores - last score accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
              + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCORE_DESC_AMY + SCORE_DESC_BOB + TAG_DESC_FRIEND,
              new AddCommand(expectedPerson));

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        //no score
        Person expectedPerson2 = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withScore("").withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson2));

        //All optional fields missing
        Person expectedPerson3 = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withBirthday("No Birthday Listed").withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withScore("").withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, new AddCommand(expectedPerson3));

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid score
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_SCORE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Score.MESSAGE_SCORE_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_SCORE_DESC, Score.MESSAGE_SCORE_CONSTRAINTS); //invalid score
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // score
        userInput = targetIndex.getOneBased() + SCORE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withScore(VALID_SCORE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public PersonBuilder withScore(String score) {
        try {
            this.person.setScore(new Score(score));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("score is expected to be unique");
        }
        return this;
    }
```
