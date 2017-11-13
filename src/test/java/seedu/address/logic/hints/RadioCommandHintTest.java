package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.assertHintContent;

import org.junit.Test;

import seedu.address.logic.commands.RadioCommand;
import seedu.address.logic.commands.hints.RadioCommandHint;

public class RadioCommandHintTest {

    @Test
    public void radioCommandHint() {

        RadioCommand.stopRadioPlayer();
        RadioCommandHint radioCommandHint = new RadioCommandHint("radio", "");
        if (!RadioCommand.isRadioPlaying()) {
            assertHintContent(radioCommandHint,
                    " play",
                    " plays radio",
                    "radio play");

            radioCommandHint = new RadioCommandHint("radio ", "");
            assertHintContent(radioCommandHint,
                    "play",
                    " plays radio",
                    "radio play");

            radioCommandHint = new RadioCommandHint("radio p", " p");
            assertHintContent(radioCommandHint,
                    "lay",
                    " plays radio",
                    "radio play");

            radioCommandHint = new RadioCommandHint("radio sto", " sto");
            assertHintContent(radioCommandHint,
                    "p",
                    " stops radio",
                    "radio play");

            radioCommandHint = new RadioCommandHint("radio play", " play");
            assertHintContent(radioCommandHint,
                    " pop",
                    " plays pop radio",
                    "radio play pop");

            radioCommandHint = new RadioCommandHint("radio play po", " play po");
            assertHintContent(radioCommandHint,
                    "p",
                    " plays pop radio",
                    "radio play pop");

            radioCommandHint = new RadioCommandHint("radio play c", " play c");
            assertHintContent(radioCommandHint,
                    "hinese",
                    " plays chinese radio",
                    "radio play chinese");


        } else {
            assertHintContent(radioCommandHint,
                    " stop",
                    " stops radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio ", "");
            assertHintContent(radioCommandHint,
                    "stop",
                    " stops radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio s", " s");
            assertHintContent(radioCommandHint,
                    "top",
                    " stops radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio pla", " pla");
            assertHintContent(radioCommandHint,
                    "y",
                    " plays radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio play", " play");
            assertHintContent(radioCommandHint,
                    " pop",
                    " plays pop radio",
                    "radio play pop");


            radioCommandHint = new RadioCommandHint("radio play po", " play po");
            assertHintContent(radioCommandHint,
                    "p",
                    " plays pop radio",
                    "radio stop");

            radioCommandHint = new RadioCommandHint("radio play c", " play c");
            assertHintContent(radioCommandHint,
                    "hinese",
                    " plays chinese radio",
                    "radio stop");
        }


        radioCommandHint = new RadioCommandHint("radio play pop", " play pop");
        assertHintContent(radioCommandHint,
                "",
                " plays pop radio",
                "radio play chinese");

        radioCommandHint = new RadioCommandHint("radio play chinese", " play chinese");
        assertHintContent(radioCommandHint,
                "",
                " plays chinese radio",
                "radio play classic");

        radioCommandHint = new RadioCommandHint("radio play classic", " play classic");
        assertHintContent(radioCommandHint,
                "",
                " plays classic radio",
                "radio play news");

        radioCommandHint = new RadioCommandHint("radio play news", " play news");
        assertHintContent(radioCommandHint,
                "",
                " plays news radio",
                "radio play pop");

        radioCommandHint = new RadioCommandHint("radio play s", " play s");
        assertHintContent(radioCommandHint,
                " pop",
                " plays pop radio",
                "radio play pop");

        radioCommandHint = new RadioCommandHint("radio stop", " stop");
        assertHintContent(radioCommandHint,
                "",
                " stops radio",
                "radio stop");

    }
}
