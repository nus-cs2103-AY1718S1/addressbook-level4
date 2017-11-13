# moomeowroar
###### /java/systemtests/ChangePwdCommandSystemTest.java
``` java
public class ChangePwdCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void changePwd() {
        /* Case: Set password without password
         * -> Password set
         */
        String command = ChangePwdCommand.COMMAND_WORD +
                " " + "password";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);

        /* Case: Change password after setting
         * -> Password updated
         */
        command = ChangePwdCommand.COMMAND_WORD + " " + "password1" + " " + "password";
        expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);

        /* Case: Entering wrong password
         * -> Rejected
         */
        command = ChangePwdCommand.COMMAND_WORD + " " + "password3" + " " + "password2";
        assertCommandFailure(command, "Wrong password.");
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = "Password changed successfully.";

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
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
