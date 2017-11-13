package seedu.address.logic.commands.hints;

import seedu.address.logic.Autocomplete;
import seedu.address.logic.commands.MusicCommand;

/**
 * Generates hint and tab auto complete for Music command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete Music command.
 */
public class MusicCommandHint extends FixedArgumentsHint {

    private static final String[] ACTION = new String[] {"play", "stop"};
    private static final String[] GENRE = MusicCommand.GENRE_LIST;

    public MusicCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code autoCorrectInput}
     * for Music Command
     */
    private void parse() {

        String[] args = arguments.trim().split("\\s+");

        String actionArgument = args[0];
        if (!isValidFixedArg(actionArgument, ACTION)) {
            //completing an arg?
            String autoCompletedArg = Autocomplete.autocompleteFromList(actionArgument, ACTION);
            if (autoCompletedArg == null || actionArgument.isEmpty()) {
                String autoCorrectHint = (MusicCommand.isMusicPlaying()) ? "stop" : "play";
                offerHint(autoCorrectHint, "music " + autoCorrectHint);
                return;
            } else {
                String autoCorrectInput = "music " + ((!MusicCommand.isMusicPlaying()) ? "play" : "stop");
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
            String autoCompletedArg = Autocomplete.autocompleteFromList(genreArgument, GENRE);
            if (autoCompletedArg == null || genreArgument.isEmpty()) {
                offerHint("pop", "music play pop");
                return;
            } else {
                String autoCompletedInput = (MusicCommand.isMusicPlaying()) ? "music stop " : "music play "
                        + autoCompletedArg;
                handleCompletingArg(genreArgument, autoCompletedArg, autoCompletedInput);
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
