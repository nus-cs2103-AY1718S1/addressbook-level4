package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

//@@author danielweide
/**
 * Test For QrSmsCommand for Equal Cases
 */
public class QrSmsCommandTest {

    @Test
    public void equals() {
        QrSmsCommand firstQrSmsCommand = new QrSmsCommand(INDEX_FIRST_PERSON);
        QrSmsCommand secondQrSmsCommand = new QrSmsCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstQrSmsCommand.equals(firstQrSmsCommand));

        // same values -> returns true
        QrSmsCommand firstQrSmsCommandCopy = new QrSmsCommand(INDEX_FIRST_PERSON);
        assertTrue(firstQrSmsCommand.equals(firstQrSmsCommandCopy));

        // different types -> returns false
        assertFalse(firstQrSmsCommand.equals(1));

        // null -> returns false
        assertFalse(firstQrSmsCommand.equals(null));

        // different person -> returns false
        assertFalse(firstQrSmsCommand.equals(secondQrSmsCommand));
    }
}
