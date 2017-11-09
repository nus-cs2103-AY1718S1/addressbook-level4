package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.FindSpecificCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsSpecifiedKeywordsPredicate;
import seedu.address.model.person.EmailContainsSpecifiedKeywordsPredicate;
import seedu.address.model.person.TagContainsSpecifiedKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindSpecificCommand}.
 */
//@@author aver0214
public class FindSpecificCommandTest {
	private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

	@Test
	public void equals() {
		NameContainsKeywordsPredicate firstPredicate =
			new NameContainsKeywordsPredicate(Collections.singletonList("first"));
		NameContainsKeywordsPredicate secondPredicate =
			new NameContainsKeywordsPredicate(Collections.singletonList("second"));

		FindSpecificCommand findFirstCommand = new FindSpecificCommand(firstPredicate);
		FindSpecificCommand findSecondCommand = new FindSpecificCommand(secondPredicate);

		// same object -> returns true
		assertTrue(findFirstCommand.equals(findFirstCommand));

		// same values -> returns true
		FindSpecificCommand findFirstCommandCopy = new FindSpecificCommand(firstPredicate);
		assertTrue(findFirstCommand.equals(findFirstCommandCopy));

		// different types -> returns false
		assertFalse(findFirstCommand.equals(1));

		// null -> returns false
		assertFalse(findFirstCommand.equals(null));

		// different person -> returns false
		assertFalse(findFirstCommand.equals(findSecondCommand));
	}

	@Test
	public void execute_zeroKeywords_InvalidCommandFormant() {
		String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
		FindSpecificCommand command = prepareFindByNameCommand(" ");
		assertCommandSuccess(command, expectedMessage, Collections.emptyList());
	}

	/**
	 * Creates a new FindSpecificCommand using {@code NameContainsKeywordsPredicate}
	 *
	 * @param inputString full string of name(s) to find
	 * @return a new FindSpecificCommand using {@code NameContainsKeywordsPredicate}
	 */
	private FindSpecificCommand prepareFindByNameCommand (String inputString) {
		FindSpecificCommand command = new FindSpecificCommand
			(new NameContainsKeywordsPredicate(Arrays.asList(inputString.split("\\s+"))));
		command.setData(model, new CommandHistory(), new UndoRedoStack());
		return command;
	}

	/**
	 * Asserts that {@code command} is successfully executed, and<br>
	 *     - the command feedback is equal to {@code expectedMessage}<br>
	 *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
	 *     - the {@code AddressBook} in model remains the same after executing the {@code command}
	 */
	private void assertCommandSuccess(FindSpecificCommand command, String expectedMessage,
									  List<ReadOnlyPerson> expectedList) {
		AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
		CommandResult commandResult = command.execute();

		assertEquals(expectedMessage, commandResult.feedbackToUser);
		assertEquals(expectedList, model.getFilteredPersonList());
		assertEquals(expectedAddressBook, model.getAddressBook());
	}

}
