package systemtests;

import static seedu.address.logic.commands.TagCommand.MESSAGE_INVALID_INDEXES;
import static seedu.address.logic.commands.TagCommand.MESSAGE_PERSONS_ALREADY_HAVE_TAG;
import static seedu.address.logic.commands.TagCommand.MESSAGE_SUCCESS;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void tag() throws Exception {
        Model model = getModel();

        /* ----------------- Performing tag operation while an unfiltered list is being shown ---------------------- */

        /* Case: tag persons in address book, command with leading spaces and trailing spaces
         * and multiple spaces between each argument
         * -> tagged
         */
        Index indexOne = INDEX_FIRST_PERSON;
        Index indexTwo = INDEX_SECOND_PERSON;
        Tag tag = new Tag("tagTester");
        String command = " " + TagCommand.COMMAND_WORD + "  " + indexOne.getOneBased() + "," + indexTwo.getOneBased()
                + " " + "tagTester" + " ";
        Person firstTaggedPerson = new PersonBuilder(ALICE).withTags("friends", "retrieveTester", "tagTester").build();
        Person secondTaggedPerson = new PersonBuilder(BENSON).withTags("owesMoney", "friends",
                "retrieveTester", "tagTester").build();
        assertCommandSuccess(command, Arrays.asList(indexOne, indexTwo),
                tag, Arrays.asList(firstTaggedPerson, secondTaggedPerson));

        /* Case: undo editing the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
                getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), firstTaggedPerson);
        model.updatePerson(
                getModel().getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()), secondTaggedPerson);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: tag persons, some of whom already have the tag in address book,
         * command with leading spaces and trailing spaces and multiple spaces between each argument
         * -> tagged
         */
        tag = new Tag("owesMoney");
        command = " " + TagCommand.COMMAND_WORD + "  " + indexOne.getOneBased() + "," + indexTwo.getOneBased()
                + " " + "owesMoney" + " ";
        firstTaggedPerson = new PersonBuilder(ALICE).withTags("friends", "retrieveTester",
                "tagTester", "owesMoney").build();
        secondTaggedPerson = new PersonBuilder(BENSON).withTags("owesMoney", "friends",
                "retrieveTester", "tagTester").build();
        assertCommandSuccess(command, Arrays.asList(indexOne, indexTwo),
                tag, Arrays.asList(firstTaggedPerson, secondTaggedPerson));

        /* ------------------ Performing tag operation while a filtered list is being shown ------------------------ */

        /* Case: filtered person list, tag index within bounds of address book and person list -> tagged */
        // all persons tagged
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        tag = new Tag("tagTesterNo2");
        command = " " + TagCommand.COMMAND_WORD + "  " + indexOne.getOneBased() + "," + indexTwo.getOneBased()
                + " " + "tagTesterNo2" + " ";
        firstTaggedPerson = new PersonBuilder(BENSON).withTags("owesMoney", "friends",
                "retrieveTester", "tagTester", "tagTesterNo2").build();
        secondTaggedPerson = new PersonBuilder(DANIEL).withTags("friends", "tagTesterNo2").build();
        assertCommandSuccess(command, Arrays.asList(indexOne, indexTwo),
                tag, Arrays.asList(firstTaggedPerson, secondTaggedPerson));

        // some persons already have the tag
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        tag = new Tag("owesMoney");
        command = " " + TagCommand.COMMAND_WORD + "  " + indexOne.getOneBased() + "," + indexTwo.getOneBased()
                + " " + "owesMoney" + " ";
        firstTaggedPerson = new PersonBuilder(BENSON).withTags("owesMoney", "friends",
                "retrieveTester", "tagTester", "tagTesterNo2").build();
        secondTaggedPerson = new PersonBuilder(DANIEL).withTags("friends", "tagTesterNo2", "owesMoney").build();
        assertCommandSuccess(command, Arrays.asList(indexOne, indexTwo),
                tag, Arrays.asList(firstTaggedPerson, secondTaggedPerson));

        /* Case: filtered person list, tag index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + indexOne.getOneBased() + ","
                + invalidIndex + " " + "dummyTag", MESSAGE_INVALID_INDEXES);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + indexOne.getOneBased() + "," + 0 + " "
                + "dummyTag", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + indexOne.getOneBased() + "," + -1 + " "
                + "dummyTag", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + indexOne.getOneBased() + "," + invalidIndex + " "
                + "dummyTag", MESSAGE_INVALID_INDEXES);

        /* Case: missing index -> rejected */
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + "dummyTag",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));

        /* Case: invalid tag name -> rejected */
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + indexOne.getOneBased() + ","
                + indexTwo.getOneBased() + " " + "!@#$", MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code TagCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at indexes {@code targetIndexes}
     * being updated to values specified {@code taggedPersons}.<br>
     * @param targetIndexes the indexes of the current model's filtered list.
     * @see TagCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, List<Index> targetIndexes,
                                      Tag tag, List<ReadOnlyPerson> taggedPersons) {
        Model expectedModel = getModel();
        List<ReadOnlyPerson> alreadyTaggedPersons = new ArrayList<>();
        List<ReadOnlyPerson> toBeTaggedPersons = new ArrayList<>();
        try {
            for (int i = 0; i < targetIndexes.size(); i++) {
                ReadOnlyPerson person = expectedModel.getFilteredPersonList().get(
                        targetIndexes.get(i).getZeroBased());
                if (person.getTags().contains(tag)) {
                    alreadyTaggedPersons.add(person);
                    continue;
                }
                toBeTaggedPersons.add(person);
                expectedModel.updatePerson(person, taggedPersons.get(i));
            }
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
                    "taggedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        StringJoiner toBeTaggedJoiner = new StringJoiner(", ");
        for (ReadOnlyPerson person : toBeTaggedPersons) {
            toBeTaggedJoiner.add(person.getName().toString());
        }
        if (alreadyTaggedPersons.size() > 0) {
            StringJoiner alreadyTaggedJoiner = new StringJoiner(", ");
            for (ReadOnlyPerson person : alreadyTaggedPersons) {
                alreadyTaggedJoiner.add(person.getName().toString());
            }
            assertCommandSuccess(command, expectedModel, String.format(MESSAGE_SUCCESS,
                    targetIndexes.size() - alreadyTaggedPersons.size(), tag.toString()) + " "
                    + toBeTaggedJoiner.toString() + "\n"
                    + String.format(MESSAGE_PERSONS_ALREADY_HAVE_TAG, alreadyTaggedPersons.size()) + " "
                    + alreadyTaggedJoiner.toString());
        } else {
            assertCommandSuccess(command, expectedModel, String.format(MESSAGE_SUCCESS,
                    targetIndexes.size(), tag.toString()) + " " + toBeTaggedJoiner.toString());
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
