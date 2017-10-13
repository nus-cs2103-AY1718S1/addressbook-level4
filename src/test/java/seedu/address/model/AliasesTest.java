package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ListCommand;

public class AliasesTest {
    private static final String LIST_COMMAND_ALIAS = "show";
    private static final String ADD_COMMAND_ALIAS = "create";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Aliases aliases;

    @Before
    public void setUp() {
        aliases = new Aliases();
    }

    @Test
    public void getAllAliases_withAliases_returnsAllAliases() {
        Set<String> allAliases = new HashSet<>(aliases.getAllAliases());

        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        aliases.addAlias(ADD_COMMAND_ALIAS, AddCommand.COMMAND_WORD);
        allAliases.add(LIST_COMMAND_ALIAS);
        allAliases.add(ADD_COMMAND_ALIAS);

        assertEquals(allAliases, aliases.getAllAliases());
    }

    @Test
    public void getCommand_validCommand_returnsCorrectCommand() {
        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        assertEquals(ListCommand.COMMAND_WORD, aliases.getCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void getCommand_invalidCommand_returnsNull() {
        assertNull(aliases.getCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void addCommand_validCommand_addsCorrectAlias() {
        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        assertEquals(ListCommand.COMMAND_WORD, aliases.getCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void removeAlias_validCommand_removesAlias() {
        Set<String> allAliases = aliases.getAllAliases();
        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        assertEquals(true, aliases.removeAlias(LIST_COMMAND_ALIAS));
        allAliases.remove(LIST_COMMAND_ALIAS);

        assertEquals(allAliases, aliases.getAllAliases());
    }

    @Test
    public void removeAlias_invalidCommand_throwsNoSuchElementException() {
        thrown.expect(NoSuchElementException.class);
        aliases.removeAlias(LIST_COMMAND_ALIAS);
    }

    @Test
    public void toString_withAliases_returnsCorrectString() {
        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        StringBuilder string = new StringBuilder("{");
        for (String alias : aliases.getAllAliases()) {
            string.append(alias);
            string.append("=");
            string.append(aliases.getCommand(alias));
            string.append(", ");
        }
        string.delete(string.length() - 2, string.length());
        string.append("}");

        assertEquals(string.toString(), aliases.toString());
    }

    @Test
    public void equals_sameSet_returnsTrue() {
        Aliases other = new Aliases();
        assertTrue(aliases.equals(other));

        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        other.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        assertTrue(aliases.equals(other));
    }

    @Test
    public void equals_differentSet_returnsFalse() {
        Aliases other = new Aliases();

        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        assertFalse(aliases.equals(other));
    }

    @Test
    public void hashCode_sameSet_returnsSameHashCode() {
        Aliases expectedAliases = new Aliases();
        assertEquals(aliases.hashCode(), expectedAliases.hashCode());

        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        expectedAliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        assertEquals(aliases.hashCode(), expectedAliases.hashCode());
    }
}
