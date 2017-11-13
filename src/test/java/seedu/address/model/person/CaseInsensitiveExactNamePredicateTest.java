//@@author AceCentury

package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.PersonBuilder;

public class CaseInsensitiveExactNamePredicateTest {

    @Test
    public void equals() throws IllegalValueException {
        Name firstName = new Name("John Doe");
        Name secondName = new Name("Jane Dane");

        CaseInsensitiveExactNamePredicate firstPredicate = new CaseInsensitiveExactNamePredicate(firstName);
        CaseInsensitiveExactNamePredicate secondPredicate = new CaseInsensitiveExactNamePredicate(secondName);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CaseInsensitiveExactNamePredicate firstPredicateCopy = new CaseInsensitiveExactNamePredicate(firstName);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different name -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_exactName_returnsTrue() throws IllegalValueException {
        // 1 word lower case input
        CaseInsensitiveExactNamePredicate predicate = new CaseInsensitiveExactNamePredicate(new Name("john"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN").build()));

        // 2 words lower case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("john doe"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN DOE").build()));

        // 1 word upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOHN"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN").build()));

        // 2 words upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOHN DOE"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN DOE").build()));

        // 1 word mixed case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JohN"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOhn").build()));

        // 2 words upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOhN Doe"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN DOE").build()));
    }

    @Test
    public void test_notExactName_returnsFalse() throws IllegalValueException {
        // 1 word lower case input
        CaseInsensitiveExactNamePredicate predicate = new CaseInsensitiveExactNamePredicate(new Name("john"));
        assertFalse(predicate.test(new PersonBuilder().withName("johnn").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("john doe").build()));

        // 2 words lower case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("john doe"));
        assertFalse(predicate.test(new PersonBuilder().withName("john").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("doe").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("johnn doe").build()));

        // 1 word upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOHN"));

        assertFalse(predicate.test(new PersonBuilder().withName("JOHNN").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("JOHN DOE").build()));

        // 2 words upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOHN DOE"));

        assertFalse(predicate.test(new PersonBuilder().withName("JOHN").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("DOE").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("johnn doe").build()));

        // 1 word mixed case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("John"));

        assertFalse(predicate.test(new PersonBuilder().withName("Johnn").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("john doe").build()));

        // 2 words mixed case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("John Doe"));

        assertFalse(predicate.test(new PersonBuilder().withName("John").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("doe").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("johnn doe").build()));
    }
}
