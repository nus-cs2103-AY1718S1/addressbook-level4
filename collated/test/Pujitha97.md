# Pujitha97
###### \java\seedu\address\model\person\DateOfBirthTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DateOfBirthTest {
    @Test
    public void isValidDateOfBirth() {
        // invalid name
        assertFalse(DateOfBirth.isValidDateOfBirth("")); // empty string
        assertFalse(DateOfBirth.isValidDateOfBirth(" ")); // spaces only

        // valid name
        assertTrue(DateOfBirth.isValidDateOfBirth("27 01 1997")); // alphabets only
    }
}

```
###### \java\seedu\address\model\person\GenderTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class GenderTest {
    @Test
    public void isValidGender() {
        // invalid Gender
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("hello world"));
        assertFalse(Gender.isValidGender("1234"));
        assertFalse(Gender.isValidGender("MALEEEEE"));
        assertFalse(Gender.isValidGender("Male123(*)"));

        // valid name
        assertTrue(Gender.isValidGender("FeMaLe")); // alphabets only
        assertTrue(Gender.isValidGender("MALE")); // alphabets only
        assertTrue(Gender.isValidGender("other")); // alphabets only
    }
}

```
