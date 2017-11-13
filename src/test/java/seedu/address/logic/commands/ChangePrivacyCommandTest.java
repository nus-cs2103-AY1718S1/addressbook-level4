package seedu.address.logic.commands;

//@@author jeffreygohkw
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static seedu.address.logic.commands.ChangePrivacyCommand.PersonPrivacySettings;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonPrivacySettingsBuilder;

public class ChangePrivacyCommandTest {

    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void personPrivacySettingsTests() {
        PersonPrivacySettings pps = new PersonPrivacySettings();

        assertFalse(pps.isAnyFieldNonNull());

        PersonPrivacySettings ppsByBuilder = new PersonPrivacySettingsBuilder().setNamePrivate("true")
            .setPhonePrivate("false").setEmailPrivate("true").setAddressPrivate("true")
            .setRemarkPrivate("false").build();
        pps.setNameIsPrivate(true);
        pps.setPhoneIsPrivate(false);
        pps.setEmailIsPrivate(true);
        pps.setAddressIsPrivate(true);
        pps.setRemarkIsPrivate(false);

        assertEquals(ppsByBuilder.getAddressIsPrivate(), pps.getAddressIsPrivate());
        assertEquals(ppsByBuilder.getEmailIsPrivate(), pps.getEmailIsPrivate());
        assertEquals(ppsByBuilder.getNameIsPrivate(), pps.getNameIsPrivate());
        assertEquals(ppsByBuilder.getPhoneIsPrivate(), pps.getPhoneIsPrivate());
        assertEquals(ppsByBuilder.getRemarkIsPrivate(), pps.getRemarkIsPrivate());
        assertEquals(ppsByBuilder.isAnyFieldNonNull(), pps.isAnyFieldNonNull());

        PersonPrivacySettings ppsCopy = new PersonPrivacySettings(pps);

        assertEquals(ppsCopy.getAddressIsPrivate(), pps.getAddressIsPrivate());
        assertEquals(ppsCopy.getEmailIsPrivate(), pps.getEmailIsPrivate());
        assertEquals(ppsCopy.getNameIsPrivate(), pps.getNameIsPrivate());
        assertEquals(ppsCopy.getPhoneIsPrivate(), pps.getPhoneIsPrivate());
        assertEquals(ppsCopy.getRemarkIsPrivate(), pps.getRemarkIsPrivate());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person newPerson = new PersonBuilder().withEmail("alice@example.com").build();
        newPerson.getName().setPrivate(true);
        newPerson.getPhone().setPrivate(true);
        newPerson.getEmail().setPrivate(true);
        newPerson.getAddress().setPrivate(true);
        newPerson.setRemark(model.getFilteredPersonList().get(0).getRemark());
        newPerson.getRemark().setPrivate(true);

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder(newPerson).setNamePrivate("true")
                .setPhonePrivate("true").setEmailPrivate("true").setAddressPrivate("true").setRemarkPrivate("true")
                .build();
        ChangePrivacyCommand changePrivacyCommand = new ChangePrivacyCommand(INDEX_FIRST_PERSON, pps);
        changePrivacyCommand.model = model;

        String expectedMessage = String.format(ChangePrivacyCommand.MESSAGE_CHANGE_PRIVACY_SUCCESS, newPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), newPerson);

        assertCommandSuccess(changePrivacyCommand, model, expectedMessage, expectedModel);

        PersonPrivacySettings ppsPublic = new PersonPrivacySettingsBuilder(newPerson).setNamePrivate("false")
                .setPhonePrivate("false").setEmailPrivate("false").setAddressPrivate("false").setRemarkPrivate("false")
                .build();

        newPerson.getName().setPrivate(false);
        newPerson.getPhone().setPrivate(false);
        newPerson.getEmail().setPrivate(false);
        newPerson.getAddress().setPrivate(false);
        newPerson.getRemark().setPrivate(false);

        ChangePrivacyCommand changePrivacyCommandPublic = new ChangePrivacyCommand(INDEX_FIRST_PERSON, ppsPublic);
        changePrivacyCommandPublic.setData(model, new CommandHistory(), new UndoRedoStack());

        String expectedMessagePublic = String.format(ChangePrivacyCommand.MESSAGE_CHANGE_PRIVACY_SUCCESS, newPerson);

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), newPerson);

        assertCommandSuccess(changePrivacyCommandPublic, model, expectedMessagePublic, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        ReadOnlyPerson lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        Person personInList = new PersonBuilder().withName(lastPerson.getName().toString())
                .withPhone(lastPerson.getPhone().toString()).withEmail(lastPerson.getEmail().toString())
                .withAddress(lastPerson.getAddress().toString()).withRemark(lastPerson.getRemark().toString())
                .withFavourite(lastPerson.getFavourite().toString())
                .build();

        personInList.setTags(lastPerson.getTags());
        personInList.getName().setPrivate(true);
        personInList.getPhone().setPrivate(true);


        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder(personInList).setNamePrivate("true")
                .setPhonePrivate("true").build();
        ChangePrivacyCommand changePrivacyCommand = new ChangePrivacyCommand(indexLastPerson, pps);
        changePrivacyCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        String expectedMessage = String.format(ChangePrivacyCommand.MESSAGE_CHANGE_PRIVACY_SUCCESS, personInList);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(lastPerson, personInList);

        assertCommandSuccess(changePrivacyCommand, model, expectedMessage, expectedModel);

        PersonPrivacySettings ppsPublic = new PersonPrivacySettingsBuilder(personInList).setNamePrivate("false")
                .setPhonePrivate("false").build();


        personInList.getName().setPrivate(false);
        personInList.getPhone().setPrivate(false);

        ChangePrivacyCommand changePrivacyCommandPublic = new ChangePrivacyCommand(indexLastPerson, ppsPublic);
        changePrivacyCommandPublic.setData(model, new CommandHistory(), new UndoRedoStack());

        String expectedMessagePublic = String.format(ChangePrivacyCommand.MESSAGE_CHANGE_PRIVACY_SUCCESS, personInList);

        expectedModel.updatePerson(lastPerson, personInList);

        assertCommandSuccess(changePrivacyCommandPublic, model, expectedMessagePublic, expectedModel);
    }
}
