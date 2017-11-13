# ritchielq-reuse
###### \java\seedu\address\logic\commands\NusmodsCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for NusmodsCommand.
 */
public class NusmodsCommandTest {

    public static final String MODULE_CS2103T = "CS2103T[TUT]=T5";
    private Model model;

    @Before
    public void setUp() {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
    }

    @After
    public void recovery() {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void execute_addModuleUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().withNusModules(MODULE_CS2103T).build();
        NusmodsDescriptor descriptor = new NusmodsDescriptorBuilder()
                .withType(VALID_TYPE_ADD).withModuleCode(VALID_MODULE_CS2103T)
                .withTutorial(VALID_LESSONSLOT_T5).build();
        NusmodsCommand nusmodsCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(NusmodsCommand.MESSAGE_NUSMODS_SUCCESS,
                editedPerson.getName().fullName, editedPerson.getNusModules());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new EmailManager());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nusmodsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder().withNusModules(MODULE_CS2103T).build();
        NusmodsCommand nusmodsCommand = prepareCommand(INDEX_FIRST_PERSON, new NusmodsDescriptorBuilder()
                .withType(VALID_TYPE_ADD).withModuleCode(VALID_MODULE_CS2103T)
                .withTutorial(VALID_LESSONSLOT_T5).build());

        String expectedMessage = String.format(NusmodsCommand.MESSAGE_NUSMODS_SUCCESS,
                editedPerson.getName().fullName, editedPerson.getNusModules());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new EmailManager());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(nusmodsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws IOException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        NusmodsDescriptor descriptor = new NusmodsDescriptorBuilder()
                .withType(VALID_TYPE_ADD).withModuleCode(VALID_MODULE_CS2103T).build();
        NusmodsCommand nusmodsCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(nusmodsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws IOException {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NusmodsCommand nusmodsCommand = prepareCommand(outOfBoundIndex, new NusmodsDescriptorBuilder()
                .withType(VALID_TYPE_ADD).withModuleCode(VALID_MODULE_CS2103T).build());

        assertCommandFailure(nusmodsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns an {@code NusmodsCommand} with parameters {@code index} and {@code descriptor}
     */
    private NusmodsCommand prepareCommand(Index index, NusmodsDescriptor descriptor) {
        NusmodsCommand nusmodsCommand = new NusmodsCommand(index, descriptor);
        nusmodsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return nusmodsCommand;
    }

}
```
###### \java\seedu\address\logic\parser\NusmodsCommandParserTest.java
``` java
public class NusmodsCommandParserTest {

    private static final String TYPE_EMPTY = " " + PREFIX_TYPE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NusmodsCommand.MESSAGE_USAGE);

    private NusmodsCommandParser parser = new NusmodsCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TYPE_DESC_ADD + MODULE_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", NusmodsCommand.MESSAGE_INVALID_TYPE);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TYPE_DESC_ADD + MODULE_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TYPE_DESC_ADD + MODULE_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC + MODULE_DESC_CS2103T,
                NusmodsCommand.MESSAGE_INVALID_TYPE); // Invalid type
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_ADD + MODULE_DESC_CS2103T + LESSON_DESC_CS2103T;

        NusmodsDescriptor descriptor = new NusmodsDescriptorBuilder().withType(VALID_TYPE_ADD)
                .withModuleCode(VALID_MODULE_CS2103T).withTutorial(VALID_LESSONSLOT_T5).build();
        NusmodsCommand expectedCommand = new NusmodsCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_ADD + MODULE_DESC_CS2103T;

        NusmodsDescriptor descriptor = new NusmodsDescriptorBuilder().withType(VALID_TYPE_ADD)
                .withModuleCode(VALID_MODULE_CS2103T).build();
        NusmodsCommand expectedCommand = new NusmodsCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\testutil\NusmodsDescriptorBuilder.java
``` java
/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class NusmodsDescriptorBuilder {

    private NusmodsDescriptor descriptor;

    public NusmodsDescriptorBuilder() {
        descriptor = new NusmodsDescriptor();
    }

    /**
     * Sets the {@code type} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withType(String type) {
        descriptor.setType(type);
        return this;
    }

    /**
     * Sets the {@code moduleCode} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withModuleCode(String moduleCode) {
        descriptor.setModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code designLecture} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withDesignLecture(String designLecture) {
        descriptor.setDesignLecture(designLecture);
        return this;
    }

    /**
     * Sets the {@code laboratory} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withLaboratory(String laboratory) {
        descriptor.setLaboratory(laboratory);
        return this;
    }

    /**
     * Sets the {@code lecture} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withLecture(String lecture) {
        descriptor.setLecture(lecture);
        return this;
    }

    /**
     * Sets the {@code packagedLecture} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withPackagedLecture(String packagedLecture) {
        descriptor.setPackagedLecture(packagedLecture);
        return this;
    }

    /**
     * Sets the {@code packagedTutorial} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withPackagedTutorial(String packagedTutorial) {
        descriptor.setPackagedTutorial(packagedTutorial);
        return this;
    }

    /**
     * Sets the {@code recitation} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withRecitation(String recitation) {
        descriptor.setRecitation(recitation);
        return this;
    }

    /**
     * Sets the {@code sectionalTeaching} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withSectionalTeaching(String sectionalTeaching) {
        descriptor.setSectionalTeaching(sectionalTeaching);
        return this;
    }

    /**
     * Sets the {@code seminar} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withSeminar(String seminar) {
        descriptor.setSeminar(seminar);
        return this;
    }

    /**
     * Sets the {@code tutorial} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withTutorial(String tutorial) {
        descriptor.setTutorial(tutorial);
        return this;
    }

    /**
     * Sets the {@code tutorial2} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withTutorial2(String tutorial2) {
        descriptor.setTutorial2(tutorial2);
        return this;
    }

    /**
     * Sets the {@code tutorial3} of the {@code NusmodsDescriptor} that we are building.
     */
    public NusmodsDescriptorBuilder withTutorial3(String tutorial3) {
        descriptor.setTutorial3(tutorial3);
        return this;
    }

    public NusmodsDescriptor build() {
        return descriptor;
    }
}
```
