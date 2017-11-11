package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.parseAndAssertHint;

import org.junit.Test;

import seedu.address.logic.commands.RadioCommand;
import seedu.address.logic.commands.hints.RadioCommandHint;

public class RadioCommandHintTest {

    @Test
    public void radioCommandHint() {

        RadioCommand.stopRadioPlayer();
        RadioCommandHint radioCommandHint = new RadioCommandHint("radio", "");
        if (!RadioCommand.isRadioPlaying()) {
            parseAndAssertHint(radioCommandHint,
                    " play",
                    " plays radio",
                    "radio play");

            radioCommandHint = new RadioCommandHint("radio ", "");
            parseAndAssertHint(radioCommandHint,
                    "play",
                    " plays radio",
                    "radio play");

            radioCommandHint = new RadioCommandHint("radio p", " p");
            parseAndAssertHint(radioCommandHint,
                    "lay",
                    " plays radio",
                    "radio play");

            radioCommandHint = new RadioCommandHint("radio sto", " sto");
            parseAndAssertHint(radioCommandHint,
                    "p",
                    " stops radio",
                    "radio play");

            radioCommandHint = new RadioCommandHint("radio play", " play");
            parseAndAssertHint(radioCommandHint,
                    " pop",
                    " plays pop radio",
                    "radio play pop");

            radioCommandHint = new RadioCommandHint("radio play po", " play po");
            parseAndAssertHint(radioCommandHint,
                    "p",
                    " plays pop radio",
                    "radio play pop");

            radioCommandHint = new RadioCommandHint("radio play c", " play c");
            parseAndAssertHint(radioCommandHint,
                    "hinese",
                    " plays chinese radio",
                    "radio play chinese");


        } else {
            parseAndAssertHint(radioCommandHint,
                    " stop",
                    " stops radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio ", "");
            parseAndAssertHint(radioCommandHint,
                    "stop",
                    " stops radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio s", " s");
            parseAndAssertHint(radioCommandHint,
                    "top",
                    " stops radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio pla", " pla");
            parseAndAssertHint(radioCommandHint,
                    "y",
                    " plays radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio play", " play");
            parseAndAssertHint(radioCommandHint,
                    " pop",
                    " plays pop radio",
                    "radio play pop");


            radioCommandHint = new RadioCommandHint("radio play po", " play po");
            parseAndAssertHint(radioCommandHint,
                    "p",
                    " plays pop radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio play c", " play c");
            parseAndAssertHint(radioCommandHint,
                    "hinese",
                    " plays chinese radio",
                    "radio stop");
        }


        radioCommandHint = new RadioCommandHint("radio play pop", " play pop");
        parseAndAssertHint(radioCommandHint,
                "",
                " plays pop radio",
                "radio play chinese");

        radioCommandHint = new RadioCommandHint("radio play chinese", " play chinese");
        parseAndAssertHint(radioCommandHint,
                "",
                " plays chinese radio",
                "radio play classic");

        radioCommandHint = new RadioCommandHint("radio play classic", " play classic");
        parseAndAssertHint(radioCommandHint,
                "",
                " plays classic radio",
                "radio play news");

        radioCommandHint = new RadioCommandHint("radio play news", " play news");
        parseAndAssertHint(radioCommandHint,
                "",
                " plays news radio",
                "radio play pop");

        radioCommandHint = new RadioCommandHint("radio play s", " play s");
        parseAndAssertHint(radioCommandHint,
                " pop",
                " plays pop radio",
                "radio play pop");

        radioCommandHint = new RadioCommandHint("radio stop", " stop");
        parseAndAssertHint(radioCommandHint,
                "",
                " stops radio",
                "radio stop");

    }
}
