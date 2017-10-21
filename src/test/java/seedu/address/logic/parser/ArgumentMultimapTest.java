package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.Test;

public class ArgumentMultimapTest {
    private static final String NOT_EXISTS = "not exists";
    @Test
    public void put_singleEntry_checkCorrectness() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, VALID_NAME_AMY);
        assertEquals(VALID_NAME_AMY, map.getValue(PREFIX_NAME).orElse(NOT_EXISTS));
    }
}
