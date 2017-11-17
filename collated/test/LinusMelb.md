# LinusMelb
###### \java\seedu\address\logic\commands\AddAvatarCommandTest.java
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
    public void checkAvatarCommandSuccess() throws Exception {

        ReadOnlyPerson updatedAvatarPicPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedAvatarPerson = new Person(updatedAvatarPicPerson);
        Avatar avatarPic = new Avatar(VALID_WEB_IMAGE_URL_A);
        updatedAvatarPerson.setAvatarPic(avatarPic);
        AddAvatarCommand updateAvatarPicCommand = prepareCommand(INDEX_FIRST_PERSON, avatarPic);

        String expectedMessage = String.format(MESSAGE_UPDATE_AVATAR_PIC_SUCCESS, updatedAvatarPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedAvatarPicPerson);

        assertCommandSuccess(updateAvatarPicCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void equals() throws Exception {
        final AddAvatarCommand avatarCommand = new AddAvatarCommand(INDEX_FIRST_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A));

        AddAvatarCommand sameValueCommand = new AddAvatarCommand(INDEX_FIRST_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A));

        assertFalse(avatarCommand.equals(null));
        assertFalse(avatarCommand.equals(new ClearCommand()));
        assertFalse(avatarCommand.equals(new AddAvatarCommand(INDEX_SECOND_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A))));
        assertTrue(avatarCommand.equals(new AddAvatarCommand(INDEX_FIRST_PERSON,
                new Avatar(VALID_WEB_IMAGE_URL_A))));
        assertTrue(avatarCommand.equals(sameValueCommand));
        assertTrue(avatarCommand.equals(avatarCommand));


    }

    /**
     * Returns an {@code UpdateAvatarPicCommand} with parameters {@code index} and {@code AvatarPic}
     */
    private AddAvatarCommand prepareCommand(Index index, Avatar avatar) {
        AddAvatarCommand updateAvatarPicCommand = new AddAvatarCommand(index, avatar);
        updateAvatarPicCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return updateAvatarPicCommand;
    }

}

```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
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
###### \java\seedu\address\logic\commands\HomeCommandTest.java
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
###### \java\seedu\address\model\person\AvatarTest.java
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
        assertFalse(Avatar.isValidUrl("images/nonExist.png") == 0);
        assertFalse(Avatar.isValidUrl(INVALID_WEB_IMAGE_URL_A) == 0);
        assertFalse(Avatar.isValidUrl(INVALID_WEB_IMAGE_URL_B) == 0);

        // valid URL
        assertTrue(Avatar.isValidUrl(VALID_WEB_IMAGE_URL_A) == 0);
        assertTrue(Avatar.isValidUrl(VALID_WEB_IMAGE_URL_B) == 0);
    }
}



```
###### \java\seedu\address\ui\BrowserPanelTest.java
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
###### \java\systemtests\AddressBookSystemTest.java
``` java
        assertEquals(expectedSyncStatus, handle.getSyncStatus().split(", ")[1]);
```
###### \java\systemtests\ClearCommandSystemTest.java
``` java
        assertStatusBarUnchangedExceptSyncStatus();
```
