package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FormClass;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.ParentPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[]{
                new Person(new Name("Alex Yeoh"), new Phone("91234563"),
                        new ParentPhone("97273111"),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                        new FormClass("12SCI23"), new Grades("940.0"), new PostalCode("123456"),
                        new Remark("Represented school for national track meet"), getTagSet("track", "athletics")),
                new Person(new Name("Bernice Yu"), new Phone("97971197"),
                        new ParentPhone("97271231"),
                        new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new FormClass("12SCI23"), new Grades("530.0"), new PostalCode("654321"),
                        new Remark("Woman"), getTagSet("colleagues", "canoeing", "scholarship")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("97129722"),
                        new ParentPhone("97211221"),
                        new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new FormClass("12SCI23"), new Grades("435.0"), new PostalCode("987654"),
                        new Remark("Awesome mate"), getTagSet("studentCouncil")),
                new Person(new Name("David Li"), new Phone("85473617"),
                        new ParentPhone("91113011"),
                        new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("studentCouncil", "needsHelp")),
                new Person(new Name("Irfan Ibrahim"), new Phone("97978789"),
                        new ParentPhone("91231211"),
                        new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                        new FormClass("12SCI23"), new Grades("564.0"), new PostalCode("999999"),
                        new Remark("Indicated preference for joining another CCA"),
                        getTagSet("ScienceTeam", "sailing")),
                new Person(new Name("Roy Balakrishnan"), new Phone("97911624"),
                        new ParentPhone("97112233"),
                        new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                        new FormClass("12SCI23"), new Grades("234.0"), new PostalCode("999666"),
                        new Remark("Mixed ethnicity"), getTagSet("colleagues")),
                new Person(new Name("Lynn Tan"), new Phone("84144141"),
                        new ParentPhone("97771111"),
                        new Email("lynntann@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("dance", "band")),
                new Person(new Name("Mariam Mohamad"), new Phone("85215625"),
                        new ParentPhone("82823131"),
                        new Email("mmohd@example.com"), new Address("Blk 6 Home Gardens Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("dance", "needsHelp")),
                new Person(new Name("Amy Leow"), new Phone("81726490"),
                        new ParentPhone("91919111"),
                        new Email("amyleow@example.com"), new Address("Blk 46 Tame Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("athletics")),
                new Person(new Name("Laura Teh"), new Phone("86542453"),
                        new ParentPhone("97272011"),
                        new Email("laurateh@example.com"), new Address("Blk 45 Cuper Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("565.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("athletics")),
                new Person(new Name("Amanda Sun"), new Phone("95851354"),
                        new ParentPhone("97211211"),
                        new Email("sunamanda@example.com"), new Address("Blk 123 Orchard Gardens Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("785.0"), new PostalCode("676767"),
                        new Remark("Arrogant but is good in studies"), getTagSet("scholarship")),
                new Person(new Name("Jolene Saram"), new Phone("85373543"),
                        new ParentPhone("97271122"),
                        new Email("saramjol@example.com"), new Address("Blk 22 Serangoon Drive 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("895.0"), new PostalCode("676767"),
                        new Remark("Top in class for math"), getTagSet("scienceTeam")),
                new Person(new Name("Laura Li"), new Phone("89345354"),
                        new ParentPhone("91112222"),
                        new Email("lilaura@example.com"), new Address("Blk 42 Tampines Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("545.0"), new PostalCode("676767"),
                        new Remark("Consitent effort given"), getTagSet("studentCouncil")),
                new Person(new Name("Goh Qing Jing"), new Phone("85473617"),
                        new ParentPhone("97272222"),
                        new Email("gohqingqing@example.com"), new Address("Blk 56 Sungei Bedok 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Class clown"), getTagSet("studentCouncil")),
                new Person(new Name("Thia Hon Teng"), new Phone("93455383"),
                        new ParentPhone("91112121"),
                        new Email("hauting@example.com"), new Address("Blk 567 Hope Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("345.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("studentCouncil", "needsHelp")),
                new Person(new Name("Dorothy Thia"), new Phone("93457384"),
                        new ParentPhone("97270021"),
                        new Email("dorothythia@example.com"), new Address("Blk 457 Wizard drive 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("295.0"), new PostalCode("676767"),
                        new Remark("Always blur"), getTagSet("studentCouncil")),
                new Person(new Name("John Doe"), new Phone("95645686"),
                        new ParentPhone("91912120"),
                        new Email("alexyeoh@example.com"), new Address("Blk 7 Ang Mo Kio Street 62"),
                        new FormClass("15APP10"), new Grades("990.0"), new PostalCode("569086 "),
                        new Remark("Developed mobile games for school"), getTagSet("GameDev", "scholarship")),
                new Person(new Name("John Hoe"), new Phone("96575688"),
                        new ParentPhone("97112211"),
                        new Email("alexyeoh@example.com"), new Address("SIS Building 4 Leng Kee Road #03-07"),
                        new FormClass("15APP10"), new Grades("690.0"), new PostalCode("159088"),
                        new Remark("Head of dance CCA"), getTagSet("studentCouncil", "dance", "athletics")),
                new Person(new Name("John Shoe"), new Phone("96576756"),
                        new ParentPhone("97272891"),
                        new Email("alexyeoh@example.com"), new Address("Blk 567 Choa Chu Kang Street 86"),
                        new FormClass("15APP10"), new Grades("960.0"), new PostalCode("640557"),
                        new Remark("Enrolled in the Design Centric Program"), getTagSet("DCP")),
                new Person(new Name("John Lowe"), new Phone("96575628"),
                        new ParentPhone("97271111"),
                        new Email("alexyeoh@example.com"), new Address("Blk 876 Lim Chu Kang Street 92"),
                        new FormClass("15APP10"), new Grades("700.0"), new PostalCode("640545"),
                        new Remark("Air Force regular"), getTagSet("studentCouncil", "flyingClub", "athletics")),
                new Person(new Name("John Know"), new Phone("94151638"),
                        new ParentPhone("97271111"),
                        new Email("alexyeoh@example.com"), new Address("Blk 743 Phua Chu Kang Street 14"),
                        new FormClass("15APP10"), new Grades("900.0"), new PostalCode("640345"),
                        new Remark("Individual champion of AstroChallenge17"), getTagSet("ScienceTeam", "scholarship")),
                new Person(new Name("John Bow"), new Phone("96576866"),
                        new ParentPhone("97273232"),
                        new Email("alexyeoh@example.com"), new Address("Blk 334 Yio Chu Kang Street 25"),
                        new FormClass("15APP10"), new Grades("90.0"), new PostalCode("069541"),
                        new Remark("suffering in studies because of CCA "), getTagSet("Archery", "needsHelp")),
                new Person(new Name("Bruno Mas"), new Phone("97973322"),
                        new ParentPhone("97111011"),
                        new Email("alexyeoh@example.com"), new Address("10 Biopolis Way #03-03/04 CHROMOS"),
                        new FormClass("16MUS20"), new Grades("979.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("studentCouncil", "band", "dance")),
                new Person(new Name("Nick Joe"), new Phone("93452797"),
                        new ParentPhone("97273011"),
                        new Email("alexyeoh@example.com"), new Address("88 Syed Alwi Road #01-02"),
                        new FormClass("16MUS20"), new Grades("936.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("band")),
                new Person(new Name("Chris Mar"), new Phone("97234797"),
                        new ParentPhone("97272312"),
                        new Email("alexyeoh@example.com"), new Address("2 Corporation Road #03-09/11"),
                        new FormClass("16MUS20"), new Grades("1056.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("band")),
                new Person(new Name("Fred Mer"), new Phone("97567832"),
                        new ParentPhone("97271111"),
                        new Email("alexyeoh@example.com"), new Address("3014, Ubi Road 1, #06-40"),
                        new FormClass("16MUS20"), new Grades("750.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("band")),
                new Person(new Name("Camila C"), new Phone("91432143"),
                        new ParentPhone("91123011"),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                        new FormClass("16MUS20"), new Grades("750.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("band", "dance")),
                new Person(new Name("Rick Sanchez"), new Phone("97273131"),
                        new ParentPhone("97070000"),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Washington Street 29, #06-40"),
                        new FormClass("16UNI137"), new Grades("3000.0"), new PostalCode("123456"),
                        new Remark("Projected valedictorian. \n Wrote and performed the hit song \"Get Schwifty\"."),
                        getTagSet("ScienceTeam", "dance", "band", "studentCouncil", "scholarship")),
                new Person(new Name("Morty Smith"), new Phone("94157312"),
                        new ParentPhone("91231231"),
                        new Email("alexyeoh@example.com"), new Address("69 Choa Chu Kang Loop #02-12"),
                        new FormClass("16UNI137"), new Grades("500.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("GameDev", "dance", "band")),
                new Person(new Name("Summer Smith"), new Phone("97971111"),
                        new ParentPhone("92020202"),
                        new Email("alexyeoh@example.com"), new Address("1002 Jalan Bukit Merah #02-04"),
                        new FormClass("16UNI137"), new Grades("700"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("fashion")),
                new Person(new Name("Beth Smith"), new Phone("97972321"),
                        new ParentPhone("91122313"),
                        new Email("alexyeoh@example.com"), new Address("290 Orchard Road #08-03"),
                        new FormClass("16UNI137"), new Grades("1000.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("fashion", "social", "judo")),
                new Person(new Name("Jerry Smith"), new Phone("97983487"),
                        new ParentPhone("97210000"),
                        new Email("alexyeoh@example.com"), new Address("501 Orchard Road #07-02"),
                        new FormClass("16UNI137"), new Grades("300.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("social")),
                new Person(new Name("Jon Sow"), new Phone("97567528"),
                        new ParentPhone("97271211"),
                        new Email("alexyeoh@example.com"), new Address("Winter Street 1, #11-40"),
                        new FormClass("11WES01"), new Grades("500.0"), new PostalCode("242238"),
                        new Remark("Leader of the Class"), getTagSet("fencing", "debate")),
                new Person(new Name("Greg C"), new Phone("97211031"),
                        new ParentPhone("97212311"),
                        new Email("alexyeoh@example.com"), new Address("Kings Street 1, #01-40"),
                        new FormClass("11WES01"), new Grades("400.0"), new PostalCode("354373"),
                        new Remark("Big and Strong"), getTagSet("athletics", "fencing")),
                new Person(new Name("Sandor C"), new Phone("97564654"),
                        new ParentPhone("97122131"),
                        new Email("alexyeoh@example.com"), new Address("Stone Street 1, #21-40"),
                        new FormClass("11WES01"), new Grades("800.0"), new PostalCode("935437"),
                        new Remark("Greg's brother"), getTagSet("athletics", "fencing")),
                new Person(new Name("Cersi Lan"), new Phone("91122990"),
                        new ParentPhone("97210131"),
                        new Email("alexyeoh@example.com"), new Address("Kings Street 1, #01-40"),
                        new FormClass("11WES01"), new Grades("750.0"), new PostalCode("254823"),
                        new Remark("Very determined to do well"), getTagSet("fashion", "debate")),
                new Person(new Name("James Lan"), new Phone("89018719"),
                        new ParentPhone("91231000"),
                        new Email("alexyeoh@example.com"), new Address("Kings Street 1, #01-40"),
                        new FormClass("11WES01"), new Grades("400.0"), new PostalCode("123456"),
                        new Remark("Studies suffering because of relationship"),
                        getTagSet("fencing", "debate", "athletics", "needsHelp")),
                new Person(new Name("Tywin Lan"), new Phone("86545248"),
                        new ParentPhone("97273333"),
                        new Email("alexyeoh@example.com"), new Address("Lannis Street. # 23-01"),
                        new FormClass("11WES01"), new Grades("1200.0"), new PostalCode("245245"),
                        new Remark("High performing in all CCAs and studies"),
                        getTagSet("fencing", "debate", "chess", "scholarship")),
                new Person(new Name("Marge Tyrell"), new Phone("84366478"),
                        new ParentPhone("97274444"),
                        new Email("alexyeoh@example.com"), new Address("High Towers Street 1, #50-01"),
                        new FormClass("11WES01"), new Grades("1000.0"), new PostalCode("374539"),
                        new Remark("Top in Class for both English and Literature"),
                        getTagSet("studentCouncil", "debate", "scholarship")),
                new Person(new Name("Dany Tar"), new Phone("81235155"),
                        new ParentPhone("97212121"),
                        new Email("alexyeoh@example.com"), new Address("Stone Street 1, #20-01"),
                        new FormClass("11WES01"), new Grades("2000.0"), new PostalCode("374539"),
                        new Remark("Top in Class for both English and Literature"),
                        getTagSet("studentCouncil", "debate", "scholarship"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
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
