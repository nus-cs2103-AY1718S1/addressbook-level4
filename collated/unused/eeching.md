# eeching
###### /Gender.java
``` java
public class Gender {

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "must be either a male or female";


    public final String value;

    /**
     * Validates given gender.
     *
     * @throws IllegalValueException if given address string is invalid.
     */

    public Gender() throws IllegalValueException {
        this.value = "";
    }
    public Gender(String gender) throws IllegalValueException {
        if (!(gender.equals("male") || gender.equals("female") || gender.equals(""))) {
            throw new IllegalValueException(MESSAGE_GENDER_CONSTRAINTS);
        }
        this.value = gender;

    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /GenderCommand.java
``` java
public class GenderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "gender";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Specify the person's gender identified by the index number used in the last person listing.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + PREFIX_GENDER + "GENDER"
            + "Example: " + COMMAND_WORD + " 1" + " g/" + "male";

    public static final String MESSAGE_UPDATE_PERSON_GENDER_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private final Gender gender;

    public GenderCommand(Index targetIndex, Gender gender) {
        this.targetIndex = targetIndex;
        this.gender = gender;
    }

    /**
     * Adds or Updates a Person's birthday
     */
    private Person updatePersonGender(ReadOnlyPerson personToUpdateGender, Gender gender) {
        Name name = personToUpdateGender.getName();
        Phone phone = personToUpdateGender.getPhone();
        Email email = personToUpdateGender.getEmail();
        Address address = personToUpdateGender.getAddress();
        Set<Tag> tags = personToUpdateGender.getTags();

        Person personUpdated = new Person(name, phone, email, address, gender, tags);

        return personUpdated;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdateGender = lastShownList.get(targetIndex.getZeroBased());
        Person personUpdated = updatePersonGender(personToUpdateGender, gender);
        //System.out.println(personUpdated.getGender() + " " + personUpdated.getGender());

        try {
            model.updatePerson(personToUpdateGender, personUpdated);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_GENDER_SUCCESS, personUpdated));
    }
}
```
###### /GenderCommandParser.java
``` java
public class GenderCommandParser implements Parser<GenderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenderCommand
     * and returns a GenderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenderCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());
            String genderInput = st.nextToken();
            String prefix = genderInput.substring(0, 2);

            if (!prefix.equals(PREFIX_GENDER.getPrefix())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenderCommand.MESSAGE_USAGE));
            }

            Gender gender = new Gender(genderInput.substring(2));
            return new GenderCommand(index, gender);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenderCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /GenderCommandTest.java
``` java
public class GenderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_updateGenderSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withGender("male").build();

        Gender gender = new Gender("male");
        GenderCommand genderCommand = prepareCommand(INDEX_FIRST_PERSON, gender);

        String expectedMessage = String.format(GenderCommand.MESSAGE_UPDATE_PERSON_GENDER_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(genderCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code GenderCommand} with the parameters {@code index + Gender}.
     */
    private GenderCommand prepareCommand(Index index, Gender gender) {
        GenderCommand command = new GenderCommand(index, gender);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


}
```
