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
                new Person(new Name("Alex Yeoh"), new Phone("student/97272001 parent/91234563 "),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                        new FormClass("12SCI23"), new Grades("940.0"), new PostalCode("123456"),
                        new Remark("Represented school for national track meet"), getTagSet("track", "athletics")),
                new Person(new Name("Bernice Yu"), new Phone("student/67375488 parent/97971197 "),
                        new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new FormClass("12SCI23"), new Grades("530.0"), new PostalCode("654321"),
                        new Remark("Woman"), getTagSet("colleagues", "canoeing", "scholarship")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("student/62361502 parent/97129722 "),
                        new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new FormClass("12SCI23"), new Grades("435.0"), new PostalCode("987654"),
                        new Remark("Awesome mate"), getTagSet("studentCouncil")),
                new Person(new Name("David Li"), new Phone("student/85473617 parent/91239712 "),
                        new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("studentCouncil", "needsHelp")),
                new Person(new Name("Irfan Ibrahim"), new Phone("student/61243107 parent/97978789 "),
                        new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                        new FormClass("12SCI23"), new Grades("564.0"), new PostalCode("999999"),
                        new Remark("Indicated preference for joining another CCA"),
                        getTagSet("ScienceTeam", "sailing")),
                new Person(new Name("Roy Balakrishnan"), new Phone("student/94534566 parent/97911624 "),
                        new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                        new FormClass("12SCI23"), new Grades("234.0"), new PostalCode("999666"),
                        new Remark("Mixed ethnicity"), getTagSet("colleagues")),
                new Person(new Name("Lynn Tan"), new Phone("student/84144141 parent/97912341 "),
                        new Email("lynntann@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("dance", "band")),
                new Person(new Name("Mariam Mohamad"), new Phone("student/85215625 parent/95643212 "),
                        new Email("mmohd@example.com"), new Address("Blk 6 Home Gardens Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("dance", "needsHelp")),
                new Person(new Name("Amy Leow"), new Phone("student/83245682 parent/81726490 "),
                        new Email("amyleow@example.com"), new Address("Blk 46 Tame Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("athletics")),
                new Person(new Name("Laura Teh"), new Phone("student/86542453 parent/99345678 "),
                        new Email("laurateh@example.com"), new Address("Blk 45 Cuper Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("565.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("athletics")),
                new Person(new Name("Amanda Sun"), new Phone("student/95851354 parent/91556811 "),
                        new Email("sunamanda@example.com"), new Address("Blk 123 Orchard Gardens Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("785.0"), new PostalCode("676767"),
                        new Remark("Arrogant but is good in studies"), getTagSet("scholarship")),
                new Person(new Name("Jolene Saram"), new Phone("student/85373543 parent/91938372 "),
                        new Email("saramjol@example.com"), new Address("Blk 22 Serangoon Drive 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("895.0"), new PostalCode("676767"),
                        new Remark("Top in class for math"), getTagSet("scienceTeam")),
                new Person(new Name("Laura Li"), new Phone("student/89345354 parent/97954567 "),
                        new Email("lilaura@example.com"), new Address("Blk 42 Tampines Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("545.0"), new PostalCode("676767"),
                        new Remark("Consitent effort given"), getTagSet("studentCouncil")),
                new Person(new Name("Goh Qing Jing"), new Phone("student/85473617 parent/90088957 "),
                        new Email("gohqingqing@example.com"), new Address("Blk 56 Sungei Bedok 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("245.0"), new PostalCode("676767"),
                        new Remark("Class clown"), getTagSet("studentCouncil")),
                new Person(new Name("Thia Hon Teng"), new Phone("student/93455383 parent/97974456 "),
                        new Email("hauting@example.com"), new Address("Blk 567 Hope Street 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("345.0"), new PostalCode("676767"),
                        new Remark("Studies suffering because of CCA"), getTagSet("studentCouncil", "needsHelp")),
                new Person(new Name("Dorothy Thia"), new Phone("student/93457384 parent/97971234 "),
                        new Email("dorothythia@example.com"), new Address("Blk 457 Wizard drive 26, #16-43"),
                        new FormClass("12SCI23"), new Grades("295.0"), new PostalCode("676767"),
                        new Remark("Always blur"), getTagSet("studentCouncil")),
                new Person(new Name("John Doe"), new Phone("student/95645686 parent/97975687 "),
                        new Email("alexyeoh@example.com"), new Address("Blk 7 Ang Mo Kio Street 62"),
                        new FormClass("15APP10"), new Grades("990.0"), new PostalCode("569086 "),
                        new Remark("Developed mobile games for school"), getTagSet("GameDev", "scholarship")),
                new Person(new Name("John Hoe"), new Phone("student/96575688 parent/97921872 "),
                        new Email("alexyeoh@example.com"), new Address("SIS Building 4 Leng Kee Road #03-07"),
                        new FormClass("15APP10"), new Grades("690.0"), new PostalCode("159088"),
                        new Remark("Head of dance CCA"), getTagSet("studentCouncil", "dance", "athletics")),
                new Person(new Name("John Shoe"), new Phone("student/96576756 parent/97125677 "),
                        new Email("alexyeoh@example.com"), new Address("Blk 567 Choa Chu Kang Street 86"),
                        new FormClass("15APP10"), new Grades("960.0"), new PostalCode("640557"),
                        new Remark("Enrolled in the Design Centric Program"), getTagSet("DCP")),
                new Person(new Name("John Lowe"), new Phone("student/96575628 parent/97965890 "),
                        new Email("alexyeoh@example.com"), new Address("Blk 876 Lim Chu Kang Street 92"),
                        new FormClass("15APP10"), new Grades("700.0"), new PostalCode("640545"),
                        new Remark("Air Force regular"), getTagSet("studentCouncil", "flyingClub", "athletics")),
                new Person(new Name("John Know"), new Phone("student/67986576 parent/54151638 "),
                        new Email("alexyeoh@example.com"), new Address("Blk 743 Phua Chu Kang Street 14"),
                        new FormClass("15APP10"), new Grades("900.0"), new PostalCode("640345"),
                        new Remark("Individual champion of AstroChallenge17"), getTagSet("ScienceTeam", "scholarship")),
                new Person(new Name("John Bow"), new Phone("student/96576866 parent/54345456 "),
                        new Email("alexyeoh@example.com"), new Address("Blk 334 Yio Chu Kang Street 25"),
                        new FormClass("15APP10"), new Grades("90.0"), new PostalCode("069541"),
                        new Remark("suffering in studies because of CCA "), getTagSet("Archery", "needsHelp")),
                new Person(new Name("Bruno Mas"), new Phone("student/96576578 parent/97973322 "),
                        new Email("alexyeoh@example.com"), new Address("10 Biopolis Way #03-03/04 CHROMOS"),
                        new FormClass("16MUS20"), new Grades("979.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("studentCouncil", "band", "dance")),
                new Person(new Name("Nick Joe"), new Phone("student/65756867 parent/93452797 "),
                        new Email("alexyeoh@example.com"), new Address("88 Syed Alwi Road #01-02"),
                        new FormClass("16MUS20"), new Grades("936.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("band")),
                new Person(new Name("Chris Mar"), new Phone("student/96756857 parent/97234797 "),
                        new Email("alexyeoh@example.com"), new Address("2 Corporation Road #03-09/11"),
                        new FormClass("16MUS20"), new Grades("1056.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("band")),
                new Person(new Name("Fred Mer"), new Phone("student/96786566 parent/97567832 "),
                        new Email("alexyeoh@example.com"), new Address("3014, Ubi Road 1, #06-40"),
                        new FormClass("16MUS20"), new Grades("750.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("band")),
                new Person(new Name("Camila C"), new Phone("student/67567288 parent/91432143 "),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                        new FormClass("16MUS20"), new Grades("750.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("band", "dance")),
                new Person(new Name("Rick Sanchez"), new Phone("student/97273131 parent/97975839 "),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Washington Street 29, #06-40"),
                        new FormClass("16UNI137"), new Grades("3000.0"), new PostalCode("123456"),
                        new Remark("Projected valedictorian. \n Wrote and performed the hit song \"Get Schwifty\"."),
                        getTagSet("ScienceTeam", "dance", "band", "studentCouncil", "scholarship")),
                new Person(new Name("Morty Smith"), new Phone("student/96576578 parent/94157312 "),
                        new Email("alexyeoh@example.com"), new Address("69 Choa Chu Kang Loop #02-12"),
                        new FormClass("16UNI137"), new Grades("500.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("GameDev", "dance", "band")),
                new Person(new Name("Summer Smith"), new Phone("student/65784657 parent/97971111 "),
                        new Email("alexyeoh@example.com"), new Address("1002 Jalan Bukit Merah #02-04"),
                        new FormClass("16UNI137"), new Grades("700"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("fashion")),
                new Person(new Name("Beth Smith"), new Phone("student/68957567 parent/97972321 "),
                        new Email("alexyeoh@example.com"), new Address("290 Orchard Road #08-03"),
                        new FormClass("16UNI137"), new Grades("1000.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("fashion", "social", "judo")),
                new Person(new Name("Jerry Smith"), new Phone("student/96742875 parent/97983487 "),
                        new Email("alexyeoh@example.com"), new Address("501 Orchard Road #07-02"),
                        new FormClass("16UNI137"), new Grades("300.0"), new PostalCode("123456"),
                        new Remark("I am a man"), getTagSet("social")),
                new Person(new Name("Jon Sow"), new Phone("student/97567528 parent/54905712 "),
                        new Email("alexyeoh@example.com"), new Address("Winter Street 1, #11-40"),
                        new FormClass("11WES01"), new Grades("500.0"), new PostalCode("242238"),
                        new Remark("Leader of the Class"), getTagSet("fencing", "debate")),
                new Person(new Name("Greg C"), new Phone("student/97211031 parent/91209481 "),
                        new Email("alexyeoh@example.com"), new Address("Kings Street 1, #01-40"),
                        new FormClass("11WES01"), new Grades("400.0"), new PostalCode("354373"),
                        new Remark("Big and Strong"), getTagSet("athletics", "fencing")),
                new Person(new Name("Sandor C"), new Phone("student/97564654 parent/97001253 "),
                        new Email("alexyeoh@example.com"), new Address("Stone Street 1, #21-40"),
                        new FormClass("11WES01"), new Grades("800.0"), new PostalCode("935437"),
                        new Remark("Greg's brother"), getTagSet("athletics", "fencing")),
                new Person(new Name("Cersi Lan"), new Phone("student/67282459 parent/91122990 "),
                        new Email("alexyeoh@example.com"), new Address("Kings Street 1, #01-40"),
                        new FormClass("11WES01"), new Grades("750.0"), new PostalCode("254823"),
                        new Remark("Very determined to do well"), getTagSet("fashion", "debate")),
                new Person(new Name("James Lan"), new Phone("student/67278538 parent/89018719 "),
                        new Email("alexyeoh@example.com"), new Address("Kings Street 1, #01-40"),
                        new FormClass("11WES01"), new Grades("400.0"), new PostalCode("123456"),
                        new Remark("Studies suffering because of relationship"),
                        getTagSet("fencing", "debate", "athletics", "needsHelp")),
                new Person(new Name("Tywin Lan"), new Phone("student/86545248 parent/95784931 "),
                        new Email("alexyeoh@example.com"), new Address("Lannis Street. # 23-01"),
                        new FormClass("11WES01"), new Grades("1200.0"), new PostalCode("245245"),
                        new Remark("High performing in all CCAs and studies"),
                        getTagSet("fencing", "debate", "chess", "scholarship")),
                new Person(new Name("Marge Tyrell"), new Phone("student/84366478 parent/97979797 "),
                        new Email("alexyeoh@example.com"), new Address("High Towers Street 1, #50-01"),
                        new FormClass("11WES01"), new Grades("1000.0"), new PostalCode("374539"),
                        new Remark("Top in Class for both English and Literature"),
                        getTagSet("studentCouncil", "debate", "scholarship")),
                new Person(new Name("Dany Tar"), new Phone("student/81235155 parent/68118845 "),
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
