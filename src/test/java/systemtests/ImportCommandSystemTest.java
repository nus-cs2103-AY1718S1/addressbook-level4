package systemtests;

import static seedu.address.logic.parser.ImportCommandParser.IMPORT_FILE_DIRECTORY;
import static seedu.address.logic.parser.ImportCommandParser.MESSAGE_INVALID_FILE_NAME;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATA;
import static seedu.address.testutil.TypicalParcels.ALICE;
import static seedu.address.testutil.TypicalParcels.AMY;
import static seedu.address.testutil.TypicalParcels.BENSON;
import static seedu.address.testutil.TypicalParcels.BOB;
import static seedu.address.testutil.TypicalParcels.CARL;
import static seedu.address.testutil.TypicalParcels.IDA;
import static seedu.address.testutil.TypicalParcels.KEYWORD_MATCHING_MEIER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ParcelBuilder;

//@@author kennard123661
public class ImportCommandSystemTest extends AddressBookSystemTest {

    private static final String STORAGE_FILE = "testAddressBookForImportSystem";
    private static final String FILE_PATH = IMPORT_FILE_DIRECTORY + STORAGE_FILE + ".xml";
    private static final XmlAddressBookStorage storage = new XmlAddressBookStorage(FILE_PATH);
    private static AddressBook addressBook;

    @Before
    public void start() throws Exception {
        // reset the storage file used in ImportCommandSystemTest
        updateStorageAndAddressBook();
    }

    @Test
    public void importTest() throws Exception {
        Model model = getModel();

        /* Case: import parcels without tags to a non-empty address book, command with leading spaces and trailing
        spaces -> added */
        updateStorageAndAddressBook(new ParcelBuilder(AMY).withTags().build(),
                new ParcelBuilder(BOB).withTags().build());
        List<ReadOnlyParcel> parcelsAdded;
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>();
        List<ReadOnlyParcel> parcels = addressBook.getParcelList();

        String command = "   " + ImportCommand.COMMAND_WORD + "  " + STORAGE_FILE + "   ";
        assertCommandSuccess(command, parcels, parcels, new ArrayList<>());

        /* Case: undo importing testAddressBookForImportSystem.xml data to the list -> Amy and Bob deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addAllParcels(parcels, new ArrayList<>(), new ArrayList<>());
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate parcel -> rejected */
        command = ImportCommand.COMMAND_WORD + " " + STORAGE_FILE;
        assertCommandFailure(command, ImportCommand.MESSAGE_INVALID_DUPLICATE_PARCELS);

        /* Case: import an addressbook xml file containing duplicate parcels except with different tags -> rejected */
        updateStorageAndAddressBook(new ParcelBuilder(AMY).withTags(Tag.HEAVY.toString()).build(),
                new ParcelBuilder(BOB).withTags(Tag.FRAGILE.toString()).build());
        command = ImportCommand.COMMAND_WORD + " " + STORAGE_FILE;
        assertCommandFailure(command, ImportCommand.MESSAGE_INVALID_DUPLICATE_PARCELS);

        /* Case: imports parcels with all fields same as other parcels in the address book except name -> imported */
        updateStorageAndAddressBook(new ParcelBuilder(AMY).withName("Kyle").build(),
                new ParcelBuilder(BOB).withName("John").build());
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: imports parcels with all fields same as other parcels in the address book except phone -> imported */
        updateStorageAndAddressBook(new ParcelBuilder(AMY).withPhone("98547321").build(),
                new ParcelBuilder(BOB).withPhone("96758989").build());
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: imports parcels with all fields same as other parcels in the address book except email -> imported */
        updateStorageAndAddressBook(new ParcelBuilder(AMY).withEmail("amy@gmail.com").build(),
                new ParcelBuilder(BOB).withEmail("bob@gmail.com").build());
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: imports parcels with all fields same as another parcel in the address book
         * except address -> imported
         */
        updateStorageAndAddressBook(new ParcelBuilder(AMY).withAddress("Amy Street S123661").build(),
                new ParcelBuilder(BOB).withAddress("BOB Street S456123").build());
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: add a parcel with all fields same as another parcel in the address book
         * except tracking number -> imported
         */
        updateStorageAndAddressBook(new ParcelBuilder(AMY).withTrackingNumber("RR123564897SG").build(),
                new ParcelBuilder(BOB).withTrackingNumber("RR123564897SG").build());
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: add a parcel with all fields same as another parcel in the address book
         * except delivery date -> imported
         */
        updateStorageAndAddressBook(new ParcelBuilder(AMY).withDeliveryDate("12-01-2017").build(),
                new ParcelBuilder(BOB).withDeliveryDate("12-01-2017").build());
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: filters the parcel list before importing -> imported */
        executeCommand(FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER);
        assert getModel().getFilteredParcelList().size() < getModel().getAddressBook().getParcelList().size();
        updateStorageAndAddressBook(IDA);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: imports to an empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getParcelList().size() == 0;
        updateStorageAndAddressBook(IDA,  BENSON);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: selects first card in the parcel list, import parcels -> import, card selection removed */
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        assert getParcelListPanel().isAnyCardSelected();
        updateStorageAndAddressBook(CARL, ALICE);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);
        assert !getParcelListPanel().isAnyCardSelected() : "Selection should be gone";

        /* Case: file is empty -> rejected */
        updateStorageAndAddressBook();
        assert addressBook.getParcelList().size() == 0;
        assertCommandFailure(command, ImportCommand.MESSAGE_INVALID_FILE_EMPTY);

        /* Case: invalid keyword -> rejected */
        command = "imports " + STORAGE_FILE;
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: missing file -> rejected */
        command = "import " + "missing";
        assertCommandFailure(command, MESSAGE_FILE_NOT_FOUND);

        /* Case: invalid xml file -> rejected */
        command = "import " + "testNotXmlFormatAddressBook";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_DATA,
                IMPORT_FILE_DIRECTORY + "testNotXmlFormatAddressBook.xml"));

        /* Case: invalid file name -> rejected */
        command = "import " + "testNotXmlFormatAddressBook.xml";
        assertCommandFailure(command, MESSAGE_INVALID_FILE_NAME);
    }

    @After
    public void clear() throws Exception {
        // reset the storage file used in ImportCommandSystemTest
        AddressBook addressBook = new AddressBookBuilder().build();
        new XmlAddressBookStorage("./data/import/" + STORAGE_FILE + ".xml").saveAddressBook(addressBook);
    }

    /**
     * Creates a new {@link AddressBook} and adds {@link ReadOnlyParcel} in parcels to the newly created
     * {@link AddressBook}. Saves the newly created {@link AddressBook} into the {@code storage}
     *
     * @param parcels the list of parcels to add into the newly created {@link AddressBook}
     * @throws IOException if {@code storage} is not a valid storage file/path
     */
    private static void updateStorageAndAddressBook(ReadOnlyParcel... parcels) throws IOException {
        AddressBookBuilder addressBookBuilder = new AddressBookBuilder();

        for (ReadOnlyParcel parcel : parcels) {
            addressBookBuilder.withParcel(parcel);
        }

        addressBook = addressBookBuilder.build();
        storage.saveAddressBook(addressBook);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)}. Executes {@code command}
     * instead.
     */
    private void assertCommandSuccess(String command, List<ReadOnlyParcel> parcelsToAdd,
                                      List<ReadOnlyParcel> expectedAddedParcels,
                                      List<ReadOnlyParcel> expectedDuplicateParcels) {

        Model expectedModel = getModel();
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>();
        List<ReadOnlyParcel> addedParcels = new ArrayList<>();

        expectedModel.addAllParcels(parcelsToAdd, addedParcels, duplicateParcels);
        String expectedResultMessage = String.format(ImportCommand.MESSAGE_SUCCESS, expectedAddedParcels.size(),
                expectedDuplicateParcels.size(), ImportCommand.getImportFormattedParcelListString(expectedAddedParcels),
                ImportCommand.getImportFormattedParcelListString(expectedDuplicateParcels));

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
