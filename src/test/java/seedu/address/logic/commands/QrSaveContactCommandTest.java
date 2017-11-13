package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

//@@author danielweide
/**
 * Test For QrSaveContactCommand for Equal Cases
 */
public class QrSaveContactCommandTest {

    @Test
    public void equals() {
        QrSaveContactCommand firstQrSaveCommand = new QrSaveContactCommand(INDEX_FIRST_PERSON);
        QrSaveContactCommand secondQrSaveCommand = new QrSaveContactCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstQrSaveCommand.equals(firstQrSaveCommand));

        // same values -> returns true
        QrSaveContactCommand firstQrSaveCommandCopy = new QrSaveContactCommand(INDEX_FIRST_PERSON);
        assertTrue(firstQrSaveCommand.equals(firstQrSaveCommandCopy));

        // different types -> returns false
        assertFalse(firstQrSaveCommand.equals(1));

        // null -> returns false
        assertFalse(firstQrSaveCommand.equals(null));

        // different person -> returns false
        assertFalse(firstQrSaveCommand.equals(secondQrSaveCommand));
    }
}
