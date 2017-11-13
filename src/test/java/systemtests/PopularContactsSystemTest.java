//@@author tshradheya
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.DetailsCommand.MESSAGE_DETAILS_PERSON_SUCCESS;
import static seedu.address.logic.commands.LocationCommand.MESSAGE_FIND_LOCATION_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_TAG_ENEMY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DetailsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.model.Model;

public class PopularContactsSystemTest extends AddressBookSystemTest {

    @Test
    public void favouriteContactsTest() {

        /* Selecting first one to increase popularity counter by one */
        String command = "   " + DetailsCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredListPopularContacts(expectedModel, ALICE, BENSON, CARL, DANIEL, ELLE);
        assertCommandSuccessForDetails(command, INDEX_FIRST_PERSON);
        assertSelectedCardChangedForDetails(INDEX_FIRST_PERSON);


        /* Executing viewtag to increase popularity counter by one for everyone in the tag */
        command = "    " + ViewTagCommand.COMMAND_WORD + " " + KEYWORD_TAG_ENEMY + "    ";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ELLE);
        ModelHelper.setFilteredListPopularContacts(expectedModel, ALICE, ELLE, BENSON, CARL, DANIEL);
        assertCommandSuccessForViewTag(command, expectedModel);

        /* Executing location to increase popularity counter by one for everyone in the tag */
        command = "    " + LocationCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "    ";
        expectedModel = getModel();
        ModelHelper.setFilteredListPopularContacts(expectedModel, ELLE, ALICE, BENSON, CARL, DANIEL);
        assertCommandSuccessForLocation(command, INDEX_FIRST_PERSON, expectedModel);

        /* Listing all contacts */
        command = "    " + ListCommand.COMMAND_WORD;
        executeCommand(command);

        /* Executing viewtag to increase popularity counter by one for everyone in the tag */
        command = "    " + ViewTagCommand.COMMAND_WORD + " " + "owesmoney";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        ModelHelper.setFilteredListPopularContacts(expectedModel, ELLE, ALICE, BENSON, CARL, DANIEL);
        assertCommandSuccessForViewTag(command, expectedModel);

    }

    /**
     * Command success for Select
     * @param command
     * @param expectedSelectedCardIndex
     */
    private void assertCommandSuccessForDetails(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_DETAILS_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccessForViewTag(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedModel.getFilteredPersonList().size() != 0) {
            assertStatusBarUnchangedExceptSyncStatus();
        } else {
            assertStatusBarUnchanged();
        }

    }

    /**
     * Executes command and checks the state and equality of model
     * @param command
     * @param expectedSelectedCardIndex
     * @param expectedModel
     */
    private void assertCommandSuccessForLocation(String command, Index expectedSelectedCardIndex, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_FIND_LOCATION_SUCCESS,
                expectedModel.getFilteredPersonList().get(expectedSelectedCardIndex.getZeroBased()).getName(),
                expectedModel.getFilteredPersonList().get(expectedSelectedCardIndex.getZeroBased()).getAddress());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

}


