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

public class SortCommandTest {

    private LogicManager logic;
    private Model model;

    private Person alice = new PersonBuilder().withName("Alice").withEmail("alice@example.com").build();
    private Person bob = new PersonBuilder().withName("Bob").withEmail("bob@example.com").build();
    private Person charlie = new PersonBuilder().withName("Charlie").withEmail("charlie@example.com").build();
    private Person zack = new PersonBuilder().withName("Zack").withEmail("zack@example.com").build();

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
