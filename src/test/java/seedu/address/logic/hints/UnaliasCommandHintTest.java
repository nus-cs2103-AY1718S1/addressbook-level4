package seedu.address.logic.hints;

import org.junit.Test;

import seedu.address.logic.commands.hints.UnaliasCommandHint;

public class UnaliasCommandHintTest {

    @Test
    public void unaliasCommandHint() {
        UnaliasCommandHint unaliasCommandHint = new UnaliasCommandHint("unalias", "");
        assertHint(
                unaliasCommandHint,
                " removes alias",
                "unalias ");

        unaliasCommandHint = new UnaliasCommandHint("alias ", "");
        assertHint(
                unaliasCommandHint,
                " alias to remove",
                "alias ");

        unaliasCommandHint = new UnaliasCommandHint("alias s", " s");
        assertHint(
                unaliasCommandHint,
                " removes s from aliases",
                "alias s ");


        unaliasCommandHint = new UnaliasCommandHint("alias aaa ", " aaa ");
        assertHint(
                unaliasCommandHint,
                " removes aaa from aliases",
                "alias aaa ");

    }

    /**
     * parses {@code unaliasCommandHint} and checks if the the hint generated has the expected fields
     */
    private void assertHint(UnaliasCommandHint unaliasCommandHint,
                            String expectedDesc,
                            String expectedAutocomplete) {
        AddCommandHintTest.assertHintContent(
                unaliasCommandHint,
                "",
                expectedDesc,
                expectedAutocomplete);
    }
}
