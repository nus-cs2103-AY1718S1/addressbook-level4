package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.exceptions.DuplicatePlaceException;


/**
 * A utility class containing a list of {@code Place} objects to be used in tests.
 */
public class TypicalPlaces {

    public static final ReadOnlyPlace ALICE = new PlaceBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withPostalCode("111111")
            .withWebsite("https://www.alice.com")
            .withPhone("85355255")
            .withTags("friends").build();
    public static final ReadOnlyPlace BENSON = new PlaceBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withPostalCode("111111")
            .withWebsite("https://www.benson.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPlace CARL = new PlaceBuilder().withName("Carl Kurz").withPhone("95352563")
            .withWebsite("https://www.carl.com").withAddress("wall street").withPostalCode("111111").build();
    public static final ReadOnlyPlace DANIEL = new PlaceBuilder().withName("Daniel Meier").withPhone("87652533")
            .withWebsite("https://www.daniel.com").withAddress("10th street").withPostalCode("111111").build();
    public static final ReadOnlyPlace ELLE = new PlaceBuilder().withName("Elle Meyer").withPhone("9482224")
            .withWebsite("https://www.elle.com").withAddress("michegan ave").withPostalCode("111111").build();
    public static final ReadOnlyPlace FIONA = new PlaceBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withWebsite("https://www.fiona.com").withAddress("little tokyo").withPostalCode("111111").build();
    public static final ReadOnlyPlace GEORGE = new PlaceBuilder().withName("George Best").withPhone("9482442")
            .withWebsite("https://www.george.com").withAddress("4th street").withPostalCode("111111").build();

    // Manually added
    public static final ReadOnlyPlace HOON = new PlaceBuilder().withName("Hoon Meier").withPhone("8482424")
            .withWebsite("https://www.hoon.com").withAddress("little india").withPostalCode("111111").build();
    public static final ReadOnlyPlace IDA = new PlaceBuilder().withName("Ida Mueller").withPhone("8482131")
            .withWebsite("https://wwww.ida.com").withAddress("chicago ave").withPostalCode("111111").build();

    // Manually added - Place's details found in {@code CommandTestUtil}
    public static final ReadOnlyPlace AMY = new PlaceBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withWebsite(VALID_WEBSITE_AMY).withAddress(VALID_ADDRESS_AMY)
            .withPostalCode(VALID_POSTAL_CODE_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPlace BOB = new PlaceBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB)
            .withPostalCode(VALID_POSTAL_CODE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPlaces() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical places.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPlace place : getTypicalPlaces()) {
            try {
                ab.addPlace(place);
            } catch (DuplicatePlaceException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyPlace> getTypicalPlaces() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
