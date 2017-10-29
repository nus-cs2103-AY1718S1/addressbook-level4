package seedu.address.google;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;
import com.google.api.services.people.v1.model.Source;

import seedu.address.google.SyncTable;
import seedu.address.model.person.ReadOnlyPerson;

public class Synchronise {

    private static com.google.api.services.people.v1.PeopleService client;
    private static SyncTable syncTable;

    public Synchronise (com.google.api.services.people.v1.PeopleService client, SyncTable syncTable) {
        this.client = client;
        this.syncTable = syncTable;
    }

    /**Uploads AddressBook contacts to Google Contacts
     * TODO: Prevent adding of duplicates
     */
    private static void exportContacts (List<ReadOnlyPerson> personList) throws IOException {
        for (ReadOnlyPerson person : personList) {
            if (!syncTable.containsKey(person)) {
                Person contactToCreate = new Person();
                List<Name> name = new ArrayList<Name>();
                List<EmailAddress> email = new ArrayList<EmailAddress>();
                List<Address> address = new ArrayList<Address>();
                List<PhoneNumber> phone = new ArrayList<PhoneNumber>();
                name.add(new Name().setGivenName(person.getName().fullName));

                if (!person.getEmail().value.equals("No Email")) {
                    email.add(new EmailAddress().setValue(person.getEmail().value));
                    contactToCreate.setEmailAddresses(email);
                }

                if (!person.getAddress().value.equals("No Address")) {
                    address.add(new Address().setFormattedValue(person.getAddress().value));
                    contactToCreate.setAddresses(address);
                }

                if (!person.getPhone().value.equals("No Phone Number")) {
                    phone.add(new PhoneNumber().setValue(person.getPhone().value));
                    contactToCreate.setPhoneNumbers(phone);
                }

                contactToCreate.setNames(name);

                Person createdContact = client.people().createContact(contactToCreate).execute();

                String id = getId(createdContact);

                syncTable.put(person, id);

                FileOutputStream fileOutputStream = new FileOutputStream("myMap.whateverExtension");
                ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);

                objectOutputStream.writeObject(syncTable.getMap());
                objectOutputStream.close();
            } else {
                System.out.println("YAY");
            }
        }
    }

    /**Gets Google contacts
     */

    private static void importContacts () throws IOException {
        ListConnectionsResponse response = client.people().connections().list("people/me")
                .setPersonFields("names,emailAddresses,addresses,phoneNumbers")
                .execute();
        List<Person> connections = response.getConnections();

        for (Person person: connections) {
            Name name = person.getNames().get(0);
            System.out.println(name.getGivenName());
            System.out.println(name.getMetadata().getSource().getUpdateTime());
            List<PhoneNumber> phone = person.getPhoneNumbers();
            if (phone != null) {
                PhoneNumber number = phone.get(0);
                System.out.println(number);
            }
        }

    }

    private static String getId (Person person ) {
        Source meta = person.getMetadata().getSources().get(0);
        return meta.getId();
    }
    public static void execute(List<ReadOnlyPerson> personList) throws IOException {
        exportContacts(personList);
//        importContacts();
    }
}
