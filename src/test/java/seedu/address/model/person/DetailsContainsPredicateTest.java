package seedu.address.model.person;

import org.junit.Test;
import seedu.address.logic.commands.FindCommand.FindDetailDescriptor;
import seedu.address.testutil.FindDetailDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DetailsContainsPredicateTest {

    @Test
    public void equals() {
        FindDetailDescriptor firstPredicateDescriptor = new FindDetailDescriptorBuilder().withName("Alice").build();
        FindDetailDescriptor secondPredicateDescriptor = new FindDetailDescriptorBuilder().withPhone("123456").build();

        DetailsContainsPredicate firstPredicate = new DetailsContainsPredicate(firstPredicateDescriptor);
        DetailsContainsPredicate secondPredicate = new DetailsContainsPredicate(secondPredicateDescriptor);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DetailsContainsPredicate firstPredicateCopy = new DetailsContainsPredicate(firstPredicateDescriptor);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchAllAttributes_returnsTrue() {
        ReadOnlyPerson person = new PersonBuilder().build();

        // same name -> returns true
        FindDetailDescriptor descriptor = new FindDetailDescriptorBuilder().withName(PersonBuilder.DEFAULT_NAME).build();
        DetailsContainsPredicate predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));

        // name is the substring in person name -> returns true
        descriptor = new FindDetailDescriptorBuilder().withName("alic").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));

        // phone and email are substring in person -> returns true
        descriptor = new FindDetailDescriptorBuilder().withPhone("8535").withEmail("ali").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));

        // address is substring in person address -> returns true
        descriptor = new FindDetailDescriptorBuilder().withAddress("jurong WEST").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));

        // tag is substring in person tag -> returns true
        descriptor = new FindDetailDescriptorBuilder().withTags("frie").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_notMatch_returnsFalse() {
        ReadOnlyPerson person = new PersonBuilder().build();

        // different name -> returns false
        FindDetailDescriptor descriptor = new FindDetailDescriptorBuilder().withName("aliv").build();
        DetailsContainsPredicate predicate = new DetailsContainsPredicate(descriptor);
        assertFalse(predicate.test(person));

        // email is matched but phone is different -> returns false
        descriptor = new FindDetailDescriptorBuilder().withPhone("0000").withEmail("ali").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertFalse(predicate.test(person));

        // different address -> returns false
        descriptor = new FindDetailDescriptorBuilder().withAddress("pgp").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertFalse(predicate.test(person));

        // different tag -> returns false
        descriptor = new FindDetailDescriptorBuilder().withTags("family").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertFalse(predicate.test(person));
    }
}
