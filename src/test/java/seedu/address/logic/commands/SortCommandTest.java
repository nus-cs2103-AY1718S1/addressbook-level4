package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

//@@author limshunyong
public class SortCommandTest {

    private LogicManager logic;
    private Model model;

    private Person alice = new PersonBuilder().withName("Alice").build();
    private Person bob = new PersonBuilder().withName("Bob").build();
    private Person charlie = new PersonBuilder().withName("Charlie").build();
    private Person zack = new PersonBuilder().withName("Zack").build();

    @Before
    public void setup() throws Exception {
        AddressBook unsorted = new AddressBook();
        unsorted.addPerson(charlie);
        unsorted.addPerson(bob);
        unsorted.addPerson(zack);
        unsorted.addPerson(alice);

        model = new ModelManager(unsorted, new UserPrefs());
        logic = new LogicManager(model);
    }

    @Test
    public void execute_sort_successful() throws Exception {
        AddressBook sorted = new AddressBook();

        sorted.addPerson(alice);
        sorted.addPerson(bob);
        sorted.addPerson(charlie);
        sorted.addPerson(zack);

        CommandResult r = logic.execute("sort");
        assertEquals(SortCommand.MESSAGE_SUCCESS, r.feedbackToUser);

        assertEquals(sorted, model.getAddressBook());

    }
}
