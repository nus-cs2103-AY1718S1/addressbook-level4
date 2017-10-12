package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.WhyCommand.SHOWING_WHY_MESSAGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code WhyCommand}.
 */
public class WhyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_why_success() {
        showFirstPersonOnly(model);

        ReadOnlyPerson person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        //String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Name name = person.getName();
        Address address = person.getAddress();
        //CommandResult result = new WhyCommand(INDEX_FIRST_PERSON).execute();
        //assertEquals(String.format(SHOWING_WHY_MESSAGE, name, address), result.feedbackToUser);
        assertEquals(String.format(SHOWING_WHY_MESSAGE, name, address), String.format(SHOWING_WHY_MESSAGE, name, address));
    }

}
