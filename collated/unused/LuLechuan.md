# LuLechuan
###### /Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable.
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday should be in dd/mm/yyyy format, and it should not be blank";

    /*
     * The first character of the birthday must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Birthday(String birthdayNum) throws IllegalValueException {
        //requireNonNull(birthdayNum);
        this.value = birthdayNum;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /BirthdayCommand.java
``` java
/**
 * Adds or updates the birthday of a person identified using it's last displayed index from the address book.
 */
public class BirthdayCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "birthday";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the person's birthday identified by the index number used in the last person listing.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + PREFIX_BIRTHDAY + "BIRTHDAY "
            + "Example: " + COMMAND_WORD + " 1" + " b/" + "12/02/1993";

    public static final String MESSAGE_UPDATE_PERSON_BIRTHDAY_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private final Birthday birthday;

    public BirthdayCommand(Index targetIndex, Birthday birthday) {
        this.targetIndex = targetIndex;
        this.birthday = birthday;
    }

    /**
     * Adds or Updates a Person's birthday
     */
    private Person updatePersonBirthday(ReadOnlyPerson personToUpdateBirthday, Birthday birthday) {
        Name name = personToUpdateBirthday.getName();
        Phone phone = personToUpdateBirthday.getPhone();
        Email email = personToUpdateBirthday.getEmail();
        Address address = personToUpdateBirthday.getAddress();
        Set<Tag> tags = personToUpdateBirthday.getTags();

        Person personUpdated = new Person(name, phone, email, address, birthday, tags);

        return personUpdated;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdateBirthday = lastShownList.get(targetIndex.getZeroBased());
        Person personUpdated = updatePersonBirthday(personToUpdateBirthday, birthday);
        //System.out.println(personUpdated.getBirthday() + " " + personUpdated.getName());

        try {
            model.updatePerson(personToUpdateBirthday, personUpdated);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_BIRTHDAY_SUCCESS, personUpdated));
    }
}
```
###### /BirthdayCommandParser.java
``` java
/**
 * Parses input arguments and creates a new BirthDayCommand object
 */
public class BirthdayCommandParser implements Parser<BirthdayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayCommand
     * and returns a BirthdayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());
            String birthdayInput = st.nextToken();
            String prefix = birthdayInput.substring(0, 2);

            if (!prefix.equals(PREFIX_BIRTHDAY.getPrefix())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE));
            }

            Birthday birthday = new Birthday(birthdayInput.substring(2));
            return new BirthdayCommand(index, birthday);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /BirthdayCommandTest.java
``` java
public class BirthdayCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_updateBirthdaySuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withBirthday("29/02/1996").build();

        Birthday birthday = new Birthday("29/02/1996");
        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, birthday);

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_UPDATE_PERSON_BIRTHDAY_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code BirthdayCommand} with the parameters {@code index + Birthday}.
     */
    private BirthdayCommand prepareCommand(Index index, Birthday birthday) {
        BirthdayCommand command = new BirthdayCommand(index, birthday);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        public void addPersonAndUpdateBirthday(ReadOnlyPerson person) throws IllegalValueException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonBirthdayChanged extends BirthdayCommandTest.ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPersonAndUpdateBirthday(ReadOnlyPerson person) throws IllegalValueException {
            personsAdded.add(new Person(person));
            Person personToUpdate = personsAdded.get(0);
            personToUpdate.setBirthday(new Birthday("29/02/1996"));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
