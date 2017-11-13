package seedu.address.logic.commands.hints;

import seedu.address.logic.Autocomplete;
import seedu.address.logic.commands.RadioCommand;

/**
 * Generates hint and tab auto complete for Radio command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete Radio command.
 */
public class RadioCommandHint extends FixedArgumentsHint {

    private static final String[] ACTION = new String[] {"play", "stop"};
    private static final String[] GENRE = RadioCommand.GENRE_LIST;

    public RadioCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code autoCorrectInput}
     * for Radio Command
     */
    private void parse() {
        String[] args = arguments.trim().split("\\s+");

        String actionArgument = args[0];
        if (!isValidFixedArg(actionArgument, ACTION)) {
            //completing an arg?
            String autoCompletedArg = Autocomplete.autocompleteFromList(actionArgument, ACTION);
            if (autoCompletedArg == null || actionArgument.isEmpty()) {
                String autoCorrectHint = (RadioCommand.isRadioPlaying()) ? "stop" : "play";
                offerHint((RadioCommand.isRadioPlaying()) ? "stop" : "play", "radio " + autoCorrectHint);
                return;
            } else {
                handleCompletingArg(actionArgument, autoCompletedArg,
                        "radio " + ((RadioCommand.isRadioPlaying()) ? "stop" : "play"));
                return;
            }
        }

        if (args.length == 1) {
            //radio play|
            if (actionArgument.equals("play")) {
                offerHint("pop", "radio play pop");
            } else {
                //stop doesn't need any more args
                handleFinishedArgs(actionArgument);
            }
            return;
        }

        String genreArgument = args[1];

        if (!isValidFixedArg(genreArgument, GENRE) && actionArgument.equals("play")) {
            //completing an arg?
            String autoCompletedArg = Autocomplete.autocompleteFromList(genreArgument, GENRE);
            if (autoCompletedArg == null || genreArgument.isEmpty()) {
                offerHint("pop", "radio play pop");
                return;
            } else {
                String autoCompletedInput = (RadioCommand.isRadioPlaying()) ? "radio stop" : "radio play "
                        + autoCompletedArg;
                handleCompletingArg(genreArgument, autoCompletedArg, autoCompletedInput);
                return;
            }
        }

        handleNextArg(genreArgument, GENRE, "radio play");
    }

    @Override
    protected String descriptionFromArg(String arg) {
        switch (arg) {
        case "play":
            return " plays radio";
        case "stop":
            return " stops radio";
        case "pop":
            return " plays pop radio";
        case "classic":
            return " plays classic radio";
        case "chinese":
            return " plays chinese radio";
        case "news":
            return " plays news radio";
        default:
            return "";
        }
    }

}
