package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.InvalidGooglePersonException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;


//@@author philemontan
/**
 * Unit testing for the GooglePersonConverterUtil class
 * Both classes and their composed classes in each package have the same name;
 * Handling: seedu.address.model.person.Person will be imported, while Google classes have to be fully qualified.
 */
public class GooglePersonConverterUtilTest {
    /**
     * (Single) Google Person -> DoC Person Conversion Tests (IMPORT)
     */

    //Negative Testing: Required DoC Person parameters set to null
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void convertGooglePersonWithNullNameException() throws InvalidGooglePersonException {
        com.google.api.services.people.v1.model.Person testPerson =
                getGooglePerson(null, "12345678", "blk001 Test Ave",
                "test@gmail.com");
        thrown.expect(InvalidGooglePersonException.class);
        GooglePersonConverterUtil.singleGoogleToDocPersonConversion(testPerson);
    }

    @Test
    public void convertGooglePersonWithNullPhoneException() throws InvalidGooglePersonException {
        com.google.api.services.people.v1.model.Person testPerson =
                getGooglePerson("Testee", null, "blk001 Test Ave", "test@gmail.com");
        thrown.expect(InvalidGooglePersonException.class);
        GooglePersonConverterUtil.singleGoogleToDocPersonConversion(testPerson);
    }

    @Test
    public void convertGooglePersonWithNullEmail() throws InvalidGooglePersonException {
        com.google.api.services.people.v1.model.Person testPerson =
                getGooglePerson("Testee", "12345678", "blk001 Test Ave", null);
        seedu.address.model.person.Person convertedPerson =
                GooglePersonConverterUtil.singleGoogleToDocPersonConversion(testPerson);
        assertEquals(convertedPerson.getEmail().toString(), GooglePersonConverterUtil.DEFAULT_EMAIL);
    }

    @Test
    public void convertGooglePersonWithNullAddress() throws InvalidGooglePersonException {
        com.google.api.services.people.v1.model.Person testPerson =
                getGooglePerson("Testee", "12345678", null, "test@gmail.com");
        seedu.address.model.person.Person convertedPerson =
                GooglePersonConverterUtil.singleGoogleToDocPersonConversion(testPerson);
        assertEquals(convertedPerson.getAddress().toString(), GooglePersonConverterUtil.DEFAULT_ADDRESS);
    }

    /**
     * (Single) DoC Person -> Google Person Conversion Tests
     */

    //Positive Testing: Test for successful conversion
    @Test
    public void convertGenericDocPerson() {
        Person docPerson = new PersonBuilder().withName("John Doe").withAddress("Elm Street")
                .withEmail("jon@gmail.com").withPhone("01234567").withTags("friends").build();
        com.google.api.services.people.v1.model.Person expectedGooglePerson =
                getGooglePerson("John Doe", "01234567", "Elm Street", "jon@gmail.com");

        com.google.api.services.people.v1.model.Person convertedGooglePerson =
                GooglePersonConverterUtil.singleDocToGooglePersonConversion(docPerson);
        assertEquals(expectedGooglePerson, convertedGooglePerson);
    }

    /**
     *  (List) DoC Person -> Google Person Conversion Tests
     */
    @Test
    public void convertListGenericDoCPerson() {
        ReadOnlyPerson docPerson = new PersonBuilder().withName("John Doe").withAddress("Elm Street")
                .withEmail("jon@gmail.com").withPhone("01234567").withTags("friends").build();
        com.google.api.services.people.v1.model.Person expectedGooglePerson =
                getGooglePerson("John Doe", "01234567", "Elm Street", "jon@gmail.com");

        List<com.google.api.services.people.v1.model.Person> expectedGooglePersonList = new ArrayList<>();
        expectedGooglePersonList.add(expectedGooglePerson);

        List<ReadOnlyPerson> docPersons = new ArrayList<>();
        docPersons.add(docPerson);

        List<com.google.api.services.people.v1.model.Person> actualGooglePersonList;
        actualGooglePersonList = GooglePersonConverterUtil.listDocToGooglePersonConversion(docPersons);

        assertEquals(expectedGooglePersonList, actualGooglePersonList);
    }

    /**
     * (List) Google Person -> DoC Person Conversion Tests
     */
    @Test
    public void convertListGooglePerson() {
        //test google person
        com.google.api.services.people.v1.model.Person testGooglePerson =
                getGooglePerson("John Doe", "01234567", "Elm Street", "jon@gmail.com");
        //test doc person
        Person testDocPerson = new PersonBuilder().withName("John Doe").withAddress("Elm Street")
                .withEmail("jon@gmail.com").withPhone("01234567").withTags("friends").build();

        //test google person list
        ArrayList<com.google.api.services.people.v1.model.Person> testGooglePersonList = new ArrayList<>();
        testGooglePersonList.add(testGooglePerson);

        //expected doc person list
        List<Person> expectedDocPersonList = new ArrayList<>();
        expectedDocPersonList.add(testDocPerson);

        //actual doc person list
        List<Person> actualDocPersonList = GooglePersonConverterUtil
                .listGoogleToDoCPersonConversion(testGooglePersonList);

        assertEquals(expectedDocPersonList, actualDocPersonList);
    }

    /**
     * Person Number and Name processing test to fit DoC regex
     */
    @Test
    public void testNameProcessingRegexFitting() {
        String invalidNameString = "?394johnD_oE*&^%#%^*()]";
        String expectedProcessedName = " 394johnD oE           ";

        try {
            assertEquals(expectedProcessedName, GooglePersonConverterUtil.processName(invalidNameString));
        } catch (InvalidGooglePersonException E) {
            assert true : "Unexpected behaviour from GooglePersonConverterUtil";
        }
    }

    @Test
    public void testNumberProcessingRegexFitting() {
        String invalidNumberString = "23k4n&((#???!sfsd+++6590000000";
        String expectedNumberString = "23490000000";

        try {
            assertEquals(expectedNumberString, GooglePersonConverterUtil.processNumber(invalidNumberString));
        } catch (InvalidGooglePersonException E) {
            assert true : "Unexpected behaviour from GooglePersonConverterUtil";
        }
    }

    /**
     * Creates a GooglePerson from input parameters, to support testing
     * Made with a default UserDefined for
     * @param name
     * @param number
     * @param address
     * @param email
     * @return the desired Google Person
     */
    public com.google.api.services.people.v1.model.Person getGooglePerson(String name, String number, String address,
                                                                          String email) {
        ArrayList<com.google.api.services.people.v1.model.Name> names = new ArrayList<>();
        ArrayList<com.google.api.services.people.v1.model.PhoneNumber> phones = new ArrayList<>();
        ArrayList<com.google.api.services.people.v1.model.Address> addresses = new ArrayList<>();
        ArrayList<com.google.api.services.people.v1.model.EmailAddress> emails = new ArrayList<>();
        com.google.api.services.people.v1.model.Person personToReturn =
                new com.google.api.services.people.v1.model.Person();

        ArrayList<com.google.api.services.people.v1.model.UserDefined> tags = new ArrayList<>();

        tags.add(new com.google.api.services.people.v1.model.UserDefined().setKey("tag").setValue("friends"));
        names.add(new com.google.api.services.people.v1.model.Name().setGivenName(name).setDisplayName(name));
        phones.add(new com.google.api.services.people.v1.model.PhoneNumber().setValue(number));
        addresses.add(new com.google.api.services.people.v1.model.Address().setFormattedValue(address));
        emails.add(new com.google.api.services.people.v1.model.EmailAddress().setValue(email));

        personToReturn.setNames(names);
        personToReturn.setAddresses(addresses);
        personToReturn.setEmailAddresses(emails);
        personToReturn.setPhoneNumbers(phones);
        personToReturn.setUserDefined(tags);
        return personToReturn;
    }
}
