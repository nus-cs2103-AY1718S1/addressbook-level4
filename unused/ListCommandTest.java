package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author kengying

@Test
public void execute_zeroKeyword_noPersonFound(){
    ListCommand command=prepareCommand(" ");
    assertCommandSuccess(command,expectedMessage,Collections.emptyList());
}

@Test
public void execute_oneTags_onePersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,1);
    ListCommand command=prepareCommand("owesMoney");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(BENSON));
}

@Test
public void execute_oneGroup_onePersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,1);
    ListCommand command=prepareCommand("Car");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(ELLE));
}

@Test
public void execute_twoTags_sevenPersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,7);
    ListCommand command=prepareCommand("owesMoney friend");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(ALICE,BENSON,CARL,DANIEL,ELLE,FIONA,GEORGE));
}

@Test
public void execute_twoGroups_sevenPersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,7);
    ListCommand command=prepareCommand("Car Savings");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(ALICE,BENSON,CARL,DANIEL,ELLE,FIONA,GEORGE));
}

@Test
public void execute_oneTagGroup_sevenPersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,2);
    ListCommand command=prepareCommand("Car owesMoney");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(BENSON,ELLE));
}

@Test
public void execute_wrongKeyword_zeroPersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,0);
    ListCommand command=prepareCommand("sdfsdf");
    assertCommandSuccess(command,expectedMessage,Collections.emptyList());
}

