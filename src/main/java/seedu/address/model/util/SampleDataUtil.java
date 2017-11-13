package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.place.Address;
import seedu.address.model.place.Name;
import seedu.address.model.place.Phone;
import seedu.address.model.place.Place;
import seedu.address.model.place.PostalCode;
import seedu.address.model.place.Website;
import seedu.address.model.place.exceptions.DuplicatePlaceException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Place[] getSamplePlaces() {
        try {
            return new Place[] {
                //@@author huyuanrong
                new Place(new Name("Marina Bay Sands"), new Phone("66888868"),
                        new Website("http://www.marinabaysands.com/"),
                    new Address("10 Bayfront Avenue"), new PostalCode("018956"),
                    getTagSet("attractions")),
                new Place(new Name("Singapore Flyer"), new Phone("6333311"),
                        new Website("http://www.singaporeflyer.com/"),
                    new Address("30 Raffles Ave"), new PostalCode("039803"),
                    getTagSet("attractions")),
                new Place(new Name("Singapore Zoo"), new Phone("62693411"),
                        new Website("http://www.wrs.com.sg/"),
                    new Address("80 Mandai Lake Rd"), new PostalCode("729826"),
                    getTagSet("attractions")),
                new Place(new Name("Beni Singapore"), new Phone("91593177"),
                        new Website("http://www.beni-sg.com/"),
                    new Address("333A Orchard Rd, #02-37"), new PostalCode("238897"),
                    getTagSet("onestar")),
                new Place(new Name("Odette"), new Phone("63850498"),
                        new Website("http://www.odetterestaurant.com/"),
                    new Address("1 Saint Andrew's Rd, #01-04, National Gallery"), new PostalCode("178957"),
                    getTagSet("twostars")),
                new Place(new Name("Joël Robuchon Restaurant"), new Phone("65777888"),
                        new Website("https://www.rwsentosa.com/en"),
                    new Address("26 Sentosa Gateway, Hotel Michael, #01-104 and 105"), new PostalCode("098138"),
                    getTagSet("threestars"))
                    //@@author
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Place samplePlace : getSamplePlaces()) {
                sampleAb.addPlace(samplePlace);
            }
            return sampleAb;
        } catch (DuplicatePlaceException e) {
            throw new AssertionError("sample data cannot contain duplicate places", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
