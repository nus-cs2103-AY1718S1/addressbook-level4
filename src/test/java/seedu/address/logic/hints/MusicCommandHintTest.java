package seedu.address.logic.hints;

import static seedu.address.logic.hints.AddCommandHintTest.assertHintContent;

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
            assertHintContent(musicCommandHint,
                    " play",
                    " plays music",
                    "music play");

            musicCommandHint = new MusicCommandHint("music ", "");
            assertHintContent(musicCommandHint,
                    "play",
                    " plays music",
                    "music play");

            musicCommandHint = new MusicCommandHint("music p", " p");
            assertHintContent(musicCommandHint,
                    "lay",
                    " plays music",
                    "music play");

            musicCommandHint = new MusicCommandHint("music play po", " play po");
            assertHintContent(musicCommandHint,
                    "p",
                    " plays pop",
                    "music play pop");
        } else {
            musicCommandHint = new MusicCommandHint("music", "");
            assertHintContent(musicCommandHint,
                    " stop",
                    " stops music",
                    "music stop");

            musicCommandHint = new MusicCommandHint("music ", " ");
            assertHintContent(musicCommandHint,
                    "stop",
                    " stops music",
                    "music stop");

            musicCommandHint = new MusicCommandHint("music s", " s");
            assertHintContent(musicCommandHint,
                    "top",
                    " stops music",
                    "music stop");

            musicCommandHint = new MusicCommandHint("music p", " p");
            assertHintContent(musicCommandHint,
                    "lay",
                    " plays music",
                    "music stop");

            musicCommandHint = new MusicCommandHint("music play po", " play po");
            assertHintContent(musicCommandHint,
                    "p",
                    " plays pop",
                    "music stop ");
        }


        musicCommandHint = new MusicCommandHint("music play", " play");
        assertHintContent(musicCommandHint,
                " pop",
                " plays pop",
                "music play pop");

        musicCommandHint = new MusicCommandHint("music play pop", " play pop");
        assertHintContent(musicCommandHint,
                "",
                " plays pop",
                "music play dance");

        musicCommandHint = new MusicCommandHint("music play dance", " play dance");
        assertHintContent(musicCommandHint,
                "",
                " plays dance tracks",
                "music play classic");

        musicCommandHint = new MusicCommandHint("music play classic", " play classic");
        assertHintContent(musicCommandHint,
                "",
                " plays the classics",
                "music play pop");

        musicCommandHint = new MusicCommandHint("music play s", " play s");
        assertHintContent(musicCommandHint,
                " pop",
                " plays pop",
                "music play pop");


        musicCommandHint = new MusicCommandHint("music stop", " stop");
        assertHintContent(musicCommandHint,
                "",
                " stops music",
                "music stop");

    }
}
