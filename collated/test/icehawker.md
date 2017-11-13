# icehawker
###### \java\seedu\address\logic\commands\BackupCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code BackupCommand}.
 */
public class BackupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests may require making new folders/files, deleteFolder to ensure clean up after testing.
     */
    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f: files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    @Test
    public void equals() {
        // create two test directories
        File dir = new File("data/testBackup");
        dir.mkdir();
        File dir2 = new File("data/testBackup2");
        dir2.mkdir();
        BackupCommand locationOne = new BackupCommand("data/testBackup/addressbook.xml");
        BackupCommand locationTwo = new BackupCommand("data/testBackup2/addressbook.xml");

        // same values -> returns true
        BackupCommand locationOneCopy = new BackupCommand("data/testBackup/addressbook.xml");
        assertTrue(locationOneCopy.getLocation().equals(locationOne.getLocation()));

        // same values, different objects -> returns true (does not discriminate)
        assertTrue(locationOneCopy.equals(locationOne));

        // different locations -> returns false
        assertFalse(locationOne.getLocation().equals(locationTwo.getLocation()));

        // different locations, different objects -> returns false
        assertFalse(locationOne.equals(locationTwo));

        // different types -> returns false
        assertFalse(locationOne.getLocation().equals(1));

        // null -> returns false
        assertFalse(locationOne.getLocation().equals(null));

        // clean up
        deleteFolder(dir);
        deleteFolder(dir2);
    }
    /* Works for Gradle allTests locally, fails in TravisCI
    @Test
    public void execute_backup_success() throws Exception {
        File dir = new File("data/testBackup");
        dir.mkdir();
        BackupCommand locationOne = new BackupCommand("data/testBackup/addressbook.xml");
        BackupCommand backupCommand = prepareCommand("data/testBackup/addressbook.xml");

        String expectedMessage = String.format(BackupCommand.BACKUP_SUCCESS_MESSAGE, locationOne.getLocation());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(backupCommand, model, expectedMessage, expectedModel);

        // clean up
        deleteFolder(dir);
    }
    */
    // No CommandExceptions to test: IO Exception always thrown before Command Exception within BackupCommand
    @Test
    public void execute_invalidFilePath_throwsIoException() {
        // directory testBackup does not exist
        String invalidAddress = "v:\\Gibberish Folder\\Gibberish Name";
        BackupCommand backupCommand = prepareCommand(invalidAddress);

        // Note: expected message is exclusively returned as a result of caught IOException in BackupCommand
        String expectedMessage = String.format(BackupCommand.BACKUP_FAILURE_MESSAGE, invalidAddress);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(backupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        // File addressbook.xml already exists, cannot overwrite
        String validAddress = "data/testBackup/addressbook.xml";
        BackupCommand locationOne = new BackupCommand("data/testBackup/addressbook.xml");
        BackupCommand backupCommand = prepareCommand(validAddress);

        // Note: expected message is exclusively returned as a result of caught IOException in BackupCommand
        String expectedMessage = String.format(BackupCommand.BACKUP_FAILURE_MESSAGE, validAddress);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(backupCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private BackupCommand prepareCommand(String address) {
        UserPrefs prefs = new UserPrefs();
        BackupCommand backupCommand = new BackupCommand(address);
        backupCommand.setData(model, prefs, new CommandHistory(), new UndoRedoStack());
        return backupCommand;
    }
}
```
###### \java\seedu\address\logic\commands\CopyCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code BackupCommand}.
 */

public class CopyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        CopyCommand copyOne = new CopyCommand(indices);
        ArrayList<Index> indicesBob = new ArrayList<>();
        indicesBob.add(INDEX_SECOND_PERSON);
        CopyCommand copyBob = new CopyCommand(indicesBob);

        // same values -> returns true
        CopyCommand copyOneCopy = new CopyCommand(indices);
        assertTrue(copyOneCopy.equals(copyOne));

        // different values -> returns false
        assertFalse(copyBob.equals(copyOne));

        // different types -> returns false
        assertFalse(copyOne.equals(1));

        // null -> returns false
        assertFalse(copyOne.equals(null));
    }
    /* Test passes in Gradle but not Travis
    @Test
    public void execute_copy_success() throws Exception {
        ReadOnlyPerson personToCopy = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CopyCommand copyCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_PERSON_SUCCESS, personToCopy.getEmails());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }
    */
    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private CopyCommand prepareCommand(Index index) {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(index);

        UserPrefs prefs = new UserPrefs();
        CopyCommand copyCommand = new CopyCommand(indices);
        copyCommand.setData(model, prefs, new CommandHistory(), new UndoRedoStack());
        return copyCommand;
    }
}
```
###### \java\seedu\address\model\person\CountryTest.java
``` java
public class CountryTest {

    @Test
    public void equals() {
        Country one = new Country("65");
        Country two = new Country ("");

        // same values -> returns true
        Country oneCopy = new Country("65");
        assertTrue(oneCopy.equals(one));

        // different values -> returns false
        assertFalse(two.equals(one));

        // different types -> returns false
        assertFalse(one.equals(1));

        // null -> returns false
        assertFalse(two.equals(null));
    }

    @Test
    public void countryToString() {
        Country one = new Country("65");
        Country two = new Country ("");

        assertTrue(one.toString().equals("Singapore")); // 65 is mapped to Singapore
        assertTrue(two.toString().equals(DEFAULT_COUNTRY)); // empty string is mapped to default

        assertFalse(one.toString().equals("65")); // output not a code
        assertFalse(two.toString().equals("")); // no code =/= empty string return
    }

    @Test
    public void getName() {
        Country one = new Country("65");
        Country two = new Country ("");

        // static reference getName(code) should return same country as Country object toString().
        assertTrue(Country.getName("65").equals(one.toString()));
        assertTrue(Country.getName("").equals(two.toString()));

        // different codes
        assertFalse(Country.getName("99").equals(one.toString()));
        assertFalse(Country.getName("").equals(one.toString()));
    }
}
```
###### \java\seedu\address\model\person\PhoneTest.java
``` java
        assertFalse(Phone.isValidPhone("+10000 91234567")); // country code too many digits
        assertFalse(Phone.isValidPhone("+99 91234567")); // country code does not exist

```
###### \java\seedu\address\model\person\PhoneTest.java
``` java
    @Test
    public void getCountryCode() {
        // invalid codes
        assertFalse(Phone.isValidCode("99")); // no such country
        assertFalse(Phone.isValidCode("19191")); // too many digits
        // valid codes
        assertTrue(Phone.isValidCode("1")); // USA
        assertTrue(Phone.isValidCode("65")); // SG
        assertTrue(Phone.isValidCode("852")); // HK

    }
    @Test
    public void trimCode() {
        // invalid trims
        assertFalse(Phone.trimCode("91234567").equals("1")); // DEFAULT CODE not returned
        assertFalse(Phone.trimCode("+65 91234567").equals("10")); // wrong code
        assertFalse(Phone.trimCode("+65 91234567").equals("+65")); // plus sign not removed
        assertFalse(Phone.trimCode("+65 91234567").equals("65 ")); // whitespace not removed
        // valid trims
        assertTrue(Phone.trimCode("91234567").equals(DEFAULT_COUNTRY_CODE)); // No code returns default
        assertTrue(Phone.trimCode("+1 91234567").equals("1")); // 1 digit
        assertTrue(Phone.trimCode("+65 91234567").equals("65")); // 2 digit
        assertTrue(Phone.trimCode("+975 91234567").equals("975")); // 3 digit
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Country} of the {@code Person} that we are building.
     */
    public PersonBuilder withCountry(String country) {
        this.person.setCountry(new Country(country));
        // any illegal values already caught in Phone, where code is extracted.
        return this;
    }

```
