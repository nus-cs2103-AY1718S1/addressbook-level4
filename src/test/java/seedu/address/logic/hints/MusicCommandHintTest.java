package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.parseAndAssertHint;

import org.junit.Test;

import seedu.address.logic.commands.MusicCommand;
import seedu.address.logic.commands.hints.MusicCommandHint;

public class MusicCommandHintTest {

    @Test
    public void musicCommandHint() {

        MusicCommand.stopMusicPlayer();
        MusicCommandHint musicCommandHint;
        if (!MusicCommand.isMusicPlaying()) {
            musicCommandHint = new MusicCommandHint("music", "");
            parseAndAssertHint(musicCommandHint,
                    " play",
                    " plays music",
                    "music play");

            musicCommandHint = new MusicCommandHint("music ", "");
            parseAndAssertHint(musicCommandHint,
                    "play",
                    " plays music",
                    "music play");

            musicCommandHint = new MusicCommandHint("music p", " p");
            parseAndAssertHint(musicCommandHint,
                    "lay",
                    " plays music",
                    "music play");

            musicCommandHint = new MusicCommandHint("music play po", " play po");
            parseAndAssertHint(musicCommandHint,
                    "p",
                    " plays pop",
                    "music play pop");
        } else {
            musicCommandHint = new MusicCommandHint("music", "");
            parseAndAssertHint(musicCommandHint,
                    " pause",
                    " pauses music",
                    "music pause");

            musicCommandHint = new MusicCommandHint("music ", "");
            parseAndAssertHint(musicCommandHint,
                    "pause",
                    " pauses music",
                    "music pause");

            musicCommandHint = new MusicCommandHint("music p", " p");
            parseAndAssertHint(musicCommandHint,
                    "lay",
                    " plays music",
                    "music pause");

            musicCommandHint = new MusicCommandHint("music play po", " play po");
            parseAndAssertHint(musicCommandHint,
                    "p",
                    " plays pop",
                    "music pause ");
        }


        musicCommandHint = new MusicCommandHint("music play", " play");
        parseAndAssertHint(musicCommandHint,
                " pop",
                " plays pop",
                "music play pop");

        musicCommandHint = new MusicCommandHint("music play pop", " play pop");
        parseAndAssertHint(musicCommandHint,
                "",
                " plays pop",
                "music play dance");

        musicCommandHint = new MusicCommandHint("music play dance", " play dance");
        parseAndAssertHint(musicCommandHint,
                "",
                " plays dance tracks",
                "music play classic");

        musicCommandHint = new MusicCommandHint("music play s", " play s");
        parseAndAssertHint(musicCommandHint,
                " pop",
                " plays pop",
                "music play pop");


        musicCommandHint = new MusicCommandHint("music pause", " pause");
        parseAndAssertHint(musicCommandHint,
                "",
                " pauses music",
                "music pause");

    }
}
