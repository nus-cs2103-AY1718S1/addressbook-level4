package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.parcel.Address;
import seedu.address.model.parcel.Email;
import seedu.address.model.parcel.Name;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.Phone;
import seedu.address.model.parcel.TrackingNumber;
import seedu.address.model.parcel.exceptions.DuplicateParcelException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Parcel[] getSampleParcels() {
        try {
            return new Parcel[] {
                new Parcel(new TrackingNumber("RR999999999SG"), new Name("Alex Yeoh"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"), new Address("Blk 29 Lor 30 Geylang, #06-40 s398362"),
                        getTagSet("friends")),
                new Parcel(new TrackingNumber("RR111111111SG"), new Name("Bernice Yu"), new Phone("99272758"),
                        new Email("berniceyu@example.com"), new Address("Blk 326 Serangoon Ave 3, #07-18 S550326"),
                        getTagSet("colleagues", "friends")),
                new Parcel(new TrackingNumber("RR222222222SG"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"), new Address("Blk 512 Ang Mo Kio Ave 8, #11-04 s560512"),
                        getTagSet("neighbours")),
                new Parcel(new TrackingNumber("RR123456789SG"), new Name("David Li"), new Phone("91031282"),
                        new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43 "
                        + "s558675"), getTagSet("family")),
                new Parcel(new TrackingNumber("RR987654321SG"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                        new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35 s535070"),
                        getTagSet("classmates")),
                new Parcel(new TrackingNumber("RR123789456SG"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                        new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31 S389825"),
                        getTagSet("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Parcel sampleParcel : getSampleParcels()) {
                sampleAb.addParcel(sampleParcel);
            }
            return sampleAb;
        } catch (DuplicateParcelException e) {
            throw new AssertionError("sample data cannot contain duplicate parcels", e);
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
