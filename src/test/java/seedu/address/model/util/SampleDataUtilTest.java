package seedu.address.model.util;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import org.junit.Test;

import seedu.address.model.parcel.Address;
import seedu.address.model.parcel.ArticleNumber;
import seedu.address.model.parcel.Email;
import seedu.address.model.parcel.Name;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.Phone;

public class SampleDataUtilTest {

    @Test
    public void getSampleParcels() throws Exception {
        Parcel[] parcels = SampleDataUtil.getSampleParcels();
        Parcel[] expectedParcels = new Parcel[]{
            new Parcel(new ArticleNumber("RR999999999SG"), new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 29 Lor 30 Geylang, #06-40 s398362"), getTagSet("friends")),
            new Parcel(new ArticleNumber("RR111111111SG"), new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 326 Serangoon Ave 3, #07-18 S550326"), getTagSet("colleagues",
                    "friends")),
            new Parcel(new ArticleNumber("RR222222222SG"), new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 512 Ang Mo Kio Ave 8, #11-04 s560512"), getTagSet("neighbours")),
            new Parcel(new ArticleNumber("RR123456789SG"), new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43 s558675"),
                    getTagSet("family")),
            new Parcel(new ArticleNumber("RR987654321SG"), new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35 s535070"), getTagSet("classmates")),
            new Parcel(new ArticleNumber("RR123789456SG"), new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31 S389825"), getTagSet("colleagues"))
        };

        assertEquals(parcels.length, expectedParcels.length);
        for (int i = 0; i < parcels.length; i++) {
            testParcelEqualExpectedParcel(parcels[i], expectedParcels[i]);
        }
    }

    public void testParcelEqualExpectedParcel(Parcel parcel, Parcel expectedParcel) {
        assertEquals(parcel, expectedParcel);
    }

}
