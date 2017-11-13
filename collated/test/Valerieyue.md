# Valerieyue
###### \java\guitests\guihandles\BirthdayStatisticsPanelHandle.java
``` java
/**
 * A handler for the {@code BirthdayStatisticsPanel} of the UI
 */
public class BirthdayStatisticsPanelHandle extends NodeHandle<Node> {

    public static final String BIRTHDAY_STATISTICS_PANEL_ID = "#birthdayStatisticsPanel";

    public BirthdayStatisticsPanelHandle(Node birthdayStatisticsPanelNode) {
        super(birthdayStatisticsPanelNode);
    }

    /**
     * Returns the BirthdayStatisticsPanel node.
     */
    public Node getBirthdayStatisticsPanelNode() {
        return getRootNode();
    }
}
```
###### \java\guitests\guihandles\TagStatisticsPanelHandle.java
``` java
/**
 * A handler for the {@code TagStatisticsPanel} of the UI
 */
public class TagStatisticsPanelHandle extends NodeHandle<Node> {

    public static final String TAG_STATISTICS_PANEL_ID = "#tagStatisticsPanel";

    public TagStatisticsPanelHandle(Node tagStatisticsPanelNode) {
        super(tagStatisticsPanelNode);
    }

    /**
     * Returns the TagStatisticsPanel node.
     */
    public Node getTagStatisticsPanelNode() {
        return getRootNode();
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple birthdays - last birthday accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_AMY
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + POSTALCODE_DESC_BOB
                + FAV_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // no birthday
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday("")
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPostalCode("")
                .withFavourite(VALID_FAV_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + FAV_DESC_BOB, new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid birthday
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_BIRTHDAY_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + FAV_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_BIRTHDAY_DESC, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        // invalid birthday
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // birthday
        userInput = targetIndex.getOneBased() + BIRTHDAY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBirthday(VALID_BIRTHDAY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // Empty string
        assertFalse(Birthday.isValidBirthday("9188")); // Less than 6 numbers
        assertFalse(Birthday.isValidBirthday("birthday")); // Invalid date
        assertFalse(Birthday.isValidBirthday("9011p1")); // Alphabets within digits
        assertFalse(Birthday.isValidBirthday("931 534")); // Spaces within digits

        // valid birthday
        assertTrue(Birthday.isValidBirthday("12 12 87")); // Can enter year using 2 digit
        assertTrue(Birthday.isValidBirthday("14-05-1997")); // Accepts '-' as separator
        assertTrue(Birthday.isValidBirthday("08 aug 1985")); // Name of month
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except birthday -> added. */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_BOB)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTALCODE_AMY).withFavourite(VALID_FAV_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + POSTALCODE_DESC_AMY + FAV_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing birthday -> added. */
        ReadOnlyPerson person = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withPostalCode(VALID_POSTALCODE_AMY).build();
        assertCommandSuccess(person);
```
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
        /* Case: Change information panel of address book using birthday statistics command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to birthday statistics panel.
        */
        assertCommandSuccess(BirthdayStatisticsCommand.COMMAND_WORD, birthdayStatisticsPanelId,
                BirthdayStatisticsCommand.MESSAGE_SUCCESS);

        /* Case: Change information panel of address book using tag statistics command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to tag statistics panel.
        */
        assertCommandSuccess(TagStatisticsCommand.COMMAND_WORD, tagStatisticsPanelId,
                TagStatisticsCommand.MESSAGE_SUCCESS);
```
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
        /* Case: Changes information panel of address book using birthday statistics command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to birthday statistics panel.
        */
        assertCommandSuccess(BirthdayStatisticsCommand.COMMAND_ALIAS, birthdayStatisticsPanelId,
                BirthdayStatisticsCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using tag statistics command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to tag statistics panel.
        */
        assertCommandSuccess(TagStatisticsCommand.COMMAND_ALIAS, tagStatisticsPanelId,
                TagStatisticsCommand.MESSAGE_SUCCESS);
```
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
        /* Case: Changes information panel of address book using birthday statistics command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to birthday statistics panel.
        */
        assertCommandSuccess("  " + BirthdayStatisticsCommand.COMMAND_WORD + "  $#%543@$   ",
                birthdayStatisticsPanelId, BirthdayStatisticsCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using tag statistics command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to tag statistics panel.
        */
        assertCommandSuccess("  " + TagStatisticsCommand.COMMAND_WORD + "  $#%543@$   ",
                tagStatisticsPanelId, TagStatisticsCommand.MESSAGE_SUCCESS);
```
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
        /* Case: Mixed case birthday statistics command word -> rejected. */
        assertCommandFailure("birTHDaystaTIStics", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case birthday statistics command alias -> rejected. */
        assertCommandFailure("bstATs", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case tag statistics command word -> rejected. */
        assertCommandFailure("tagstaTIStics", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case tag statistics command alias -> rejected. */
        assertCommandFailure("tstATs", MESSAGE_UNKNOWN_COMMAND);
```
