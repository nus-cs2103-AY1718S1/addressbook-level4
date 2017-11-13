package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.user.UserCreds;
import seedu.address.model.user.UserPrefs;
import seedu.address.storage.XmlPersonListStorage;

//@@author HanYaodong
//TODO: the export command test depends on Storage part. Consider separation of components.
public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    /**
     * Typical address model consists of ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE
     */
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());

    //@@author zenghou-unused
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());

        List<Index> indexes = Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2));
        String filePath = "TestFile.xml";
        ExportCommand exportCommand = new ExportCommand(indexes, filePath);

        exportCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(exportCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
    //@@author

    @Test
    public void equals() {
        List<Index> indexes = Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2));
        String filePath = "TestFile.xml";
        ExportCommand exportCommand = new ExportCommand(indexes, filePath);

        // same object -> true
        assertTrue(exportCommand.equals(exportCommand));

        // same value -> true
        ExportCommand exportCommandCopy = new ExportCommand(indexes, filePath);
        assertTrue(exportCommand.equals(exportCommandCopy));

        // different type -> false
        assertFalse(exportCommand.equals(1));

        // null -> false
        assertFalse(exportCommand.equals(null));

        // different index -> false
        List<Index> newIndexes = Collections.singletonList(Index.fromOneBased(1));
        ExportCommand exportCommandDifferentIndex = new ExportCommand(newIndexes, filePath);
        assertFalse(exportCommand.equals(exportCommandDifferentIndex));

        //different filePath -> false
        ExportCommand exportCommandDifferentFilePath = new ExportCommand(indexes, "OtherFile.xml");
        assertFalse(exportCommand.equals(exportCommandDifferentFilePath));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        int outOfBoundaryOneBasedIndex = this.model.getFilteredPersonList().size() + 1;
        ExportCommand command = prepareCommand(new Integer[]{outOfBoundaryOneBasedIndex}, getTestFilePath());

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        CommandResult result = command.execute();

    }

    @Test
    public void execute_validIndexesAndFilePath_success() throws Exception {
        // export typical person: Alice, Benson, Daniel
        ExportCommand command = prepareCommand(new Integer[]{1, 2, 4}, getTestFilePath());

        // test command output
        CommandResult commandResult = command.execute();
        assertEquals(commandResult.feedbackToUser, String.format(
            ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS, constructNameList(ALICE, BENSON, DANIEL), getTestFilePath()));

        // test file output
        UniquePersonList origin = new UniquePersonList();
        origin.setPersons(Arrays.asList(ALICE, BENSON, DANIEL));
        XmlPersonListStorage tmpStorage = new XmlPersonListStorage(getTestFilePath());
        UniquePersonList readBack = tmpStorage.readPersonList().get();
        assertEquals(readBack, origin);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        prepareCommand(new Integer[]{outOfBoundIndex.getOneBased()}, getTestFilePath()).execute();
    }

    @Test
    public void execute_validIndexesAndFilePathFilteredList_success() throws Exception {
        showFirstPersonOnly(this.model);
        // export typical person: Alice
        ExportCommand command = prepareCommand(new Integer[]{1}, getTestFilePath());

        // test command output
        CommandResult commandResult = command.execute();
        assertEquals(commandResult.feedbackToUser, String.format(
            ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS, constructNameList(ALICE), getTestFilePath()));

        // test file output
        UniquePersonList origin = new UniquePersonList();
        origin.setPersons(Arrays.asList(ALICE));
        XmlPersonListStorage tmpStorage = new XmlPersonListStorage(getTestFilePath());
        UniquePersonList readBack = tmpStorage.readPersonList().get();
        assertEquals(readBack, origin);
    }

    /**
     * @return an {@code ExportCommand} with parameters {@code indexes} and {@code filePath}
     */
    private ExportCommand prepareCommand(Integer[] indexesInt, String filePath) {
        model.getUserCreds().validateCurrentSession(); // validate user
        List<Index> indexes = Arrays.stream(indexesInt).map(Index::fromOneBased).collect(Collectors.toList());
        ExportCommand export = new ExportCommand(indexes, filePath);
        export.setData(this.model, new CommandHistory(), new UndoRedoStack());
        return export;
    }

    /**
     * Builds a String consisting of {@code persons}'s name in the format of
     * "[person_1's name], [person_2's name], ..., [person_n's name] "
     */
    private String constructNameList(ReadOnlyPerson... persons) {
        StringBuilder personNameList = new StringBuilder();
        for (ReadOnlyPerson person : persons) {
            personNameList.append(person.getName().fullName).append(", ");
        }
        personNameList.deleteCharAt(personNameList.lastIndexOf(","));
        return personNameList.toString();
    }

    /**
     * @return a valid file path for testing.
     */
    private String getTestFilePath() {
        return testFolder.getRoot().getPath() + "TempPersonList.xml";
    }
}
