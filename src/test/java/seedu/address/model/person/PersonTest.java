package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author Jeremy
public class PersonTest {

    @Test
    public void testTagProperty() {
        Person personOne = new PersonBuilder().withName("Tester").withTags("Friend").build();
        Person personTwo = new PersonBuilder().withName("Jane").withTags("Family").build();

        //Same UniqueTagList -> Returns True
        assertTrue(personOne.tagProperty().equals(personOne.tagProperty()));

        //Different UniqueTagList -> Returns False
        assertFalse(personOne.tagProperty().equals(personTwo.tagProperty()));
    }

    @Test
    public void testPersonHash() {
        Person personOne = new PersonBuilder().withName("Tester").build();
        Person personTwo = new PersonBuilder().withName("Jane").build();

        int firstPersonHash = personOne.hashCode();
        int secondPersonHash = personTwo.hashCode();

        // Same person object has same hash
        assertTrue(firstPersonHash == firstPersonHash);

        // Different person object has different hash
        assertFalse(firstPersonHash == secondPersonHash);
    }


}
