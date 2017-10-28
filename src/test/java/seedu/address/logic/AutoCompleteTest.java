package seedu.address.logic;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.trie.CommandTrie;
import seedu.address.logic.trie.Trie;

/**
 * Test Class for autocomplete function
 */
public class AutoCompleteTest {

    private Trie commandTrie;

    //@@author grantcm
    @Before
    public void setup() {
        commandTrie = new CommandTrie();
    }

    @Test
    public void testAutoComplete() {
        assert commandTrie.attemptAutoComplete("a").equals("add");
        assert commandTrie.attemptAutoComplete("e").equals("e");
        assert commandTrie.attemptAutoComplete("ed").equals("edit");
        assert commandTrie.attemptAutoComplete("ex").equals("exit");
        assert commandTrie.attemptAutoComplete("H").equals("H");
        assert commandTrie.attemptAutoComplete("He").equals("help");
        assert commandTrie.attemptAutoComplete("Hi").equals("history");
        assert commandTrie.attemptAutoComplete("l").equals("list");
        assert commandTrie.attemptAutoComplete("red").equals("redo");
        assert commandTrie.attemptAutoComplete("rem").equals("remark");
        assert commandTrie.attemptAutoComplete("s").equals("select");
        assert commandTrie.attemptAutoComplete("u").equals("undo");
        assert commandTrie.attemptAutoComplete("fil").equals("filter");
        assert commandTrie.attemptAutoComplete("fin").equals("find");
        assert commandTrie.attemptAutoComplete("g").equals("group");
    }

    @Test
    public void testExtendedAutoComplete() {
        assert commandTrie.attemptAutoComplete("add").equals(" n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]\n");
        assert commandTrie.attemptAutoComplete("delete").equals(" INDEX\n");
        assert commandTrie.attemptAutoComplete("edit").equals(" INDEX [n/NAME] [p/PHONE] [e/EMAIL] "
                + "[a/ADDRESS] [t/TAG]\n");
    }
    //@@author
}
