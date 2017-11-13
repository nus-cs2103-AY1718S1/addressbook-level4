package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_SAMPLE;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Test;

import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.logic.commands.commandmode.PasswordMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.SecurityManager;
import seedu.address.testutil.PasswordFileUtil;

public class PasswordCommandTest {

    private void addPass() throws IOException, NoSuchAlgorithmException {
        SecurityManager.savePass(PASSWORD_SAMPLE);
    }

    private void removePass() throws IOException, NoSuchAlgorithmException, WrongPasswordException {
        SecurityManager.removePass(PASSWORD_SAMPLE);
    }

    @After
    public void cleanUp() {
        PasswordFileUtil.removePassFile();
    }

    @Test
    public void addPassSuccess() {
        PasswordMode addPassMode = new PasswordCommand.SetPass(PASSWORD_SAMPLE);
        PasswordCommand passCommand = new PasswordCommand(addPassMode);
        assertCommandSuccess(passCommand, PasswordCommand.MESSAGE_SET_PASS, true);
    }

    @Test
    public void addPassFailure() throws IOException, NoSuchAlgorithmException {
        //add test password to test removal of passwords
        addPass();

        PasswordMode addPassMode = new PasswordCommand.SetPass(PASSWORD_SAMPLE);
        PasswordCommand passCommand = new PasswordCommand(addPassMode);
        assertCommandFailure(passCommand, PasswordCommand.MESSAGE_PASS_EXISTS, true);
    }

    @Test
    public void removePassSuccess() throws IOException, NoSuchAlgorithmException {
        //add test password to test removal of passwords
        addPass();

        PasswordMode removePassMode = new PasswordCommand.ClearPass(PASSWORD_SAMPLE);
        PasswordCommand passCommand = new PasswordCommand(removePassMode);
        assertCommandSuccess(passCommand, PasswordCommand.MESSAGE_CLEARED_PASS, false);
    }

    @Test
    public void removePassWrongPassFailure() throws IOException, NoSuchAlgorithmException {
        //add test password to test removal of passwords
        addPass();

        PasswordMode removePassMode = new PasswordCommand.ClearPass("wrong pass");
        PasswordCommand passCommand = new PasswordCommand(removePassMode);
        assertCommandFailure(passCommand, PasswordCommand.MESSAGE_WRONG_PASS, true);
    }

    @Test
    public void removePassNoPassFailure() throws IOException, NoSuchAlgorithmException {
        PasswordMode removePassMode = new PasswordCommand.ClearPass(PASSWORD_SAMPLE);
        PasswordCommand passCommand = new PasswordCommand(removePassMode);
        assertCommandFailure(passCommand, PasswordCommand.MESSAGE_NO_PASS_TO_CLEAR, false);
    }

    @Test
    public void changePassSuccess() throws IOException, NoSuchAlgorithmException {
        //add test password to test removal of passwords
        addPass();

        PasswordMode changePassMode = new PasswordCommand.ChangePass(PASSWORD_SAMPLE, PASSWORD_SAMPLE);
        PasswordCommand passCommand = new PasswordCommand(changePassMode);
        assertCommandSuccess(passCommand, PasswordCommand.MESSAGE_CHANGED_PASS, true);
    }

    @Test
    public void changePassWrongPassFailure() throws IOException, NoSuchAlgorithmException {
        //add test password to test removal of passwords
        addPass();

        PasswordMode changePassMode = new PasswordCommand.ChangePass("wrong Pass", PASSWORD_SAMPLE);
        PasswordCommand passCommand = new PasswordCommand(changePassMode);
        assertCommandFailure(passCommand, PasswordCommand.MESSAGE_WRONG_PASS + "\n"
                + PasswordCommand.MESSAGE_PASS_NOT_CHANGED, true);
    }

    @Test
    public void changePassNoExisingPassFailure() throws IOException, NoSuchAlgorithmException {
        PasswordMode changePassMode = new PasswordCommand.ChangePass(PASSWORD_SAMPLE, PASSWORD_SAMPLE);
        PasswordCommand passCommand = new PasswordCommand(changePassMode);
        assertCommandFailure(passCommand, PasswordCommand.MESSAGE_NO_PASS_TO_CHANGE, false);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - if the data/pass file should or should not exist.
     */
    public static void assertCommandSuccess(Command command, String expectedMessage, boolean shouldExists) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertShouldPasswordFileExist(shouldExists);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, String expectedMessage, boolean shouldExists) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertShouldPasswordFileExist(shouldExists);
        }
    }

    /**
     * Asserts if the password file should exist or not.
     */
    private static void assertShouldPasswordFileExist(boolean shouldExist) {
        if (shouldExist) {
            assertTrue("Password File Should Exist", PasswordFileUtil.passFileExist());
        } else {
            assertFalse("Password File Not Should Exist", PasswordFileUtil.passFileExist());
        }
    }
}
