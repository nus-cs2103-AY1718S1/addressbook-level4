package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AddressContainsKeywordsSubstrPredicate;
import seedu.address.model.person.EmailContainsKeywordsSubstrPredicate;
import seedu.address.model.person.NameContainsKeywordsSubstrPredicate;
import seedu.address.model.person.PhoneContainsKeywordsSubstrPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class MultiFilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    /*
     * Use various comparsions to verify if equals method has been properly overriden
     */
    public void equals() {
        NameContainsKeywordsSubstrPredicate firstNamePredicate =
                new NameContainsKeywordsSubstrPredicate(Collections.singletonList("first"));
        NameContainsKeywordsSubstrPredicate secondNamePredicate =
                new NameContainsKeywordsSubstrPredicate(Collections.singletonList("second"));

        PhoneContainsKeywordsSubstrPredicate firstPhonePredicate =
                new PhoneContainsKeywordsSubstrPredicate(Collections.singletonList("999"));
        PhoneContainsKeywordsSubstrPredicate secondPhonePredicate =
                new PhoneContainsKeywordsSubstrPredicate(Collections.singletonList("998"));

        EmailContainsKeywordsSubstrPredicate firstEmailPredicate =
                new EmailContainsKeywordsSubstrPredicate(Collections.singletonList("ex@gmail.com"));
        EmailContainsKeywordsSubstrPredicate secondEmailPredicate =
                new EmailContainsKeywordsSubstrPredicate(Collections.singletonList("ed@gmail.com"));

        AddressContainsKeywordsSubstrPredicate firstAddressPredicate =
                new AddressContainsKeywordsSubstrPredicate(Collections.singletonList("example1"));
        AddressContainsKeywordsSubstrPredicate secondAddressPredicate =
                new AddressContainsKeywordsSubstrPredicate(Collections.singletonList("example2"));

        MultiFilterCommand firstFilterCommand = new MultiFilterCommand(
                firstNamePredicate,
                firstPhonePredicate,
                firstEmailPredicate,
                firstAddressPredicate
        );
        MultiFilterCommand firstFilterCommandCopy = new MultiFilterCommand(
                firstNamePredicate,
                firstPhonePredicate,
                firstEmailPredicate,
                firstAddressPredicate
        );
        MultiFilterCommand secondFilterCommand = new MultiFilterCommand(
                secondNamePredicate,
                secondPhonePredicate,
                secondEmailPredicate,
                secondAddressPredicate
        );
        MultiFilterCommand firstNullableCommand = new MultiFilterCommand(
                null,
                firstPhonePredicate,
                firstEmailPredicate,
                null

        );
        MultiFilterCommand secondNullableCommand = new MultiFilterCommand(
                null,
                secondPhonePredicate,
                secondEmailPredicate,
                null

        );

        // same object -> returns true
        assertTrue(secondFilterCommand.equals(secondFilterCommand));

        // same values -> returns true
        assertTrue(firstFilterCommand.equals(firstFilterCommandCopy));

        // different types -> returns false
        assertFalse(firstFilterCommand.equals(1));

        // null -> returns false
        assertFalse(firstFilterCommand.equals(null));

        // different person -> returns false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));

        /* different values -> returns false (also shows that filter command
        /* can compare two null values
         */
        assertFalse(firstNullableCommand.equals(secondNullableCommand));
    }

    @Test
    /*
     * Verifies if comparison is case-insensitive
     */
    public void execute_caseInsensitive_multiplePersonsFound() {
        for (char i = 'a'; i <= 'z'; i++) {
            final String lowercaseStr = i + "";
            final String uppercaseStr = (i + "").toUpperCase();

            // Query address book using lowercase name
            List<ReadOnlyPerson> expectedListLowercase =
                    new ArrayList<>(model.getAddressBook().getPersonList())
                    .stream()
                    .filter(p -> p.getName().toString().toLowerCase().contains(lowercaseStr))
                    .collect(Collectors.toList());

            String expectedMessageLowercase = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                    expectedListLowercase.size());
            MultiFilterCommand command = prepareCommand(lowercaseStr, null, null, null);
            assertCommandSuccess(command, expectedMessageLowercase, expectedListLowercase);

            // Query address book using lowercase name
            List<ReadOnlyPerson> expectedListUppercase =
                    new ArrayList<>(model.getAddressBook().getPersonList())
                            .stream()
                            .filter(p -> p.getName().toString().toUpperCase().contains(uppercaseStr))
                            .collect(Collectors.toList());

            // Query address book using uppercase name
            String expectedMessageUppercase = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                    expectedListUppercase.size());
            command = prepareCommand(uppercaseStr, null, null, null);
            assertCommandSuccess(command, expectedMessageUppercase, expectedListUppercase);

            assertEquals(expectedListLowercase, expectedListUppercase);
        }
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        MultiFilterCommand command = prepareCommand(" ", " ", " ", " ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    /*
     * Verifies that a null predicate will not be used to filter persons
     * i.e. if all fields are null, all persons in the address book are returned
     */
    public void execute_multipleKeywords_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getAddressBook().getPersonList().size());
        MultiFilterCommand command = prepareCommand(null, null, null, null);
        assertCommandSuccess(command, expectedMessage,
                model.getAddressBook().getPersonList());
    }

    @Test
    /*
     * Checks if able to perform a substring filtering on names
     * Match is case-insensitive
     */
    public void execute_matchNameSubstring_multiplePersonsFound() {
        MultiFilterCommand command = prepareCommand("on", null, null, null);

        List<ReadOnlyPerson> expectedList = new ArrayList<>(model.getAddressBook().getPersonList())
                .stream()
                .filter(p -> p.getName().toString().toLowerCase().contains("on"))
                .collect(Collectors.toList());
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, expectedMessage, expectedList);
    }

    @Test
    /*
     * Checks if able to perform a substring filtering on email
     * Match is case-insensitive
     */
    public void execute_matchEmailSubstring_multiplePersonsFound() {
        MultiFilterCommand command = prepareCommand(null, null, "ne", null);
        List<ReadOnlyPerson> expectedList = new ArrayList<>(model.getAddressBook().getPersonList())
                .stream()
                .filter(p -> p.getEmail().toString().toLowerCase().contains("ne"))
                .collect(Collectors.toList());
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, expectedMessage, expectedList);
    }

    @Test
    /*
     * Checks if able to perform a multi-filter search (by name and email)
     * Match is case-insensitive
     */
    public void execute_multiFilterSearch_multiplePersonsFound() {
        MultiFilterCommand command = prepareCommand("a", null, "ali", null);
        List<ReadOnlyPerson> expectedList = new ArrayList<>(model.getAddressBook().getPersonList())
                .stream()
                .filter(p -> p.getName().toString().toLowerCase().contains("a"))
                .filter(p -> p.getEmail().toString().toLowerCase().contains("ali"))
                .collect(Collectors.toList());
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, expectedMessage, expectedList);
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private MultiFilterCommand prepareCommand(String name, String phone, String email, String address) {
        MultiFilterCommand command = new MultiFilterCommand(
                name == null ? null : new NameContainsKeywordsSubstrPredicate(
                        Arrays.asList(name.split("\\s+"))),
                phone == null ? null : new PhoneContainsKeywordsSubstrPredicate(
                        Arrays.asList(phone.split("\\s+"))),
                email == null ? null : new EmailContainsKeywordsSubstrPredicate(
                        Arrays.asList(email.split("\\s+"))),
                address == null ? null : new AddressContainsKeywordsSubstrPredicate(
                        Arrays.asList(address.split("\\s+"))));

        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(
            MultiFilterCommand command,
            String expectedMessage,
            List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
