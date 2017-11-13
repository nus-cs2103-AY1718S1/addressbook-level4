# danielbrzn
###### \java\guitests\guihandles\ProgressWindowHandle.java
``` java
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.stage.Stage;
import seedu.address.ui.ProgressWindow;

/**
 * A handle to the {@code ProgressWindow} of the application.
 */
public class ProgressWindowHandle extends StageHandle {

    public static final String PROGRESS_WINDOW_TITLE = "Import Progress";

    public ProgressWindowHandle(Stage progWindowStage) {
        super(progWindowStage);
    }

    /**
     * Returns true if a progress window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(PROGRESS_WINDOW_TITLE);
    }

    /**
     * Returns the progress of the currently shown progress window.
     */
    public ReadOnlyDoubleProperty getProgress(ProgressWindow progWindow) {
        return progWindow.getProgress();
    }
}
```
###### \java\seedu\address\commons\util\GoogleUtilTest.java
``` java
package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.GooglePersonBuilder;
import seedu.address.testutil.PersonBuilder;

public class GoogleUtilTest {

    @Test
    public void convertFromGooglePerson_success() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().build();
        seedu.address.model.person.Person typicalAddressBookPerson = new PersonBuilder().withTags("Google")
                .withTwitter("").withInstagram("").build();
        assertEquals(typicalAddressBookPerson, GoogleUtil.convertPerson(typicalPerson));

    }

    @Test
    public void convertFromGooglePersonWithNonCanonicalPhoneNumber_success() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().withPhone(GooglePersonBuilder.DEFAULT_PHONE).build();
        seedu.address.model.person.Person typicalAddressBookPerson = new PersonBuilder().withTags("Google")
                .withTwitter("").withInstagram("").build();
        assertEquals(typicalAddressBookPerson, GoogleUtil.convertPerson(typicalPerson));

    }
}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void addAlias(String alias, String command) {
            fail("This method should not be called.");
        }

        @Override
        public String getAlias(String alias) {
            fail("This method should not be called.");
            return null;
        }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void resetAlias(HashMap<String, String> prevAliasMap) {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public UserPrefs getUserPrefs() {
            fail("This method should not be called.");
            return null;
        }

```
###### \java\seedu\address\logic\commands\AliasCommandTest.java
``` java

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AliasCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addAlias_success() throws Exception {

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addAlias("a", "add");

        assertCommandSuccess(prepareCommand("a", "add"), model,
                String.format(AliasCommand.MESSAGE_SUCCESS, "a", "add"),
                expectedModel);
    }

    @Test
    public void execute_addAliasForInvalidCommand_failure() throws Exception {

        assertCommandFailure(prepareCommand("a", "abcde"), model, AliasCommand.MESSAGE_INVALID_COMMAND);
    }

    @Test
    public void execute_addAliasOverride_success() throws Exception {

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addAlias("a", "add");

        model.addAlias("a", "list");

        assertCommandSuccess(prepareCommand("a", "add"), model,
                String.format(AliasCommand.MESSAGE_OVERRIDE, "a", "add", "list"),
                expectedModel);

    }

    @Test
    public void execute_addRestrictedAlias_failure() throws Exception {

        assertCommandFailure(prepareCommand("add", "add"), model,
                AliasCommand.MESSAGE_RESTRICTED_ALIAS);
    }

    @Test
    public void equals() {
        AliasCommand aliasAddCommand = new AliasCommand("a", "add");
        AliasCommand aliasFindCommand = new AliasCommand("f", "find");

        // same object -> returns true
        assertTrue(aliasAddCommand.equals(aliasAddCommand));

        // same values -> returns true;
        AliasCommand aliasAddCommandCopy = new AliasCommand("a", "add");
        assertTrue(aliasAddCommand.equals(aliasAddCommandCopy));

        // different types -> returns false
        assertFalse(aliasAddCommand.equals(1));

        // null -> returns false
        assertFalse(aliasAddCommand.equals(null));

        // different commands -> returns false
        assertFalse(aliasAddCommand.equals(aliasFindCommand));
    }

    /**
     * Returns an {@code AliasCommand} with parameters {@code alias} and {@code command}
     */
    private AliasCommand prepareCommand(String alias, String command) {
        AliasCommand aliasCommand = new AliasCommand(alias, command);
        aliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return aliasCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.testfx.api.FxToolkit;

import com.google.api.services.people.v1.model.Person;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.GooglePersonBuilder;
import seedu.address.testutil.PersonBuilder;

public class ImportCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_importContactsWithoutGoogleAuthorization_success() {
        ImportCommand importCommand = prepareCommand("Google");
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_IN_PROGRESS, expectedModel);
    }

    @Test
    public void execute_importContactsFromEmptyList_noPersonAdded() {
        ImportCommand importCommand = prepareCommand("Google");
        importCommand.importContacts(new ArrayList<Person>());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_importContactsFromNonEmptyList_personsAdded()
            throws DuplicatePersonException, InterruptedException, TimeoutException {
        ImportCommand importCommand = prepareCommand("Google");
        ArrayList<Person> googlePersonList = new ArrayList<Person>();
        GooglePersonBuilder googlePerson = new GooglePersonBuilder();
        googlePersonList.add(googlePerson.build());
        // Initialise a primary stage and the FxToolkit for importContacts to run
        FxToolkit.registerPrimaryStage();
        importCommand.importContacts(googlePersonList);
        PersonBuilder person = new PersonBuilder();
        expectedModel.addPerson(person.withInstagram("").withTwitter("")
                .withTags(GooglePersonBuilder.DEFAULT_TAGS).build());
        assertEquals(expectedModel, model);
    }

    @Test
    public void equals() {
        ImportCommand importGoogleCommand = new ImportCommand("Google");
        ImportCommand importAppleCommand = new ImportCommand("Apple");

        // same object -> returns true
        assertTrue(importGoogleCommand.equals(importGoogleCommand));

        // same values -> returns true;
        ImportCommand importGoogleCommandCopy = new ImportCommand("Google");
        assertTrue(importGoogleCommand.equals(importGoogleCommandCopy));

        // same values but different case -> returns true;
        ImportCommand importGoogleCommandCase = new ImportCommand("GoOgLe");
        assertTrue(importGoogleCommand.equals(importGoogleCommandCase));

        // different types -> returns false
        assertFalse(importGoogleCommand.equals(1));

        // null -> returns false
        assertFalse(importGoogleCommand.equals(null));

        // different commands -> returns false
        assertFalse(importGoogleCommand.equals(importAppleCommand));
    }

    /**
     * Returns an {@code ImportCommand} with parameters {@code service}
     */
    public ImportCommand prepareCommand(String service) {
        ImportCommand importCommand = new ImportCommand(service);
        importCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return importCommand;
    }

}
```
###### \java\seedu\address\logic\commands\LocationCommandTest.java
``` java

package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowUrlEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code LocationCommand}.
 */

public class LocationCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundsIndex.getOneBased());
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundsIndex.getOneBased());
    }

    @Test
    public void equals() {
        LocationCommand showLocationOfFirstCommand = new LocationCommand(INDEX_FIRST_PERSON);
        LocationCommand showLocationOfSecondCommand = new LocationCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(showLocationOfFirstCommand.equals(showLocationOfFirstCommand));

        // same values -> returns true
        LocationCommand showLocationOfFirstCommandCopy = new LocationCommand(INDEX_FIRST_PERSON);
        assertTrue(showLocationOfFirstCommand.equals(showLocationOfFirstCommandCopy));

        // different types -> returns false
        assertFalse(showLocationOfFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showLocationOfFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(showLocationOfFirstCommand.equals(showLocationOfSecondCommand));
    }


    /**
     * Executes a {@code LocationCommand} with the given {@code index}, and checks that {@code ShowUrlEvent}
     * is raised with the correct URL.
     */
    private void assertExecutionSuccess(Index index) {
        LocationCommand locationCommand = prepareCommand(index);

        try {
            CommandResult commandResult = locationCommand.execute();
            assertEquals(String.format(LocationCommand.MESSAGE_SUCCESS, model.getFilteredPersonList()
                    .get(index.getZeroBased()).getName()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        String url = LocationCommand.GOOGLE_MAPS_URL_PREFIX + locationCommand.parseAddressForUrl(
                model.getFilteredPersonList().get(index.getZeroBased()).getAddress());

        ShowUrlEvent lastEvent = (ShowUrlEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(url, lastEvent.getUrl());
    }

    /**
     * Executes a {@code LocationCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        LocationCommand locationCommand = prepareCommand(index);

        try {
            locationCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code LocationCommand} with parameters {@code index}.
     */
    private LocationCommand prepareCommand(Index index) {
        LocationCommand locationCommand = new LocationCommand(index);
        locationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locationCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // no email
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail("").withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withTwitter(VALID_TWITTER_AMY).withInstagram(VALID_INSTAGRAM_AMY)
                .withDisplayPic(VALID_DISPLAYPIC).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
            + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY  + TWITTER_DESC_AMY + INSTAGRAM_DESC_AMY,
                new AddCommand(expectedPerson));

        // no address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress("").withBirthday(VALID_BIRTHDAY_AMY)
                .withTwitter(VALID_TWITTER_AMY).withInstagram(VALID_INSTAGRAM_AMY)
                .withDisplayPic(VALID_DISPLAYPIC).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY  + TWITTER_DESC_AMY + INSTAGRAM_DESC_AMY,
                new AddCommand(expectedPerson));

        // no birthday
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withBirthday("")
                .withTwitter(VALID_TWITTER_AMY).withInstagram(VALID_INSTAGRAM_AMY)
                .withDisplayPic(VALID_DISPLAYPIC).withTags().build();

        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + TWITTER_DESC_AMY + INSTAGRAM_DESC_AMY
                + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AliasCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AliasCommand;

public class AliasCommandParserTest {
    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        final String alias = "a";
        final String command = "add";
        assertParseSuccess(parser, alias + " "
                + command, new AliasCommand(alias, command));
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AliasCommand.MESSAGE_USAGE);

        // missing the command to map alias to
        assertParseFailure(parser, "b", expectedMessage);

        // missing everything
        assertParseFailure(parser, AliasCommand.COMMAND_WORD, expectedMessage);

    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        final String service = "google";
        assertParseSuccess(parser, service, new ImportCommand(service));
    }

    @Test
    public void parse_missingField_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE);

        // missing the service to import from
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_tooManyFields_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE);

        // too many arguments
        assertParseFailure(parser, "google icloud", expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\LocationCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.LocationCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class LocationCommandParserTest {

    private LocationCommandParser parser = new LocationCommandParser();

    @Test
    public void parse_validArgs_returnsLocationCommand() {
        assertParseSuccess(parser, "1", new LocationCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

```
###### \java\seedu\address\testutil\GooglePersonBuilder.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.Birthday;
import com.google.api.services.people.v1.model.Date;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.PhoneNumber;


/**
 * A utility class to help with building Google Person objects.
 */
public class GooglePersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BIRTHDAY_DAY = "3";
    public static final String DEFAULT_BIRTHDAY_MONTH = "7";
    public static final String DEFAULT_BIRTHDAY_YEAR = "1990";
    public static final String DEFAULT_TAGS = "Google";

    private com.google.api.services.people.v1.model.Person person;


    public GooglePersonBuilder() {

        ArrayList<com.google.api.services.people.v1.model.Name> names = new ArrayList<>();
        ArrayList<PhoneNumber> phone = new ArrayList<>();
        ArrayList<EmailAddress> emails = new ArrayList<>();
        ArrayList<com.google.api.services.people.v1.model.Address> addresses = new ArrayList<>();
        ArrayList<Birthday> birthday = new ArrayList<>();

        names.add(new com.google.api.services.people.v1.model.Name().setDisplayName(DEFAULT_NAME));
        phone.add(new PhoneNumber().setCanonicalForm(DEFAULT_PHONE));
        emails.add(new EmailAddress().setValue(DEFAULT_EMAIL));
        addresses.add(new com.google.api.services.people.v1.model.Address().setFormattedValue(DEFAULT_ADDRESS));
        Birthday convertedBirthday = new Birthday();
        convertedBirthday.setDate(new Date().setDay(Integer.parseInt(DEFAULT_BIRTHDAY_DAY))
                .setMonth(Integer.parseInt(DEFAULT_BIRTHDAY_MONTH))
                .setYear(Integer.parseInt(DEFAULT_BIRTHDAY_YEAR)));
        birthday.add(convertedBirthday);

        this.person = new com.google.api.services.people.v1.model.Person().setNames(names).setAddresses(addresses)
                .setEmailAddresses(emails).setPhoneNumbers(phone).setBirthdays(birthday);

    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withName(String name) {
        List<Name> names = new ArrayList<>();
        names.add(new com.google.api.services.people.v1.model.Name().setDisplayName(name));
        this.person.setNames(names);

        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withAddress(String address) {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new com.google.api.services.people.v1.model.Address().setFormattedValue(address));
        this.person.setAddresses(addresses);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withPhone(String phone) {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber().setValue(phone));
        this.person.setPhoneNumbers(phoneNumbers);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withEmail(String email) {
        List<EmailAddress> emails = new ArrayList<>();
        emails.add(new EmailAddress().setValue(email));
        this.person.setEmailAddresses(emails);
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withBirthday(String birthday) {
        List<Birthday> birthdays = new ArrayList<>();
        birthdays.add(new com.google.api.services.people.v1.model.Birthday().setText(birthday));
        this.person.setBirthdays(birthdays);

        return this;
    }

    public com.google.api.services.people.v1.model.Person build() {
        return this.person;
    }

}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the Twitter {@code UserName} of the {@code Person} that we are building.
     */
    public PersonBuilder withTwitter(String twitterUser) {
        try {
            this.person.setTwitterName(new UserName(twitterUser));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("twitter username is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the Instagram {@code UserName} of the {@code Person} that we are building.
     */
    public PersonBuilder withInstagram(String instagramUser) {
        try {
            this.person.setInstagramName(new UserName(instagramUser));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("instagram username is expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\ui\ProgressWindowTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.ProgressWindowHandle;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;

public class ProgressWindowTest extends GuiUnitTest {

    private static final double HALF_PROGRESS = 0.5;
    private ProgressWindow progWindow;
    private ProgressWindowHandle progWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> progWindow = new ProgressWindow(new SimpleDoubleProperty(HALF_PROGRESS)));
        Stage progWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(progWindow.getRoot().getScene()));
        FxToolkit.showStage();
        progWindowHandle = new ProgressWindowHandle(progWindowStage);
    }

    @Test
    public void display() {
        ReadOnlyDoubleProperty shownProgress = progWindowHandle.getProgress(progWindow);
        assertEquals(shownProgress.doubleValue(), new SimpleDoubleProperty(HALF_PROGRESS).doubleValue(), 0);
    }
}
```
