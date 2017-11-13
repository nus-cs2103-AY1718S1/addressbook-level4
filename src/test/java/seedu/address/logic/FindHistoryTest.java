package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author karrui
public class FindHistoryTest {
    private final Person alice = new PersonBuilder().build();
    private final Person bob = new PersonBuilder().withName("Bob").build();
    private final Person charlie = new PersonBuilder().withName("Charlie").build();
    private FindHistory findHistory;

    @Before
    public void setUp() {
        findHistory = new FindHistory();
    }

    @Test
    public void add() {
        findHistory.add(alice);
        assertEquals(Collections.singletonList(alice), findHistory.getHistory());
    }

    @Test
    public void set() {
        findHistory.add(alice);
        findHistory.set(alice, bob);
        assertEquals(Collections.singletonList(bob), findHistory.getHistory());
    }

    @Test
    public void deletePerson() {
        findHistory.add(alice);
        findHistory.deletePerson(alice);
        assertEquals(Collections.emptyList(), findHistory.getHistory());
    }

    @Test
    public void resetData() {
        LinkedList<ReadOnlyPerson> newData = new LinkedList<>();
        newData.add(alice);
        newData.add(bob);

        findHistory.add(charlie);
        assertEquals(Collections.singletonList(charlie), findHistory.getHistory());
        findHistory.resetData(newData);
        assertEquals(Arrays.asList(alice, bob), findHistory.getHistory());
    }
}
