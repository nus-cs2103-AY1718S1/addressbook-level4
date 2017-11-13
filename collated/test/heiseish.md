# heiseish
###### /java/systemtests/RemarkCommandSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_ANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class RemarkCommandSystemTest extends AddressBookSystemTest {

    // this message is used to match message in result display, which should be empty for any failed execution
    private static final String MESSAGE_EXECUTION_FAILURE_EMPTY = "";

    @Test
    public void remark() throws Exception {
        Model model = getModel();

        /* -------------- Performing remark operation while an unfiltered list is being shown -------------------- */

        /* Case: remark a valid index in the list -> edited
         */
        Model expectedModel = getModel();
        Index index = INDEX_FIRST_PERSON;
        String command = " " + RemarkCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + REMARK_DESC_ANY;

        ReadOnlyPerson personToEdit = getPerson(expectedModel, index);
        Person newPerson = RemarkCommand.addOrChangeRemark(
                personToEdit, new Remark(VALID_REMARK));
        assertCommandSuccess(command, index, newPerson);

        /* Case: undo remarking the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo remarking the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
                getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), newPerson);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: remark a person with new values same as existing values -> remark */
        command = RemarkCommand.COMMAND_WORD + " " + INDEX_SECOND_PERSON.getOneBased()
                + " ";
        assertCommandSuccess(command, INDEX_SECOND_PERSON, BENSON);



        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered person list, edit index within bounds of address book and person list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = RemarkCommand.COMMAND_WORD + " " + index.getOneBased() + " " + REMARK_DESC_ANY;
        personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        newPerson = new PersonBuilder(personToEdit).withRemark(VALID_REMARK).build();
        assertCommandSuccess(command, index, newPerson);

        /* Case: filtered person list, edit index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(RemarkCommand.COMMAND_WORD + " " + invalidIndex + REMARK_DESC_ANY,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* ---------------------Performing remark operation while a person card is selected ------------------------- */

        /* Case: selects first card in the person list, remark a person -> remarked, card selection
         * remains unchanged but browser url changes
         */
        /*
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = RemarkCommand.COMMAND_WORD + " "
                + index.getOneBased() + REMARK_DESC_ANY;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new person's name
        newPerson = new Person(ALICE);
        newPerson.setRemark(new Remark("dev"));
        assertCommandSuccess(command, index, newPerson, index);
        */
        /* -------------------------------- Performing invalid remark operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(RemarkCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(RemarkCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(RemarkCommand.COMMAND_WORD + " " + invalidIndex + REMARK_DESC_ANY,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: missing index -> rejected */
        assertCommandFailure(RemarkCommand.COMMAND_WORD + NAME_DESC_BOB,
                MESSAGE_EXECUTION_FAILURE_EMPTY);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyPerson, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toRemark the index of the current model's filtered list
     * @see RemarkCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyPerson, Index)
     */
    private void assertCommandSuccess(String command, Index toRemark, ReadOnlyPerson editedPerson) {
        assertCommandSuccess(command, toRemark, editedPerson, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toRemark the index of the current model's filtered list.
     * @see RemarkCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toRemark, ReadOnlyPerson editedPerson,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updatePerson(
                    expectedModel.getFilteredPersonList().get(toRemark.getZeroBased()), editedPerson);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(RemarkCommand.MESSAGE_REMARK_PERSON_SUCCESS, editedPerson), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see RemarkCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
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
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command, false);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command, true);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### /java/systemtests/FavoriteCommandSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.Collections;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;


public class FavoriteCommandSystemTest extends AddressBookSystemTest {

    // this message is used to match message in result display, which should be empty for any failed execution
    private static final String MESSAGE_EXECUTION_FAILURE_EMPTY = "";

    private static final String MESSAGE_INVALID_FAVORITE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE);

    @Test
    public void favorite() {
        /* ----------- Performing favorite operation while an unfiltered list is being shown -------------------- */

        /* Case: favorite the first person in the list, command with leading spaces and trailing spaces -> favorited */
        Model expectedModel = getModel();
        String command = FavoriteCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        ReadOnlyPerson personToFavorite = favoritePerson(expectedModel, INDEX_FIRST_PERSON);

        String expectedResultMessage = String.format(
                FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, personToFavorite);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* Case: favorite the last person in the list -> favorited */
        Model modelBeforeFavoritingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeFavoritingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo favoriting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeFavoritingLast, expectedResultMessage);

        /* Case: redo favoriting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        favoritePerson(modelBeforeFavoritingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeFavoritingLast, expectedResultMessage);

        /* Case: favorite the middle person in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ----------- Performing favorite operation while a filtered list is being shown ------------------- */

        /* Case: filtered person list, favorite index within bounds of address book and person list -> favorited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index, INDEX_SECOND_PERSON);

        /* Case: filtered person list, favorite index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = FavoriteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* --------------- Performing favorite operation while a person card is selected ------------------- */

        /* Case: favorite the selected person -> person list panel selects the same person*/
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = INDEX_SECOND_PERSON;
        selectPerson(selectedIndex);
        command = FavoriteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        personToFavorite = favoritePerson(expectedModel, selectedIndex);

        expectedResultMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, personToFavorite);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------- Performing invalid favorite operation ---------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = FavoriteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid index (-1) -> rejected */
        command = FavoriteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = FavoriteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(FavoriteCommand.COMMAND_WORD + " abc", MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(FavoriteCommand.COMMAND_WORD + " 1 abc", MESSAGE_EXECUTION_FAILURE_EMPTY);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("FaVORIte 1",
                MESSAGE_EXECUTION_FAILURE_EMPTY);
    }

    /**
     * Removes the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the favorited person
     */
    private ReadOnlyPerson favoritePerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = getPerson(model, index);
        try {
            model.favoritePerson(targetPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        Person favoritedPerson = new Person(targetPerson);
        favoritedPerson.setFavorite(
                new Favorite(!targetPerson.getFavorite().favorite));
        return favoritedPerson;
    }

    /**
     * Favorite the person at {@code toFavorite} by creating a default {@code FavoriteCommand} using
     * {@code toFavorite} and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see FavoriteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toFavorite) {
        Model expectedModel = getModel();
        ReadOnlyPerson personToFavorite = favoritePerson(expectedModel, toFavorite);
        String expectedResultMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, personToFavorite);
        assertCommandSuccess(
                FavoriteCommand.COMMAND_WORD + " "
                        + toFavorite.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Favorite the person at {@code toFavorite} in a filtered list which is equivalent to a person at
     * {@code defaultIndex} in the normal Model, by creating a default {@code FavoriteCommand} using {@code toFavorite}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see FavoriteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toFavorite, Index defaultIndex) {
        Model expectedModel = getModel();

        ReadOnlyPerson personToFavorite = favoritePerson(expectedModel, defaultIndex);
        String expectedResultMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, personToFavorite);
        expectedModel.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(Collections.singletonList(KEYWORD_MATCHING_MEIER)));
        assertCommandSuccess(
                FavoriteCommand.COMMAND_WORD + " "
                        + toFavorite.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see FavoriteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command, false);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

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

        executeCommand(command, true);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### /java/systemtests/FindCommandSystemTest.java
``` java
        /* Case: find phone number of person in address book -> 1 person (Daniel) found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 3 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL, GEORGE, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 1 person (Daniel) found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> empty tag so all found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

```
###### /java/seedu/address/ui/IconImageTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.image.Image;

public class IconImageTest {
    private static final String ICON = "/images/heart.png";
    private static final String ICON_OUTLINE = "/images/heartOutline.png";
    private static final String DEFAULT = "/images/default.png";
    private static final String DEFAULT_GROUP = "/images/group.png";
    private static final String FACEBOOK = "/images/facebook.png";

    private Image heart;
    private Image heartOutline;
    private Image fbicon;
    private Image circlePerson;
    private Image circleGroup;
    private IconImage image = new IconImage();

    @Before
    public void setUp() {
        circlePerson = new Image(getClass().getResourceAsStream(DEFAULT));
        circleGroup = new Image(getClass().getResourceAsStream(DEFAULT_GROUP));
        heart = new Image(getClass().getResourceAsStream(ICON), 25, 25, false, false);
        heartOutline = new Image(getClass().getResourceAsStream(ICON_OUTLINE), 20, 20, false, false);
        fbicon = new Image(getClass().getResourceAsStream(FACEBOOK), 25, 25, false, false);

    }

    @Test
    public void testImage() {
        assertTrue(heart.equals(image.getHeart()));
        assertTrue(heartOutline.equals(image.getHeartOutline()));
        assertTrue(fbicon.equals(image.getFbicon()));
        assertTrue(circlePerson.equals(image.getCirclePerson()));
        assertTrue(circleGroup.equals(image.getCircleGroup()));
    }
}
```
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_MAP_URL_PREFIX
                + StringUtil.partiallyEncode(ALICE.getAddress().value)
                + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // associated facebook page of a person
        postNow(facebookOpenEventStub);
        expectedPersonUrl = new URL(FACEBOOK_PREFIX + dummy.getFacebook().value);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // search name of a person
        postNow(searchNameEvent);
        expectedPersonUrl = new URL(GOOGLE_URL_PREFIX + dummy.getName().fullName + GOOGLE_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // search major of a person
        postNow(searchMajorEvent);
        expectedPersonUrl = new URL(GOOGLE_URL_PREFIX
                + "NUS " + dummy.getMajor().value + GOOGLE_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

```
###### /java/seedu/address/commons/util/LanguageUtilTest.java
``` java
package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LanguageUtilTest {
    @Test
    public void getClosestCommand() throws Exception {
        //for find
        assertEquals("find", getCommand("fnid"));
        assertEquals("find", getCommand("fin"));

        //for add
        assertEquals("add", getCommand("ad"));
        assertEquals("add", getCommand("dad"));

        //for delete
        assertEquals("delete", getCommand("dele"));
        assertEquals("delete", getCommand("detele"));

    }

    @Test
    public void levenshteinDistance() throws Exception {
        //addition
        assertEquals((Integer) 1, getLevenshteinDistance("ad", "add"));
        assertEquals((Integer) 2, getLevenshteinDistance("dele", "delete"));
        //substitution
        assertEquals((Integer) 2, getLevenshteinDistance("lsit", "list"));
        assertEquals((Integer) 3, getLevenshteinDistance("letede", "delete"));
        //subtraction
        assertEquals((Integer) 1, getLevenshteinDistance("liste", "list"));
        assertEquals((Integer) 2, getLevenshteinDistance("deletete", "delete"));
        //mixed: 1 sub + 1 subtraction
        assertEquals((Integer) 3, getLevenshteinDistance("lsite", "list"));
    }

    /**
     * Return result from getClosestCommand in languageUtil
     * @param cmd commandWord
     * @return nearest commandword
     */
    private String getCommand(String cmd) {
        return LanguageUtil.getClosestCommand(cmd);
    }

    /**
     * Return Levenshtein distance between 2 strings
     * @param s1 first string
     * @param s2 second string
     * @return levenshtein distance
     */
    private Integer getLevenshteinDistance(String s1, String s2) {
        return LanguageUtil.levenshteinDistance(s1, s1.length(), s2, s2.length());
    }

}
```
###### /java/seedu/address/commons/util/CollectionUtilTest.java
``` java
    @Test
    public void checkMutualOrContains() throws IllegalValueException {
        UniqueTagList list1 = new UniqueTagList();
        UniqueTagList list2 = new UniqueTagList();
        list1.add(new Tag("friends"));
        list2.add(new Tag("frie"));
        assertMutualOrContain(list1.toSet(), list2.toSet());

        //disjoint
        list2.add(new Tag("friend"));
        assertMutualOrContain(list1.toSet(), list2.toSet());

        //return false
        UniqueTagList list3 = new UniqueTagList();
        list3.add(new Tag("cs2103"));
        assertNotMutualOrContain(list1.toSet(), list3.toSet());


    }
```
###### /java/seedu/address/logic/parser/FavoriteCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.FavoriteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FavoriteCommandParserTest {

    private FavoriteCommandParser parser = new FavoriteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new FavoriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                MESSAGE_INVALID_COMMAND_FORMAT + FavoriteCommand.MESSAGE_USAGE);
    }
}
```
###### /java/seedu/address/logic/parser/RemarkCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_ANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = MESSAGE_INVALID_COMMAND_FORMAT + RemarkCommand.MESSAGE_USAGE;

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-70" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "2 blah blah", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "2 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_remark_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + REMARK_DESC_ANY;

        String randomRemark = REMARK_DESC_ANY;
        RemarkCommand expected = new RemarkCommand(Index.fromOneBased(1), new Remark(randomRemark));
        //success
        assertParseSuccess(parser, userInput, expected);
    }

}
```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;

public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String remarkString = "";

    @Test
    public void testExecuteUndoableCommand() throws Exception {
    }

    @Test
    public void testEqual() throws Exception {
        RemarkCommand remark1 = new RemarkCommand(Index.fromOneBased(1), new Remark("haha"));
        RemarkCommand remark2 = new RemarkCommand(Index.fromOneBased(1), new Remark("haha"));
        assertTrue(assertRemarkCommandEqual(remark1, remark2));
    }



    @Test
    public void execute_unfilteredList_success() {
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark(remarkString));
        ReadOnlyPerson remarkedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_PERSON_SUCCESS, remarkedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }


    /**
     * Check if two RemarkCommand objects are equal
     */
    private boolean assertRemarkCommandEqual(RemarkCommand remark1, RemarkCommand remark2) {
        if (remark1.equals(remark2)) {
            return true;
        }
        return false;
    }

    /**
     * Remark filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex,
                new Remark("Nice"));

        assertCommandFailure(remarkCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### /java/seedu/address/logic/commands/FavoriteCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FavoriteCommand}.
 */
public class FavoriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToFavorite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favoritedPerson = new Person(personToFavorite);
        favoritedPerson.setFavorite(new Favorite(!personToFavorite.getFavorite().favorite));

        FavoriteCommand favoriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, favoritedPerson);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favoritePerson(personToFavorite);

        assertCommandSuccess(favoriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavoriteCommand favoriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favoriteCommand, model, MESSAGE_EXECUTION_FAILURE
                + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToFavorite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favoritedPerson = new Person(personToFavorite);
        favoritedPerson.setFavorite(new Favorite(!personToFavorite.getFavorite().favorite));

        FavoriteCommand favoriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavoriteCommand.MESSAGE_FAVORITE_PERSON_SUCCESS, favoritedPerson);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favoritePerson(personToFavorite);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(favoriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FavoriteCommand favoriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favoriteCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FavoriteCommand favoriteFirstCommand = new FavoriteCommand(INDEX_FIRST_PERSON);
        FavoriteCommand favoriteSecondCommand = new FavoriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favoriteFirstCommand.equals(favoriteFirstCommand));

        // same values -> returns true
        FavoriteCommand favoriteFirstCommandCopy = new FavoriteCommand(INDEX_FIRST_PERSON);
        assertTrue(favoriteFirstCommand.equals(favoriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favoriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favoriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favoriteFirstCommand.equals(favoriteSecondCommand));
    }

    /**
     * Returns a {@code FavoriteCommand} with the parameter {@code index}.
     */
    private FavoriteCommand prepareCommand(Index index) {
        FavoriteCommand favoriteCommand = new FavoriteCommand(index);
        favoriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favoriteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### /java/seedu/address/model/person/PersonTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.Collections;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class PersonTest {
    public static final UniqueTagList TAG_LIST = new UniqueTagList();
    private Person person = new Person(AMY);

    @Test(expected = IllegalValueException.class)
    public void setName() throws Exception {
        person.setName(new Name("Akatsuki_wtf@12"));
    }

    @Test
    public void nameProperty() throws Exception {
        assertEquals(person.nameProperty().get(), new Name(VALID_NAME_AMY));
    }

    @Test
    public void getName() throws Exception {
        assertEquals(person.getName(), new Name(VALID_NAME_AMY));
    }

    @Test(expected = IllegalValueException.class)
    public void setPhone() throws Exception {
        person.setPhone(new Phone("asd1213f_2"));
    }

    @Test
    public void phoneProperty() throws Exception {
        assertEquals(person.phoneProperty().get(), new Phone(VALID_PHONE_AMY));
    }

    @Test
    public void getPhone() throws Exception {
        assertEquals(person.getPhone(), new Phone(VALID_PHONE_AMY));
    }

    @Test(expected = IllegalValueException.class)
    public void setEmail() throws Exception {
        person.setEmail(new Email("notValidEmail"));
    }

    @Test
    public void emailProperty() throws Exception {
        assertEquals(person.emailProperty().get(), new Email(VALID_EMAIL_AMY));
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals(person.getEmail(), new Email(VALID_EMAIL_AMY));
    }

    @Test(expected = NullPointerException.class)
    public void setAddress() throws Exception {
        person.setAddress(null);
    }

    @Test
    public void addressProperty() throws Exception {
        assertEquals(person.addressProperty().get(), new Address(VALID_ADDRESS_AMY));
    }

    @Test
    public void getAddress() throws Exception {
        assertEquals(person.getAddress(), new Address(VALID_ADDRESS_AMY));
    }

    @Test(expected = NullPointerException.class)
    public void setRemark() throws Exception {
        person.setRemark(null);
    }

    @Test
    public void remarkProperty() throws Exception {
        assertEquals(person.remarkProperty().get(), new Remark(""));
    }

    @Test
    public void getRemark() throws Exception {
        assertEquals(person.getRemark(), new Remark(""));
    }

    @Test(expected = NullPointerException.class)
    public void setFavorite() throws Exception {
        person.setFavorite(null);
    }

    @Test
    public void favoriteProperty() throws Exception {
        assertEquals(person.favoriteProperty().get(), new Favorite());
    }

    @Test
    public void getFavorite() throws Exception {
        assertEquals(person.getFavorite(), new Favorite());
    }

    @Test(expected = NullPointerException.class)
    public void setMajor() throws Exception {
        person.setMajor(null);
    }

    @Test
    public void majorProperty() throws Exception {
        assertEquals(person.majorProperty().get(), new Major(""));
    }

    @Test
    public void getMajor() throws Exception {
        assertEquals(person.getMajor(), new Major(""));
    }

    @Test(expected = NullPointerException.class)
    public void setFacebook() throws Exception {
        person.setFacebook(null);
    }

    @Test
    public void facebookProperty() throws Exception {
        assertEquals(person.facebookProperty().get(), new Facebook(""));
    }

    @Test
    public void getFacebook() throws Exception {
        assertEquals(person.getFacebook(), new Facebook(""));
    }

    @Test
    public void getTags() throws Exception {
        TAG_LIST.add(new Tag("friend"));
        assertEquals(person.getTags(), TAG_LIST.toSet());
    }

    @Test
    public void getTagsString() throws Exception {
        assertEquals(person.getTagsString(), Collections.singletonList("friend"));
    }

    @Test
    public void tagProperty() throws Exception {
        assertEquals(person.tagProperty().get(), TAG_LIST);
    }

    @Test(expected = NullPointerException.class)
    public void setTags() throws Exception {
        person.setTags(null);
    }

    @Test
    public void equals() throws Exception {
        assertTrue(person.equals(person));
    }

    @Test
    public void replacement() {
        ReadOnlyPerson copyPerson = new Person(person);
        person.resetData(copyPerson);
        assertTrue(copyPerson.equals(person));
    }

}
```
###### /java/seedu/address/model/person/predicates/TagContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {
    private static Set<Tag> set1 = new UniqueTagList().toSet();
    private static Set<Tag> set2 = new UniqueTagList().toSet();

    @Before
    public void setUp() throws Exception {
        set1.add(new Tag("friends"));
        set1.add(new Tag("family"));
        set2.add(new Tag("friends"));

    }
    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(set2);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(set1);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Only one matching sub-keyword
        predicate = new TagContainsKeywordsPredicate(set1);
        assertTrue(predicate.test(new PersonBuilder().withTags("friendship").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(new UniqueTagList().toSet());
        assertFalse(predicate.test(new PersonBuilder().withTags("cs2103").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(set1);
        assertFalse(predicate.test(new PersonBuilder().withTags("cs2103").build()));

        predicate = new TagContainsKeywordsPredicate(set1);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street").withBirthday("01/01/1990")
                .withTags("cs2103").build()));
    }

    @Test
    public void equals() throws Exception {
        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(set1);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(set2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(set1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
```
###### /java/seedu/address/model/person/predicates/AnyContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.PersonBuilder;

public class AnyContainsKeywordsPredicateTest {
    private static Set<Tag> set1 = new UniqueTagList().toSet();
    private static Set<Tag> set2 = new UniqueTagList().toSet();
    private static AnyContainsKeywordsPredicate predicate1 = new AnyContainsKeywordsPredicate(
            Arrays.asList("Alice", "street"));
    private static AnyContainsKeywordsPredicate predicate2 = new AnyContainsKeywordsPredicate(
            Collections.singletonList("gmail.com"));


    @Before
    public void setUp() throws Exception {
        set1.add(new Tag("friends"));
        set1.add(new Tag("family"));
        set2.add(new Tag("friends"));

    }

    @Test
    public void test_anyContainsKeywords_returnsTrue() {
        // One keyword
        assertTrue(predicate2.test(new PersonBuilder().withEmail("huehue@gmail.com").build()));

        // Multiple keywords
        assertTrue(predicate1.test(new PersonBuilder().withName("Alice").build()));

        // Only one matching sub-keyword
        assertTrue(predicate1.test(new PersonBuilder().withName("Alicey").build()));
    }

    @Test
    public void test_anyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AnyContainsKeywordsPredicate predicate0 = new AnyContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate0.test(new PersonBuilder().withTags("cs2103").build()));

        // Non-matching keyword
        assertFalse(predicate2.test(new PersonBuilder().withEmail("haha@yahoo.com").build()));

        assertFalse(predicate1.test(new PersonBuilder().withName("Alic").withPhone("12345")
                .withEmail("alic@email.com").withAddress("Stree").withBirthday("01/01/1990")
                .withTags("cs2103").build()));
    }

    @Test
    public void equals() throws Exception {

        // same object -> returns true
        assertTrue(predicate1.equals(predicate1));

        // same values -> returns true
        AnyContainsKeywordsPredicate firstPredicateCopy = new AnyContainsKeywordsPredicate(
                Arrays.asList("Alice", "street"));
        assertTrue(predicate1.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(predicate1.equals(1));

        // null -> returns false
        assertFalse(predicate1.equals(null));

        // different person -> returns false
        assertFalse(predicate1.equals(predicate2));
    }

}
```
###### /java/seedu/address/model/person/predicates/AddressContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {
    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(
                Arrays.asList("Prince", "George", "Park"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Prince George Park").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Prince", "George", "Park", "NUS"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Prince George Park").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Prince", "George", "Park", "NUS"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("NUSU").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Prince", "George", "Park", "NUS"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("NUS").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("NUS").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Prince", "George", "Park", "NUS"));
        assertFalse(predicate.test(new PersonBuilder().withName("NUS").build()));

        // Keywords match name, email and birthday, but does not match address
        predicate = new AddressContainsKeywordsPredicate(
                Arrays.asList("Alice", "alice@email.com", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
```
###### /java/seedu/address/model/person/predicates/RemarkContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class RemarkContainsKeywordsPredicateTest {
    @Test
    public void test_remarkContainsKeywords_returnsTrue() {
        // One keyword
        RemarkContainsKeywordsPredicate predicate = new RemarkContainsKeywordsPredicate(
                Collections.singletonList("dev"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("dev").build()));

        // Multiple keywords
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("dev", "UI"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("UI").build()));

        // Only one matching keyword
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("dev", "UI"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("dev of UI").build()));

        // Mixed-case keywords
        predicate = new RemarkContainsKeywordsPredicate(Collections.singletonList("dev"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("DEV").build()));
    }

    @Test
    public void test_remarkDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RemarkContainsKeywordsPredicate predicate = new RemarkContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRemark("dev").build()));

        // Non-matching keyword
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("dev", "UI"));
        assertFalse(predicate.test(new PersonBuilder().withRemark("Model").build()));

        // Keywords match name, email and birthday, address, but does not match Remark
        predicate = new RemarkContainsKeywordsPredicate(
                Arrays.asList("Alice", "alice@email.com", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street")
                .withRemark("dev").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RemarkContainsKeywordsPredicate firstPredicate =
                new RemarkContainsKeywordsPredicate(firstPredicateKeywordList);
        RemarkContainsKeywordsPredicate secondPredicate =
                new RemarkContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RemarkContainsKeywordsPredicate firstPredicateCopy =
                new RemarkContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
```
###### /java/seedu/address/model/person/predicates/PhoneContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {
    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(
                Collections.singletonList("84041421"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("84041421").build()));

        // Multiple keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("84041421", "84041415"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("84041421").build()));

        // Only one matching sub-keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("84041421", "840414"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("8404142").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("84041421").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("84041421", "84041415"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("84041499").build()));

        // Keywords match name, address, email and birthday, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(
                Arrays.asList("Ben", "Street", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("84041421");
        List<String> secondPredicateKeywordList = Arrays.asList("84041421", "84041415");

        PhoneContainsKeywordsPredicate firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
```
###### /java/seedu/address/model/person/predicates/MajorContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class MajorContainsKeywordsPredicateTest {
    @Test
    public void test_majorContainsKeywords_returnsTrue() {
        // One keyword
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate(
                Collections.singletonList("Chemical"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Chemical Engineering").build()));

        // Multiple keywords
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Chemical", "Engineering"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Chemical Engineering").build()));

        // Only one matching keyword
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Chemical", "Engineering"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Engineering").build()));

        // Mixed-case keywords
        predicate = new MajorContainsKeywordsPredicate(Collections.singletonList("chemical"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Chemical Engineering").build()));
    }

    @Test
    public void test_majorDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("chemical").build()));

        // Non-matching keyword
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("chemical", "engineering"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("computer science").build()));

        // Keywords match name, email and birthday, address, but does not match major
        predicate = new MajorContainsKeywordsPredicate(
                Arrays.asList("Alice", "alice@email.com", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street")
                .withMajor("chemical").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MajorContainsKeywordsPredicate firstPredicate =
                new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
        MajorContainsKeywordsPredicate secondPredicate =
                new MajorContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsKeywordsPredicate firstPredicateCopy =
                new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
```
###### /java/seedu/address/model/person/predicates/EmailContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordsPredicateTest {
    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(
                Collections.singletonList("example@mail.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@mail.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("example@mail.com", "example@yeahoo.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@mail.com").build()));

        // Only one matching sub-keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("example@mail.com", "example@yeahoo.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@mail.com.sg").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("example@MAIl.com", "example@yeahoo.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@mail.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("example@mail.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("example@mail.com", "example@yahoo.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("example@facebook.com").build()));

        // Keywords match name, address and birthday, but does not match email
        predicate = new EmailContainsKeywordsPredicate(
                Arrays.asList("Ben", "Street", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("example@mail.com");
        List<String> secondPredicateKeywordList = Arrays.asList("example@mail.com", "example@yahoo.com");

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
```
###### /java/seedu/address/model/person/predicates/FacebookContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class FacebookContainsKeywordsPredicateTest {
    @Test
    public void test_facebookContainsKeywords_returnsTrue() {
        // One keyword
        FacebookContainsKeywordsPredicate predicate = new FacebookContainsKeywordsPredicate(
                Collections.singletonList("zuck"));
        assertTrue(predicate.test(new PersonBuilder().withFacebook("zuck").build()));

        // Multiple keywords
        predicate = new FacebookContainsKeywordsPredicate(Arrays.asList("zuck", "galois"));
        assertTrue(predicate.test(new PersonBuilder().withFacebook("zuck").build()));

        // Only one matching keyword
        predicate = new FacebookContainsKeywordsPredicate(Arrays.asList("zuck", "galois"));
        assertTrue(predicate.test(new PersonBuilder().withFacebook("zuckerberg").build()));

        // Mixed-case keywords
        predicate = new FacebookContainsKeywordsPredicate(Collections.singletonList("zuck"));
        assertTrue(predicate.test(new PersonBuilder().withFacebook("ZUCK").build()));
    }

    @Test
    public void test_facebookDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FacebookContainsKeywordsPredicate predicate = new FacebookContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withFacebook("zuck").build()));

        // Non-matching keyword
        predicate = new FacebookContainsKeywordsPredicate(Arrays.asList("zuck", "galois"));
        assertFalse(predicate.test(new PersonBuilder().withFacebook("michaelJackson").build()));

        // Keywords match name, email and birthday, address, but does not match facebook
        predicate = new FacebookContainsKeywordsPredicate(
                Arrays.asList("Alice", "alice@email.com", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street")
                .withFacebook("galois").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FacebookContainsKeywordsPredicate firstPredicate =
                new FacebookContainsKeywordsPredicate(firstPredicateKeywordList);
        FacebookContainsKeywordsPredicate secondPredicate =
                new FacebookContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FacebookContainsKeywordsPredicate firstPredicateCopy =
                new FacebookContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
```
###### /java/seedu/address/model/person/MajorTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MajorTest {
    @Test
    public void isEqualMajor() {

        // equal Major objects
        assertTrue(new Major("Chemical Engineering").equals(new Major("Chemical Engineering")));
        assertTrue(new Major("Computer Science").equals(new Major("Computer Science")));

        //unequal Major objects
        assertFalse(new Major("Chemical Engineering").equals(new Major("Computer Science")));
        assertFalse(new Major("Computer Science").equals(new Major("")));
    }
}
```
###### /java/seedu/address/model/person/RemarkTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {
    @Test
    public void isEqualRemarks() {

        // equal Remark objects
        assertTrue(new Remark("haha").equals(new Remark("haha")));
        assertTrue(new Remark("hmpq").equals(new Remark("hmpq")));

        //unequal Remark objects
        assertFalse(new Remark("haha").equals(new Remark("bobo")));
        assertFalse(new Remark("owing money").equals(new Remark("new friend")));
    }
}
```
###### /java/seedu/address/model/person/FavoriteTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FavoriteTest {

    @Test
    public void testEquals() {
        // equal Favorite objects
        assertTrue(new Favorite(true).equals(new Favorite(true)));
        assertTrue(new Favorite(false).equals(new Favorite(false)));

        //unequal Favorite objects
        assertFalse(new Favorite(true).equals(new Favorite(false)));
        assertFalse(new Favorite(false).equals(new Favorite(true)));
    }
}
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRemark(String remark) {
        ParserUtil.parseRemark(Optional.of(remark)).ifPresent(descriptor::setRemark);
        return this;
    }

    /**
     * Sets the {@code Major} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withMajor(String major) {
        ParserUtil.parseMajor(Optional.of(major)).ifPresent(descriptor::setMajor);
        return this;
    }


    /**
     * Sets the {@code Facebook} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFacebook(String facebook) {
        ParserUtil.parseFacebook(Optional.of(facebook)).ifPresent(descriptor::setFacebook);
        return this;
    }

    //author
    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withMajor(String major) {
        this.person.setMajor(new Major(major));
        return this;
    }


    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withFacebook(String facebook) {
        this.person.setFacebook(new Facebook(facebook));
        return this;
    }
```
###### /java/guitests/guihandles/StatusBarFooterHandle.java
``` java
    /**
     * Returns the text of the total number of people
     */
    public String getTotalPersons() {
        return totalPersonsNode.getText();
    }

    /**
     * Returns the text of the total number of group
     */
    public String getTotalGroups() {
        return totalGroupsNode.getText();
    }

    /**
     * Remembers the content of the 'total number of person' portion of the status bar.
     */
    public void rememberTotalPersons() {
        lastRememberedTotalPersons = getTotalPersons();
    }

    /**
     * Returns true if the current content of the 'total persons' is different from the value remembered by the most
     * recent {@code rememberTotalPersons()} call.
     */
    public boolean isTotalPersonsChanged() {
        return !lastRememberedTotalPersons.equals(getTotalPersons());
    }

    /**
     * Remembers the content of the 'total number of group' portion of the status bar.
     */
    public void rememberTotalGroups() {
        lastRememberedTotalGroups = getTotalGroups();
    }

    /**
     * Returns true if the current content of the 'total groups' is different from the value remembered by the most
     * recent {@code rememberTotalPersons()} call.
     */
    public boolean isTotalGroupsChanged() {
        return !lastRememberedTotalGroups.equals(getTotalPersons());
    }



```
