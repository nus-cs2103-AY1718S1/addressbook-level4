package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author lincredibleJC
public class FormClassTest {
    @Test
    public void isValidFormClass() throws Exception {
        // invalid name
        assertFalse(FormClass.isValidFormClass("")); // empty string
        assertFalse(FormClass.isValidFormClass(" ")); // spaces only
        assertFalse(FormClass.isValidFormClass("^")); // only non-alphanumeric characters
        assertFalse(FormClass.isValidFormClass("ClassA*")); // contains non-alphanumeric characters
        assertFalse(FormClass.isValidFormClass("Ability 5")); // multiple words
        // valid name
        assertTrue(FormClass.isValidFormClass("ALevel")); // alphabets only
        assertTrue(FormClass.isValidFormClass("48")); // numbers only Class 48
        assertTrue(FormClass.isValidFormClass("4.8")); // with a '.'
        assertTrue(FormClass.isValidFormClass("4-8")); // with a '-'
        assertTrue(FormClass.isValidFormClass("12s23")); // alphanumeric characters
        assertTrue(FormClass.isValidFormClass("12S23")); // with capital letters
        assertTrue(FormClass.isValidFormClass("Class2F1Ability")); // long names
    }

}
