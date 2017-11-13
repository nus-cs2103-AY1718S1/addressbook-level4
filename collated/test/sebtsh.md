# sebtsh
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_COMPANY_AMY = "Microsoft";
    public static final String VALID_COMPANY_BOB = "Google";
    public static final String VALID_POSITION_AMY = "Manager";
    public static final String VALID_POSITION_BOB = "Director";
    public static final String VALID_STATUS_AMY = "Requires follow up";
    public static final String VALID_STATUS_BOB = "Waiting for reply";
    public static final String VALID_PRIORITY_AMY = "H";
    public static final String VALID_PRIORITY_BOB = "M";
    public static final String VALID_NOTE_AMY = "Met this person last week";
    public static final String VALID_NOTE_BOB = "Likes coffee";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String POSITION_DESC_AMY = " " + PREFIX_POSITION + VALID_POSITION_AMY;
    public static final String POSITION_DESC_BOB = " " + PREFIX_POSITION + VALID_POSITION_BOB;
    public static final String STATUS_DESC_AMY = " " + PREFIX_STATUS + VALID_STATUS_AMY;
    public static final String STATUS_DESC_BOB = " " + PREFIX_STATUS + VALID_STATUS_BOB;
    public static final String PRIORITY_DESC_AMY = " " + PREFIX_PRIORITY + VALID_PRIORITY_AMY;
    public static final String PRIORITY_DESC_BOB = " " + PREFIX_PRIORITY + VALID_PRIORITY_BOB;
    public static final String NOTE_DESC_AMY = " " + PREFIX_NOTE + VALID_NOTE_AMY;
    public static final String NOTE_DESC_BOB = " " + PREFIX_NOTE + VALID_NOTE_BOB;
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY; // empty string not allowed for company
    public static final String INVALID_POSITION_DESC = " " + PREFIX_POSITION; //empty string not allowed for position
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS; //empty string not allowed for status
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "A"; //only H, M, L allowed for priority
    public static final String INVALID_NOTE_DESC = " " + PREFIX_NOTE; //empty string not allowed for status
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withCompany(VALID_COMPANY_AMY).withPosition(VALID_POSITION_AMY)
                .withStatus(VALID_STATUS_AMY).withPriority(VALID_PRIORITY_AMY)
                .withNote(VALID_NOTE_AMY).withPhoto(VALID_PHOTO_AMY).withTags
                (VALID_TAG_FRIEND).withRelation(VALID_REL_SIBLINGS).build();

        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withCompany(VALID_COMPANY_BOB).withPosition(VALID_POSITION_BOB)
                .withStatus(VALID_STATUS_BOB).withPriority(VALID_PRIORITY_BOB)
                .withNote(VALID_NOTE_BOB).withPhoto(VALID_PHOTO_BOB).withTags
                (VALID_TAG_FRIEND).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .withRelation(VALID_REL_SIBLINGS).build();
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withCompany(VALID_COMPANY_BOB)
                .withPosition(VALID_POSITION_BOB).withStatus(VALID_STATUS_BOB).withPriority(VALID_PRIORITY_BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COMPANY_DESC_BOB + POSITION_DESC_BOB
                + STATUS_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COMPANY_DESC_BOB + POSITION_DESC_BOB
                + STATUS_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + COMPANY_DESC_BOB + POSITION_DESC_BOB
                + STATUS_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + COMPANY_DESC_BOB + POSITION_DESC_BOB
                + STATUS_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple companies - last company accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + COMPANY_DESC_AMY + COMPANY_DESC_BOB + POSITION_DESC_BOB
                + STATUS_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple positions - last position accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + COMPANY_DESC_BOB + POSITION_DESC_AMY + POSITION_DESC_BOB
                + STATUS_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple statuses - last status accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + COMPANY_DESC_AMY + COMPANY_DESC_BOB + POSITION_DESC_BOB
                        + STATUS_DESC_AMY + STATUS_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + COMPANY_DESC_AMY + COMPANY_DESC_BOB + POSITION_DESC_BOB
                        + STATUS_DESC_BOB + PRIORITY_DESC_AMY + PRIORITY_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_COMPANY_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Company.MESSAGE_COMPANY_CONSTRAINTS);

        // invalid position
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_POSITION_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Position.MESSAGE_POSITION_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_STATUS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Status.MESSAGE_STATUS_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_PRIORITY_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // invalid note
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_NOTE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Note.MESSAGE_NOTE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS); // invalid email
        // invalid address
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS);
        // invalid company
        assertParseFailure(parser, "1" + INVALID_COMPANY_DESC, Company.MESSAGE_COMPANY_CONSTRAINTS);
        // invalid position
        assertParseFailure(parser, "1" + INVALID_POSITION_DESC, Position.MESSAGE_POSITION_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC, Status.MESSAGE_STATUS_CONSTRAINTS); // invalid status
        // invalid priority
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC, Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_NOTE_DESC, Note.MESSAGE_NOTE_CONSTRAINTS); // invalid note
        assertParseFailure(parser, "1" + INVALID_PHOTO_DESC, Photo
                .MESSAGE_PHOTO_CONSTRAINTS); // invalid photo
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND + COMPANY_DESC_BOB
                + POSITION_DESC_BOB + STATUS_DESC_AMY + PRIORITY_DESC_AMY + NOTE_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withCompany(VALID_COMPANY_BOB).withPosition(VALID_POSITION_BOB)
                .withStatus(VALID_STATUS_AMY).withPriority(VALID_PRIORITY_AMY)
                .withNote(VALID_NOTE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND + COMPANY_DESC_BOB
                + POSITION_DESC_BOB + STATUS_DESC_AMY + PRIORITY_DESC_AMY + NOTE_DESC_BOB + COMPANY_DESC_BOB
                + POSITION_DESC_BOB + STATUS_DESC_AMY + PRIORITY_DESC_AMY
                + NOTE_DESC_BOB + PHOTO_DESC_AMY + PHOTO_DESC_BOB + REL_DESC_COLLEAGUE;


        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withCompany(VALID_COMPANY_BOB)
                .withPosition(VALID_POSITION_BOB).withStatus(VALID_STATUS_AMY).withPriority(VALID_PRIORITY_AMY)
                .withNote(VALID_NOTE_BOB).withPhoto(VALID_PHOTO_BOB).withTags
                (VALID_TAG_FRIEND, VALID_TAG_HUSBAND).withRelation(VALID_REL_COLLEAGUE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
```
###### \java\seedu\address\model\person\CompanyTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompanyTest {

    @Test
    public void isValidCompany() {
        // invalid companies
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only

        // valid companies
        assertTrue(Company.isValidCompany("Microsoft"));
        assertTrue(Company.isValidCompany("-")); // one character
        // long company
        assertTrue(Company.isValidCompany("Mongolian Tribal Software Engineering Line Dance Corporation Limited"));
    }
}
```
###### \java\seedu\address\model\person\NoteTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoteTest {
    @Test
    public void isValidNote() {
        // invalid notes
        assertFalse(Note.isValidNote("")); // empty string
        assertFalse(Note.isValidNote(" ")); // spaces only

        // valid notes
        assertTrue(Note.isValidNote("Hates seafood"));
        assertTrue(Note.isValidNote("-")); // one character
        // long note
        assertTrue(Note.isValidNote("Sometimes likes ice cream but only the expensive kind not very sure"));
    }
}
```
###### \java\seedu\address\model\person\PositionTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PositionTest {
    @Test
    public void isValidPosition() {
        // invalid positions
        assertFalse(Position.isValidPosition("")); // empty string
        assertFalse(Position.isValidPosition(" ")); // spaces only

        // valid positions
        assertTrue(Position.isValidPosition("Manager"));
        assertTrue(Position.isValidPosition("-")); // one character
        // long position
        assertTrue(Position.isValidPosition("Production Executive Operational Second Assistant Director"));
    }
}
```
###### \java\seedu\address\model\person\PriorityTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // invalid priorities
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("High")); // anything that is not H, M, or L

        // valid priorities. Only H, M, and L are valid priorities
        assertTrue(Priority.isValidPriority("H"));
        assertTrue(Priority.isValidPriority("M"));
        assertTrue(Priority.isValidPriority("L"));
    }
}
```
###### \java\seedu\address\model\person\StatusTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatusTest {
    @Test
    public void isValidStatus() {
        // invalid statuses
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only

        // valid statuses
        assertTrue(Status.isValidStatus("Requires follow up"));
        assertTrue(Status.isValidStatus("-")); // one character
        // long status
        assertTrue(Status.isValidStatus("Was wondering if he wanted follow up or not but have not decided"));
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(ReadOnlyPerson person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setCompany(person.getCompany());
        descriptor.setPosition(person.getPosition());
        descriptor.setStatus(person.getStatus());
        descriptor.setPriority(person.getPriority());
        descriptor.setNote(person.getNote());
        descriptor.setPhoto(person.getPhoto());
        descriptor.setTags(person.getTags());
        descriptor.setRelation(person.getRelation());
    }
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Company} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withCompany(String company) {
        try {
            ParserUtil.parseCompany(Optional.of(company)).ifPresent(descriptor::setCompany);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("company is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Position} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPosition(String position) {
        try {
            ParserUtil.parsePosition(Optional.of(position)).ifPresent(descriptor::setPosition);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("position is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withStatus(String status) {
        try {
            ParserUtil.parseStatus(Optional.of(status)).ifPresent(descriptor::setStatus);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("status is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPriority(String priority) {
        try {
            ParserUtil.parsePriority(Optional.of(priority)).ifPresent(descriptor::setPriority);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNote(String note) {
        try {
            ParserUtil.parseNote(Optional.of(note)).ifPresent(descriptor::setNote);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("note is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public static final String DEFAULT_COMPANY = "NIL";
    public static final String DEFAULT_POSITION = "NIL";
    public static final String DEFAULT_STATUS = "NIL";
    public static final String DEFAULT_PRIORITY = "L";
    public static final String DEFAULT_NOTE = "NIL";
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Company defaultCompany = new Company(DEFAULT_COMPANY);
            Position defaultPosition = new Position(DEFAULT_POSITION);
            Status defaultStatus = new Status(DEFAULT_STATUS);
            Priority defaultPriority = new Priority(DEFAULT_PRIORITY);
            Note defaultNote = new Note(DEFAULT_NOTE);
            Photo defaultPhoto = new Photo(DEFAULT_PHOTO);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            Set<Relationship> defaultRel = SampleDataUtil.getRelSet(DEFAULT_RELATIONSHIP);
            this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress, defaultCompany,
                    defaultPosition, defaultStatus, defaultPriority,
                    defaultNote, defaultPhoto, defaultTags, defaultRel);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default person's values are invalid.");
        }
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany(String company) {
        try {
            this.person.setCompany(new Company(company));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Position} of the {@code Person} that we are building.
     */
    public PersonBuilder withPosition(String position) {
        try {
            this.person.setPosition(new Position(position));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Person} that we are building.
     */
    public PersonBuilder withStatus(String status) {
        try {
            this.person.setStatus(new Status(status));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Person} that we are building.
     */
    public PersonBuilder withPriority(String priority) {
        try {
            this.person.setPriority(new Priority(priority));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String note) {
        try {
            this.person.setNote(new Note(note));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonUtil.java
``` java
    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(ReadOnlyPerson person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_COMPANY + person.getCompany().value + " ");
        sb.append(PREFIX_POSITION + person.getPosition().value + " ");
        sb.append(PREFIX_STATUS + person.getStatus().value + " ");
        sb.append(PREFIX_PRIORITY + person.getPriority().value + " ");
        sb.append(PREFIX_NOTE + person.getNote().value + " ");
        sb.append(PREFIX_PHOTO + person.getPhoto().photoUrl + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        person.getRelation().stream().forEach(
            s -> sb.append(PREFIX_ADD_RELATIONSHIP + s.relType + " ")
        );
        return sb.toString();
    }
}
```
