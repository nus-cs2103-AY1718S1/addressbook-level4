# Henning
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortFilteredPersonListScore() {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java

        @Override
        public void addFavouritePerson(ReadOnlyPerson target) {
            fail("This method should not be called.");
        }

        @Override
        public void removeFavouritePerson(ReadOnlyPerson target) {
            fail("This method should not be called.");
        }

        @Override
        public void changeListTo(String listName) {
            fail("This method should not be called.");
        }

        @Override
        public void setCurrentList(String currentList)  {
            fail("This method should not be called.");
        }

        @Override
        public String getCurrentList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredFavouritePersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredFavouritePersonList() {
            fail("This method should not be called.");
            return null;
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
        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withScore(VALID_SCORE_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCORE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withScore(VALID_SCORE_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + SCORE_DESC_AMY,
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

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid score
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_SCORE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Score.MESSAGE_SCORE_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCORE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + SCORE_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_SCORE_DESC, Score.MESSAGE_SCORE_CONSTRAINTS); //invalid score
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;

        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND + BIRTHDAY_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + SCORE_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withScore(VALID_SCORE_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // score
        userInput = targetIndex.getOneBased() + SCORE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withScore(VALID_SCORE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()  + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + ADDRESS_DESC_AMY
                + EMAIL_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

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
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public Person build() {
        return this.person;
    }

}
```
