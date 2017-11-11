package seedu.address.logic.hints;

import org.junit.Test;

import seedu.address.logic.commands.hints.UnaliasCommandHint;

public class UnaliasCommandHintTest {

    @Test
    public void unaliasCommandHint() {
        UnaliasCommandHint unaliasCommandHint = new UnaliasCommandHint("unalias", "");
        parseAndAssertHint(
                unaliasCommandHint,
                " removes alias",
                "unalias ");

        unaliasCommandHint = new UnaliasCommandHint("alias ", "");
        parseAndAssertHint(
                unaliasCommandHint,
                " alias to remove",
                "alias ");

        unaliasCommandHint = new UnaliasCommandHint("alias s", " s");
        parseAndAssertHint(
                unaliasCommandHint,
                " removes s from aliases",
                "alias s ");


        unaliasCommandHint = new UnaliasCommandHint("alias aaa ", " aaa ");
        parseAndAssertHint(
                unaliasCommandHint,
                " removes aaa from aliases",
                "alias aaa ");

    }

    /**
     * parses {@code unaliasCommandHint} and checks if the the hint generated has the expected fields
     */
    private void parseAndAssertHint(UnaliasCommandHint unaliasCommandHint,
                                    String expectedDesc,
                                    String expectedAutocomplete) {
        AddCommandHintTest.parseAndAssertHint(
                unaliasCommandHint,
                "",
                expectedDesc,
                expectedAutocomplete);
    }
}
