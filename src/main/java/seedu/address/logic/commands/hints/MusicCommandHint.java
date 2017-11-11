package seedu.address.logic.commands.hints;

import java.util.Arrays;
import java.util.List;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.MusicCommand;
import seedu.address.logic.parser.HintParser;

public class MusicCommandHint extends FixedArgumentsHint {

    public MusicCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
    }

    private final static String[] ACTION = new String[] {"play", "pause", "stop"};
    private final  static String[] GENRE = MusicCommand.genreList;


    @Override
    public void parse() {

        String[] args = arguments.trim().split("\\s+");

        if (args.length == 0) {
            String autoCorrectHint = (MusicCommand.isMusicPlaying()) ? "pause" : "play";
            offerHint(autoCorrectHint, "music " + autoCorrectHint);
            return;
        }

        String actionArgument = args[0];
        if (!isValidFixedArg(actionArgument, ACTION)) {
            //completing an arg?
            String autoCompletedArg = HintParser.autocompleteFromList(actionArgument, ACTION);
            if (autoCompletedArg == null || actionArgument.isEmpty()) {
                String autoCorrectHint = (MusicCommand.isMusicPlaying()) ? "pause" : "play";
                offerHint(autoCorrectHint, "music " + autoCorrectHint);
                return;
            } else {
                String autoCorrectInput = "music " + autoCompletedArg;
                if (!MusicCommand.isMusicPlaying()) {
                    autoCorrectInput = "music play";
                } else {
                    if (autoCompletedArg.equals("play")) {
                        autoCorrectInput = "music pause";
                    }
                }
                handleCompletingArg(actionArgument, autoCompletedArg, autoCorrectInput);
                return;
            }
        }

        if (args.length == 1) {
            //music play|
            if (actionArgument.equals("play")) {
                offerHint("pop", "music play pop");
            } else {
                //pause and stop don't need any more args
               handleFinishedArgs(actionArgument);
            }
            return;
        }

        String genreArgument = args[1];

        if (!isValidFixedArg(genreArgument, GENRE) && actionArgument.equals("play")) {
            //completing an arg?
            String autoCompletedArg = HintParser.autocompleteFromList(genreArgument, GENRE);
            if (autoCompletedArg == null || genreArgument.isEmpty()) {
                offerHint("pop", "music play pop");
                return;
            } else {
                handleCompletingArg(genreArgument, autoCompletedArg, (MusicCommand.isMusicPlaying()) ? "music pause" : "music play " + autoCompletedArg);
                return;
            }
        }

        //music play|
        handleNextArg(genreArgument, GENRE, "music play");
    }

    @Override
    protected String descriptionFromArg(String arg) {
        switch (arg) {
        case "play":
            return " plays music";
        case "pause":
            return " pauses music";
        case "stop":
            return " stops music";
        case "pop":
            return " plays pop";
        case "classic":
            return " plays the classics";
        case "dance":
            return " plays dance tracks";
        default:
            return "";
        }
    }

}
