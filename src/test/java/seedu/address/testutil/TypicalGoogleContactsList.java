//@@author PhuaJunJie
package seedu.address.testutil;

import com.google.api.services.people.v1.model.Person;

/**
 * List of Stub Google contacts and its version of the addressbook contact
 */
public class TypicalGoogleContactsList {
    public static final Person FREDDYGOOGLE = new GoogleContactBuilder("Freddy", "91234567",
            "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildGooglePerson();
    public static final Person MAYGOOGLE = new GoogleContactBuilder("May", "91234567",
            "may", "Simei Blk 15 avenue 21",
            "1234567891011121311").buildGooglePerson();
    public static final Person FREDDYSYNGOOGLE = new GoogleContactBuilder("Freddy", "90000000",
            "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildGooglePerson();

    public static final Person BERNICE = new GoogleContactBuilder("Alice Pauline",
            "85355255", "alice@example.com",
            "123, Jurong West Ave 6, #08-111", null).buildGooglePerson();

    public static final Person BERNICEWITHGOOGLEID = new GoogleContactBuilder("Alice Pauline",
            "85355255", "alice@example.com",
            "123, Jurong West Ave 6, #08-111", "12345678910111213112").buildGooglePerson();

    public static final seedu.address.model.person.Person FREEDYADDRESSBOOK = new GoogleContactBuilder("Freddy",
            "91234567", "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildAddressBookPerson();
    public static final seedu.address.model.person.Person MAYADDRESSBOOK = new GoogleContactBuilder("May",
            "91234567",
            "may@hotmail.com", "Simei Blk 15 avenue 21",
            "1234567891011121311").buildAddressBookPerson();
    public static final seedu.address.model.person.Person FREDDYSYNADDRESSBOOK = new GoogleContactBuilder(
            "Freddy", "90000000", "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "1234567891011121310").buildAddressBookPerson();
    public static final seedu.address.model.person.Person FREEDYNOTGOOGLEADDRESSBOOK = new GoogleContactBuilder(
            "Freddy", "91234567", "freddy@hotmail.com", "Simei Blk 1 avenue 2",
            "").buildAddressBookPerson();
    public static final seedu.address.model.person.Person BERNICEADDRESSBOOK = new GoogleContactBuilder(
            "Alice Pauline", "85355255", "alice@example.com",
            "123, Jurong West Ave 6, #08-111", "12345678910111213112").buildAddressBookPerson();

}
