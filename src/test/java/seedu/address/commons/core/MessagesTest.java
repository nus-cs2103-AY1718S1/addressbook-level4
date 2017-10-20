package seedu.address.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EXTENSION_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

public class MessagesTest {

    @Test
    public void assertNonNullStaticUnknownCommand() {
        assertNotNull(MESSAGE_UNKNOWN_COMMAND);
        assertEquals(MESSAGE_UNKNOWN_COMMAND, new Messages().MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void assertNonNullStaticInvalidCommandFormat() {
        assertNotNull(MESSAGE_INVALID_COMMAND_FORMAT);
        assertEquals(MESSAGE_INVALID_COMMAND_FORMAT, new Messages().MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void assertNonNullStaticInvalidExtensionFormat() {
        assertNotNull(MESSAGE_INVALID_EXTENSION_FORMAT);
        assertEquals(MESSAGE_INVALID_EXTENSION_FORMAT, new Messages().MESSAGE_INVALID_EXTENSION_FORMAT);
    }

    @Test
    public void assertNonNullStaticInvalidPersonDisplayedIndex() {
        assertNotNull(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertEquals(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, new Messages().MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void assertNonNullStaticPersonsListedOverview() {
        assertNotNull(MESSAGE_PERSONS_LISTED_OVERVIEW);
        assertEquals(MESSAGE_PERSONS_LISTED_OVERVIEW, new Messages().MESSAGE_PERSONS_LISTED_OVERVIEW);
    }
}
