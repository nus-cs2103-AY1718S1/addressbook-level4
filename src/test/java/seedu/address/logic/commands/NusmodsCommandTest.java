package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSONSLOT_T5;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_ADD;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.email.EmailManager;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.NusmodsCommand.NusmodsDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.ImageInit;
import seedu.address.testutil.NusmodsDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;


//@@author ritchielq-reuse
/**
 * Contains integration tests (interaction with the Model) and unit tests for NusmodsCommand.
 */
public class NusmodsCommandTest {

    public static final String MODULE_CS2103T = "CS2103T[TUT]=T5";
    private Model model;

    @Before
    public void setUp() {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
    }

    @After
    public void recovery() {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void execute_addModuleUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().withNusModules(MODULE_CS2103T).build();
        NusmodsDescriptor descriptor = new NusmodsDescriptorBuilder()
                .withType(VALID_TYPE_ADD).withModuleCode(VALID_MODULE_CS2103T)
                .withTutorial(VALID_LESSONSLOT_T5).build();
        NusmodsCommand nusmodsCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(NusmodsCommand.MESSAGE_NUSMODS_SUCCESS,
                editedPerson.getName().fullName, editedPerson.getNusModules());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new EmailManager());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nusmodsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder().withNusModules(MODULE_CS2103T).build();
        NusmodsCommand nusmodsCommand = prepareCommand(INDEX_FIRST_PERSON, new NusmodsDescriptorBuilder()
                .withType(VALID_TYPE_ADD).withModuleCode(VALID_MODULE_CS2103T)
                .withTutorial(VALID_LESSONSLOT_T5).build());

        String expectedMessage = String.format(NusmodsCommand.MESSAGE_NUSMODS_SUCCESS,
                editedPerson.getName().fullName, editedPerson.getNusModules());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new EmailManager());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nusmodsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws IOException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        NusmodsDescriptor descriptor = new NusmodsDescriptorBuilder()
                .withType(VALID_TYPE_ADD).withModuleCode(VALID_MODULE_CS2103T).build();
        NusmodsCommand nusmodsCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(nusmodsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws IOException {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NusmodsCommand nusmodsCommand = prepareCommand(outOfBoundIndex, new NusmodsDescriptorBuilder()
                .withType(VALID_TYPE_ADD).withModuleCode(VALID_MODULE_CS2103T).build());

        assertCommandFailure(nusmodsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns an {@code NusmodsCommand} with parameters {@code index} and {@code descriptor}
     */
    private NusmodsCommand prepareCommand(Index index, NusmodsDescriptor descriptor) {
        NusmodsCommand nusmodsCommand = new NusmodsCommand(index, descriptor);
        nusmodsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return nusmodsCommand;
    }

}
