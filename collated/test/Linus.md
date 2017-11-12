# Linus
###### /java/systemtests/ClearCommandSystemTest.java
``` java
        assertStatusBarUnchangedExceptSyncStatus();
```
###### /java/systemtests/ClearCommandSystemTest.java
``` java

    }
}
```
###### /java/systemtests/AddressBookSystemTest.java
``` java
        assertEquals(expectedSyncStatus, handle.getSyncStatus().split(", ")[1]);
```
###### /java/systemtests/AddressBookSystemTest.java
``` java
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        try {
            assertEquals("", getCommandBox().getInput());
            assertEquals("", getResultDisplay().getText());
            assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());
            assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE),
                    getBrowserPanel().getLoadedUrl());
            assertEquals("./" + testApp.getStorageSaveLocation(), getStatusBarFooter().getSaveLocation());
            assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
```
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);

        URL expectedPersonUrl = MainApp.class.getResource(FXML_FILE_FOLDER + BROWSER_PAGE);;

        waitUntilBrowserLoaded(browserPanelHandle);

        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
```
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
}
```
###### /java/seedu/address/logic/commands/HomeCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class HomeCommandTest {

    private Model model;
    private Model expectedModel;
    private HomeCommand homeCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        homeCommand = new HomeCommand();
        homeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(homeCommand, model, HomeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(homeCommand, model, HomeCommand.MESSAGE_SUCCESS, expectedModel);
    }
}

```
###### /java/seedu/address/logic/commands/HomeCommandTest.java
``` java

```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String INVALID_WEB_IMAGE_URL_A =
            "INVALID_IMAGE_URL";
    public static final String INVALID_WEB_IMAGE_URL_B =
            "http://invalid.com/invalid.jpg";
    public static final String VALID_WEB_IMAGE_URL_A =
            "http://188.166.212.235/storage/avatars/default-M.png";
    public static final String VALID_WEB_IMAGE_URL_B =
            "http://188.166.212.235/storage/avatars/default-F.png";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
```
###### /java/seedu/address/logic/commands/AddAvatarCommandTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddAvatarCommand.MESSAGE_UPDATE_AVATAR_PIC_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_IMAGE_URL_A;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

public class AddAvatarCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_webUrlUnfilteredList_success() throws Exception {
        ReadOnlyPerson updatedAvatarPicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedAvatarPerson = new Person(updatedAvatarPicPerson);
        Avatar avatarPic = new Avatar();
        updatedAvatarPerson.setAvatarPic(avatarPic);
        AddAvatarCommand updateAvatarPicCommand = prepareCommand(INDEX_FIRST_PERSON, avatarPic);

        String expectedMessage = String.format(MESSAGE_UPDATE_AVATAR_PIC_SUCCESS, updatedAvatarPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedAvatarPicPerson);

        assertCommandSuccess(updateAvatarPicCommand, model, expectedMessage, expectedModel);

        System.out.println(model.getFilteredPersonList().get(0).getAvatarPic().source);
    }

    @Test
    public void equals() throws Exception {
        final AddAvatarCommand standardCommand = new AddAvatarCommand(INDEX_FIRST_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A));

        // same values -> returns true
        AddAvatarCommand commandWithSameValues = new AddAvatarCommand(INDEX_FIRST_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A));

        // returns true
        assertTrue(standardCommand.equals(new AddAvatarCommand(INDEX_FIRST_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A))));

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddAvatarCommand(INDEX_SECOND_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A))));

    }

    /**
     * Returns an {@code UpdateAvatarPicCommand} with parameters {@code index} and {@code AvatarPic}
     */
    private AddAvatarCommand prepareCommand(Index index, Avatar avatar) {
        AddAvatarCommand updateAvatarPicCommand = new AddAvatarCommand(index, avatar);
        updateAvatarPicCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return updateAvatarPicCommand;
    }

    private String urlToPath(String url) {
        return url.substring(url.indexOf("AvatarPics"));
    }
}

```
###### /java/seedu/address/model/person/AvatarTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEB_IMAGE_URL_A;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEB_IMAGE_URL_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_IMAGE_URL_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_IMAGE_URL_B;

import org.junit.Test;

public class AvatarTest {
    @Test
    public void isValidUrl() {
        // 0 stands for valid image URL
        // -1 stands for invalid image URL
        // -2 stands for expired image URL

        // invalid URL
        assertFalse(Avatar.isValidUrl("") == 0); // empty string
        assertFalse(Avatar.isValidUrl("images/fail.png") == 0);
        assertFalse(Avatar.isValidUrl(INVALID_WEB_IMAGE_URL_A) == 0);
        assertFalse(Avatar.isValidUrl(INVALID_WEB_IMAGE_URL_B) == 0);

        // valid URL
        System.out.println(Avatar.isValidUrl(VALID_WEB_IMAGE_URL_A));
        assertTrue(Avatar.isValidUrl(VALID_WEB_IMAGE_URL_A) == 0);
        assertTrue(Avatar.isValidUrl(VALID_WEB_IMAGE_URL_B) == 0);
    }
}



```
