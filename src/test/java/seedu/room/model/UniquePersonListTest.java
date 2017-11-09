package seedu.room.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.room.testutil.TypicalPersons.ALICE;
import static seedu.room.testutil.TypicalPersons.BENSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.room.commons.core.Messages;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.person.UniquePersonList;
import seedu.room.model.tag.Tag;

public class UniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }

    //@@author: Haozhe321
    @Test
    public void removeByTagTest() throws IllegalValueException, CommandException {
        //build uniquePersonList with only Alice
        UniquePersonList uniquePersonList = new UniquePersonList();
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.getInternalList().size() == 1);
        uniquePersonList.removeByTag(new Tag("friends"));

        //check that we have removed Alice, who has a tag called "friends"
        assertTrue(uniquePersonList.getInternalList().size() == 0);


        uniquePersonList.add(ALICE);
        uniquePersonList.add(BENSON);
        uniquePersonList.removeByTag(new Tag("friends"));

        //both Alice and Benson have tags called "friends", so size of list after removing is 0
        assertTrue(uniquePersonList.getInternalList().size() == 0);


        //set up the list with Alice and Benson, where only Benson has the tag "owesMoney"
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BENSON);
        uniquePersonList.removeByTag(new Tag("owesMoney"));

        //check that only Alice is left in the list
        assertTrue(uniquePersonList.getInternalList().size() == 1);
        assertTrue(uniquePersonList.getInternalList().contains(ALICE));
        assertFalse(uniquePersonList.getInternalList().contains(BENSON));
    }

    @Test
    public void removeByTag_nonExistentTag_throwsException() throws IllegalValueException,
            CommandException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BENSON);

        /*since removeByTag is case sensitive, both Alice and Benson do not have tag "OWESMoney",
        so a CommandException is thrown */
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_TAG_FOUND);
        uniquePersonList.removeByTag(new Tag("OWESMoney"));
    }
    //@@author: Haozhe321
}
