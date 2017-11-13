# stan789
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
    @Test
    public void emptyEmail_failure() {
        Person person = new PersonBuilder().withName("ALICE").withEmail(null).build();
        try {
            model.addPerson(person);
        } catch (DuplicatePersonException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertExecutionFailure(lastPersonIndex, EmailCommand.MESSAGE_EMPTY_EMAIL);
    }

```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        EmailCommandStub emailCommand = prepareCommand(index);

        try {
            CommandResult commandResult = emailCommand.execute();
            assertEquals(EmailCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

    }

```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
    /**
     * Executes a {@code EmailCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        EmailCommandStub emailCommand = prepareCommand(index);

        try {
            emailCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
    @Test
    public void execute_exportVCardFile_importSuccess() throws IOException {

        String fileLocation = "src/test/data/VCardFileTest/OneBook.vcf";
        String extension = "vcf";
        String fileName = "OneBook";

        ExportCommand exportCommand = prepareCommand(fileLocation, fileName, extension);
        expectedModel.exportFile(fileLocation, extension);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileName);
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
    @Test
    public void execute_exportCsvFile_importSuccess() throws IOException {

        String fileLocation = "src/test/data/VCardFileTest/OneBook.csv";
        String extension = "csv";
        String fileName = "OneBook";

        ExportCommand exportCommand = prepareCommand(fileLocation, fileName, extension);
        expectedModel.exportFile(fileLocation, extension);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileName);
        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
    @After
    public void tearDown() {
        File fileVcard = new File("src/test/data/VCardFileTest/OneBook.vcf");
        fileVcard.delete();
        File fileCsv = new File("src/test/data/VCardFileTest/OneBook.csv");
        fileCsv.delete();
    }

```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
    @Test
    public void execute_exportEmptyString_exportFailure() throws IOException {

        String fileLocation = "";
        String extension = "";
        String fileName = "";

        ExportCommand exportCommand = prepareCommand(fileLocation, fileName, extension);
        assertCommandFailure(exportCommand, model, MESSAGE_WRITE_ERROR);
    }

```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
    @Test(expected = IOException.class)
    public void emptyFileDirectoryVcardIoException() throws IOException {
        model.exportFile("", "vcf");

    }

```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
    @Test(expected = IOException.class)
    public void emptyFileDirectoryCsvIoException() throws IOException {
        model.exportFile("", "csv");

    }

```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_importVCardFile_importSuccess() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts.vcf");
        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts());
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_importVCardFileWithOneTag_importSuccess() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_with_one_tag.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts());
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_importVCardFileWithoutFullName_importSuccess() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_with_empty_fullName.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts());
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_importVCardFileWithDuplicate_importSuccessWithWarning() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_with_duplicate.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts())
                + ImportCommand.MESSAGE_DUPLICATE;
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_importVCardFileWithInfoInvalidFormat_importSuccessWithWarning() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_with_illegal_value.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts())
                + ImportCommand.MESSAGE_ILLEGAL_VALUE;
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_importVCardFileWithInfoInvalidFormatAndDuplicate_importSuccessWithWarning() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_duplicate_and_illegal_value.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        ImportAnalysis importAnalysis = new ImportAnalysis();
        expectedModel.importFile(fileLocation, importAnalysis);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importAnalysis.getNumContacts())
                + ImportCommand.MESSAGE_DUPLICATE + ImportCommand.MESSAGE_ILLEGAL_VALUE;
        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test(expected = IOException.class)
    public void testFileWithInvalidFormat() throws IOException {
        ImportAnalysis importAnalysis = new ImportAnalysis();
        model.importFile(Paths.get("src/test/data/VCardFileTest/contacts_example.vcf"), importAnalysis);
    }

```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_importEmptyVCardFile_importFailure() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/empty.vcf");
        ImportCommand importCommand = prepareCommand(fileLocation);
        String expectedMessage = ImportCommand.MESSAGE_EMPTY_FILE;
        assertCommandFailure(importCommand, model, expectedMessage);
    }

```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_importInvalidVCardFileNoBeginCard_importFailure() throws IOException {

        Path fileLocation = Paths.get("src/test/data/VCardFileTest/contacts_without_begin.vcf");

        ImportCommand importCommand = prepareCommand(fileLocation);
        String expectedMessage = ImportCommand.MESSAGE_FILE_INVALID;
        assertCommandFailure(importCommand, model, expectedMessage);
    }

```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
    @Test
    public void execute_sortByName_sortSuccess() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand(SORT_NAME);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.executeSort(SORT_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
    @Test
    public void execute_sortByName_sortSuccessBinMode() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand_binMode(SORT_NAME);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.setBinDisplay();
        expectedModel.executeBinSort(SORT_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
    @Test
    public void execute_sortByEmail_sortSuccess() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand(SORT_EMAIL);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.executeSort(SORT_EMAIL);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
    @Test
    public void execute_sortByEmail_sortSuccessBinMode() throws EmptyAddressBookException {
        SortCommand sortCommand = prepareCommand_binMode(SORT_EMAIL);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.setBinDisplay();
        expectedModel.executeBinSort(SORT_EMAIL);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
    @Test(expected = EmptyAddressBookException.class)
    public void testEmptyAddressBookException() throws EmptyAddressBookException {
        model.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        model.executeSort(SORT_NAME);

    }

```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsExportCommand() {
        String extension = ExportCommandParser.VCF_EXTENSION;
        String location = "src/test/data/VCardFileTest/new.vcf";
        String fileName = "new";
        assertParseSuccess(parser, location, new ExportCommand(location , fileName, extension));

    }

```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgs_throwsParserException() {
        String location = "hshhsvdsjbdjsbd";
        assertParseFailure(parser, location, ExportCommandParser.INVALID_DIRECTORY);

    }

```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgsWrongFileFormat_throwsParserException() {
        String location = "src/test/data/VCardFileTest/new.txt";
        assertParseFailure(parser, location, ExportCommandParser.INVALID_EXTENSION);

    }

```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgsWrongFileNameFormat_throwsParserException() {
        String location = "src/test/data/VCardFileTest/!new.txt";
        assertParseFailure(parser, location, ExportCommandParser.INVALID_FILE_NAME);

    }

```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
    @Test
    public void parse_emptyArgs_throwsParserException() {
        String location = "";
        assertParseFailure(parser, location,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

    }

}
```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsImportCommand() {
        String location = "src/test/data/VCardFileTest/contacts.vcf";
        Path fileLocation = Paths.get(location);
        assertParseSuccess(parser, location, new ImportCommand(fileLocation));

    }

```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgsInvalidDirectory_throwsParserException() {
        String location = "hshhsvdsjbdjsbd";
        assertParseFailure(parser, location, ImportCommandParser.NO_FILE_FOUND);

    }

```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgsCreateWrongFileFormat_throwsParserException() {
        String location = "src/test/data/ConfigUtilTest/TypicalConfig.json";
        assertParseFailure(parser, location, ImportCommandParser.FILE_WRONG_FORMAT);

    }

```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
    @Test
    public void parse_emptyArgs_throwsParserException() {
        String location = "";
        assertParseFailure(parser, location,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

    }


}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "name", new SortCommand(SortCommand.SORT_NAME));
        assertParseSuccess(parser, "email", new SortCommand(SortCommand.SORT_EMAIL));
    }

```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\storage\ImportVCardFileTest.java
``` java
    @Test
    public void getPersonFromFile_withoutBegin_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_without_begin.vcf"), importAnalysis);
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

```
###### \java\seedu\address\storage\ImportVCardFileTest.java
``` java
    @Test
    public void getPersonFromFile_withoutEnd_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_without_end.vcf"), importAnalysis);
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

```
###### \java\seedu\address\storage\ImportVCardFileTest.java
``` java
    @Test
    public void getPersonFromFile_someWithoutEnd_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_some_without_end.vcf"), importAnalysis);
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

```
###### \java\seedu\address\storage\ImportVCardFileTest.java
``` java
    @Test
    public void getPersonFromFile_someWithoutBegin_throwsIoException() throws IOException {
        ImportVCardFile importVCardFile = new ImportVCardFile(
                Paths.get("src/test/data/VCardFileTest/contacts_some_without_begin.vcf"), importAnalysis);
        thrown.expect(IOException.class);
        importVCardFile.getPersonFromFile();

    }

}
```
###### \java\systemtests\ImportCommandSystemTest.java
``` java
    @Test
    public void importing() {

        Model expectedModel = getModel();
        Model model = getModel();

        /* Case: export VCard file and import the file back to OneBook -> import successful */
        String command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/OneBook.vcf";
        executeCommand(ExportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/OneBook.vcf");
        executeCommand(ClearCommand.COMMAND_WORD);
        model.resetData(new AddressBookData(model.getAddressBook(), new RecycleBin()));
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        ImportAnalysis importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/OneBook.vcf"), importAnalysis);
        } catch (IOException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        assertCommandSuccess(command, importAnalysis.getNumContacts(), expectedModel);
        assertEquals(model, expectedModel);

        /* Case: import VCard file with valid format -> import successful */
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf";
        importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"), importAnalysis);
        } catch (IOException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        assertCommandSuccess(command, importAnalysis.getNumContacts(), expectedModel);

        /* Case: import VCard file with valid format to empty address book -> import successful */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf";
        importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"), importAnalysis);
        } catch (IOException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        assertCommandSuccess(command, importAnalysis.getNumContacts(), expectedModel);

        /* Case: import VCard file with name only -> import successful */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBookData());
        command = ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/name_only.vcf";
        importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/name_only.vcf"), importAnalysis);
        } catch (IOException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
        assertCommandSuccess(command, importAnalysis.getNumContacts(), expectedModel);
    }

```
###### \java\systemtests\ImportCommandSystemTest.java
``` java
    @After
    public void tearDown() {
        File file = new File("src/test/data/VCardFileTest/OneBook.vcf");
        file.delete();
    }

```
###### \java\systemtests\SortCommandSystemTest.java
``` java
    @Test
    public void sort() {
        Model expectedModel = getModel();

        /* Case: import to existing address book and sort -> sorted  */
        executeCommand(ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf");
        String command = SortCommand.COMMAND_WORD + " " + SORT_NAME;
        ImportAnalysis importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"), importAnalysis);
            expectedModel.executeSort(SORT_NAME);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        } catch (IOException e) {
            assertCommandFailure(command, MESSAGE_FILE_INVALID, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: sort the list by name -> sorted */
        command = SortCommand.COMMAND_WORD + " " + SORT_NAME;
        try {
            expectedModel.executeSort(SORT_NAME);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: sort the list by email -> sorted */
        command = SortCommand.COMMAND_WORD + " " + SORT_EMAIL;
        try {
            expectedModel.executeSort(SORT_EMAIL);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: invalid arguments -> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), expectedModel);

        /* Case: invalid arguments (no sort type)-> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), expectedModel);

        /* Case: sort from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        assertCommandFailure(SortCommand.COMMAND_WORD + " " + SORT_NAME,
                MESSAGE_NO_PERSON_TO_SORT, expectedModel);

    }

```
