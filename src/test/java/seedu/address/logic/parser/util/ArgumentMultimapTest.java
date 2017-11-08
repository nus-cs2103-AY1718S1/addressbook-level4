package seedu.address.logic.parser.util;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_PHONE;

import java.util.HashMap;

import org.junit.Test;

//@@author yunpengn
public class ArgumentMultimapTest {
    private static final String NOT_EXISTS = "not exists";

    @Test
    public void put_singleEntry_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, VALID_NAME_AMY);
        assertEquals(VALID_NAME_AMY, map.getValue(PREFIX_NAME).orElse(NOT_EXISTS));
    }

    @Test
    public void put_singleEmptyEntry_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        assertEquals(NOT_EXISTS, map.getValue(PREFIX_NAME).orElse(NOT_EXISTS));
    }

    @Test
    public void put_sameNameMultipleTimes_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, VALID_NAME_AMY);
        map.put(PREFIX_NAME, VALID_NAME_BOB);
        assertEquals(2, map.getAllValues(PREFIX_NAME).size());
    }

    @Test
    public void put_multipleEntries_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, VALID_NAME_AMY);
        map.put(PREFIX_PHONE, VALID_PHONE_BOB);
        assertEquals(VALID_NAME_AMY, map.getValue(PREFIX_NAME).orElse(NOT_EXISTS));
        assertEquals(VALID_PHONE_BOB, map.getValue(PREFIX_PHONE).orElse(NOT_EXISTS));
    }

    @Test
    public void getAllValues_multipleEntries_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, VALID_NAME_AMY);
        map.put(PREFIX_NAME, VALID_NAME_BOB);
        map.put(PREFIX_PHONE, VALID_PHONE_BOB);

        HashMap<Prefix, String> values = map.getAllValues();
        assertEquals(2, values.size());
        assertEquals(VALID_NAME_BOB, values.get(PREFIX_NAME));
        assertEquals(VALID_PHONE_BOB, values.get(PREFIX_PHONE));
    }

    @Test
    public void getPreamble_checkCorrectness() {
        String preamble = "Some things here";
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(new Prefix(""), preamble);
        assertEquals(preamble, map.getPreamble());
    }
}
