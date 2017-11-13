# derickjw
###### \java\seedu\address\logic\commands\FindByPhoneCommandTest.java
``` java
    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindByPhoneCommand command = prepareCommand("95352563 87652533 9482224");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));
    }
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void execute_listCommandSuccess_noDefaultAccount() {
        String listCommand = ListCommand.COMMAND_WORD;
        String createDefaultAccountCommand = CreateDefaultAccountCommand.COMMAND_WORD;
        assertCommandSuccess(createDefaultAccountCommand, CreateDefaultAccountCommand.MESSAGE_CREATE_SUCCESS, model);
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_clearCommandSuccess_noDefaultAccount() {
        String clearCommand = ClearCommand.COMMAND_WORD;
        String createDefaultAccountCommand = CreateDefaultAccountCommand.COMMAND_WORD;
        assertCommandSuccess(createDefaultAccountCommand, CreateDefaultAccountCommand.MESSAGE_CREATE_SUCCESS, model);
        assertCommandSuccess(clearCommand, ClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_sortCommandSuccess_noDefaultAccount() {
        String sortCommand = SortCommand.COMMAND_WORD;
        String createDefaultAccountCommand = CreateDefaultAccountCommand.COMMAND_WORD;
        assertCommandSuccess(createDefaultAccountCommand, CreateDefaultAccountCommand.MESSAGE_CREATE_SUCCESS, model);
        assertCommandSuccess(sortCommand, SortCommand.MESSAGE_SUCCESS, model);
    }
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicateTest.java
``` java
    @Test
    public void testPhoneContainsKeywordsReturnsTrue() {
        // One phone number search
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("94571111"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94571111").build()));

        // Multiple phone number searches
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("94571111", "94571112"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94571111").build()));
    }

    @Test
    public void testPhoneDoesNotContainKeywordsReturnsFalse() {
        // Zero phone number search
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("94571111").build()));

        // Non-matching phone number
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("94571112"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("94571113").build()));

        // Keywords match name, email and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12346")
                .withEmail("alice@email.com").withAddress("Street").build()));
    }
}
```
###### \java\systemtests\ChangePasswordCommandTest.java
``` java

public class ChangePasswordCommandTest extends AddressBookSystemTest {

    @Test
    public void changePassword() {
        /* Case: Change password of Default Account
         * -> Password set
         */
        String command = ChangePasswordCommand.COMMAND_WORD + " " + "admin " + "admin " + "password";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);
    }

    /**
     * Executes {@code ChangePasswordCommand} and verifies that the command box displays an empty string,
     * the result display box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of
     * people in the filtered list, and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {

        executeCommand("createDefaultAcc");
        String expectedResultMessage = "Password changed successfully!";

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, Model expectedModel) {

        String expectedResultMessage = "Invalid Credentials!";

        executeCommand("changepw wrongUser wrongPw failureTest");
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\ChangeUsernameCommandTest.java
``` java

public class ChangeUsernameCommandTest extends AddressBookSystemTest {

    @Test
    public void changeUsername() {
        /* Case: Change username of Default Account
         * -> Username set
         */
        String command = ChangeUsernameCommand.COMMAND_WORD + " " + "admin " + "newUser " + "admin";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);
    }

    /**
     * Executes {@code ChangeUsernameCommand} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {

        executeCommand("createDefaultAcc");
        String expectedResultMessage = "Username changed successfully!";

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\CreateDefaultAccountCommandSystemTest.java
``` java

public class CreateDefaultAccountCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void createDefaultAccount() {
        /* Case : No default account exists
         * -> Default account created successfully
         */
        String command = CreateDefaultAccountCommand.COMMAND_WORD;
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);

        /* Case : Default account already exists
         * -> Default account failed to be created
         */

        expectedModel = getModel();
        assertCommandFailure(command,expectedModel);

    }

    /**
     * Executes the {@code command} and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing
     * {@code command}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedMessage = "Default account created successfully!";

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes the {@code CreateDefaultAccountCommand} and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing
     * {@code CreateDefaultAccountCommand}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, Model expectedModel) {
        String expectedMessage = "Account already exists!";

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\RemoveAccountCommandSystemTest.java
``` java

public class RemoveAccountCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void changeUsername() {
        /* Case: Change username of Default Account
         * -> Username set
         */
        String command = RemoveAccountCommand.COMMAND_WORD + " " + "admin " + "admin";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);
    }

    /**
     * Executes {@code RemoveAccountCommand} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {

        executeCommand("createDefaultAcc");
        String expectedResultMessage = "Login removed successfully";

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
```
