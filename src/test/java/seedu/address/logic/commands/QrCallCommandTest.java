package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

//@@author danielweide
/**
 * Test For QrCallCommand for Equal Cases
 */
public class QrCallCommandTest {

    @Test
    public void equals() {
        QrCallCommand firstQrCallCommand = new QrCallCommand(INDEX_FIRST_PERSON);
        QrCallCommand secondQrCallCommand = new QrCallCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstQrCallCommand.equals(firstQrCallCommand));

        // same values -> returns true
        QrCallCommand firstQrCallCommandCopy = new QrCallCommand(INDEX_FIRST_PERSON);
        assertTrue(firstQrCallCommand.equals(firstQrCallCommandCopy));

        // different types -> returns false
        assertFalse(firstQrCallCommand.equals(1));

        // null -> returns false
        assertFalse(firstQrCallCommand.equals(null));

        // different person -> returns false
        assertFalse(firstQrCallCommand.equals(secondQrCallCommand));
    }
}
