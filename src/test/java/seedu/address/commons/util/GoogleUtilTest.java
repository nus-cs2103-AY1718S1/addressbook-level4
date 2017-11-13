//@@author danielbrzn
package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.GooglePersonBuilder;
import seedu.address.testutil.PersonBuilder;

public class GoogleUtilTest {

    @Test
    public void convertFromGooglePersonWithAllFields_success() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().build();
        seedu.address.model.person.Person typicalAddressBookPerson = new PersonBuilder().withTags("Google")
                .withTwitter("").withInstagram("").build();
        assertEquals(typicalAddressBookPerson, GoogleUtil.convertPerson(typicalPerson));

    }

    @Test
    public void convertFromGooglePersonWithNonCanonicalPhoneNumber_success() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().withPhone(GooglePersonBuilder.DEFAULT_PHONE).build();
        seedu.address.model.person.Person typicalAddressBookPerson = new PersonBuilder().withTags("Google")
                .withTwitter("").withInstagram("").build();
        assertEquals(typicalAddressBookPerson, GoogleUtil.convertPerson(typicalPerson));

    }

    @Test
    public void convertFromGooglePersonWithInvalidPhone_returnsNull() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().withPhone("").build();
        assertEquals(null, GoogleUtil.convertPerson(typicalPerson));

    }

    @Test
    public void convertFromGooglePersonWithInvalidName_returnsNull() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().withName("").build();
        assertEquals(null, GoogleUtil.convertPerson(typicalPerson));

    }
    @Test
    public void convertFromGooglePersonWithNoPhoneList_returnsNull() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().noPhone().build();
        assertEquals(null, GoogleUtil.convertPerson(typicalPerson));

    }

    @Test
    public void convertFromGooglePersonWithNoEmail_success() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().withEmail("").build();
        seedu.address.model.person.Person typicalAddressBookPerson = new PersonBuilder().withTags("Google")
                .withTwitter("").withInstagram("").withEmail("").build();
        assertEquals(typicalAddressBookPerson, GoogleUtil.convertPerson(typicalPerson));

    }
    @Test
    public void convertFromGooglePersonWithNoNameList_returnsNull() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().noName().build();
        assertEquals(null, GoogleUtil.convertPerson(typicalPerson));

    }
    @Test
    public void convertFromGooglePersonWithNoEmailList_success() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().noEmail().build();
        seedu.address.model.person.Person typicalAddressBookPerson = new PersonBuilder().withTags("Google")
                .withTwitter("").withInstagram("").withEmail("").build();
        assertEquals(typicalAddressBookPerson, GoogleUtil.convertPerson(typicalPerson));

    }

    @Test
    public void convertFromGooglePersonWithNoBirthdayList_success() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().noBirthday().build();
        seedu.address.model.person.Person typicalAddressBookPerson = new PersonBuilder().withTags("Google")
                .withTwitter("").withInstagram("").withBirthday("").build();
        assertEquals(typicalAddressBookPerson, GoogleUtil.convertPerson(typicalPerson));

    }

    @Test
    public void convertFromGooglePersonWithNoAddressList_success() throws IllegalValueException {
        Person typicalPerson = new GooglePersonBuilder().noAddress().build();
        seedu.address.model.person.Person typicalAddressBookPerson = new PersonBuilder().withTags("Google")
                .withTwitter("").withInstagram("").withAddress("").build();
        assertEquals(typicalAddressBookPerson, GoogleUtil.convertPerson(typicalPerson));

    }
}
