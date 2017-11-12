package seedu.address.logic;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.ListObserver;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author khooroko
public class CommandTest {
    protected static final String UNEXPECTED_EXECTION = "Execution should not get to this line";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        ListObserver.init(model);
    }

    /**
     * Selects the first person in the current list.
     */
    protected void selectFirstPerson() {
        model.updateSelectedPerson(ListObserver.getCurrentFilteredList().get(INDEX_FIRST_PERSON.getZeroBased()));
    }
}
